package com.clearavenue.data.objects;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class UserProfile {

	@Id
	private ObjectId id;
	private String userId;
	private String password;

	@Embedded
	private final List<UserMedication> medications;

	public UserProfile() {
		medications = new ArrayList<UserMedication>();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UserMedication> getMedications() {
		return medications;
	}
}
