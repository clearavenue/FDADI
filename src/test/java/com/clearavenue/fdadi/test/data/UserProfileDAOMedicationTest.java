/*
 *
 */
package com.clearavenue.fdadi.test.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

import com.clearavenue.data.MongoDB;
import com.clearavenue.data.UserProfileDAO;
import com.clearavenue.data.objects.UserMedication;
import com.clearavenue.data.objects.UserProfile;

/**
 * The Class UserProfileDAOMedicationTest.
 */
public class UserProfileDAOMedicationTest {

	/** The Constant mongo. */
	private static final Datastore MONGO = MongoDB.instance().getDatabase();

	/** The Constant dao. */
	private static final UserProfileDAO DAO = new UserProfileDAO(MONGO);

	/** The Constant testUserId. */
	private static final String TEST_USERID = "testuser";

	/** The Constant testUserPwd. */
	private static final String TEST_USERPWD = "testpwd";

	/**
	 * Inits the.
	 */
	@Before
	public final void init() {
		MONGO.findAndDelete(MONGO.createQuery(UserProfile.class).field("userId").equal(TEST_USERID));
		MONGO.save(new UserProfile(TEST_USERID, TEST_USERPWD));
	}

	/**
	 * Adds the medication test.
	 */
	@Test
	public final void addMedicationTest() {
		final UserMedication med = new UserMedication("tylenol");

		final UserProfile user = DAO.findByUserId(TEST_USERID);
		DAO.addUserMedication(user, med);

		final UserProfile updatedUser = DAO.findByUserId(TEST_USERID);
		assertNotNull(updatedUser);
		assertEquals(TEST_USERID, updatedUser.getUserId());
		assertEquals(TEST_USERPWD, updatedUser.getPassword());

		assertEquals(1, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().contains(med));
	}

	/**
	 * Adds the medication2 test.
	 */
	@Test
	public final void addMedication2Test() {
		final UserMedication med1 = new UserMedication("tylenol");
		final UserMedication med2 = new UserMedication("alcohol");

		final UserProfile user = DAO.findByUserId(TEST_USERID);
		DAO.addUserMedication(user, med1, med2);

		final UserProfile updatedUser = DAO.findByUserId(TEST_USERID);
		assertNotNull(updatedUser);
		assertEquals(TEST_USERID, updatedUser.getUserId());
		assertEquals(TEST_USERPWD, updatedUser.getPassword());

		assertEquals(2, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().contains(med1));
		assertTrue(updatedUser.getMedications().contains(med2));
	}

	/**
	 * Del medication test.
	 */
	@Test
	public final void delMedicationTest() {
		final UserMedication med1 = new UserMedication("tylenol");

		final UserProfile user = DAO.findByUserId(TEST_USERID);
		DAO.addUserMedication(user, med1);

		UserProfile updatedUser = DAO.findByUserId(TEST_USERID);
		assertNotNull(updatedUser);
		assertEquals(TEST_USERID, updatedUser.getUserId());
		assertEquals(TEST_USERPWD, updatedUser.getPassword());

		assertEquals(1, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().contains(med1));

		DAO.deleteUserMedication(user, med1);

		updatedUser = DAO.findByUserId(TEST_USERID);
		assertNotNull(updatedUser);
		assertEquals(TEST_USERID, updatedUser.getUserId());
		assertEquals(TEST_USERPWD, updatedUser.getPassword());

		assertEquals(0, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().isEmpty());
	}

	/**
	 * Del medication2 test.
	 */
	@Test
	public final void delMedication2Test() {
		final UserMedication med1 = new UserMedication("tylenol");
		final UserMedication med2 = new UserMedication("alcohol");

		final UserProfile user = DAO.findByUserId(TEST_USERID);
		DAO.addUserMedication(user, med1, med2);

		UserProfile updatedUser = DAO.findByUserId(TEST_USERID);
		assertNotNull(updatedUser);
		assertEquals(TEST_USERID, updatedUser.getUserId());
		assertEquals(TEST_USERPWD, updatedUser.getPassword());

		assertEquals(2, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().contains(med1));
		assertTrue(updatedUser.getMedications().contains(med2));

		DAO.deleteUserMedication(user, med1);

		updatedUser = DAO.findByUserId(TEST_USERID);
		assertNotNull(updatedUser);
		assertEquals(TEST_USERID, updatedUser.getUserId());
		assertEquals(TEST_USERPWD, updatedUser.getPassword());

		assertEquals(1, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().contains(med2));
	}

	/**
	 * Removes the test user.
	 */
	@AfterClass
	public static final void removeTestUser() {
		MONGO.findAndDelete(MONGO.createQuery(UserProfile.class).field("userId").equal(TEST_USERID));
	}
}
