/*
 *
 */
package com.clearavenue.fdadi.test.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.clearavenue.data.MongoDB;
import com.clearavenue.data.UserProfileDAO;
import com.clearavenue.data.objects.UserMedication;
import com.clearavenue.data.objects.UserProfile;

/**
 * The Class MedicationListHttpTest.
 */
public class MedicationListHttpTest {

	/** The Constant mongo. */
	private static final Datastore MONGO = MongoDB.instance().getDatabase();

	/** The Constant dao. */
	private static final UserProfileDAO DAO = new UserProfileDAO(MONGO);

	/** The Constant testUserName. */
	private static final String TEST_USERID = "test";

	/** The Constant testUserPassword. */
	private static final String TEST_PWD = "testertester";

	/** The Constant TIMEOUT. */
	private static final long TIMEOUT = 5;

	/** The driver. */
	private static WebDriver driver;

	/** The meds test list. */
	private static List<UserMedication> medsTestList = new ArrayList<UserMedication>();

	static {
		// Fill the List for the meds
		medsTestList.add(new UserMedication("Eye Allergy Relief"));
		medsTestList.add(new UserMedication("ABILIFY"));
	}

	/**
	 * Adds the test user.
	 */
	@BeforeClass
	public static final void addTestUser() {
		DAO.save(new UserProfile(TEST_USERID, TEST_PWD));
	}

	/**
	 * Inits the.
	 */
	@Before
	public final void init() {
		final DesiredCapabilities capabilities = DesiredCapabilities.htmlUnitWithJs();
		capabilities.setBrowserName("firefox");
		driver = new HtmlUnitDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);

		driver.get("https://agile.clearavenue.com/FDADI");
	}

	/**
	 * Valid med register test.
	 */
	@Test
	public final void validMedRegisterTest() {
		final UserProfile user = DAO.findByUserId(TEST_USERID);

		// Create Medications for user
		final UserMedication userMedAbilify = new UserMedication("ABILIFY");
		final UserMedication userMedEyeAllergy = new UserMedication("Eye Allergy Relief");

		// Add medications to the user
		DAO.addUserMedication(user, userMedAbilify, userMedEyeAllergy);

		WebElement element = driver.findElement(By.id("username"));
		element.sendKeys(TEST_USERID);

		element = driver.findElement(By.id("pwd"));
		element.sendKeys(TEST_PWD);

		element = driver.findElement(By.id("loginButton"));
		element.click();

		final String expected = "myMedications - test";
		final String actual = driver.getTitle();

		assertEquals(expected, actual);

		final List<WebElement> items = driver.findElements(By.className("list-group-item"));
		final Collection<String> medications = CollectionUtils.collect(items, new Transformer<WebElement, String>() {

			@Override
			public String transform(final WebElement input) {
				return input.getText();
			}
		});

		for (final UserMedication med : medsTestList) {
			assertTrue(medications.contains(med.getMedicationName()));
		}

	}

	/**
	 * Teardown.
	 */
	@After
	public final void teardown() {
		MONGO.findAndDelete(MONGO.createQuery(UserProfile.class).field("userId").equal(TEST_USERID));
		driver.quit();
	}
}
