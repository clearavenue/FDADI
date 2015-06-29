/*
 *
 */
package com.clearavenue.fdadi.test.http.registration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import com.clearavenue.data.UserProfileDAO;
import com.clearavenue.data.objects.UserProfile;

/**
 * The Class RegistrationHttpTest.
 */
public class RegistrationHttpTest {

	/** The driver. */
	private static WebDriver driver;

	/** The Constant mongo. */
	private static final Datastore MONGO = MongoDB.instance().getDatabase();

	/** The Constant dao. */
	private static final UserProfileDAO DAO = new UserProfileDAO(MONGO);

	/** The test user id. */
	private static final String TEST_USERID = "registrationtestuser";

	/** The test user pwd. */
	private static final String TEST_PWD = "newpwd";

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

		MONGO.findAndDelete(MONGO.createQuery(UserProfile.class).field("userId").equal(TEST_USERID));
	}

	/**
	 * Valid registration test.
	 */
	@Test
	public final void validRegistrationTest() {
		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(TEST_USERID);

		element = driver.findElement(By.name("pwd"));
		element.sendKeys(TEST_PWD);

		element = driver.findElement(By.id("registerButton"));
		element.click();

		final String expected = "myMedications - registrationtestuser";
		final String actual = driver.getTitle();
		assertEquals(expected, actual);
	}

	/**
	 * Invalid register test.
	 */
	@Test
	public final void invalidRegisterTest() {
		final UserProfile newuser = new UserProfile(TEST_USERID, TEST_PWD);
		DAO.save(newuser);

		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(TEST_USERID);

		element = driver.findElement(By.name("pwd"));
		element.sendKeys(TEST_PWD);

		element = driver.findElement(By.id("registerButton"));
		element.click();

		final String url = driver.getCurrentUrl();
		assertTrue(url.contains("loginError"));

		element = driver.findElement(By.id("warning"));
		final WebElement panelBody = element.findElement(By.className("panel-body"));
		final WebElement div = panelBody.findElement(By.tagName("div"));
		assertEquals("Registration failed: Username is already taken.", div.getText());
	}

	/**
	 * Cleanup.
	 */
	@After
	public final void cleanup() {
		MONGO.findAndDelete(MONGO.createQuery(UserProfile.class).field("userId").equal(TEST_USERID));
		driver.quit();
	}
}