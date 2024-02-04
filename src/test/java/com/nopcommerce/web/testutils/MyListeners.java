package com.nopcommerce.web.testutils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.nopcommerce.web.baseutils.ExtentReporter;
import com.nopcommerce.web.driverbase.BrowserBase;



public class MyListeners implements ITestListener {

	private ExtentReports extentReport;
	private ExtentTest extentTest;
	private BrowserBase browserBase;
	private WebDriver driver;


	@Override
	public void onStart(ITestContext context) {
		try {
			browserBase = new BrowserBase(); // Initialize browserBase
			driver = browserBase.getDriver(); // Initialize driver
			extentReport = ExtentReporter.generateExtentReport();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
		extentTest = extentReport.createTest(result.getName());
		extentTest.log(Status.INFO, result.getName() + " started executing");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// driver = driver(result);
		String screenshotPath;
		try {
			screenshotPath = captureScreenshot(browserBase.getDriver(), result.getName());

			extentTest.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " is successfully executed", ExtentColor.GREEN));
			extentTest.info("<a href='file://" + screenshotPath + "' target='_blank'>View Screenshot</a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onTestFailure(ITestResult result) {
		// driver = driver(result);
		String destinationScreenshotPath;
		try {
			destinationScreenshotPath = captureScreenshot(browserBase.getDriver(), result.getName());

			extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " is failed", ExtentColor.RED));
			extentTest.log(Status.FAIL, result.getThrowable());
			extentTest.addScreenCaptureFromPath("../Screenshots/" + result.getName() + ".png");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " is skipped", ExtentColor.YELLOW));
		extentTest.log(Status.INFO, result.getThrowable());
	}

	@Override
	public void onFinish(ITestContext context) {
		extentReport.flush();

		String pathOfExtentReport = System.getProperty("user.dir") + "/test-output/ExtentReports/extentReport.html";
		File extentReportFile = new File(pathOfExtentReport);

		try {
			Desktop.getDesktop().browse(extentReportFile.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String captureScreenshot(WebDriver driver, String testName) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		String destination = System.getProperty("user.dir") + "/test-output/Screenshots/" + testName + ".png";
		File destinationFile = new File(destination);

		try {
			org.apache.commons.io.FileUtils.copyFile(source, destinationFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return destinationFile.getAbsolutePath();
	}
}
