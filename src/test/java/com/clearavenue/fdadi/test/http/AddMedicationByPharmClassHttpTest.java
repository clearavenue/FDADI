/*
 * 
 */
package com.clearavenue.fdadi.test.http;

import static org.junit.Assert.assertEquals;
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
 * The Class AddMedicationByPharmClassHttpTest.
 */
public class AddMedicationByPharmClassHttpTest {

	/** The driver. */
	private static WebDriver driver;
	
	/** The dao. */
	private static final UserProfileDAO DAO = new UserProfileDAO(MongoDB.instance().getDatabase());

	/** The Constant testUserName. */
	private static final String TEST_USERID = "test";
	
	/** The Constant testUserPwd. */
	private static final String TEST_USER_PASSWORD = "testertester";

	/**
	 * Adds the test user.
	 */
	@BeforeClass
	public static final void addTestUser() {
		DAO.save(new UserProfile(TEST_USERID, TEST_USER_PASSWORD));
	}

	/**
	 * Inits the.
	 */
	@Before
	public void init() {

		DesiredCapabilities capabilities = DesiredCapabilities.htmlUnitWithJs();
		capabilities.setBrowserName("firefox");
		driver = new HtmlUnitDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get("http://52.0.199.20:8080/FDADI");
	
	}
	
	/**
	 * Valid add medication by pharm class test.
	 */
	@Test
	public void addMedicationByPharmClassTest(){
		
		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(TEST_USERID);

		element = driver.findElement(By.name("pwd"));
		element.sendKeys(TEST_USER_PASSWORD);

		driver.findElement(By.id("loginButton")).click();

		String expected = "myMedications - " + TEST_USERID;
		String actual = driver.getTitle();

		assertEquals(expected, actual);
		
		driver.findElement(By.id("addMedByPClassButton")).click();
		
		expected = "myMedications - " + TEST_USERID + " - Add Med By PharmClass";
		actual = driver.getTitle();
		
		assertEquals(expected, actual);
		
		// Click item in list "Androgen [EPC]"
		driver.findElement(By.xpath("//li[text() = 'Androgen [EPC]']")).click();
		
		// click add  "getButton"
		driver.findElement(By.id("getButton")).click();
		
		// check you are on the right page  "Add Med By Name"
		expected = "myMedications - " + TEST_USERID + " - Add Med By Name";
		actual = driver.getTitle();
		
		assertEquals(expected, actual);
		
		// click med in list  "DANAZOL"
		driver.findElement(By.xpath("//li[text() = 'DANAZOL']")).click();
		
		// click add "addButton"
		driver.findElement(By.id("addButton")).click();
		
		// verify on the right page "myMedication - username"
		expected = "myMedications - " + TEST_USERID;
		actual = driver.getTitle();
		
		assertEquals(expected, actual);
		
		// verify item is in the list "DANAZOL" 
		assertTrue(driver.findElement(By.xpath("//li[text() = 'DANAZOL']")).isDisplayed());
		
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
