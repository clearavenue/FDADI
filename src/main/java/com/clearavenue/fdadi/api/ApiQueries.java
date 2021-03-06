/*
 *
 */
package com.clearavenue.fdadi.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * The Class ApiQueries.
 */
public final class ApiQueries {

	/** The Constant ONE_THOUSAND. */
	private static final int ONE_THOUSAND = 1000;

	/** The Constant TEN. */
	private static final int TEN = 10;

	/**
	 * Do not instantiate a new api queries.
	 */
	private ApiQueries() {
	}

	/**
	 * Gets the label.
	 *
	 * @param drugName
	 *            the drug name
	 * @return the label
	 * @throws UnirestException
	 *             the unirest exception
	 */
	public static String getLabel(final String drugName) throws UnirestException {
		final String fixedDrugName = drugName.replaceAll("%", "%25");
		String url = "https://api.fda.gov/drug/label.json?search=";
		url += "(openfda.generic_name:%22" + fixedDrugName;
		url = url.replaceAll(", ", "%22+AND+openfda.generic_name:%22");
		url = url.replaceAll("%25", "%22+AND+openfda.generic_name:%22");

		url += "%22)+(openfda.brand_name:%22" + fixedDrugName + "%22)";
		url = url.replaceAll(", ", "%22+AND+openfda.brand_name:%22");
		url = url.replaceAll("%25", "%22+AND+openfda.generic_name:%22");

		url = url.replace(' ', '+');
		try {
			return makeQuery(url).getJSONObject(0).toString();
		} catch (final JSONException e) {
			return "";
		}

	}

	/**
	 * Queries the FDA Drug Label API to find a drug with the given pharmacologic class.
	 *
	 * @param pharmClass
	 *            Pharmacologic class
	 * @param limit
	 *            Max number of results to be returned. Must be less than or equal to 1000
	 * @return List of drug generic names with given pharmacologic class, or an empty list if no drugs of the given class were found.
	 * @throws UnirestException
	 *             the unirest exception
	 */
	public static List<String> findByPharmClass(String pharmClass, int limit) throws UnirestException {
		if (limit > ONE_THOUSAND) {
			limit = ONE_THOUSAND;
		}

		String url = "https://api.fda.gov/drug/label.json?search=";
		pharmClass = pharmClass.replace(' ', '+');
		url += "openfda.pharm_class_epc%22:" + pharmClass;
		url += "%22&count=openfda.generic_name.exact";
		url += "&limit=" + limit;
		try {
			final JSONArray results = makeQuery(url);
			final List<String> out = new ArrayList<String>(results.length());
			for (int i = 0; i < results.length(); i++) {
				out.add(results.getJSONObject(i).getString("term"));
			}
			return out;
		} catch (final JSONException e) {
			return new ArrayList<String>();
		}
	}

	/**
	 * Returns value of findByPharmClass with default limit of 1000.
	 *
	 * @param pharmClass
	 *            Pharmocological class
	 * @return Result of findByPharmClass(pharmClass, 1000)
	 * @throws UnirestException
	 *             the unirest exception
	 */
	public static List<String> findByPharmClass(final String pharmClass) throws UnirestException {
		return findByPharmClass(pharmClass, ONE_THOUSAND);
	}

	/**
	 * Queries the FDA API to determine whether the drug with the given generic or brand name is in an ongoing recall. Also searches the "product_description" field for drugName
	 *
	 * @param drugName
	 *            Generic or brand name of drug
	 * @param limit
	 *            Max number of recalls to return
	 * @return RecallStatus object List giving information about each recall, or an empty list if no ongoing recalls were found.
	 * @throws UnirestException
	 *             the unirest exception
	 */
	public static List<RecallEvent> getRecallStatus(String drugName, final int limit) throws UnirestException {
		drugName = drugName.replaceAll(",", "").replace(' ', '+').replaceAll("%", "%25");
		String url = "https://api.fda.gov/drug/enforcement.json?";
		url += "search=(openfda.generic_name:%22" + drugName + "%22";
		url += "+openfda.brand_name:%22" + drugName + "%22";
		url += "+product_description:%22" + drugName + "%22)+AND+status:Ongoing&limit=" + limit;
		try {
			final List<RecallEvent> recalls = new ArrayList<RecallEvent>();
			final JSONArray results = makeQuery(url);
			for (int i = 0; i < results.length(); i++) {
				recalls.add(new RecallEvent(drugName, results.getJSONObject(i)));
			}
			return recalls;
		} catch (final JSONException e) {
			return new ArrayList<RecallEvent>();
		}

	}

	/**
	 * Returns the same thing as getRecallStatus(String, int) with default value of 10 as limit argument.
	 *
	 * @param drugName
	 *            Generic or brand name of drug
	 * @return Value of getRecallStatus(drugName, 10)
	 * @throws UnirestException
	 *             the unirest exception
	 */
	public static List<RecallEvent> getRecallStatus(final String drugName) throws UnirestException {
		return getRecallStatus(drugName, TEN);
	}

	/**
	 * Returns JSON array of all results of query.
	 *
	 * @param url
	 *            URL to be queried
	 * @return JSONArray of all results returned by the query
	 * @throws UnirestException
	 *             the unirest exception
	 */
	private static JSONArray makeQuery(final String url) throws UnirestException {
		final HttpResponse<JsonNode> response = Unirest.get(url).asJson();
		final JSONArray result = response.getBody().getObject().getJSONArray("results");
		return result;
	}
}
