/*
 *
 */
package com.clearavenue.data.objects;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * The Class AllMedications.
 */
@Entity
public class AllMedications {

	/** The id. */
	@Id
	private ObjectId id;

	/** The medication names. */
	private final List<String> medicationNames;

	/**
	 * Instantiates a new all medications.
	 */
	public AllMedications() {
		medicationNames = new ArrayList<String>();
	}

	/**
	 * Gets the medication names.
	 *
	 * @return the medication names
	 */
	public final List<String> getMedicationNames() {
		return medicationNames;
	}
}
