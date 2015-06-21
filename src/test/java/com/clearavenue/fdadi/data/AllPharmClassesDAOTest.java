package com.clearavenue.fdadi.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mongodb.morphia.Datastore;

import com.clearavenue.data.AllPharmClassesDAO;
import com.clearavenue.data.MongoDB;

public class AllPharmClassesDAOTest {

	private static final Datastore mongo = MongoDB.instance().getDatabase();
	private static final AllPharmClassesDAO dao = new AllPharmClassesDAO(mongo);

	@Test
	public void getAllPharmClasses() {
		List<String> pharm = new ArrayList<String>();
		pharm.add("steroids");
		pharm.add("antibiotics");
		pharm.add("diabetic");

		List<String> actual = dao.findAll();
		assertTrue(actual.containsAll(pharm));
	}

	@Test
	public void getAllPharmClassesNotFound() {
		List<String> pharm = new ArrayList<String>();
		pharm.add("steroids");
		pharm.add("antibiotics");
		pharm.add("diabetic");
		pharm.add("notfound");

		List<String> actual = dao.findAll();
		assertFalse(actual.containsAll(pharm));
	}
}
