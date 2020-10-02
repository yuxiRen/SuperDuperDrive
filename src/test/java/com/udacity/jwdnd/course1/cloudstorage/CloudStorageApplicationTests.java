package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
	public void getSignupPageTest() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void getLoginPageTest() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}
	@Test
	public void getUnauthorizedHomePageTest() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}
	@Test
	public void accessTest() throws InterruptedException {
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
		Thread.sleep(80);
		Assertions.assertEquals(true, driver.getCurrentUrl().contains("login?logout"));
		//Unauthorized Access Restrictions
		getUnauthorizedHomePageTest();
	}
	@Test
	public void createNoteTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait (driver, 30);
		JavascriptExecutor jse =(JavascriptExecutor) driver;
		//Login
		driver.get("http://localhost:" + this.port + "/login");
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("login"));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());
//
//		//added note
//		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
//		jse.executeScript("arguments[0].click()", notesTab);
//		wait.withTimeout(Duration.ofSeconds(30));
//		WebElement newNote = driver.findElement(By.id("newnote"));
//		wait.until(ExpectedConditions.elementToBeClickable(newNote)).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
//		WebElement notedescription = driver.findElement(By.id("note-description"));
//		notedescription.sendKeys(noteDescription);
//		WebElement savechanges = driver.findElement(By.id("save-changes"));
//		savechanges.click();
//		Assertions.assertEquals("Result", driver.getTitle());
//
//		//check for note
//		driver.get("http://localhost:" + this.port + "/home");
//		notesTab = driver.findElement(By.id("nav-notes-tab"));
//		jse.executeScript("arguments[0].click()", notesTab);
//		WebElement notesTable = driver.findElement(By.id("userTable"));
//		List<WebElement> notesList = notesTable.findElements(By.tagName("th"));
//		Boolean created = false;
//		for (int i=0; i < notesList.size(); i++) {
//			WebElement element = notesList.get(i);
//			if (element.getAttribute("innerHTML").equals(noteTitle)) {
//				created = true;
//				break;
//			}
//		}
//		Assertions.assertTrue(created);
	}
}
