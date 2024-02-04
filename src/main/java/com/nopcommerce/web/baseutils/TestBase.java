package com.nopcommerce.web.baseutils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class TestBase {

	private Properties locators;
	private WebDriverWait wait;
	protected WebDriver driver;
	private Properties validationMessages;


	public TestBase(WebDriver driver, String Filepath) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		this.locators = loadLocators(Filepath);
		System.out.println("Properties loaded in test base");

	}

	protected Properties loadLocators(String filePath) {
		Properties properties = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(filePath)) {
			if (input == null) {
				throw new IOException("Unable to find the properties file: " + filePath);
			}
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error loading properties file: " + filePath, e);
		}
		return properties;
	}

	//    private void printLocators() {
	//       // System.out.println("Locators:");
	//        locators.forEach((key, value) -> System.out.println(key + ": " + value));
	//    }

	protected By getLocator(String key) {
		String locator = locators.getProperty(key);
		if (locator == null) {
			throw new IllegalArgumentException("Locator not found for key: " + key);
		}

		String[] split = locator.split(":");
		String locatorType = split[0];
		String locatorValue = split[1];

		switch (locatorType) {
		case "id":
			return By.id(locatorValue);
		case "name":
			return By.name(locatorValue);
		case "classname":
		case "class":
			return By.className(locatorValue);
		case "tagname":
		case "tag":
			return By.tagName(locatorValue);
		case "linktext":
		case "link":
			return By.linkText(locatorValue);
		case "partiallinktext":
		case "partiallink":
			return By.partialLinkText(locatorValue);
		case "cssselector":
		case "css":
			return By.cssSelector(locatorValue);
		case "xpath":
			return By.xpath(locatorValue);
		default:
			throw new IllegalArgumentException("Unsupported locator type: " + locatorType);
		}
	}

	public WebElement getWebElement(String locatorKey) {
		try {
			By locator = getLocator(locatorKey);
			waitForVisibilityOfElement(locator);
			return driver.findElement(locator);
		} catch (Exception e) {
			String errorMessage = "Error while finding element with locator key: " + locatorKey;
			handleException(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}
	}

	//Relative Locators

	public WebElement nearElement(By referenceElement, By relativeElement) {
		try {
			waitForVisibilityOfElement(referenceElement);
			return driver.findElement(RelativeLocator.with(referenceElement).near(relativeElement));
		} catch (Exception e) {
			handleException("Error while finding element near", e);
			throw e;
		}
	}

	public WebElement aboveElement(By referenceElement, By relativeElement) {
		try {
			waitForVisibilityOfElement(referenceElement);
			return driver.findElement(RelativeLocator.with(referenceElement).above(relativeElement));
		} catch (Exception e) {
			handleException("Error while finding element above", e);
			throw e;
		}
	}

	public WebElement belowElement(By referenceElement, By relativeElement) {
		try {
			waitForVisibilityOfElement(referenceElement);
			return driver.findElement(RelativeLocator.with(referenceElement).below(relativeElement));
		} catch (Exception e) {
			handleException("Error while finding element below", e);
			throw e;
		}
	}

	public WebElement leftOfElement(By referenceElement, By relativeElement) {
		try {
			waitForVisibilityOfElement(referenceElement);
			return driver.findElement(RelativeLocator.with(referenceElement).toLeftOf(relativeElement));
		} catch (Exception e) {
			handleException("Error while finding element to the left", e);
			throw e;
		}
	}

	public WebElement rightOfElement(By referenceElement, By relativeElement) {
		try {
			waitForVisibilityOfElement(referenceElement);
			return driver.findElement(RelativeLocator.with(referenceElement).toRightOf(relativeElement));
		} catch (Exception e) {
			handleException("Error while finding element to the right", e);
			throw e;
		}
	}


	public String getValidationMessage(String key) {
		return validationMessages.getProperty(key);
	}

	//Exception


	public void handleException(String message, Exception e) {
		// You can customize how you want to handle exceptions (e.g., logging, reporting)
		System.err.println("Exception: " + message);
		e.printStackTrace();
	}

	//Waits 

	public void waitForElementToBeClickable(By locator) {
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void waitForVisibilityOfElement(By locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void waitForPresenceOfElement(By locator) {
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void waitForInvisibilityOfElement(By locator) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	public void waitForTextToBePresentInElement(By locator, String text) {
		wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
	}

	public void waitForPageToLoad() {

		wait.until(driver -> String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
				.equals("complete"));
	}

	public void refreshPage() {
		driver.navigate().refresh();
		waitForPageToLoad();
	}

	// CLick , send, text, dropdown actions

	public void clickElement(By locator) {
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		element.click();
	}

	public void sendKeys(WebElement element, String value) {
		element.clear();
		element.sendKeys(value);
	}

	public String getTextFromElement(By locator) {
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return element.getText();
	}

	public void selectDropdownByVisibleText(By locator, String visibleText) {
		WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		// Use Select class for handling dropdowns
		new Select(dropdown).selectByVisibleText(visibleText);
	}

	//Actions

	public void performRightClick(By locator) {
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

		// Use Actions class for right-click
		Actions actions = new Actions(driver);
		actions.contextClick(element).perform();
	}


	public void hoverOverElement(By locator) {
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		// Use Actions class for mouse hover
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}

	public void doubleClickElement(By locator) {
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		// Use Actions class for double-click
		Actions actions = new Actions(driver);
		actions.doubleClick(element).perform();
	}

	//Javascript executor

	private void executeJavaScript(String script, Object... args) {
		try {
			((JavascriptExecutor) driver).executeScript(script, args);
		} catch (Exception e) {
			handleException("Error while executing JavaScript", e);
			throw e;
		}
	}

	//Frames

	public void switchToFrame(By frameLocator) {
		try {
			WebElement frameElement = driver.findElement(frameLocator);
			driver.switchTo().frame(frameElement);
		} catch (Exception e) {
			handleException("Error while switching to frame", e);
			throw e;
		}
	}

	public void switchToDefaultContent() {
		try {
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			handleException("Error while switching to default content", e);
			throw e;
		}
	}

	//Windows

	public void switchToWindow(String windowHandle) {
		try {
			driver.switchTo().window(windowHandle);
		} catch (Exception e) {
			handleException("Error while switching to window", e);
			throw e;
		}
	}

	public String getWindowHandle() {
		try {
			return driver.getWindowHandle();
		} catch (Exception e) {
			handleException("Error while getting window handle", e);
			throw e;
		}
	}

	public void openNewWindow(String url) {
		String currentWindowHandle = getWindowHandle();
		executeJavaScript("window.open(arguments[0], '_blank');", url);
		waitForNewWindow(currentWindowHandle);
	}

	private void waitForNewWindow(String currentWindowHandle) {
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		for (String windowHandle : driver.getWindowHandles()) {
			if (!windowHandle.equals(currentWindowHandle)) {
				switchToWindow(windowHandle);
				break;
			}
		}
	}

	//Alerts

	public void acceptAlert() {
		try {
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			alert.accept();
		} catch (Exception e) {
			handleException("Error while accepting alert", e);
			throw e;
		}
	}

	public void dismissAlert() {
		try {
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			alert.dismiss();
		} catch (Exception e) {
			handleException("Error while dismissing alert", e);
			throw e;
		}
	}

	public String getAlertText() {
		try {
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			return alert.getText();
		} catch (Exception e) {
			handleException("Error while getting alert text", e);
			throw e;
		}
	}

	public void sendKeysToAlert(String keysToSend) {
		try {
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			alert.sendKeys(keysToSend);
		} catch (Exception e) {
			handleException("Error while sending keys to alert", e);
			throw e;
		}
	}


}
