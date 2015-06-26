package com.clearavenue.fdadi;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

import com.clearavenue.data.MongoDB;
import com.clearavenue.data.objects.UserProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring-dispatcher-servlet.xml")
@WebAppConfiguration
public class FDADIControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private FDADIController controller;
	@Autowired
	MockHttpSession session;

	private static final Datastore mongo = MongoDB.instance().getDatabase();
	private final String testUserId = "registrationtestuser";
	private final String testUserPwd = "newpwd";

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		final FDADIController instance = new FDADIController();

		mockMvc = MockMvcBuilders.standaloneSetup(instance).build();

		mongo.findAndDelete(mongo.createQuery(UserProfile.class).field("userId").equal(testUserId));
	}

	@Test
	public void logoutTest() {

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

	@Test
	public void loginTest() {
		session.removeAttribute("username");
		final MockHttpServletRequestBuilder request = post("/processLogin").session(session).param("username", "bill").param("pwd", "hunt");
		try {
			final ResultActions results = mockMvc.perform(request);
			results.andExpect(request().sessionAttribute("username", Matchers.equalTo("bill")));
			results.andExpect(MockMvcResultMatchers.redirectedUrl("/"));
		} catch (final Exception e) {
			fail("Exception: " + e.getMessage());
		}
	}

	@Test
	public void badLoginTest() {
		mongo.findAndDelete(mongo.createQuery(UserProfile.class).field("userId").equal(testUserId));
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

	@Test
	public void redirectFromIndexIfLoggedOutTest() {
		session.removeAttribute("username");
		final MockHttpServletRequestBuilder request = get("/").session(session);
		try {
			final ResultActions results = mockMvc.perform(request);
			results.andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
		} catch (final Exception e) {
			fail("Exception: " + e.getMessage());
		}
	}

	@Test
	public void redirectFromAddMedByNameIfLoggedOutTest() {
		session.removeAttribute("username");
		final MockHttpServletRequestBuilder request = get("/addMedByName").session(session);
		try {
			final ResultActions results = mockMvc.perform(request);
			results.andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
		} catch (final Exception e) {
			fail("Exception: " + e.getMessage());
		}
	}

	@Test
	public void redirectFromAddMedByPharmClassIfLoggedOutTest() {
		session.removeAttribute("username");
		final MockHttpServletRequestBuilder request = get("/addMedByPharmClass").session(session);
		try {
			final ResultActions results = mockMvc.perform(request);
			results.andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
		} catch (final Exception e) {
			fail("Exception: " + e.getMessage());
		}
	}

	@Test
	public void redirectFromMedDetailsIfLoggedOutTest() {
		session.removeAttribute("username");
		final MockHttpServletRequestBuilder request = post("/medDetails").session(session);
		try {
			final ResultActions results = mockMvc.perform(request);
			results.andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
		} catch (final Exception e) {
			fail("Exception: " + e.getMessage());
		}
	}

	@Test
	public void registerTest() {
		mongo.findAndDelete(mongo.createQuery(UserProfile.class).field("userId").equal(testUserId));
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

}