package com.clearavenue.fdadi.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

public class JsonParserTest {

	@Test
	public void getInteractionsTest() {
		String expectedInteractions = "Drug interactions See Warnings and Precautions for interaction with CNS drugs and alcohol. Methocarbamol may inhibit the effect of pyridostigmine bromide. Therefore, methocarbamol should be used with caution in patients with myasthenia gravis receiving anticholinesterase agents.";
		try {
			String json = ApiQueries.getLabel("METHOCARBAMOL");
			String actualInteractions = JsonParser.getInteractions(json);
			assertEquals(expectedInteractions, actualInteractions);
		} catch (UnirestException e) {
			fail("ApiQueries.getLabel Should not have thrown UnirestException: " + e.getMessage());
		}
	}
	
	@Test
	public void noInteractionsTest(){
		String expectedInteractions = "";
		try {
			String json = ApiQueries.getLabel("tylenol");
			String actualInteractions = JsonParser.getInteractions(json);
			assertEquals(expectedInteractions, actualInteractions);
		} catch (UnirestException e) {
			fail("ApiQueries.getLabel Should not have thrown UnirestException: " + e.getMessage());
		}
	}

}
