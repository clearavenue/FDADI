package com.clearavenue.fdadi.data;

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

public class UserProfileDAOMedicationTest {

	private static final Datastore mongo = MongoDB.instance().getDatabase();
	private static final UserProfileDAO dao = new UserProfileDAO(mongo);

	private static final String testUserId = "testuser";
	private static final String testUserPwd = "testpwd";

	@Before
	public void init() {
		mongo.findAndDelete(mongo.createQuery(UserProfile.class).field("userId").equal(testUserId));
		mongo.save(new UserProfile(testUserId, testUserPwd));
	}

	@Test
	public void addMedicationTest() {
		UserMedication med = new UserMedication("tylenol");

		UserProfile user = dao.findByUserId(testUserId);
		dao.addUserMedication(user, med);

		UserProfile updatedUser = dao.findByUserId(testUserId);
		assertNotNull(updatedUser);
		assertEquals(testUserId, updatedUser.getUserId());
		assertEquals(testUserPwd, updatedUser.getPassword());

		assertEquals(1, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().contains(med));
	}

	@Test
	public void addMedication2Test() {
		UserMedication med1 = new UserMedication("tylenol");
		UserMedication med2 = new UserMedication("alcohol");

		UserProfile user = dao.findByUserId(testUserId);
		dao.addUserMedication(user, med1, med2);

		UserProfile updatedUser = dao.findByUserId(testUserId);
		assertNotNull(updatedUser);
		assertEquals(testUserId, updatedUser.getUserId());
		assertEquals(testUserPwd, updatedUser.getPassword());

		assertEquals(2, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().contains(med1));
		assertTrue(updatedUser.getMedications().contains(med2));
	}

	@Test
	public void delMedicationTest() {
		UserMedication med1 = new UserMedication("tylenol");

		UserProfile user = dao.findByUserId(testUserId);
		dao.addUserMedication(user, med1);

		UserProfile updatedUser = dao.findByUserId(testUserId);
		assertNotNull(updatedUser);
		assertEquals(testUserId, updatedUser.getUserId());
		assertEquals(testUserPwd, updatedUser.getPassword());

		assertEquals(1, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().contains(med1));

		dao.deleteUserMedication(user, med1);

		updatedUser = dao.findByUserId(testUserId);
		assertNotNull(updatedUser);
		assertEquals(testUserId, updatedUser.getUserId());
		assertEquals(testUserPwd, updatedUser.getPassword());

		assertEquals(0, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().isEmpty());
	}

	@Test
	public void delMedication2Test() {
		UserMedication med1 = new UserMedication("tylenol");
		UserMedication med2 = new UserMedication("alcohol");

		UserProfile user = dao.findByUserId(testUserId);
		dao.addUserMedication(user, med1, med2);

		UserProfile updatedUser = dao.findByUserId(testUserId);
		assertNotNull(updatedUser);
		assertEquals(testUserId, updatedUser.getUserId());
		assertEquals(testUserPwd, updatedUser.getPassword());

		assertEquals(2, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().contains(med1));
		assertTrue(updatedUser.getMedications().contains(med2));

		dao.deleteUserMedication(user, med1);

		updatedUser = dao.findByUserId(testUserId);
		assertNotNull(updatedUser);
		assertEquals(testUserId, updatedUser.getUserId());
		assertEquals(testUserPwd, updatedUser.getPassword());

		assertEquals(1, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().contains(med2));
	}

	@AfterClass
	public static void removeTestUser() {
		mongo.findAndDelete(mongo.createQuery(UserProfile.class).field("userId").equal(testUserId));
	}
}
