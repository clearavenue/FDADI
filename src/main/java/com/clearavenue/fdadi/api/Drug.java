package com.clearavenue.fdadi.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.mashape.unirest.http.exceptions.UnirestException;

public class Drug {

	private String brandName, genericName, sideEffects, usage, interactions, indications, counterindications;

	public Drug(String json) {
		final JSONObject data = new JSONObject(json);
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

	public static List<Drug> getDrugs(String... names) {
		final List<Drug> out = new ArrayList<Drug>();
		for (final String s : names) {
			String json;
			try {
				json = ApiQueries.getLabel(s);
				final Drug drug = new Drug(json);
				out.add(drug);
			} catch (final UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return out;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getGenericName() {
		return genericName;
	}

	public String getSideEffects() {
		return sideEffects;
	}

	public String getUsage() {
		return usage;
	}

	public String getInteractions() {
		return interactions;
	}

	public String getIndications() {
		return indications;
	}

	public String getCounterindications() {
		return counterindications;
	}

}
