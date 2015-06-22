package com.clearavenue.data;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clearavenue.data.objects.AllMedications;
import com.clearavenue.data.objects.AllPharmClasses;
import com.clearavenue.data.objects.UserMedication;
import com.clearavenue.data.objects.UserProfile;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

public class MongoDB {
	private static final Logger logger = LoggerFactory.getLogger(MongoDB.class);

	private static final MongoDB INSTANCE = new MongoDB();

	private Datastore datastore;

	public static final String DB_NAME = "fdadi";
	private static final String dbIP = "AgileBPA-0.clearavenue.0553.mongodbdns.com";
	private static final int dbPort = 27000;

	protected MongoClient mongoClient;

	private MongoDB() {
		try {
			mongoClient = new MongoClient(dbIP, dbPort);
			mongoClient.setWriteConcern(WriteConcern.ACKNOWLEDGED);

			datastore = new Morphia().map(UserProfile.class, UserMedication.class, AllMedications.class, AllPharmClasses.class).createDatastore(mongoClient, DB_NAME);
			datastore.ensureIndexes();

			logger.info("Connection to database [{}] initialized", DB_NAME);
		} catch (final Exception e) {
			logger.error("Failed to init MongoDB: {}.", e.getMessage());
		}
	}

	public static MongoDB instance() {
		return INSTANCE;
	}

	public Datastore getDatabase() {
		return datastore;
	}

	public void close() {
		mongoClient.close();
	}
}
