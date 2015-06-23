package com.clearavenue.fdadi.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.mashape.unirest.http.exceptions.UnirestException;

public class DrugInteractions {

	/**
	 * Finds all mentions of each drug's generic and brand name within the drug interactions entry of each other drug.
	 *
	 * @param drugs
	 *            Array of generic names OR brand names of drugs
	 * @return Hashmap where the keys are each drug that has an interaction with another. The value of each key is a list of the names of drugs which have an interaction with that
	 *         key If a drug has no interactions, it will not exist as a key in the map.
	 * @throws UnirestException
	 */
	public static HashMap<String, ArrayList<String>> findInteractions(List<String> drugs) throws UnirestException {
		final HashMap<String, ArrayList<String>> out = new HashMap<>();

		final String[] interactions = new String[drugs.size()];
		for (int currentDrug = 0; currentDrug < interactions.length; currentDrug++) {
			final String json = ApiQueries.getLabel(drugs.get(currentDrug));
			final String inter = JsonParser.getInteractions(json);
			for (int otherDrug = 0; otherDrug < interactions.length; otherDrug++) {
				if (currentDrug != otherDrug && inter.toLowerCase(Locale.ENGLISH).contains(drugs.get(otherDrug).toLowerCase(Locale.ENGLISH))) {
					if (!out.containsKey(drugs.get(currentDrug))) {
						out.put(drugs.get(currentDrug), new ArrayList<String>());
					}
					out.get(drugs.get(currentDrug)).add(drugs.get(otherDrug));
				}
			}
		}
		return out;
	}
}
