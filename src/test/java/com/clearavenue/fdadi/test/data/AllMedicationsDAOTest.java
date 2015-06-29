/*
 *
 */
package com.clearavenue.fdadi.test.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mongodb.morphia.Datastore;

import com.clearavenue.data.AllMedicationsDAO;
import com.clearavenue.data.MongoDB;
import com.clearavenue.data.objects.AllMedications;

/**
 * The Class AllMedicationsDAOTest.
 */
public class AllMedicationsDAOTest {

	/** The Constant mongo. */
	private static final Datastore MONGO = MongoDB.instance().getDatabase();

	/** The Constant dao. */
	private static final AllMedicationsDAO DAO = new AllMedicationsDAO(MONGO);

	/**
	 * Gets the all medications.
	 */
	@Test
	public final void getAllMedications() {
		final List<String> meds = new ArrayList<String>();
		meds.add("BENZOCAINE");
		meds.add("ABILIFY");
		meds.add("Ibuprofen");

		final List<String> actual = DAO.findAll();
		assertTrue(actual.containsAll(meds));
	}

	/**
	 * Gets the not found medications.
	 */
	@Test
	public final void getNotFoundMedications() {
		final List<String> meds = new ArrayList<String>();
		meds.add("BENZOCAINE");
		meds.add("ABILIFY");
		meds.add("Ibuprofen");
		meds.add("notfound");

		final List<String> actual = DAO.findAll();
		assertFalse(actual.containsAll(meds));
	}

	/**
	 * Adds the all medications.
	 */
	@Test
	public final void addAllMedications() {
		MONGO.delete(MONGO.createQuery(AllMedications.class));
		DAO.initCollection();
	}
}
