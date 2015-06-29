/*
 * 
 */
package com.clearavenue.data.objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.mongodb.morphia.annotations.Embedded;

/**
 * The Class UserMedication.
 */
@Embedded
public class UserMedication implements Comparable<UserMedication> {

	/** The Constant P2. */
	private static final int P2 = 31;

	/** The Constant P1. */
	private static final int P1 = 17;

	/** The medication name. */
	private String medicationName;

	/**
	 * Instantiates a new user medication.
	 */
	public UserMedication() {
	}

	/**
	 * Instantiates a new user medication.
	 *
	 * @param name
	 *            the name
	 */
	public UserMedication(final String name) {
		setMedicationName(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(final Object that) {
		if (!(that instanceof UserMedication)) {
			return false;
		}
		if (that == this) {
			return true;
		}

		final UserMedication rhs = (UserMedication) that;
		return new EqualsBuilder().append(medicationName, rhs.medicationName).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		return new HashCodeBuilder(P1, P2).append(medicationName).toHashCode();
	}

	/**
	 * Gets the medication name.
	 *
	 * @return the medication name
	 */
	public final String getMedicationName() {
		return medicationName;
	}

	/**
	 * Sets the medication name.
	 *
	 * @param name
	 *            the new medication name
	 */
	public final void setMedicationName(final String name) {
		medicationName = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return medicationName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public final int compareTo(final UserMedication o) {
		return medicationName.compareTo(o.medicationName);
	}

}
