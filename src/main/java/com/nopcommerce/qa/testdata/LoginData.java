package com.nopcommerce.qa.testdata;

import org.testng.annotations.DataProvider;

import com.nopcommerce.qa.baseutils.ExcelUtilities;

public class LoginData {
	
	  @DataProvider(name = "validLoginCredentials")
	    public Object[][] provideLoginCredentials() {
	        return ExcelUtilities.getTestDataFromExcel("Login", "Email", "Password").toArray(new Object[0][]);
	    }
	  @DataProvider(name = "onlyEmailCredential")
	    public Object[][] provideOnlyEmailCredential() {
	        return ExcelUtilities.getTestDataFromExcel("Login", "Email").toArray(new Object[0][]);
	    }

}
