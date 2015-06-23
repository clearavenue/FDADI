package com.clearavenue.fdadi.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

public class DrugTest {

	private static String label;
	private static Drug zoloft;

	@BeforeClass
	public static void getZoloftLabel() {
		try {
			label = ApiQueries.getLabel("Zoloft");
			zoloft = new Drug(label);
		} catch (UnirestException e) {
			fail("Should not have thrown exception: " + e.getMessage());
		}
	}

	@Test
	public void getAdverseEffectsTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		String sideEffects = zoloft.getSideEffects();
		assertTrue(sideEffects.contains("Nausea"));
	}

	@Test
	public void getNoAdverseEffectsTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		String sideEffects = zoloft.getSideEffects();
		assertFalse(sideEffects.contains("Leprosy"));
	}

	@Test
	public void getUsageTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		String usage = zoloft.getUsage();
		assertTrue(usage.contains("Panic"));
	}

	@Test
	public void getNotUsageTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		String usage = zoloft.getUsage();
		assertFalse(usage.contains("Birth Control"));
	}

	@Test
	public void getContraindicationsTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		String usage = zoloft.getCounterindications();
		assertTrue(usage.contains("disulfiram"));
	}

	@Test
	public void getNoContraindicationsTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		String usage = zoloft.getCounterindications();
		assertFalse(usage.contains("vitamin c"));
	}

}
