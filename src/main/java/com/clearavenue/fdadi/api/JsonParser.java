package com.clearavenue.fdadi.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonParser {

	/**
	 * Returns the contents of the drug_interactions field of the supplied json
	 *
	 * @param json
	 *            String containing the JSON data retrieved from the FDA label API
	 * @return String describing drug interactions, or an empty string if there are none in the supplied JSON.
	 */
	private static final Logger logger = LoggerFactory.getLogger(JsonParser.class);

	public static String getInteractions(String json) {
		logger.info(json);
		final StringBuilder out = new StringBuilder();

		try {
			final JSONObject data = new JSONObject(json);
			final JSONArray array = data.getJSONArray("drug_interactions");

			for (int i = 0; i < array.length(); i++) {
				out.append(array.getString(i));
				if (i < array.length() - 1) {
					out.append("\n");
				}
			}
			return out.toString();
		} catch (final JSONException e) {
			return "";
		}
	}

}
