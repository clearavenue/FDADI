package com.clearavenue.data.objects;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class UserMedication {

	private String medicationName;

	public UserMedication() {
	}

	public UserMedication(String name) {
		setMedicationName(name);
	}

	public String getMedicationName() {
		return medicationName;
	}

	public void setMedicationName(String name) {
		this.medicationName = name;
	}
}
