/*
 *
 */
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

/**
 * The Class UserProfileDAO.
 */
public class UserProfileDAO extends BasicDAO<UserProfile, String> {

	/**
	 * Instantiates a new user profile dao.
	 *
	 * @param ds
	 *            the ds
	 */
	public UserProfileDAO(final Datastore ds) {
		super(ds);
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public final List<UserProfile> findAll() {
		return find().asList();
	}

	/**
	 * Find by user id.
	 *
	 * @param userId
	 *            the user id
	 * @return the user profile
	 */
	public final UserProfile findByUserId(final String userId) {
		if (StringUtils.isBlank(userId)) {
			return null;
		}

		final Query<UserProfile> query = getDatastore().createQuery(UserProfile.class);
		query.criteria("userId").equal(userId);
		final QueryResults<UserProfile> users = find(query);
		return users.get();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.mongodb.morphia.dao.BasicDAO#save(java.lang.Object)
	 */
	@Override
	public final Key<UserProfile> save(final UserProfile entity) {
		if (StringUtils.isBlank(entity.getUserId()) || StringUtils.isBlank(entity.getPassword())) {
			return null;
		}

		final UserProfile existingUser = findByUserId(entity.getUserId());
		if (existingUser == null) {
			return super.save(entity);
		} else {
			return null;
		}
	}

	/**
	 * Adds the user medication.
	 *
	 * @param entity
	 *            the entity
	 * @param medications
	 *            the medications
	 */
	public final void addUserMedication(final UserProfile entity, final UserMedication... medications) {
		final UpdateOperations<UserProfile> ops = getDatastore().createUpdateOperations(UserProfile.class).addAll("medications", Arrays.asList(medications), false);
		getDatastore().update(entity, ops);
	}

	/**
	 * Delete user medication.
	 *
	 * @param entity
	 *            the entity
	 * @param medications
	 *            the medications
	 */
	public final void deleteUserMedication(final UserProfile entity, final UserMedication... medications) {
		for (final UserMedication medication : medications) {
			final UpdateOperations<UserProfile> ops = getDatastore().createUpdateOperations(UserProfile.class).removeAll("medications", medication);
			getDatastore().update(entity, ops);
		}
	}

}
