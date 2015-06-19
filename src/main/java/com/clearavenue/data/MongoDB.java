package com.clearavenue.data;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.clearavenue.data.objects.AllMedications;
import com.clearavenue.data.objects.UserMedication;
import com.clearavenue.data.objects.UserProfile;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

public class MongoDB {
	private static final MongoDB INSTANCE = new MongoDB();

	private Datastore datastore;

	public static final String DB_NAME = "fdadi";
	private static final String dbIP = "54.158.46.50";
	private static final int dbPort = 27000;

	private MongoDB() {
		try {
			System.out.println(String.format("Initializing connection to MongoDB...%s:%d", dbIP, dbPort));
			final MongoClient mongoClient = new MongoClient(dbIP, dbPort);
			mongoClient.setWriteConcern(WriteConcern.ACKNOWLEDGED);

			datastore = new Morphia().map(UserProfile.class, UserMedication.class, AllMedications.class).createDatastore(mongoClient, DB_NAME);
			datastore.ensureIndexes();
			System.out.println(String.format("Connection to database '%s' initialized", DB_NAME));
		} catch (final Exception e) {
			System.out.println(String.format("Failed to init MongoDB: %s.", e.getMessage()));
		}
	}

	public static MongoDB instance() {
		return INSTANCE;
	}

	public Datastore getDatabase() {
		return datastore;
	}
}
