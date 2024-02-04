package com.nopcommerce.web.driverbase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterSuite;
import org.yaml.snakeyaml.Yaml;

import com.nopcommerce.web.baseutils.ExcelUtilities;

public class BrowserBase {

    private static WebDriver driver;
    public static final long IMPLICIT_WAIT_TIME = 30;
    public static final long PAGE_LOAD_TIME = 30;
    private static final String BROWSERSTACK_USERNAME = "naveenr_cWK86j";
    private static final String BROWSERSTACK_ACCESS_KEY = "HuUFEPMqLW1Byeyim7Hs";
    private static final String CAPABILITIES_FILE_PATH = "src/test/resources/browserstack.yml";
    //https://naveenr_cWK86j:HuUFEPMqLW1Byeyim7Hs@hub-cloud.browserstack.com/wd/hub

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
            //String browserName = "browserstack";
            System.out.println("Executing Tests in " + browserName + " Browser");

            if (browserName.equalsIgnoreCase("browserstack")) {
                driver = initBrowserStackDriver();
            } else {

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
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle or log the exception as needed
            throw new RuntimeException("Failed to initialize browser and open URL: " + e.getMessage());
        }
    }
    
    private static WebDriver initBrowserStackDriver() throws IOException {
        MutableCapabilities caps = readCapabilitiesFromYaml();
        return new RemoteWebDriver(new URL(getBrowserStackUrl()), caps);
    }

    private static MutableCapabilities readCapabilitiesFromYaml() throws IOException {
        try (InputStream input = new FileInputStream(CAPABILITIES_FILE_PATH)) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlMap = yaml.load(input);

            MutableCapabilities caps = new MutableCapabilities();
            setCapabilitiesFromMap(caps, yamlMap);

            return caps;
        }
    }

    private static void setCapabilitiesFromMap(MutableCapabilities caps, Map<String, Object> yamlMap) {
        caps.setCapability("browserstack.user", yamlMap.get("userName"));
        caps.setCapability("browserstack.key", yamlMap.get("accessKey"));

        List<Map<String, Object>> platforms = (List<Map<String, Object>>) yamlMap.get("platforms");
        if (platforms != null && !platforms.isEmpty()) {
            Map<String, Object> platform = platforms.get(0); // Assuming the first platform
            caps.setCapability("os", platform.get("os"));
            caps.setCapability("os_version", platform.get("osVersion"));
            caps.setCapability("browser", platform.get("browserName"));
            caps.setCapability("browser_version", platform.get("browserVersion"));
        }

        // Add any additional capabilities based on your YAML file structure
        // Example: caps.setCapability("some_capability", yamlMap.get("someCapability"));

        // Fix for W3C compatibility
        caps.setCapability("bstack:options", getBrowserStackOptions(yamlMap));
    }

    private static Map<String, Object> getBrowserStackOptions(Map<String, Object> yamlMap) {
        Map<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("os", yamlMap.get("os"));
        browserstackOptions.put("osVersion", yamlMap.get("osVersion"));
        browserstackOptions.put("browser", yamlMap.get("browserName"));
        browserstackOptions.put("browserVersion", yamlMap.get("browserVersion"));

        Map<String, Object> caps = new HashMap<>();
        caps.put("bstack:options", browserstackOptions);

        return caps;
    }



    private static String getBrowserStackUrl() {
        String baseUrl = "https://hub-cloud.browserstack.com/wd/hub";
        String userInfo = BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_ACCESS_KEY;

        try {
            return new URL(baseUrl).getProtocol() + "://" + userInfo + "@" + new URL(baseUrl).getHost() + new URL(baseUrl).getFile();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to construct BrowserStack URL: " + e.getMessage());
        }
    }
    
    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;  // reset the driver instance
        }
    
    }
}
