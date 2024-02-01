package com.nopcommerce.qa.base;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.nopcommerce.qa.baseutils.ExcelUtilities;


public class BrowserBase {

	private static WebDriver driver;
	public static final long IMPLICIT_WAIT_TIME = 30;
	public static final long PAGE_LOAD_TIME = 30;
	
	  public BrowserBase() {
	        super();
	    }
	
	public static WebDriver getDriver() throws IOException {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }

	private static void initializeDriver() throws IOException {

		try {
			String browserName = ExcelUtilities.browserDataFromExcel("Browser", 1, 0);
			System.out.println("Executing Tests in " + browserName + " Browser");

			String url = ExcelUtilities.browserDataFromExcel("browser", 1, 1);

			switch (browserName.toLowerCase()) {
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
				break;
			case "safari":
				driver = new SafariDriver();
				break;
			default:
				throw new IllegalArgumentException("Invalid browser name: " + browserName);
			}

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIME));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIME));
			driver.get(url);
			System.out.println("Fetched the URL");

		} catch (Exception e) {
			e.printStackTrace();
			// Handle or log the exception as needed
			throw new RuntimeException("Failed to initialize browser and open URL: " + e.getMessage());
		}
	}

	
	@AfterSuite
	public static void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;  // reset the driver instance
        }
	}

}
