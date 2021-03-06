/*
 * 
 */
package com.clearavenue.fdadi.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * The Class Drug.
 */
public class Drug {

	/** The brand name. */
	private String brandName;

	/** The generic name. */
	private String genericName;

	/** The side effects. */
	private String sideEffects;

	/** The usage. */
	private String usage;

	/** The interactions. */
	private String interactions;

	/** The indications. */
	private String indications;

	/** The counterindications. */
	private String counterindications;

	/**
	 * Parses the supplied JSON and constructs a Drug object. If the supplied JSON is not properly formatted, then all fields will be empty.
	 *
	 * @param json
	 *            Result of FDA Label api query containing a single drug's label info.
	 */
	public Drug(final String json) {
		JSONObject data;
		try {
			data = new JSONObject(json);
		} catch (final JSONException e) {
			data = new JSONObject();
		}

		if (data.has("openfda")) {
			final JSONObject openfda = data.getJSONObject("openfda");
			brandName = openfda.getJSONArray("brand_name").getString(0);
			genericName = openfda.getJSONArray("generic_name").getString(0);
		} else {
			brandName = "";
			genericName = "";
		}

		if (data.has("adverse_reactions")) {
			sideEffects = data.getJSONArray("adverse_reactions").getString(0);
		} else {
			sideEffects = "";
		}

		if (data.has("dosage_and_administration")) {
			usage = data.getJSONArray("dosage_and_administration").getString(0);
		} else {
			usage = "";
		}

		if (data.has("drug_interactions")) {
			interactions = data.getJSONArray("drug_interactions").getString(0);
		} else {
			interactions = "";
		}

		if (data.has("purpose")) {
			indications = data.getJSONArray("purpose").getString(0);
		} else if (data.has("indications_and_usage")) {
			indications = data.getJSONArray("indications_and_usage").getString(0);
		} else {
			indications = "";
		}

		if (data.has("contraindications")) {
			counterindications = data.getJSONArray("contraindications").getString(0);
		} else {
			counterindications = "";
		}

	}

	/**
	 * Queries the FDA api for all drugs in the supplied list, and returns a List of them. If, for any drug in the list, a label could not be found, it is ignored.
	 *
	 * @param names
	 *            List of brand or generic names of drugs to search for
	 * @return List<Drug> containing a Drug object for every drug that was successfully found.
	 */
	public static List<Drug> getDrugs(final String... names) {
		final List<Drug> out = new ArrayList<Drug>();
		for (final String s : names) {
			String json;
			try {
				json = ApiQueries.getLabel(s);
				final Drug drug = new Drug(json);
				out.add(drug);
			} catch (final UnirestException e) {
				e.printStackTrace();
			} catch (final JSONException e) {
				// Drug was not found. Simply don't add it to the list.
			}
		}
		return out;
	}

	/**
	 * Gets the brand name.
	 *
	 * @return the brand name
	 */
	public final String getBrandName() {
		return brandName;
	}

	/**
	 * Gets the generic name.
	 *
	 * @return the generic name
	 */
	public final String getGenericName() {
		return genericName;
	}

	/**
	 * Gets the side effects.
	 *
	 * @return the side effects
	 */
	public final String getSideEffects() {
		return sideEffects;
	}

	/**
	 * Gets the usage.
	 *
	 * @return the usage
	 */
	public final String getUsage() {
		return usage;
	}

	/**
	 * Gets the interactions.
	 *
	 * @return the interactions
	 */
	public final String getInteractions() {
		return interactions;
	}

	/**
	 * Gets the indications.
	 *
	 * @return the indications
	 */
	public final String getIndications() {
		return indications;
	}

	/**
	 * Gets the counterindications.
	 *
	 * @return the counterindications
	 */
	public final String getCounterindications() {
		return counterindications;
	}

}
