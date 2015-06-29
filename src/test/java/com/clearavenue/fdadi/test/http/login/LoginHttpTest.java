/*
 *
 */
package com.clearavenue.fdadi.test.http.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.clearavenue.data.MongoDB;
import com.clearavenue.data.UserProfileDAO;
import com.clearavenue.data.objects.UserProfile;

/**
 * The Class LoginHttpTest.
 */
public class LoginHttpTest {

	/** The driver. */
	private static WebDriver driver;

	/** The dao. */
	private static final UserProfileDAO DAO = new UserProfileDAO(MongoDB.instance().getDatabase());

	/** The Constant testUserName. */
	private static final String TEST_USERID = "loginhttptestuser";

	/** The Constant testUserPwd. */
	private static final String TEST_PWD = "loginhttptestpwd";

	/** The Constant TIMEOUT. */
	private static final long TIMEOUT = 5;

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
	 * Valid login page test.
	 */
	@Test
	public final void validLoginPageTest() {
		final String expected = "myMedications Login";
		final String actual = driver.getTitle();

		assertEquals(expected, actual);
	}

	/**
	 * Valid faq page test.
	 */
	@Test
	public final void validFaqPageTest() {
		driver.findElement(By.cssSelector("a[href*='faq']")).click();

		final String expected = "myMedications FAQ";
		final String actual = driver.getTitle();

		assertEquals(expected, actual);
	}

	/**
	 * Valid login test.
	 */
	@Test
	public final void validLoginTest() {
		final UserProfile user = DAO.findByUserId(TEST_USERID);
		assertNotNull(user);

		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(TEST_USERID);

		element = driver.findElement(By.name("pwd"));
		element.sendKeys(TEST_PWD);

		element = driver.findElement(By.id("loginButton"));
		element.click();

		final String expected = String.format("myMedications - %s", TEST_USERID);
		final String actual = driver.getTitle();

		assertEquals(expected, actual);
	}

	/**
	 * Invalid login test.
	 */
	@Test
	public final void invalidLoginTest() {
		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(TEST_USERID);

		element = driver.findElement(By.name("pwd"));
		element.sendKeys("badpassword");

		element = driver.findElement(By.id("loginButton"));
		element.click();

		final String url = driver.getCurrentUrl();
		assertTrue(url.contains("loginError"));

		element = driver.findElement(By.id("warning"));
		final WebElement panelBody = element.findElement(By.className("panel-body"));
		final WebElement div = panelBody.findElement(By.tagName("div"));
		assertEquals("Invalid login/password", div.getText());
	}

	/**
	 * Teardown.
	 */
	@After
	public final void teardown() {
		driver.quit();
	}

	/**
	 * Removes the test user.
	 */
	@AfterClass
	public static final void removeTestUser() {
		DAO.delete(DAO.findByUserId(TEST_USERID));
	}
}