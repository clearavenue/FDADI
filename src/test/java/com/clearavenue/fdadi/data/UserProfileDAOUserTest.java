package com.clearavenue.fdadi.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

import com.clearavenue.data.MongoDB;
import com.clearavenue.data.UserProfileDAO;
import com.clearavenue.data.objects.UserProfile;

public class UserProfileDAOUserTest {

	private static final Datastore mongo = MongoDB.instance().getDatabase();
	private static final UserProfileDAO dao = new UserProfileDAO(mongo);

	private final String testUserId = "testuser";
	private final String testUserPwd = "testpwd";

	@Before
	public void init() {
		mongo.findAndDelete(mongo.createQuery(UserProfile.class).field("userId").equal(testUserId));
	}

	@Test
	public void addUserTest() {
		UserProfile user = dao.findByUserId(testUserId);
		assertNull(user);

		UserProfile newuser = new UserProfile(testUserId, testUserPwd);
		dao.save(newuser);

		user = dao.findByUserId(testUserId);
		assertNotNull(user);
		assertEquals(testUserId, user.getUserId());
		assertEquals(testUserPwd, user.getPassword());
	}

	@Test
	public void addUserNoIdTest() {
		UserProfile user = dao.findByUserId(testUserId);
		assertNull(user);

		UserProfile newuser = new UserProfile(null, testUserPwd);
		dao.save(newuser);

		user = dao.findByUserId(testUserId);
		assertNull(user);
	}

	@Test
	public void addUserNoPwdTest() {
		UserProfile user = dao.findByUserId(testUserId);
		assertNull(user);

		UserProfile newuser = new UserProfile(testUserId, null);
		dao.save(newuser);

		user = dao.findByUserId(testUserId);
		assertNull(user);
	}
}
