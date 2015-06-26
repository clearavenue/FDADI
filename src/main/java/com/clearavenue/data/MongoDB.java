package com.clearavenue.data;

import java.io.IOException;
import java.util.Properties;

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

	protected MongoClient mongoClient;

	private MongoDB() {
		try {
			Properties properties = new Properties();
			properties.load(getClass().getClassLoader().getResourceAsStream("myMedications.properties"));
			String dbIP = properties.getProperty("mongoServerHost");
			int dbPort = Integer.parseInt(properties.getProperty("mongoServerPort"));

			logger.info("Connecting to [{}:{}]", dbIP, dbPort);
			mongoClient = new MongoClient(dbIP, dbPort);
			mongoClient.setWriteConcern(WriteConcern.ACKNOWLEDGED);

			datastore = new Morphia().map(UserProfile.class, UserMedication.class, AllMedications.class, AllPharmClasses.class).createDatastore(mongoClient, DB_NAME);
			datastore.ensureIndexes();

			logger.info("Connection to database [{}] initialized", DB_NAME);
		} catch (final IOException e) {
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
