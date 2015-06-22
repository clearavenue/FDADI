 package com.clearavenue.fdadi.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.junit.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

public class DrugInteractionsTest {

	@Test
	public void findInteractionsTest() {
		String[] drugs = {"glimepiride", "methocarbamol", "alcohol", "tylenol"};
		try {
			HashMap<String, ArrayList<String>> results = DrugInteractions.findInteractions(drugs);
			Set<String> keys = results.keySet();
			assertFalse(keys.contains("tylenol"));
			assertTrue(keys.contains("methocarbamol"));
			assertTrue(keys.contains("glimepiride"));
			assertTrue(results.get("glimepiride").contains("alcohol"));
			assertFalse(results.get("glimepiride").contains("tylenol"));
		} catch (UnirestException e) {
			fail("Unirest exception while testing findInteractions: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

}
