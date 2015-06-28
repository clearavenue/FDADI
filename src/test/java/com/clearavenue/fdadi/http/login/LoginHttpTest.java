package com.clearavenue.fdadi.http.login;

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

public class LoginHttpTest {

	static WebDriver driver;
	static UserProfileDAO dao = new UserProfileDAO(MongoDB.instance().getDatabase());

	static final String testUserName = "loginhttptestuser";
	static final String testUserPwd = "loginhttptestpwd";

	@BeforeClass
	public static void addTestUser() {
		dao.save(new UserProfile(testUserName, testUserPwd));
	}

	@Before
	public void init() {
		DesiredCapabilities capabilities = DesiredCapabilities.htmlUnitWithJs();
		capabilities.setBrowserName("firefox");
		driver = new HtmlUnitDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		driver.get("https://agile.clearavenue.com/FDADI");
	}

	@Test
	public void validLoginPageTest() {
		String expected = "myMedications Login";
		String actual = driver.getTitle();

		assertEquals(expected, actual);
	}

	@Test
	public void validFaqPageTest() {
		driver.findElement(By.cssSelector("a[href*='faq']")).click();

		String expected = "myMedications FAQ";
		String actual = driver.getTitle();

		assertEquals(expected, actual);
	}

	@Test
	public void validLoginTest() {
		UserProfile user = dao.findByUserId(testUserName);
		assertNotNull(user);

		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(testUserName);

		element = driver.findElement(By.name("pwd"));
		element.sendKeys(testUserPwd);

		element = driver.findElement(By.id("loginButton"));
		element.click();

		String expected = String.format("myMedications - %s", testUserName);
		String actual = driver.getTitle();

		assertEquals(expected, actual);
	}

	@Test
	public void invalidLoginTest() {
		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys(testUserName);

		element = driver.findElement(By.name("pwd"));
		element.sendKeys("badpassword");

		element = driver.findElement(By.id("loginButton"));
		element.click();

		String url = driver.getCurrentUrl();
		assertTrue(url.contains("loginError"));

		element = driver.findElement(By.id("warning"));
		WebElement panelBody = element.findElement(By.className("panel-body"));
		WebElement div = panelBody.findElement(By.tagName("div"));
		assertEquals("Invalid login/password", div.getText());
	}

	@After
	public void teardown() {
		driver.quit();
	}

	@AfterClass
	public static void removeTestUser() {
		dao.delete(dao.findByUserId(testUserName));
	}
}