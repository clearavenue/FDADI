package com.clearavenue.fdadi.http.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class LoginHttpTest {

	static WebDriver driver;

	@Before
	public void init() {

		DesiredCapabilities capabilities = DesiredCapabilities.htmlUnitWithJs();
		capabilities.setBrowserName("firefox");
		driver = new HtmlUnitDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get("http://52.0.199.20:8080/FDADI");
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
		element.sendKeys("bill");

		element = driver.findElement(By.name("pwd"));
		element.sendKeys("hunt");

		element.submit();

		String expected = "FDADI - bill";
		String actual = driver.getTitle();

		assertEquals(expected, actual);
	}

	@Test
	public void invalidLoginTest() {

		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys("bill");

		element = driver.findElement(By.name("pwd"));
		element.sendKeys("badpassword");

		element.submit();

		String url = driver.getCurrentUrl();
		assertTrue(url.contains("loginError"));
	}

	public void validateUsernameTest() {

		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys("bi&ll");

		element = driver.findElement(By.id("usernameGroup"));
		String clazz = element.getAttribute("class");
		System.out.println(clazz);
	}

	public void validatePasswordTest() {

		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys("bill");

		element = driver.findElement(By.name("pwd"));
		element.sendKeys("badpassword");

		element.submit();

		String url = driver.getCurrentUrl();
		assertTrue(url.contains("loginError"));
	}

	@After
	public void teardown() {
		driver.quit();
	}
}