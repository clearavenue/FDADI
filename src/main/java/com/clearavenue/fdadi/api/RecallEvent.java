package com.clearavenue.fdadi.api;

import org.json.JSONObject;

public class RecallEvent {

	private final String drugName;
	private final String reason;
	private final String distributionPattern;
	private final String productDescription;

	/**
	 * @param drugName
	 *            Name of drug to be displayed. Does not affect the JSON or any other fields.
	 * @param recall
	 *            JSON Object retrieved from FDA drug enforcement api describing a single recall
	 */
	public RecallEvent(String drugName, JSONObject recall) {
		this.drugName = drugName;
		reason = recall.getString("reason_for_recall");
		distributionPattern = recall.getString("distribution_pattern");
		productDescription = recall.getString("product_description");
	}

	/**
	 * @return Drug name, as shown on main page of webapp.
	 */
	public String getDrugName() {
		return drugName;
	}

	/**
	 * @return Reason for recall
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * Returns a string describing where the product being recalled was sold. For example, "Three states: WV, MA, VA" or "Nationwide"
	 *
	 * @return String describing areas affected by recall
	 */
	public String getDistributionPattern() {
		return distributionPattern;
	}

	/**
	 * @return Description of product being recalled.
	 */
	public String getProductDescription() {
		return productDescription;
	}

	@Override
	public String toString() {
		return "Reason: " + reason + "\nDistribution Pattern: " + distributionPattern + "\nProduct Description: " + productDescription;
	}
}