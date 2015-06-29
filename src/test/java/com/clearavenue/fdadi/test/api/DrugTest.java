/*
 *
 */
package com.clearavenue.fdadi.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.clearavenue.fdadi.api.ApiQueries;
import com.clearavenue.fdadi.api.Drug;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * The Class DrugTest.
 */
public class DrugTest {

	/** The label. */
	private static String label;

	/** The zoloft. */
	private static Drug zoloft;

	/** The blank label. */
	private static String blankLabel;

	/** The blank drug. */
	private static Drug blankDrug;

	/**
	 * Gets the zoloft label.
	 */
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

	/**
	 * Gets the adverse effects test.
	 */
	@Test
	public final void getAdverseEffectsTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		final String sideEffects = zoloft.getSideEffects();
		assertTrue(sideEffects.contains("Nausea"));
	}

	/**
	 * Gets the no adverse effects test.
	 */
	@Test
	public final void getNoAdverseEffectsTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		final String sideEffects = zoloft.getSideEffects();
		assertFalse(sideEffects.contains("Leprosy"));
	}

	/**
	 * Gets the usage test.
	 */
	@Test
	public final void getUsageTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		final String usage = zoloft.getUsage();
		assertTrue(usage.contains("Panic"));
	}

	/**
	 * Gets the not usage test.
	 */
	@Test
	public final void getNotUsageTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		final String usage = zoloft.getUsage();
		assertFalse(usage.contains("Birth Control"));
	}

	/**
	 * Gets the contraindications test.
	 */
	@Test
	public final void getContraindicationsTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		final String usage = zoloft.getCounterindications();
		assertTrue(usage.contains("disulfiram"));
	}

	/**
	 * Gets the no contraindications test.
	 */
	@Test
	public final void getNoContraindicationsTest() {
		if (StringUtils.isBlank(label)) {
			fail("Zoloft label should not be empty");
		}

		final String usage = zoloft.getCounterindications();
		assertFalse(usage.contains("vitamin c"));
	}

	/**
	 * Checks if is name blank test.
	 */
	@Test
	public final void isNameBlankTest() {
		assertTrue(StringUtils.isBlank(blankDrug.getBrandName()));
	}

	/**
	 * Checks if is generic name blank test.
	 */
	@Test
	public final void isGenericNameBlankTest() {
		assertTrue(StringUtils.isBlank(blankDrug.getGenericName()));
	}

	/**
	 * Checks if is usage blank test.
	 */
	@Test
	public final void isUsageBlankTest() {
		assertTrue(StringUtils.isBlank(blankDrug.getUsage()));
	}

	/**
	 * Checks if is interactions blank test.
	 */
	@Test
	public final void isInteractionsBlankTest() {
		assertTrue(StringUtils.isBlank(blankDrug.getInteractions()));
	}

	/**
	 * Checks if is indications blank test.
	 */
	@Test
	public final void isIndicationsBlankTest() {
		assertTrue(StringUtils.isBlank(blankDrug.getIndications()));
	}

	/**
	 * Checks if is counterindications blank test.
	 */
	@Test
	public final void isCounterindicationsBlankTest() {
		assertTrue(StringUtils.isBlank(blankDrug.getCounterindications()));
	}

	/**
	 * Gets the all drugs test.
	 *
	 */
	@Test
	public final void getAllDrugsTest() {
		final int expectedSize = 3;
		final List<Drug> drugs = Drug.getDrugs("Zoloft", "", "Ibuprofen");
		assertTrue(drugs.get(0).getBrandName().toLowerCase().contains("zoloft"));
		assertTrue(StringUtils.isBlank(drugs.get(1).getBrandName()));
		assertTrue(drugs.get(2).getBrandName().toLowerCase().contains("ibuprofen"));
		assertEquals(expectedSize, drugs.size());
	}
}
