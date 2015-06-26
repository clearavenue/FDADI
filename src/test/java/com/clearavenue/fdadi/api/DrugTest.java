package com.clearavenue.fdadi.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

public class DrugTest {

	private static String label;
	private static Drug zoloft;
	private static String blankLabel;
	private static Drug blankDrug;

	@BeforeClass
	public static void getZoloftLabel() {
		try {
			label = ApiQueries.getLabel("Zoloft");
			zoloft = new Drug(label);

			blankLabel = "";
			blankDrug = new Drug(blankLabel);
		} catch (final UnirestException e) {
			fail("Should not have thrown exception: " + e.getMessage());
		}
	}

	@Test
	public void getAdverseEffectsTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		final String sideEffects = zoloft.getSideEffects();
		assertTrue(sideEffects.contains("Nausea"));
	}

	@Test
	public void getNoAdverseEffectsTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		final String sideEffects = zoloft.getSideEffects();
		assertFalse(sideEffects.contains("Leprosy"));
	}

	@Test
	public void getUsageTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		final String usage = zoloft.getUsage();
		assertTrue(usage.contains("Panic"));
	}

	@Test
	public void getNotUsageTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		final String usage = zoloft.getUsage();
		assertFalse(usage.contains("Birth Control"));
	}

	@Test
	public void getContraindicationsTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		final String usage = zoloft.getCounterindications();
		assertTrue(usage.contains("disulfiram"));
	}

	@Test
	public void getNoContraindicationsTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		final String usage = zoloft.getCounterindications();
		assertFalse(usage.contains("vitamin c"));
	}

	@Test
	public void isNameBlankTest() {
		assertTrue(StringUtils.isBlank(blankDrug.getBrandName()));
	}

	@Test
	public void isGenericNameBlankTest() {
		assertTrue(StringUtils.isBlank(blankDrug.getGenericName()));
	}

	@Test
	public void isUsageBlankTest() {
		assertTrue(StringUtils.isBlank(blankDrug.getUsage()));
	}

	@Test
	public void isInteractionsBlankTest() {
		assertTrue(StringUtils.isBlank(blankDrug.getInteractions()));
	}

	@Test
	public void isIndicationsBlankTest() {
		assertTrue(StringUtils.isBlank(blankDrug.getIndications()));
	}

	@Test
	public void isCounterindicationsBlankTest() {
		assertTrue(StringUtils.isBlank(blankDrug.getCounterindications()));
	}

	@Test
	public void getAllDrugsTest() {
		final List<Drug> drugs = Drug.getDrugs("Zoloft", "", "Ibuprofen");
		assertTrue(drugs.get(0).getBrandName().toLowerCase().contains("zoloft"));
		assertTrue(StringUtils.isBlank(drugs.get(1).getBrandName()));
		assertTrue(drugs.get(2).getBrandName().toLowerCase().contains("ibuprofen"));
		assertEquals(drugs.size(), 3);
	}
}
