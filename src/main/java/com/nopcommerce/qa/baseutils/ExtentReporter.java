package com.nopcommerce.qa.baseutils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ExtentReporter {

	public static ExtentReports generateExtentReport() throws IOException {
		
		//Email Report ssl code block
//		 System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
//		 System.setProperty("https.cipherSuites", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");

		ExtentReports extentReport = new ExtentReports();

		String browserName = ExcelUtilities.browserDataFromExcel("Browser", 1, 0);

		String url = ExcelUtilities.browserDataFromExcel("browser", 1, 1);

		// Set up the ExtentSparkReporter with the report file location
		File extentReportFile = new File(System.getProperty("user.dir") + "/test-output/ExtentReports/extentReport.html");
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentReportFile);

		// Configure ExtentSparkReporter settings
		sparkReporter.config().setTheme(Theme.DARK);
		sparkReporter.config().setReportName("NopCommerce Test Automation Results Report");
		sparkReporter.config().setDocumentTitle("TN Automation Report");
		sparkReporter.config().setTimeStampFormat("dd/MM/yyyy hh:mm:ss");

		// Attach ExtentSparkReporter to ExtentReports
		extentReport.attachReporter(sparkReporter);

		// Load configuration properties
		Properties configProp = new Properties();
		File configPropFile = new File(System.getProperty("user.dir") + "/src/main/java/com/nopcommerce/qa/config/config.properties");

		try (FileInputStream fisConfigProp = new FileInputStream(configPropFile)) {
			configProp.load(fisConfigProp);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Set system information in the ExtentReports
		extentReport.setSystemInfo("Application URL", url);
		extentReport.setSystemInfo("Browser Name", browserName);
		extentReport.setSystemInfo("Email", configProp.getProperty("automationTesterEmail"));
		//extentReport.setSystemInfo("Password", configProp.getProperty("validPassword"));
		extentReport.setSystemInfo("Operating System", System.getProperty("os.name"));
		extentReport.setSystemInfo("Username", System.getProperty("user.name"));
		extentReport.setSystemInfo("Java Version", System.getProperty("java.version"));
		
		//To Send report in Email
		//EmailUtils.sendEmailWithAttachment(configProp.getProperty("fromEmail"),("fromPassword"),("toEmail"),("subject"),("body"), extentReportFile.getAbsolutePath());
		return extentReport;
	}
}
