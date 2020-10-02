package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {
	private static String firstName = "rita";
	private static String lastName = "ren";
	private static String userName = "reny";
	private static String password = "123";
	private static String noteTitle = "example note";
	private static String noteDescription = "example note description";
	private static String credentialURL = "www.exampleCredentialURL.com";
	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}
	@Test
	public void getUnauthorizedHomePage() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}
	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}
	@Test
	public void newUserAccessTest() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		// Signup
		driver.get("http://localhost:" + this.port + "/signup");
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.sendKeys(firstName);
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.sendKeys(lastName);
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement signUpButton = driver.findElement(By.id("signup"));
		signUpButton.click();

		//Login
		driver.get("http://localhost:" + this.port + "/login");
		inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("login"));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());

		//Logout
		WebElement logoutButton = driver.findElement(By.id("logout"));
		logoutButton.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("login")));
		Assertions.assertEquals("Login", driver.getTitle());

		//Unauthorized Access Restrictions
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}
}
