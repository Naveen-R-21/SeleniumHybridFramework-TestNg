package com.nopcommerce.web.pages;

import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.github.javafaker.Faker;
import com.nopcommerce.web.baseutils.TestBase;
import com.nopcommerce.web.testdata.RegisterFakerData;
import com.nopcommerce.web.testdata.ValidationTexts;

public class RegisterPage extends TestBase  {


	private RegisterFakerData registerData;
	private Properties registerLocators;
	static WebElement lastNameLocator ;
	
	


	public RegisterPage(WebDriver driver, Faker faker) {
		super(driver, "com/nopcommerce/web/locators/register.properties");
		this.registerData = new RegisterFakerData(faker);
	}

	public RegisterPage clickRegisterButton() {
		try {
			getWebElement("Register.clickRegisterButton").click();
			return this;
		} catch (Exception e) {
			handleException("Error while clicking Register button", e);
			throw e;
		}
	}


	public RegisterPage enterFirstName(String firstName) {
		try {

			WebElement lastNameLocator = getWebElement("Register.firstName");
			sendKeys(lastNameLocator,firstName);
			return this;
		} catch (Exception e) {
			handleException("Error while entering First Name", e);
			throw e;
		}
	}

	public RegisterPage enterLastName(String lastName) {
		try {
			WebElement lastNameLocator = getWebElement("Register.lastName");
			sendKeys(lastNameLocator,lastName);
			return this;
		} catch (Exception e) {
			handleException("Error while entering Last Name", e);
			throw e;
		}
	}


	public RegisterPage enterEmail(String emailText) {
		try {
			WebElement emailTextLocator = getWebElement("Register.Email");
			sendKeys(emailTextLocator,emailText);
			return this;
		} catch (Exception e) {
			handleException("Error while entering Email", e);
			throw e;
		}
	}

	public RegisterPage enterCompanyName(String companyName) {
		try {
			WebElement companyNameLocator = getWebElement("Register.companyName");
			sendKeys(companyNameLocator,companyName);
			return this;
		} catch (Exception e) {
			handleException("Error while entering Company Name", e);
			throw e;
		}
	}

	public RegisterPage enterPassword(String password) {
		try {
			WebElement passwordLocator = getWebElement("Register.password");
			sendKeys(passwordLocator,password);
			return this;
		} catch (Exception e) {
			handleException("Error while entering Password", e);
			throw e;
		}
	}

	public RegisterPage enterConfirmPassword(String confirmPassword) {
		try {
			WebElement confirmPasswordLocator = getWebElement("Register.confirmPassword");
			sendKeys(confirmPasswordLocator,confirmPassword);
			return this;
		} catch (Exception e) {
			handleException("Error while entering Confirm Password", e);
			throw e;
		}
	}

	public RegisterPage submitRegisterForm() {
		try {

			getWebElement("Register.submitRegisterButton").click();
			return this;

		} catch (Exception e) {
			handleException("Error while submitting the registration form", e);
			throw e;
		}
	}

	public RegisterPage registerCompletedSuccessMessage() {
		try {

			String actualText = getWebElement("Register.registerCompletedMessage").getText();
			String expectedText = ValidationTexts.registerCompletedMessage;
			Assert.assertEquals(actualText, expectedText, "Text does not match");
			return this;

		} catch (Exception e) {
			handleException("Error while submitting the registration form", e);
			throw e;
		}
	}

	public void registerUserWithoutData() {
		try {

			clickRegisterButton().submitRegisterForm();
		} catch (Exception e) {
			handleException("Error while registering a user", e);
			throw e;
		}
	}

	public void registerNewUser() throws Exception {
		try {

			registerData.registerNewUser();

			System.out.println("Registering a new user...");

			enterFirstName(registerData.getFirstName())
			.enterLastName(registerData.getLastName())
			.enterEmail(registerData.getEmail())
			.enterCompanyName(registerData.getCompanyName())
			.enterPassword(registerData.getPassword())
			.enterConfirmPassword(registerData.getConfirmPassword())
			.submitRegisterForm().registerCompletedSuccessMessage();

			System.out.println("User registration completed ");
		} catch (Exception e) {
			handleException("Error while registering a user", e);
			throw e;
		}
	}


}