package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.Duration;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {
	private static String firstName = "rita";
	private static String lastName = "ren";
	private static String userName = "reny";
	private static String password = "123";
	private static String noteTitle = "example note";
	private static String newNoteTitle = "new Note";
	private static String noteDescription = "example note description";
	private static String credentialURL = "www.exampleCredentialURL.com";
	private static String newCredentialUsername = "reny2.0";


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

	@Order(1)
	@Test
	public void signupPageTest() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Order(2)
	@Test
	public void unauthorizedHomePageTest() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	public void loginPageTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("login"));
		loginButton.click();
		Thread.sleep(1000);
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Order(3)
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
		loginPageTest();
		//Logout
		WebElement logoutButton = driver.findElement(By.id("logout"));
		logoutButton.click();
		Thread.sleep(2000);
		Assertions.assertEquals(true, driver.getCurrentUrl().contains("login?logout"));
		//Unauthorized Access Restrictions
		unauthorizedHomePageTest();
	}

//	@Order(4)
//	@Test
	public void noteTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait (driver, 30);
		JavascriptExecutor jse =(JavascriptExecutor) driver;
		//Login
		loginPageTest();

		//Note Creation
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);
		wait.withTimeout(Duration.ofSeconds(30));
		WebElement newNoteButton = driver.findElement(By.id("newnote"));
		wait.until(ExpectedConditions.elementToBeClickable(newNoteButton)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
		WebElement notedescription = driver.findElement(By.id("note-description"));
		notedescription.sendKeys(noteDescription);
		WebElement savechanges = driver.findElement(By.id("save-changes"));
		savechanges.click();
		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("tbody"));
		Assertions.assertEquals(1, notesList.size());

		//Note Viewing
		notesTable = driver.findElement(By.id("userTable"));
		notesList = notesTable.findElements(By.tagName("th"));
		Boolean created = false;
		for (int i=0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			if (element.getAttribute("innerHTML").equals(noteTitle)) {
				created = true;
				break;
			}
		}
		Assertions.assertTrue(created);

		//Note Editing
		List<WebElement> notesOptionList = notesTable.findElements(By.tagName("td"));
		WebElement editElement = null;
		for (int i = 0; i < notesOptionList.size(); i++) {
			WebElement element = notesOptionList.get(i);
			editElement = element.findElement(By.id("note-edit"));
			if (editElement != null){
				break;
			}
		}
		wait.until(ExpectedConditions.elementToBeClickable(editElement)).click();
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		wait.until(ExpectedConditions.elementToBeClickable(noteTitle));
		noteTitle.clear();
		noteTitle.sendKeys(newNoteTitle);
		savechanges = driver.findElement(By.id("save-changes"));
		savechanges.click();
		Assertions.assertEquals("Home", driver.getTitle());

		//Note Editing Check
		notesTable = driver.findElement(By.id("userTable"));
		notesList = notesTable.findElements(By.tagName("th"));
		Boolean editeSuccess = false;
		for (int i = 0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			if (element.getAttribute("innerHTML").equals(newNoteTitle)) {
				editeSuccess = true;
				break;
			}
		}
		Assertions.assertTrue(editeSuccess);

		//Note Deletion
		notesTable = driver.findElement(By.id("userTable"));
		notesList = notesTable.findElements(By.tagName("td"));
		int notesSizeBeforeDelete = notesTable.findElements(By.tagName("th")).size();
		WebElement element = notesList.get(0);
		WebElement deleteElement = element.findElement(By.id("note-delete"));
		wait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
		notesTable = driver.findElement(By.id("userTable"));
		notesList = notesTable.findElements(By.tagName("th"));
		int notesSizeAfterDelete = notesTable.findElements(By.tagName("th")).size();
		Assertions.assertEquals(notesSizeBeforeDelete - 1, notesSizeAfterDelete);
	}
	@Order(5)
	@Test
	public void credentialTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait (driver, 30);
		JavascriptExecutor jse =(JavascriptExecutor) driver;
		//Login
		loginPageTest();
		//Credential Creation
		WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialTab);
		WebElement newCredential = driver.findElement(By.id("new-credential"));
		wait.until(ExpectedConditions.elementToBeClickable(newCredential)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(credentialURL);
		WebElement credUsername = driver.findElement(By.id("credential-username"));
		credUsername.sendKeys(userName);
		WebElement credPassword = driver.findElement(By.id("credential-password"));
		credPassword.sendKeys(password);
		WebElement submitButton = driver.findElement(By.id("submit-credential"));
		wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
		WebElement credentialTableTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credentialList = credentialTableTable.findElements(By.tagName("tbody"));
		Assertions.assertEquals(1, credentialList.size());

		//Credential Viewing

		WebElement credentialTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credList = credentialTable.findElements(By.tagName("th"));
		Boolean created = false;
		for (int i=0; i < credList.size(); i++) {
			WebElement element = credList.get(i);
			if (element.getAttribute("innerHTML").equals(userName)) {
				created = true;
				break;
			}
		}
		Assertions.assertTrue(created);

		//Credential Editing
		WebElement credsTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credsList = credsTable.findElements(By.tagName("td"));
		WebElement editElement = null;
		for (int i = 0; i < credsList.size(); i++) {
			WebElement element = credsList.get(i);
			editElement = element.findElement(By.id("credential-edit"));
			if (editElement != null){
				break;
			}
		}
		wait.until(ExpectedConditions.elementToBeClickable(editElement)).click();
		credUsername = driver.findElement(By.id("credential-username"));
		wait.until(ExpectedConditions.elementToBeClickable(credUsername));
		credUsername.clear();
		credUsername.sendKeys(newCredentialUsername);
		WebElement saveChange = driver.findElement(By.id("submit-credential"));
		wait.until(ExpectedConditions.elementToBeClickable(saveChange));
		Assertions.assertEquals("Home", driver.getTitle());
	}
}
