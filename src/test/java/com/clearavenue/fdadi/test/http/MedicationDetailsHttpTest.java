/*
 *
 */
package com.clearavenue.fdadi.test.http;

import static org.junit.Assert.assertEquals;

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
 * The Class MedicationDetailsHttpTest.
 */
public class MedicationDetailsHttpTest {

	/** The driver. */
	private static WebDriver driver;

	/** the dao. */
	private static final UserProfileDAO DAO = new UserProfileDAO(MongoDB.instance().getDatabase());
	
	/** The Constant testUserName. */
	private static final String TEST_USERID = "test";
	
	/** The Constant testUserPwd. */
	private static final String TEST_USER_PASSWORD = "testertester";

	/**
	 * Adds the test user.
	 */
	@BeforeClass
	public static final void addTestUser(){
		DAO.save(new UserProfile(TEST_USERID, TEST_USER_PASSWORD));
	}
	
	/**
	 * Inits the diver.
	 */
	@Before
	public void init() {

		final DesiredCapabilities capabilities = DesiredCapabilities.htmlUnitWithJs();
		capabilities.setBrowserName("firefox");
		driver = new HtmlUnitDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get("http://52.0.199.20:8080/FDADI");

	}

	/** 
	 * Get Medication Details test. 
	 */
	@Test
	public void getMedDetailsTest(){

		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(TEST_USERID);

		element = driver.findElement(By.name("pwd"));
		element.sendKeys(TEST_USER_PASSWORD);

		driver.findElement(By.id("loginButton")).click();

		String expected = "myMedications - " + TEST_USERID;
		String actual = driver.getTitle();

		assertEquals(expected, actual);

		driver.findElement(By.id("addMedByNameButton")).click();

		expected = "myMedications - " + TEST_USERID + " - Add Med By Name";
		actual = driver.getTitle();

		assertEquals(expected, actual);

		driver.findElement(By.xpath("//li[text() = 'Fleet']")).click();

		driver.findElement(By.id("addButton")).click();

		expected = "myMedications - " + TEST_USERID;
		actual = driver.getTitle();

		assertEquals(expected, actual);

		driver.findElement(By.xpath("//li[text() = 'Fleet']")).click();


		driver.findElement(By.id("medDetails")).click();

		expected = "myMedications - Medication information";
		actual = driver.getTitle();

		assertEquals(expected, actual);
	}

	/**
	 * Teardown.
	 */
	@After
	public void teardown() {
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
