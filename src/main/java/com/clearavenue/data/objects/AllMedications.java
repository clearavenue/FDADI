package com.clearavenue.data.objects;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class AllMedications {

	@Id
	private ObjectId id;
	private final List<String> medicationNames;

	public AllMedications() {
		medicationNames = new ArrayList<String>();
	}

	public List<String> getMedicationNames() {
		return medicationNames;
	}
}
