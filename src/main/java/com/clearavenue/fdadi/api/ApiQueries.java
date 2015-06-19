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
	 * @param drugName Generic name of the drug
	 * @return Entire contents of drug label, in JSON format, or an empty string if the drug was not found.
	 * @throws UnirestException
	 */
	public static String getLabel(String drugName) throws UnirestException{
		String url = "https://api.fda.gov/drug/label.json?search=";
		url += "openfda.generic_name:" + drugName;
		try{
			
			return makeQuery(url).getJSONObject(0).toString();
		}catch (JSONException e) {
			return "";
		}
		
	}
	
	/**
	 * Queries the FDA API to determine whether the drug with the given
	 * generic name is in an ongoing recall. Also searches the
	 * "product_description" field for drugName
	 * 
	 * @param drugName Generic name of drug
	 * @param limit Max number of recalls to return
	 * @return RecallStatus object array giving information about each recall, or null if no ongoing recalls were found.
	 * @throws UnirestException 
	 */
	public static RecallEvent[] getRecallStatus(String drugName, int limit) throws UnirestException{
		String url = "https://api.fda.gov/drug/enforcement.json?";
		url += "search=(openfda.generic_name:" + drugName + "+product_description:" + drugName + ")+AND+status:Ongoing&limit=" + limit;
		try {
			JSONArray results = makeQuery(url);
			//System.out.println(results.toString(2));
			RecallEvent[] recalls = new RecallEvent[results.length()];
			for(int i = 0; i < results.length(); i++){
				recalls[i] = new RecallEvent(results.getJSONObject(i));
			}
			return recalls;
		} catch (JSONException e) {
			return null;
		}
		
	}
	
	/**
	 * Returns JSON array of all results of query
	 * @param url URL to be queried
	 * @return JSONArray of all results returned by the query
	 * @throws UnirestException
	 */
	private static JSONArray makeQuery(String url) throws UnirestException{
		HttpResponse<JsonNode> response = Unirest.get(url).asJson();
		JSONArray result = response.getBody().getObject().getJSONArray("results");
		return result;
	}
	
	
	public static void main(String[] args) throws UnirestException {
		RecallEvent[] results = getRecallStatus("Dextrose", 20);
		for(RecallEvent e : results){
			System.out.println(e.toString() + "\n\n");
		}
	}
}
