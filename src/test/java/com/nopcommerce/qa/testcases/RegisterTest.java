package com.nopcommerce.qa.testcases;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import com.nopcommerce.qa.base.BrowserBase;
import com.nopcommerce.qa.pages.RegisterPage;

public class RegisterTest extends BrowserBase {
	private RegisterPage registerPage;

	 @BeforeClass
	    public void setUpRegisterPage() throws IOException {
	        Faker faker = new Faker();
	        registerPage = new RegisterPage(getDriver(), faker);
	        System.out.println("Properties loaded in testcase file");
	    }
	@Test(priority = 1)
	public void registerWithoutUserData() {

		registerPage.registerUserWithoutData() ;

	}

	@Test(priority = 2)
	public void registerNewUser() throws Exception {

		registerPage.registerNewUser() ;

	}

}


