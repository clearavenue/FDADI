package com.clearavenue.fdadi.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mongodb.morphia.Datastore;

import com.clearavenue.data.AllMedicationsDAO;
import com.clearavenue.data.MongoDB;
import com.clearavenue.data.objects.AllMedications;

public class AllMedicationsDAOTest {

	private static final Datastore mongo = MongoDB.instance().getDatabase();
	private static final AllMedicationsDAO dao = new AllMedicationsDAO(mongo);

	public void addAllMedications() {
		List<String> meds = new ArrayList<String>();
		meds.add("Tylenol");
		meds.add("Morphine");
		meds.add("Advil");

		AllMedications all = new AllMedications();
		all.getMedicationNames().addAll(meds);
		dao.save(all);
	}

	@Test
	public void getAllMedications() {
		List<String> meds = new ArrayList<String>();
		meds.add("Tylenol");
		meds.add("Morphine");
		meds.add("Advil");

		List<String> actual = dao.findAll();
		assertTrue(actual.containsAll(meds));
	}

	@Test
	public void getNotFoundMedications() {
		List<String> meds = new ArrayList<String>();
		meds.add("Tylenol");
		meds.add("Morphine");
		meds.add("Advil");
		meds.add("notfound");

		List<String> actual = dao.findAll();
		assertFalse(actual.containsAll(meds));
	}

}
