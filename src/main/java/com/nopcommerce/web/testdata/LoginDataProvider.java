package com.nopcommerce.web.testdata;

import java.util.List;

import org.testng.annotations.DataProvider;
import com.nopcommerce.web.baseutils.ExcelUtilities;

public class LoginDataProvider {

    @DataProvider(name = "loginData")
    public static Object[][] loginData() {
        List<Object[]> testDataList = ExcelUtilities.getTestDataFromExcel("Login", "Email", "Password");
        Object[][] testData = new Object[testDataList.size()][2];
        for (int i = 0; i < testDataList.size(); i++) {
            testData[i] = testDataList.get(i);
        }
        return testData;
    }
}
