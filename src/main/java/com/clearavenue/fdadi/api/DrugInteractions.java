package com.clearavenue.fdadi.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.mashape.unirest.http.exceptions.UnirestException;

public class DrugInteractions {

	/**
	 * Finds all mentions of each drug's generic and brand name within the drug interactions entry of each other drug.
	 * 
	 * @param drugs
	 *            Array of generic names of drugs
	 * @return Hashmap where the keys are each drug that has an interaction with another. The value of each key is a list of the names of drugs which have an interaction with that
	 *         key If a drug has no interactions, it will not exist as a key in the map.
	 * @throws UnirestException
	 */
	public static HashMap<String, ArrayList<String>> findInteractions(String... drugs) throws UnirestException {
		HashMap<String, ArrayList<String>> out = new HashMap<>();

		String[] interactions = new String[drugs.length];
		for (int currentDrug = 0; currentDrug < interactions.length; currentDrug++) {
			String json = ApiQueries.getLabel(drugs[currentDrug]);
			String inter = JsonParser.getInteractions(json);
			for (int otherDrug = 0; otherDrug < interactions.length; otherDrug++) {
				if (currentDrug != otherDrug && inter.toLowerCase(Locale.ENGLISH).contains(drugs[otherDrug].toLowerCase(Locale.ENGLISH))) {
					if (!out.containsKey(drugs[currentDrug])) {
						out.put(drugs[currentDrug], new ArrayList<String>());
					}
					out.get(drugs[currentDrug]).add(drugs[otherDrug]);
				}
			}
		}
		return out;
	}
}
