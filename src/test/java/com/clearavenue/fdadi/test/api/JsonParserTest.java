/*
 * 
 */
package com.clearavenue.fdadi.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.clearavenue.fdadi.api.ApiQueries;
import com.clearavenue.fdadi.api.JsonParser;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Testing JSON parsing.
 *
 */
public class JsonParserTest {

	/**
	 * Gets the interactions test.
	 */
	@Test
	public final void getInteractionsTest() {
		final String expectedInteractions = "Drug interactions See Warnings and Precautions for interaction with CNS drugs and alcohol. "
				+ "Methocarbamol may inhibit the effect of pyridostigmine bromide. Therefore, methocarbamol should be used with caution in patients with "
				+ "myasthenia gravis receiving anticholinesterase agents.";
		try {
			final String json = ApiQueries.getLabel("METHOCARBAMOL");
			final String actualInteractions = JsonParser.getInteractions(json);
			assertEquals(expectedInteractions, actualInteractions);
		} catch (final UnirestException e) {
			fail("ApiQueries.getLabel Should not have thrown UnirestException: " + e.getMessage());
		}
	}

	/**
	 * No interactions test.
	 */
	@Test
	public final void noInteractionsTest() {
		final String expectedInteractions = "";
		try {
			final String json = ApiQueries.getLabel("tylenol");
			final String actualInteractions = JsonParser.getInteractions(json);
			assertEquals(expectedInteractions, actualInteractions);
		} catch (final UnirestException e) {
			fail("ApiQueries.getLabel Should not have thrown UnirestException: " + e.getMessage());
		}
	}

}
