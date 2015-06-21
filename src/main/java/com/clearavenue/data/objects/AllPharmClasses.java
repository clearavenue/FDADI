package com.clearavenue.data.objects;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class AllPharmClasses {

	@Id
	private ObjectId id;
	private final List<String> pharmClassNames = new ArrayList<String>();

	public AllPharmClasses() {
	}

	public List<String> getPharmClassNames() {
		return pharmClassNames;
	}
}
