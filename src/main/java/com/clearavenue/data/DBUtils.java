package com.clearavenue.data;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import com.clearavenue.data.objects.UserMedication;
import com.clearavenue.data.objects.UserProfile;

public class DBUtils {
	private static Datastore ds = MongoDB.instance().getDatabase();

	void DBUtil() {
	}

	public static List<UserProfile> getAllUserProfiles() {
		return ds.createQuery(UserProfile.class).asList();
	}

	public static UserProfile findUserProfile(final String userId) {
		if (StringUtils.isBlank(userId)) {
			return null;
		}

		final UserProfile user = ds.find(UserProfile.class).field("userId").equal(userId).get();
		return user;
	}

	public static void addUserProfile(final String userId, final String pwd) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(pwd)) {
			return;
		}

		ds.save(new UserProfile(userId, pwd));
	}

	public static void addUserMedication(final String userId, final String medicationName) {
		UserProfile user = findUserProfile(userId);

		UpdateOperations<UserProfile> updates = ds.createUpdateOperations(UserProfile.class).add("medications", new UserMedication(medicationName));
		UpdateResults results = ds.update(user, updates);
		if (results.getUpdatedCount() == 0) {
			System.out.println(String.format("Error inserting medication: %s", results.getWriteResult().toString()));
		}
	}

	public static void deleteUserMedication(final String userId, final String medicationName) {
		UserProfile user = findUserProfile(userId);

		UpdateOperations<UserProfile> deletes = ds.createUpdateOperations(UserProfile.class).removeAll("medications", medicationName);
		UpdateResults results = ds.update(user, deletes);
		if (results.getUpdatedCount() == 0) {
			System.out.println(String.format("Error inserting medication: %s", results.getWriteResult().toString()));
		}
	}
}
