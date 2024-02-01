package com.nopcommerce.qa.baseutils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class TestBase {

    private Properties locators;
    private WebDriverWait wait;
    protected WebDriver driver;

    public TestBase(WebDriver driver, String Filepath) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       // this.locators = loadLocators("com/nopcommerce/qa/locators/login.properties");
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
    
    public void sendKeys(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }



	public void handleException(String message, Exception e) {
		// You can customize how you want to handle exceptions (e.g., logging, reporting)
		System.err.println("Exception: " + message);
		e.printStackTrace();
	}

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
}
