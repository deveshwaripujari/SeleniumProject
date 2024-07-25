package com.example.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class GoogleSearchTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        System.out.println("Setting up the test...");
        String driverPath = System.getProperty("webdriver.chrome.driver", "/Users/deveshwari/Desktop/chromedriver-mac-arm64/chromedriver");
        if (driverPath == null || driverPath.isEmpty()) {
            throw new IllegalStateException("System property 'webdriver.chrome.driver' must be set");
        }
        
        System.setProperty("webdriver.chrome.driver", driverPath);
        System.out.println("ChromeDriver path set to: " + driverPath);
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        // options.addArguments("--headless");
        
        System.out.println("Initializing ChromeDriver...");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("Test setup completed.");
    }

    @Test
    public void testGoogleSearch() {
        System.out.println("Starting Google Search test");
        
        System.out.println("Navigating to Google...");
        driver.get("https://www.google.com");
        
        // Accept cookies if the banner appears
        try {
            System.out.println("Checking for cookie acceptance banner...");
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Accept all')]")));
            acceptButton.click();
            System.out.println("Accepted cookies.");
        } catch (Exception e) {
            System.out.println("Cookie banner not found or not clickable. Proceeding with test.");
        }
        
        System.out.println("Locating search box...");
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("q")));
        String searchTerm = "Deveshwari Pujari";
        System.out.println("Entering search term: " + searchTerm);
        searchBox.sendKeys(searchTerm);
        System.out.println("Submitting search...");
        searchBox.submit();
        
        System.out.println("Waiting for search results...");
        try {
            Thread.sleep(10000); // Wait for 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Verifying search results...");
        assertTrue("Page title should contain 'Deveshwari Pujari'", driver.getTitle().contains("Deveshwari Pujari"));
        System.out.println("Test completed successfully. Page title: " + driver.getTitle());
    }

    @After
    public void tearDown() {
        System.out.println("Tearing down the test...");
        if (driver != null) {
            driver.quit();
        }
        System.out.println("Test teardown completed.");
    }
}
