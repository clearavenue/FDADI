package com.clearavenue.fdadi.http.registration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import com.clearavenue.data.UserProfileDAO;
import com.clearavenue.data.objects.UserProfile;

@Ignore
public class RegistrationHttpTest {

	static WebDriver driver;
	private static final Datastore mongo = MongoDB.instance().getDatabase();
	private static final UserProfileDAO dao = new UserProfileDAO(mongo);

	private final String testUserId = "registrationtestuser";
	private final String testUserPwd = "newpwd";

	@Before
	public void init() {
		final DesiredCapabilities capabilities = DesiredCapabilities.htmlUnitWithJs();
		capabilities.setBrowserName("firefox");
		driver = new HtmlUnitDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		driver.get("https://agile.clearavenue.com/FDADI");

		mongo.findAndDelete(mongo.createQuery(UserProfile.class).field("userId").equal(testUserId));
	}

	@Test
	public void validRegistrationTest() {
		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(testUserId);

		element = driver.findElement(By.name("pwd"));
		element.sendKeys(testUserPwd);

		element = driver.findElement(By.id("registerButton"));
		element.click();

		final String expected = "myMedications - registrationtestuser";
		final String actual = driver.getTitle();
		assertEquals(expected, actual);
	}

	@Test
	public void invalidRegisterTest() {
		final UserProfile newuser = new UserProfile(testUserId, testUserPwd);
		dao.save(newuser);

		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(testUserId);

		element = driver.findElement(By.name("pwd"));
		element.sendKeys(testUserPwd);

		element = driver.findElement(By.id("registerButton"));
		element.click();

		final String url = driver.getCurrentUrl();
		assertTrue(url.contains("loginError"));

		element = driver.findElement(By.id("warning"));
		WebElement panelBody = element.findElement(By.className("panel-body"));
		WebElement div = panelBody.findElement(By.tagName("div"));
		assertEquals("Registration failed: Username is already taken.", div.getText());
	}

	@After
	public void cleanup() {
		mongo.findAndDelete(mongo.createQuery(UserProfile.class).field("userId").equal(testUserId));
		driver.quit();
	}
}