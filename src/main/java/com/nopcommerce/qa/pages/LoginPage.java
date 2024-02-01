package com.nopcommerce.qa.pages;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.javafaker.Faker;
import com.nopcommerce.qa.baseutils.TestBase;
import com.nopcommerce.qa.testdata.RegisterData;

public class LoginPage extends TestBase {

	private WebDriver driver;
	private Properties registerLocators;	

	   public LoginPage(WebDriver driver) {
	        super(driver,"com/nopcommerce/qa/locators/login.properties");
	        System.out.println("Properties loaded in page file ");
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

	public LoginPage enterEmailAddress(String emailText) {
		try {
			WebElement emailAddressLocator = getWebElement("login.emailAddressField");
			sendKeys(emailAddressLocator,emailText);
			return this;
		} catch (Exception e) {
			handleException("Error while entering email address", e);
			throw e;
		}
	}

	public LoginPage enterPassword(String passwordText) {
		try {
			WebElement passwordLocator = getWebElement("login.passwordField");
			sendKeys(passwordLocator,passwordText);
			return this;
		} catch (Exception e) {
			handleException("Error while entering password", e);
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
	
	public void clickingLoginWithoutPassword(String emailText) {
		try {
			enterEmailAddress(emailText).clickSubmitButton();
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
	
	public void enteringValidLoginCredentials(String emailText, String passwordText) {
		try {
			enterEmailAddress(emailText).enterPassword(passwordText).clickSubmitButton();
		} catch (Exception e) {
			handleException("Error while entering valid login credentials", e);
			throw e;
		}
	}

	
}
