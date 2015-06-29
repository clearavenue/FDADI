/*
 *
 */
package com.clearavenue.data.objects;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * The Class UserProfile.
 */
@Entity
public class UserProfile {

	/** The id. */
	@Id
	private ObjectId id;

	/** The user id. */
	private String userId;

	/** The password. */
	private String password;

	/** The medications. */
	@Embedded
	private final List<UserMedication> medications = new ArrayList<UserMedication>();

	/**
	 * Instantiates a new user profile.
	 */
	public UserProfile() {
	}

	/**
	 * Instantiates a new user profile.
	 *
	 * @param username
	 *            the username
	 * @param pwd
	 *            the password
	 */
	public UserProfile(final String username, final String pwd) {
		setUserId(username);
		setPassword(pwd);
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public final String getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param uid
	 *            the new user id
	 */
	public final void setUserId(final String uid) {
		userId = uid;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public final String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param pwd
	 *            the new password
	 */
	public final void setPassword(final String pwd) {
		password = pwd;
	}

	/**
	 * Gets the medications.
	 *
	 * @return the medications
	 */
	public final List<UserMedication> getMedications() {
		return medications;
	}
}
