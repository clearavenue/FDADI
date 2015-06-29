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

import com.clearavenue.data.AllPharmClassesDAO;
import com.clearavenue.data.MongoDB;
import com.clearavenue.data.objects.AllPharmClasses;

/**
 * The Class AllPharmClassesDAOTest.
 */
public class AllPharmClassesDAOTest {

	/** The Constant MONGO. */
	private static final Datastore MONGO = MongoDB.instance().getDatabase();

	/** The Constant DAO. */
	private static final AllPharmClassesDAO DAO = new AllPharmClassesDAO(MONGO);

	/**
	 * Gets the all pharm classes.
	 */
	@Test
	public final void getAllPharmClasses() {
		final List<String> pharm = new ArrayList<String>();
		pharm.add("Anti-epileptic Agent [EPC]");
		pharm.add("Aminosalicylate [EPC]");
		pharm.add("B Lymphocyte Stimulator-specific Inhibitor [EPC]");

		final List<String> actual = DAO.findAll();
		assertTrue(actual.containsAll(pharm));
	}

	/**
	 * Gets the all pharm classes not found.
	 */
	@Test
	public final void getAllPharmClassesNotFound() {
		final List<String> pharm = new ArrayList<String>();
		pharm.add("Anti-epileptic Agent [EPC]");
		pharm.add("Aminosalicylate [EPC]");
		pharm.add("B Lymphocyte Stimulator-specific Inhibitor [EPC]");
		pharm.add("notfound");

		final List<String> actual = DAO.findAll();
		assertFalse(actual.containsAll(pharm));
	}

	/**
	 * Adds the all pharm class.
	 */
	@Test
	public final void addAllPharmClass() {
		MONGO.delete(MONGO.createQuery(AllPharmClasses.class));
		DAO.initCollection();
	}
}
