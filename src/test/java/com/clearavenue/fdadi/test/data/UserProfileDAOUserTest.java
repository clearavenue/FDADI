/*
 *
 */
package com.clearavenue.fdadi.test.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

import com.clearavenue.data.MongoDB;
import com.clearavenue.data.UserProfileDAO;
import com.clearavenue.data.objects.UserProfile;

/**
 * The Class UserProfileDAOUserTest.
 */
public class UserProfileDAOUserTest {

	/** The Constant mongo. */
	private static final Datastore MONGO = MongoDB.instance().getDatabase();

	/** The Constant dao. */
	private static final UserProfileDAO DAO = new UserProfileDAO(MONGO);

	/** The test user id. */
	private final String testUserId = "testuser";

	/** The test user pwd. */
	private final String testUserPwd = "testpwd";

	/**
	 * Inits the.
	 */
	@Before
	public final void init() {
		MONGO.findAndDelete(MONGO.createQuery(UserProfile.class).field("userId").equal(testUserId));
	}

	/**
	 * Adds the user test.
	 */
	@Test
	public final void addUserTest() {
		UserProfile user = DAO.findByUserId(testUserId);
		assertNull(user);

		final UserProfile newuser = new UserProfile(testUserId, testUserPwd);
		DAO.save(newuser);

		user = DAO.findByUserId(testUserId);
		assertNotNull(user);
		assertEquals(testUserId, user.getUserId());
		assertEquals(testUserPwd, user.getPassword());
	}

	/**
	 * Adds the user no id test.
	 */
	@Test
	public final void addUserNoIdTest() {
		UserProfile user = DAO.findByUserId(testUserId);
		assertNull(user);

		final UserProfile newuser = new UserProfile(null, testUserPwd);
		DAO.save(newuser);

		user = DAO.findByUserId(testUserId);
		assertNull(user);
	}

	/**
	 * Adds the user no pwd test.
	 */
	@Test
	public final void addUserNoPwdTest() {
		UserProfile user = DAO.findByUserId(testUserId);
		assertNull(user);

		final UserProfile newuser = new UserProfile(testUserId, null);
		DAO.save(newuser);

		user = DAO.findByUserId(testUserId);
		assertNull(user);
	}

	/**
	 * Find by user id no exist test.
	 */
	@Test
	public final void findByUserIdNoExistTest() {
		final UserProfile user = DAO.findByUserId(testUserId);
		assertNull(user);
	}
}
