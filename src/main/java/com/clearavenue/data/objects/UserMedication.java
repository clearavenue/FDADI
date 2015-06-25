package com.clearavenue.data.objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class UserMedication implements Comparable<UserMedication> {

	private String medicationName;

	public UserMedication() {
	}

	public UserMedication(String name) {
		setMedicationName(name);
	}

	@Override
	public boolean equals(Object that) {
		if (!(that instanceof UserMedication)) {
			return false;
		}
		if (that == this) {
			return true;
		}

		UserMedication rhs = (UserMedication) that;
		return new EqualsBuilder().append(medicationName, rhs.medicationName).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(medicationName).toHashCode();
	}

	public String getMedicationName() {
		return medicationName;
	}

	public void setMedicationName(String name) {
		medicationName = name;
	}

	@Override
	public String toString() {
		return medicationName;
	}

	@Override
	public int compareTo(UserMedication o) {
		return medicationName.compareTo(o.medicationName);
	}

}
