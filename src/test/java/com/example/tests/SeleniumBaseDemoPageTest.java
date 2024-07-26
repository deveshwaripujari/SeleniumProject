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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SeleniumBaseDemoPageTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private DemoPage demoPage;

    @Before
    public void setUp() {
        System.out.println("Setting up the test...");
        System.setProperty("webdriver.chrome.driver", "/Users/deveshwari/Desktop/chromedriver-mac-arm64/chromedriver");
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.setCapability("networkConnectionEnabled", false);
        
        try {
            driver = new ChromeDriver(options);
            System.out.println("ChromeDriver initialized successfully.");
        } catch (Exception e) {
            System.out.println("Failed to initialize ChromeDriver: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        demoPage = new DemoPage(driver);
        
        try {
            driver.get("https://seleniumbase.io/demo_page");
            System.out.println("Navigated to the demo page successfully.");
        } catch (Exception e) {
            System.out.println("Failed to navigate to the demo page: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testTextInputField() throws InterruptedException {
        String expectedText = "MAPS is boring";
        demoPage.enterTextInput(expectedText);
        
        Thread.sleep(10000);
        
        String actualText = demoPage.getTextInputValue();
        assertEquals("Text input should match the entered value", expectedText, actualText);
        System.out.println("testTextInputField passed: MAPS is boring");
    }

    @Test
    public void testSvgColor() {
        System.out.println("Running testSvgColor...");
        try {
            String rgbColor = demoPage.getSvgRectColor();
            System.out.println("SVG rect color: " + rgbColor);
            // Add assertions here if needed
        } catch (Exception e) {
            System.out.println("testSvgColor failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testIframeCheckbox() {
        System.out.println("Running testIframeCheckbox...");
        try {
            demoPage.toggleIframeCheckbox();
            assertTrue("Checkbox should be checked", demoPage.isIframeCheckboxChecked());
            System.out.println("testIframeCheckbox passed.");
        } catch (Exception e) {
            System.out.println("testIframeCheckbox failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testSelectDropdown() {
        System.out.println("Running testSelectDropdown...");
        try {
            demoPage.selectDropdownOption("Set to 50%");
            assertEquals("Meter value should be 50", "50", demoPage.getMeterValue());
            System.out.println("testSelectDropdown passed.");
        } catch (Exception e) {
            System.out.println("testSelectDropdown failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        System.out.println("Tearing down the test...");
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed.");
        }
    }

    // DemoPage class to encapsulate page interactions
    private class DemoPage {
        private WebDriver driver;
        private WebDriverWait wait;

        public DemoPage(WebDriver driver) {
            this.driver = driver;
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }

        public void enterTextInput(String text) {
            WebElement inputField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("myTextInput")));
            inputField.clear();
            inputField.sendKeys(text);
        }

        public String getTextInputValue() {
            WebElement inputField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("myTextInput")));
            return inputField.getAttribute("value");
        }

        public String getSvgRectColor() {
            WebElement svgRect = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("rect")));
            return svgRect.getCssValue("fill");
        }

        public void toggleIframeCheckbox() {
            driver.switchTo().frame("myFrame2");
            WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("checkBox6")));
            checkbox.click();
            driver.switchTo().defaultContent();
        }

        public boolean isIframeCheckboxChecked() {
            driver.switchTo().frame("myFrame2");
            WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("checkBox6")));
            boolean isChecked = checkbox.isSelected();
            driver.switchTo().defaultContent();
            return isChecked;
        }

        public void selectDropdownOption(String option) {
            WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("mySelect")));
            dropdown.sendKeys(option);
        }

        public String getMeterValue() {
            WebElement meter = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("meterValue")));
            return meter.getText();
        }
    }
}