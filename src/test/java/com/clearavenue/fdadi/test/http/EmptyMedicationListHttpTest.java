/*
 *
 */
package com.clearavenue.fdadi.test.http;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.clearavenue.data.MongoDB;
import com.clearavenue.data.objects.UserProfile;

/**
 * The Class EmptyMedicationListHttpTest.
 */
public class EmptyMedicationListHttpTest {

	/** The driver. */
	private static WebDriver driver;

	/** The Constant mongo. */
	private static final Datastore MONGO = MongoDB.instance().getDatabase();

	/** The Constant emptyMedTestUserName. */
	private static final String TEST_USERID = "emptyTest";

	/** The Constant emptyMedTestUserPassword. */
	private static final String TEST_PWD = "testertester";

	/** The Constant TIMEOUT. */
	private static final long TIMEOUT = 5;

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
	 * Valid empty med register test.
	 */
	@Test
	public final void validEmptyMedRegisterTest() {

		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(TEST_USERID);

		element = driver.findElement(By.name("pwd"));
		element.sendKeys(TEST_PWD);

		element = driver.findElement(By.id("registerButton"));
		element.click();

		final String expected = "myMedications - emptyTest";
		final String actual = driver.getTitle();

		assertEquals(expected, actual);

	}

	/**
	 * Verify empty medication list.
	 */
	@Test
	public final void verifyEmptyMedicationList() {

		final UserProfile user = new UserProfile();
		user.setUserId(TEST_USERID);

		assertEquals(0, user.getMedications().size());
	}

	/**
	 * Teardown.
	 */
	@After
	public final void teardown() {
		MONGO.findAndDelete(MONGO.createQuery(UserProfile.class).field("userId").equal("emptyTest"));
		driver.quit();
	}

}
