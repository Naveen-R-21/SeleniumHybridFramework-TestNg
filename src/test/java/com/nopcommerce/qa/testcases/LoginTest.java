package com.nopcommerce.qa.testcases;

import java.io.IOException;

import org.testng.annotations.*;
import com.nopcommerce.qa.base.BrowserBase;
import com.nopcommerce.qa.pages.LoginPage;

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
	
	@Test(priority = 2, dataProvider = "onlyEmailCredential", dataProviderClass = com.nopcommerce.qa.testdata.LoginData.class)
	public void verifyLoginWithoutPassword(String email) {

		loginPage.clickingLoginWithoutPassword(email);

	}
	
	@Test(priority = 3,dataProvider = "validLoginCredentials", dataProviderClass = com.nopcommerce.qa.testdata.LoginData.class)
	public void verifyLogin(String email, String password) {

		loginPage.enteringValidLoginCredentials(email, password);

	}

}

