package com.clearavenue.data;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;

import com.clearavenue.data.objects.UserMedication;
import com.clearavenue.data.objects.UserProfile;

public class UserProfileDAO extends BasicDAO<UserProfile, String> {

	public UserProfileDAO(Datastore ds) {
		super(ds);
	}

	public List<UserProfile> findAll() {
		return find().asList();
	}

	public UserProfile findByUserId(String userId) {
		if (StringUtils.isBlank(userId)) {
			return null;
		}

		Query<UserProfile> query = getDatastore().createQuery(UserProfile.class);
		query.criteria("userId").equal(userId);
		QueryResults<UserProfile> users = find(query);
		return users.get();
	}

	@Override
	public Key<UserProfile> save(UserProfile entity) {
		if (StringUtils.isBlank(entity.getUserId()) || StringUtils.isBlank(entity.getPassword())) {
			return null;
		}

		return super.save(entity);
	}

	public void addUserMedication(UserProfile entity, final UserMedication... medications) {
		UpdateOperations<UserProfile> ops = getDatastore().createUpdateOperations(UserProfile.class).addAll("medications", Arrays.asList(medications), false);
		getDatastore().update(entity, ops);
	}

	public void deleteUserMedication(final UserProfile entity, final UserMedication... medications) {
		for (UserMedication medication : medications) {
			UpdateOperations<UserProfile> ops = getDatastore().createUpdateOperations(UserProfile.class).removeAll("medications", medication);
			getDatastore().update(entity, ops);
		}
	}

}
