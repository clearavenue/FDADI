package com.clearavenue.fdadi.data;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mongodb.morphia.Datastore;

import com.clearavenue.data.AllMedicationsDAO;
import com.clearavenue.data.MongoDB;

public class AllMedicationsDAOTest {

	private static final Datastore mongo = MongoDB.instance().getDatabase();

	@Test
	public void getAllMedications() {
		List<String> meds = new ArrayList<String>();
		meds.add("tylenol");
		meds.add("alcohol");
		meds.add("advil");

		AllMedicationsDAO dao = new AllMedicationsDAO(mongo);
		List<String> actual = dao.findAll();
		assertTrue(actual.containsAll(meds));
	}

}
