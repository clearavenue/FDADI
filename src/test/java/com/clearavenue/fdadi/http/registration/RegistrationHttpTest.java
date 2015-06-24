package com.clearavenue.fdadi.http.registration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

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

public class RegistrationHttpTest {

	static WebDriver driver;
	private static final Datastore mongo = MongoDB.instance().getDatabase();
	private static final UserProfileDAO dao = new UserProfileDAO(mongo);

	private final String testUserId = "registrationtestuser";  
	private final String testUserPwd = "newpwd";

	@Before
	public void init() {

		DesiredCapabilities capabilities = DesiredCapabilities.htmlUnitWithJs();
		capabilities.setBrowserName("firefox");
		driver = new HtmlUnitDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get("https://agile.clearavenue.com/FDADI");  
		
		mongo.findAndDelete(mongo.createQuery(UserProfile.class).field("userId").equal(testUserId));
	}
	
//	@Test
	public void validRegistrationTest() {
		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(testUserId);  

		element = driver.findElement(By.name("pwd"));
		element.sendKeys(testUserPwd);   

		element = driver.findElement(By.id("registerButton"));
		element.click();   

		String expected = "FDADI - registrationtestuser";
		String actual = driver.getTitle();
		System.out.println(actual);
		assertEquals(expected, actual);
	}
//	@Test
	public void invalidRegisterTest() {
		UserProfile newuser = new UserProfile(testUserId, testUserPwd);
		dao.save(newuser);

		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(testUserId);

		element = driver.findElement(By.name("pwd"));
		element.sendKeys(testUserPwd);

		element = driver.findElement(By.id("registerButton"));
		element.click();   

		String url = driver.getCurrentUrl();
		System.out.println(url);
		assertTrue(url.contains("loginError"));

	}
}