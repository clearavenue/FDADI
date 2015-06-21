package com.clearavenue.data;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import com.clearavenue.data.objects.AllPharmClasses;

public class AllPharmClassesDAO extends BasicDAO<AllPharmClasses, String> {

	public AllPharmClassesDAO(Datastore ds) {
		super(ds);
	}

	public List<String> findAll() {
		return find().asList().get(0).getPharmClassNames();
	}

}
