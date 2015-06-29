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
 * The Class AllPharmClasses.
 */
@Entity
public class AllPharmClasses {

	/** The id. */
	@Id
	private ObjectId id;

	/** The pharm class names. */
	private final List<String> pharmClassNames = new ArrayList<String>();

	/**
	 * Instantiates a new all pharm classes.
	 */
	public AllPharmClasses() {
	}

	/**
	 * Gets the pharm class names.
	 *
	 * @return the pharm class names
	 */
	public final List<String> getPharmClassNames() {
		return pharmClassNames;
	}
}
