package com.clearavenue.fdadi.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

import com.clearavenue.data.DBUtils;
import com.clearavenue.data.MongoDB;
import com.clearavenue.data.objects.UserProfile;

public class DBUtilsTest {

	private final Datastore mongo = MongoDB.instance().getDatabase();

	UserProfile testUser = new UserProfile("testuser", "testpwd");

	@Before
	public void init() {
		mongo.findAndDelete(mongo.createQuery(UserProfile.class).field("userId").equal(testUser.getUserId()));

	}

	@Test
	public void addUserTest() {
		UserProfile user = mongo.find(UserProfile.class).filter("userId", testUser.getUserId()).get();
		assertNull(user);

		DBUtils.addUserProfile(testUser.getUserId(), testUser.getPassword());

		user = mongo.find(UserProfile.class).filter("userId", testUser.getUserId()).get();
		assertNotNull(user);
		assertEquals(testUser.getUserId(), user.getUserId());
		assertEquals(testUser.getPassword(), user.getPassword());
	}

	@Test
	public void addUserNoIdTest() {
		UserProfile user = mongo.find(UserProfile.class).filter("userId", testUser.getUserId()).get();
		assertNull(user);

		DBUtils.addUserProfile(null, testUser.getPassword());

		user = mongo.find(UserProfile.class).filter("userId", testUser.getUserId()).get();
		assertNull(user);
	}

	@Test
	public void addUserNoPwdTest() {
		UserProfile user = mongo.find(UserProfile.class).filter("userId", testUser.getUserId()).get();
		assertNull(user);

		DBUtils.addUserProfile(testUser.getUserId(), null);

		user = mongo.find(UserProfile.class).filter("userId", testUser.getUserId()).get();
		assertNull(user);
	}

}
