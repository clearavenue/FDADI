package com.clearavenue.fdadi.http;

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
import org.junit.Ignore;
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

@Ignore
public class MedicationListHttpTest {

	private static final Datastore mongo = MongoDB.instance().getDatabase();
	private static final UserProfileDAO dao = new UserProfileDAO(mongo);

	private static final String testUserName = "test";
	private static final String testUserPassword = "testertester";

	static WebDriver driver;
	static List<UserMedication> medsTestList = new ArrayList<UserMedication>();

	static {
		// Fill the List for the meds
		medsTestList.add(new UserMedication("Eye Allergy Relief"));
		medsTestList.add(new UserMedication("ABILIFY"));
	}

	@BeforeClass
	public static void addTestUser() {
		dao.save(new UserProfile(testUserName, testUserPassword));
	}

	@Before
	public void init() {
		DesiredCapabilities capabilities = DesiredCapabilities.htmlUnitWithJs();
		capabilities.setBrowserName("firefox");
		driver = new HtmlUnitDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get("https://agile.clearavenue.com/FDADI");
	}

	@Test
	public void validMedRegisterTest() {
		UserProfile user = dao.findByUserId(testUserName);

		// Create Medications for user
		UserMedication userMedAbilify = new UserMedication("ABILIFY");
		UserMedication userMedEyeAllergy = new UserMedication("Eye Allergy Relief");

		// Add medications to the user
		dao.addUserMedication(user, userMedAbilify, userMedEyeAllergy);

		WebElement element = driver.findElement(By.id("username"));
		element.sendKeys(testUserName);

		element = driver.findElement(By.id("pwd"));
		element.sendKeys(testUserPassword);

		element = driver.findElement(By.id("loginButton"));
		element.click();

		String expected = "myMedications - test";
		String actual = driver.getTitle();

		assertEquals(expected, actual);

		List<WebElement> items = driver.findElements(By.className("list-group-item"));
		Collection<String> medications = CollectionUtils.collect(items, new Transformer<WebElement, String>() {

			@Override
			public String transform(WebElement input) {
				return input.getText();
			}
		});

		for (UserMedication med : medsTestList) {
			assertTrue(medications.contains(med.getMedicationName()));
		}

	}

	@After
	public void teardown() {
		mongo.findAndDelete(mongo.createQuery(UserProfile.class).field("userId").equal(testUserName));
		driver.quit();
	}
}
