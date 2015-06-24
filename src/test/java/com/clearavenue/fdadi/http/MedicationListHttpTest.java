package com.clearavenue.fdadi.http;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
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
import com.clearavenue.data.objects.UserMedication;
import com.clearavenue.data.objects.UserProfile;

public class MedicationListHttpTest {
	
	private static final Datastore mongo = MongoDB.instance().getDatabase();
	private static final UserProfileDAO dao = new UserProfileDAO(mongo);

	private static final String testUserName = "test";
	private static final String testUserPassword = "testertester";
	
	static WebDriver driver;
	static List<UserMedication> medsTestList = new ArrayList<UserMedication>();

	@Before
	public void init() {

		DesiredCapabilities capabilities = DesiredCapabilities.htmlUnitWithJs();
		capabilities.setBrowserName("firefox");
		driver = new HtmlUnitDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get("http://52.0.199.20:8080/FDADI");
		
		//Fill the List for the meds
		medsTestList.add(new UserMedication("Eye Allergy Relief"));
		medsTestList.add(new UserMedication("ABILIFY"));
		
	}

	
	@Test
	public void validFilledMedRegisterTest() {
		
		//Create user profile
		UserProfile newUser = new UserProfile(testUserName, testUserPassword);
		
		//Create Medications for user
		UserMedication userMedAbilify = new UserMedication("ABILIFY");
		UserMedication userMedEyeAllergy = new UserMedication("Eye Allergy Relief");
		
		//Add medications to the user
		dao.addUserMedication(newUser, userMedAbilify);
		dao.addUserMedication(newUser, userMedEyeAllergy);
		
		
		WebElement element = driver.findElement(By.id("username"));
		element.sendKeys(testUserName);

		element = driver.findElement(By.id("pwd"));
		element.sendKeys(testUserPassword);

		element = driver.findElement(By.id("registerButton"));
		element.click();

		String expected = "FDADI - test";
		String actual = driver.getTitle();

		assertEquals(expected, actual);
		
	}
	
	//@Test
	public void verifyMedicationList(){
		UserProfile newUser = new UserProfile(testUserName, testUserPassword);
		
//		List<UserMedication> allMedications = dao.get(testUserName).getMedications();
		
		System.out.println("all - " + newUser.getMedications().size() + "  meds - " + medsTestList.size());
		if (newUser.getMedications().size() == medsTestList.size()){

			medsTestList.containsAll(newUser.getMedications());
		}
		else{
			Assert.fail("Verify Medication List Test Failed: Lists are not the same size.");
		}
	}
	
	
	@After
	public void teardown() {
		mongo.findAndDelete(mongo.createQuery(UserProfile.class)
				.field("userId").equal("test"));
		driver.quit();
	}
}
