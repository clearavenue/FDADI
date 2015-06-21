package com.clearavenue.fdadi.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mongodb.morphia.Datastore;

import com.clearavenue.data.AllMedicationsDAO;
import com.clearavenue.data.MongoDB;

public class AllMedicationsDAOTest {

	private static final Datastore mongo = MongoDB.instance().getDatabase();
	private static final AllMedicationsDAO dao = new AllMedicationsDAO(mongo);

	@Test
	public void getAllMedications() {
		List<String> meds = new ArrayList<String>();
		meds.add("tylenol");
		meds.add("alcohol");
		meds.add("advil");

		List<String> actual = dao.findAll();
		assertTrue(actual.containsAll(meds));
	}

	@Test
	public void getNotFoundMedications() {
		List<String> meds = new ArrayList<String>();
		meds.add("tylenol");
		meds.add("alcohol");
		meds.add("advil");
		meds.add("notfound");

		List<String> actual = dao.findAll();
		assertFalse(actual.containsAll(meds));
	}

}
