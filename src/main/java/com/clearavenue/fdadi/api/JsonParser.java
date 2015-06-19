package com.clearavenue.fdadi.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
	
	/**
	 * Returns the contents of the drug_interactions field of the supplied json
	 * @param json String containing the JSON data retrieved from the FDA label API
	 * @return String describing drug interactions, or null if there are none in the supplied JSON.
	 */
	public static String getInteractions(String json){
		JSONObject data = new JSONObject(json);
		try {
			JSONArray array = data.getJSONArray("drug_interactions");
			String out = "";
			for(int i = 0; i < array.length(); i++){
				out += array.getString(i);
				if(i < array.length() - 1){
					out += "\n";
				}
			}
			return out;
		} catch (JSONException e) {
			return "";
		}
	}
}
