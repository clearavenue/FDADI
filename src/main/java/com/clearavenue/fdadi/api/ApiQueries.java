package com.clearavenue.fdadi.api;

import org.json.JSONArray;
import org.json.JSONException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ApiQueries {

	/**
	 * Queries the FDA Drug Label API to find a drug with the given name
	 *
	 * @param drugName
	 *            Generic name of the drug
	 * @return Entire contents of drug label, in JSON format, or an empty string if the drug was not found.
	 * @throws UnirestException
	 */
	public static String getLabel(String drugName) throws UnirestException {
		String url = "https://api.fda.gov/drug/label.json?search=";
		url += "openfda.generic_name:" + drugName;
		try {

			return makeQuery(url).getJSONObject(0).toString();
		} catch (final JSONException e) {
			return "";
		}

	}

	/**
	 * Queries the FDA Drug Label API to find a drug with the given pharmacologic class
	 *
	 * @param pharmClass
	 *            Pharmacologic class
	 * @param limit
	 *            Max number of results to be returned. Must be less than or equal to 1000
	 * @return Array of drug generic names with given pharmacologic class, or a zero-length array if no drugs of the given class were found.
	 * @throws UnirestException
	 */
	public static String[] findByPharmClass(String pharmClass, int limit) throws UnirestException {
		String url = "https://api.fda.gov/drug/label.json?search=";
		pharmClass = pharmClass.replace(' ', '+');
		url += "openfda.pharm_class_epc%22:" + pharmClass;
		url += "%22&count=openfda.generic_name.exact";
		url += "&limit=" + limit;
		try {
			final JSONArray results = makeQuery(url);
			final String[] out = new String[results.length()];
			for (int i = 0; i < out.length; i++) {
				out[i] = results.getJSONObject(i).getString("term");
			}
			return out;
		} catch (final JSONException e) {
			return new String[0];
		}
	}

	/**
	 * Queries the FDA API to determine whether the drug with the given generic name is in an ongoing recall. Also searches the "product_description" field for drugName
	 *
	 * @param drugName
	 *            Generic name of drug
	 * @param limit
	 *            Max number of recalls to return
	 * @return RecallStatus object array giving information about each recall, or null if no ongoing recalls were found.
	 * @throws UnirestException
	 */
	public static RecallEvent[] getRecallStatus(String drugName, int limit) throws UnirestException {
		String url = "https://api.fda.gov/drug/enforcement.json?";
		url += "search=(openfda.generic_name:" + drugName + "+product_description:" + drugName + ")+AND+status:Ongoing&limit=" + limit;
		try {
			final JSONArray results = makeQuery(url);
			final RecallEvent[] recalls = new RecallEvent[results.length()];
			for (int i = 0; i < results.length(); i++) {
				recalls[i] = new RecallEvent(results.getJSONObject(i));
			}
			return recalls;
		} catch (final JSONException e) {
			return new RecallEvent[0];
		}

	}

	/**
	 * Returns JSON array of all results of query
	 *
	 * @param url
	 *            URL to be queried
	 * @return JSONArray of all results returned by the query
	 * @throws UnirestException
	 */
	private static JSONArray makeQuery(String url) throws UnirestException {
		final HttpResponse<JsonNode> response = Unirest.get(url).asJson();
		final JSONArray result = response.getBody().getObject().getJSONArray("results");
		return result;
	}
}
