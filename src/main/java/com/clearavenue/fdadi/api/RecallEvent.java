/*
 *
 */
package com.clearavenue.fdadi.api;

import org.json.JSONObject;

/**
 * The Class RecallEvent.
 */
public class RecallEvent {

	/** The drug name. */
	private final String drugName;

	/** The reason. */
	private final String reason;

	/** The distribution pattern. */
	private final String distributionPattern;

	/** The product description. */
	private final String productDescription;

	/**
	 * Instantiates a new recall event.
	 *
	 * @param name
	 *            Name of drug to be displayed. Does not affect the JSON or any other fields.
	 * @param recall
	 *            JSON Object retrieved from FDA drug enforcement api describing a single recall
	 */
	public RecallEvent(final String name, final JSONObject recall) {
		drugName = name;
		reason = recall.getString("reason_for_recall");
		distributionPattern = recall.getString("distribution_pattern");
		productDescription = recall.getString("product_description");
	}

	/**
	 * Gets the drug name.
	 *
	 * @return Drug name, as shown on main page of webapp.
	 */
	public final String getDrugName() {
		return drugName;
	}

	/**
	 * Gets the reason.
	 *
	 * @return Reason for recall
	 */
	public final String getReason() {
		return reason;
	}

	/**
	 * Returns a string describing where the product being recalled was sold. For example, "Three states: WV, MA, VA" or "Nationwide"
	 *
	 * @return String describing areas affected by recall
	 */
	public final String getDistributionPattern() {
		return distributionPattern;
	}

	/**
	 * Gets the product description.
	 *
	 * @return Description of product being recalled.
	 */
	public final String getProductDescription() {
		return productDescription;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return "Reason: " + reason + "\nDistribution Pattern: " + distributionPattern + "\nProduct Description: " + productDescription;
	}
}