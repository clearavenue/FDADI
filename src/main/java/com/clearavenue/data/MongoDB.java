/*
 * 
 */
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

/**
 * The Class MongoDB.
 */
public final class MongoDB {

	/** The Constant logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoDB.class);

	/** The Constant INSTANCE. */
	private static final MongoDB INSTANCE = new MongoDB();

	/** The datastore. */
	private Datastore datastore;

	/** The Constant DB_NAME. */
	public static final String DB_NAME = "fdadi";

	/** The mongo client. */
	private MongoClient mongoClient;

	/**
	 * Instantiates a new mongo db.
	 */
	private MongoDB() {
		try {
			final Properties properties = new Properties();
			properties.load(getClass().getClassLoader().getResourceAsStream("myMedications.properties"));
			final String dbIP = properties.getProperty("mongoServerHost");
			final int dbPort = Integer.parseInt(properties.getProperty("mongoServerPort"));

			LOGGER.info("Connecting to [{}:{}]", dbIP, dbPort);
			mongoClient = new MongoClient(dbIP, dbPort);
			mongoClient.setWriteConcern(WriteConcern.ACKNOWLEDGED);

			datastore = new Morphia().map(UserProfile.class, UserMedication.class, AllMedications.class, AllPharmClasses.class).createDatastore(mongoClient, DB_NAME);
			datastore.ensureIndexes();

			LOGGER.info("Connection to database [{}] initialized", DB_NAME);

			initCollections();
		} catch (final IOException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Failed to init MongoDB: {}.", e.getMessage());
			}
		}
	}

	/**
	 * Instance.
	 *
	 * @return the mongo db
	 */
	public static MongoDB instance() {
		return INSTANCE;
	}

	/**
	 * Gets the database.
	 *
	 * @return the database
	 */
	public Datastore getDatabase() {
		return datastore;
	}

	/**
	 * Close.
	 */
	public void close() {
		mongoClient.close();
	}

	/**
	 * Inits the collections.
	 */
	private void initCollections() {
		// if there is nothing in the AllMedications and AllPharmClasses, then populate them
		final AllMedicationsDAO medDAO = new AllMedicationsDAO(getDatabase());
		final AllPharmClassesDAO pcDAO = new AllPharmClassesDAO(getDatabase());

		if (medDAO.count() == 0) {
			medDAO.initCollection();
		}
		if (pcDAO.count() == 0) {
			pcDAO.initCollection();
		}
	}

}
