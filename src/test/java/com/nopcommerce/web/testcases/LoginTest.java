package com.nopcommerce.web.testcases;

import java.io.IOException;

import org.testng.annotations.*;

import com.nopcommerce.web.driverbase.BrowserBase;
import com.nopcommerce.web.pages.LoginPage;

public class LoginTest extends BrowserBase {

	private LoginPage loginPage;

	@BeforeClass
	public void setUpRegisterPage() throws IOException {
		loginPage = new LoginPage(getDriver());
		System.out.println("Properties loaded in testcase file");
	}

	@Test(priority = 1)
	public void verifyLoginWithoutCredentials() {

		loginPage.clickingLoginWithoutCredentials() ;

	}

	@Test(priority = 2)
	public void verifyLoginWithoutPassword() {

		loginPage.clickingLoginWithoutPassword();

	}

	@Test(priority = 3)
	public void verifyLogin() {

		loginPage.enteringValidLoginCredentials();

	}

}

