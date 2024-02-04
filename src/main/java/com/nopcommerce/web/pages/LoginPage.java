package com.nopcommerce.web.pages;

import java.util.List;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.nopcommerce.web.baseutils.ExcelUtilities;
import com.nopcommerce.web.baseutils.TestBase;

public class LoginPage extends TestBase {

	private WebDriver driver;
	private Properties registerLocators;	

	public LoginPage(WebDriver driver) {
		super(driver,"com/nopcommerce/web/locators/login.properties");
		System.out.println("Properties loaded in page file ");
	}

	private String getEmailOrPasswordFromExcel(String sheetName, String fieldName) {
		// Call your method to get data from Excel based on sheetName and fieldName
		List<Object[]> testDataList = ExcelUtilities.getTestDataFromExcel(sheetName, "Email", "Password");

		if (!testDataList.isEmpty()) {
			Object[] testData = testDataList.get(0);
			if ("Email".equalsIgnoreCase(fieldName) && testData.length >= 1) {
				return testData[0].toString();
			} else if ("Password".equalsIgnoreCase(fieldName) && testData.length >= 2) {
				return testData[1].toString();
			}
		}
		throw new RuntimeException("Unable to retrieve data from Excel for " + fieldName);
	}


	public LoginPage clickLoginButton() {
		try {
			getWebElement("login.loginClickButton").click();
			return this;
		} catch (Exception e) {
			handleException("Error while clicking login button", e);
			throw e;
		}
	}

	public LoginPage enterEmailAddress() {
		try {
			String emailText = getEmailOrPasswordFromExcel("Login", "Email");
			WebElement emailAddressLocator = getWebElement("login.emailAddressField");
			sendKeys(emailAddressLocator, emailText);
			return this;
		} catch (Exception e) {
			handleException("Error while entering email address from Excel", e);
			throw e;
		}
	}


	public LoginPage enterPassword() {
		try {
			String passwordText = getEmailOrPasswordFromExcel("Login", "Password");
			WebElement passwordLocator = getWebElement("login.passwordField");
			sendKeys(passwordLocator, passwordText);
			return this;
		} catch (Exception e) {
			handleException("Error while entering password from Excel", e);
			throw e;
		}
	}

	public LoginPage clickSubmitButton() {
		try {
			getWebElement("login.loginSubmitButton").click();
			return this;
		} catch (Exception e) {
			handleException("Error while clicking submit button", e);
			throw e;
		}
	}

	// Method Chaining

	public void clickingLoginWithoutPassword() {
		try {
			enterEmailAddress().clickSubmitButton();
		} catch (Exception e) {
			handleException("Error while Logging in without credentials", e);
			throw e;
		}
	}

	public void clickingLoginWithoutCredentials() {
		try {
			clickLoginButton().clickSubmitButton();
		} catch (Exception e) {
			handleException("Error while Logging in without Password", e);
			throw e;
		}
	}

	public void enteringValidLoginCredentials() {
		try {
			enterEmailAddress().enterPassword().clickSubmitButton();
		} catch (Exception e) {
			handleException("Error while entering valid login credentials", e);
			throw e;
		}
	}


}
