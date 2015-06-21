package com.clearavenue.data;

import java.util.Arrays;
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

	public static boolean addUserProfile(final String userId, final String pwd) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(pwd)) {
			return false;
		}

		UserProfile newUser = new UserProfile(userId, pwd);
		ds.save(newUser);

		UserProfile user = DBUtils.findUserProfile(userId);
		return user == null ? false : user.getPassword().equals(pwd) && user.getUserId().equals(userId);
	}

	public static void addUserMedication(final UserProfile user, final UserMedication... medications) {
		UpdateOperations<UserProfile> updates = ds.createUpdateOperations(UserProfile.class).addAll("medications", Arrays.asList(medications), false);
		UpdateResults results = ds.update(user, updates);
		if (results.getUpdatedCount() == 0) {
			System.out.println(String.format("Error inserting medication: %s", results.getWriteResult().toString()));
		}
	}

	public static void deleteUserMedication(final UserProfile user, final UserMedication... medications) {
		for (UserMedication medication : medications) {
			UpdateOperations<UserProfile> deletes = ds.createUpdateOperations(UserProfile.class).removeAll("medications", medication);
			UpdateResults results = ds.update(user, deletes);
			if (results.getUpdatedCount() == 0) {
				System.out.println(String.format("Error inserting medication: %s", results.getWriteResult().toString()));
			}
		}
	}
}
