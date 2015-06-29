/*
 *
 */
package com.clearavenue.fdadi.test;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.clearavenue.data.MongoDB;
import com.clearavenue.data.UserProfileDAO;
import com.clearavenue.data.objects.UserProfile;
import com.clearavenue.fdadi.FDADIController;
import com.clearavenue.fdadi.api.RecallEvent;

/**
 * The Class FDADIControllerTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring-dispatcher-servlet.xml")
@WebAppConfiguration
public class FDADIControllerTest {

	/** The mock mvc. */
	private MockMvc mockMvc;

	/** The session. */
	@Autowired
	private MockHttpSession session;

	/** The Constant mongo. */
	private static final Datastore MONGO = MongoDB.instance().getDatabase();

	/** The test user id. */
	private final String testUserId = "registrationtestuser";

	/** The test user pwd. */
	private final String testUserPwd = "newpwd";

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public final void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		final FDADIController instance = new FDADIController();

		final InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/jsp/");
		resolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(instance).setViewResolvers(resolver).build();

		MONGO.findAndDelete(MONGO.createQuery(UserProfile.class).field("userId").equal(testUserId));
	}

	/**
	 * Logout test.
	 */
	@Test
	public final void logoutTest() {

		session.setAttribute("username", "testUsername");
		final MockHttpServletRequestBuilder getRequest = get("/logout").session(session);

		try {
			final ResultActions results = mockMvc.perform(getRequest);

			results.andExpect(status().is3xxRedirection());
			results.andExpect(view().name("redirect:/"));
			results.andExpect(request().sessionAttribute("username", Matchers.nullValue()));
		} catch (final Exception e) {
			fail("Exception: " + e.getMessage());
		}
	}

	/**
	 * Login test.
	 */
	@Test
	public final void loginTest() {
		final UserProfileDAO dao = new UserProfileDAO(MongoDB.instance().getDatabase());
		dao.save(new UserProfile(testUserId, testUserPwd));

		session.removeAttribute("username");
		final MockHttpServletRequestBuilder request = post("/processLogin").session(session).param("username", testUserId).param("pwd", testUserPwd);
		try {
			final ResultActions results = mockMvc.perform(request);
			results.andExpect(request().sessionAttribute("username", Matchers.equalTo(testUserId)));
			results.andExpect(MockMvcResultMatchers.redirectedUrl("/"));
		} catch (final Exception e) {
			fail("Exception: " + e.getMessage());
		}
	}

	/**
	 * Bad login test.
	 */
	@Test
	public final void badLoginTest() {
		MONGO.findAndDelete(MONGO.createQuery(UserProfile.class).field("userId").equal(testUserId));
		session.removeAttribute("username");
		final MockHttpServletRequestBuilder request = post("/processLogin").session(session).param("username", testUserId).param("pwd", "TerriblePassword");
		try {
			final ResultActions results = mockMvc.perform(request);
			results.andExpect(MockMvcResultMatchers.redirectedUrl("/login?loginError"));
			results.andExpect(request().sessionAttribute("username", Matchers.nullValue()));
		} catch (final Exception e) {
			fail("Exception: " + e.getMessage());
		}
	}

	/**
	 * Redirect from index if logged out test.
	 */
	@Test
	public final void redirectFromIndexIfLoggedOutTest() {
		session.removeAttribute("username");
		final MockHttpServletRequestBuilder request = get("/").session(session);
		try {
			final ResultActions results = mockMvc.perform(request);
			results.andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
		} catch (final Exception e) {
			fail("Exception: " + e.getMessage());
		}
	}

	/**
	 * Redirect from add med by name if logged out test.
	 */
	@Test
	public final void redirectFromAddMedByNameIfLoggedOutTest() {
		session.removeAttribute("username");
		final MockHttpServletRequestBuilder request = get("/addMedByName").session(session);
		try {
			final ResultActions results = mockMvc.perform(request);
			results.andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
		} catch (final Exception e) {
			fail("Exception: " + e.getMessage());
		}
	}

	/**
	 * Redirect from add med by pharm class if logged out test.
	 */
	@Test
	public final void redirectFromAddMedByPharmClassIfLoggedOutTest() {
		session.removeAttribute("username");
		final MockHttpServletRequestBuilder request = get("/addMedByPharmClass").session(session);
		try {
			final ResultActions results = mockMvc.perform(request);
			results.andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
		} catch (final Exception e) {
			fail("Exception: " + e.getMessage());
		}
	}

	/**
	 * Redirect from med details if logged out test.
	 */
	@Test
	public final void redirectFromMedDetailsIfLoggedOutTest() {
		session.removeAttribute("username");
		final MockHttpServletRequestBuilder request = post("/medDetails").session(session);
		try {
			final ResultActions results = mockMvc.perform(request);
			results.andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
		} catch (final Exception e) {
			fail("Exception: " + e.getMessage());
		}
	}

	/**
	 * Register test.
	 */
	@Test
	public final void registerTest() {
		MONGO.findAndDelete(MONGO.createQuery(UserProfile.class).field("userId").equal(testUserId));
		session.removeAttribute("username");
		final MockHttpServletRequestBuilder request = post("/register").session(session).param("username", testUserId).param("pwd", testUserPwd);
		try {
			final ResultActions results = mockMvc.perform(request);
			results.andExpect(MockMvcResultMatchers.redirectedUrl("/"));
			results.andExpect(request().sessionAttribute("username", Matchers.equalTo(testUserId)));
		} catch (final Exception e) {
			fail("Exception: " + e.getMessage());
		}
	}

	/**
	 * Recall test.
	 */
	@Test
	public final void recallTest() {
		final MockHttpServletRequestBuilder request = post("/recalls").session(session).param("medlist", "Abilify---,---Ibuprofen---,---");
		try {

			final ResultActions results = mockMvc.perform(request);
			results.andExpect(model().attribute("recallList", Matchers.notNullValue()));
			results.andExpect(model().attribute("recallList", Matchers.instanceOf(List.class)));
			results.andExpect(model().attribute("recallList", Matchers.hasItem(Matchers.instanceOf(List.class))));
			results.andExpect(model().attribute("recallList", Matchers.hasItem(Matchers.hasItem(Matchers.instanceOf(RecallEvent.class)))));

		} catch (final Exception e) {
			fail("Exception: " + e.getMessage());
		}
	}

}