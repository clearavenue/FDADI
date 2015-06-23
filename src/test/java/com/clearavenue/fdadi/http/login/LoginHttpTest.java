package com.clearavenue.fdadi.http.login;

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

public class LoginHttpTest {

	static WebDriver driver;
	static UserProfileDAO dao = new UserProfileDAO(MongoDB.instance().getDatabase());

	@BeforeClass
	public static void addTestUser() {
		dao.save(new UserProfile("loginhttptestuser", "loginhttptestpwd"));
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
	public void validLoginPageTest() {

		String expected = "FDADI Login";
		String actual = driver.getTitle();

		assertEquals(expected, actual);
	}

	@Test
	public void validLoginTest() {

		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys("loginhttptestuser");

		element = driver.findElement(By.name("pwd"));
		element.sendKeys("loginhttptestpwd");

		element.submit();

		String expected = "FDADI - loginhttptestuser";
		String actual = driver.getTitle();

		assertEquals(expected, actual);
	}

	@Test
	public void invalidLoginTest() {

		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys("loginhttptestuser");

		element = driver.findElement(By.name("pwd"));
		element.sendKeys("badpassword");

		element = driver.findElement(By.id("loginButton"));
		element.click();

		String url = driver.getCurrentUrl();
		assertTrue(url.contains("loginError"));
	}

	@After
	public void teardown() {
		driver.quit();
	}

	@AfterClass
	public static void removeTestUser() {
		dao.delete(dao.findByUserId("loginhttptestuser"));
	}
}