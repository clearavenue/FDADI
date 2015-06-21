package com.clearavenue.fdadi.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

import com.clearavenue.data.AllMedicationsDAO;
import com.clearavenue.data.DBUtils;
import com.clearavenue.data.MongoDB;
import com.clearavenue.data.objects.AllMedications;
import com.clearavenue.data.objects.UserMedication;
import com.clearavenue.data.objects.UserProfile;

public class DBMedicationsTest {

	private static final Datastore mongo = MongoDB.instance().getDatabase();

	private static final UserProfile testUser = new UserProfile("testuser", "testpwd");

	@Before
	public void init() {
		mongo.findAndDelete(mongo.createQuery(UserProfile.class).field("userId").equal(testUser.getUserId()));
		mongo.save(testUser);
	}

	@Test
	public void addMedicationTest() {
		UserMedication med = new UserMedication("tylenol");

		UserProfile user = DBUtils.findUserProfile(testUser.getUserId());
		DBUtils.addUserMedication(user, med);

		UserProfile updatedUser = DBUtils.findUserProfile(testUser.getUserId());
		assertNotNull(updatedUser);
		assertEquals(testUser.getUserId(), updatedUser.getUserId());
		assertEquals(testUser.getPassword(), updatedUser.getPassword());

		assertEquals(1, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().contains(med));
	}

	@Test
	public void addMedication2Test() {
		UserMedication med1 = new UserMedication("tylenol");
		UserMedication med2 = new UserMedication("alcohol");

		UserProfile user = DBUtils.findUserProfile(testUser.getUserId());
		DBUtils.addUserMedication(user, med1, med2);

		UserProfile updatedUser = DBUtils.findUserProfile(testUser.getUserId());
		assertNotNull(updatedUser);
		assertEquals(testUser.getUserId(), updatedUser.getUserId());
		assertEquals(testUser.getPassword(), updatedUser.getPassword());

		assertEquals(2, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().contains(med1));
		assertTrue(updatedUser.getMedications().contains(med2));
	}

	@Test
	public void delMedicationTest() {
		UserMedication med1 = new UserMedication("tylenol");

		UserProfile user = DBUtils.findUserProfile(testUser.getUserId());
		DBUtils.addUserMedication(user, med1);

		UserProfile updatedUser = DBUtils.findUserProfile(testUser.getUserId());
		assertNotNull(updatedUser);
		assertEquals(testUser.getUserId(), updatedUser.getUserId());
		assertEquals(testUser.getPassword(), updatedUser.getPassword());

		assertEquals(1, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().contains(med1));

		DBUtils.deleteUserMedication(user, med1);

		updatedUser = DBUtils.findUserProfile(testUser.getUserId());
		assertNotNull(updatedUser);
		assertEquals(testUser.getUserId(), updatedUser.getUserId());
		assertEquals(testUser.getPassword(), updatedUser.getPassword());

		assertEquals(0, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().isEmpty());
	}

	@Test
	public void delMedication2Test() {
		UserMedication med1 = new UserMedication("tylenol");
		UserMedication med2 = new UserMedication("alcohol");

		UserProfile user = DBUtils.findUserProfile(testUser.getUserId());
		DBUtils.addUserMedication(user, med1, med2);

		UserProfile updatedUser = DBUtils.findUserProfile(testUser.getUserId());
		assertNotNull(updatedUser);
		assertEquals(testUser.getUserId(), updatedUser.getUserId());
		assertEquals(testUser.getPassword(), updatedUser.getPassword());

		assertEquals(2, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().contains(med1));
		assertTrue(updatedUser.getMedications().contains(med2));

		DBUtils.deleteUserMedication(user, med1);

		updatedUser = DBUtils.findUserProfile(testUser.getUserId());
		assertNotNull(updatedUser);
		assertEquals(testUser.getUserId(), updatedUser.getUserId());
		assertEquals(testUser.getPassword(), updatedUser.getPassword());

		assertEquals(1, updatedUser.getMedications().size());
		assertTrue(updatedUser.getMedications().contains(med2));
	}

	@Test
	public void getAllMedications() {
		List<String> meds = new ArrayList<String>();
		meds.add("tylenol");
		meds.add("alcohol");
		meds.add("advil");
		meds.add("find");

		AllMedicationsDAO dao = new AllMedicationsDAO(mongo);
		AllMedications actual = dao.find().asList().get(0);
		assertTrue(actual.getMedicationNames().containsAll(meds));
	}

	@AfterClass
	public static void removeTestUser() {
		mongo.findAndDelete(mongo.createQuery(UserProfile.class).field("userId").equal(testUser.getUserId()));
	}

}
