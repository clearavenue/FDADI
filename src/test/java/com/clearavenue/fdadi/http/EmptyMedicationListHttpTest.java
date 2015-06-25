package com.clearavenue.fdadi.http;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.clearavenue.data.MongoDB;
import com.clearavenue.data.objects.UserMedication;
import com.clearavenue.data.objects.UserProfile;

@Ignore
public class EmptyMedicationListHttpTest {

	static WebDriver driver;
	static List<UserMedication> medsTestList = new ArrayList<UserMedication>();

	private static final Datastore mongo = MongoDB.instance().getDatabase();

	private static final String emptyMedTestUserName = "emptyTest";
	private static final String emptyMedTestUserPassword = "testertester";

	@Before
	public void init() {

		DesiredCapabilities capabilities = DesiredCapabilities.htmlUnitWithJs();
		capabilities.setBrowserName("firefox");
		driver = new HtmlUnitDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get("http://52.0.199.20:8080/FDADI");

	}

	@Test
	public void validEmptyMedRegisterTest() {

		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(emptyMedTestUserName);

		element = driver.findElement(By.name("pwd"));
		element.sendKeys(emptyMedTestUserPassword);

		element = driver.findElement(By.id("registerButton"));
		element.click();

		String expected = "myMedications - emptyTest";
		String actual = driver.getTitle();

		assertEquals(expected, actual);

	}

	@Test
	public void verifyEmptyMedicationList() {

		UserProfile user = new UserProfile();
		user.setUserId(emptyMedTestUserName);

		assertEquals(0, user.getMedications().size());
	}

	@After
	public void teardown() {
		mongo.findAndDelete(mongo.createQuery(UserProfile.class).field("userId").equal("emptyTest"));
		driver.quit();
	}

}
