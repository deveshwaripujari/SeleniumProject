package com.example.tests;

import com.example.pages.DemoPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.Assert.*;

public class SeleniumBaseDemoTest {
    private WebDriver driver;
    private DemoPage demoPage;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/deveshwari/Desktop/chromedriver-mac-arm64/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        demoPage = new DemoPage(driver);
        driver.get("https://seleniumbase.io/demo_page");
    }

    @Test
    public void testTextInputField() throws InterruptedException {
        String expectedText = "MAPS is boring";
        demoPage.enterTextInput(expectedText);
        Thread.sleep(2000);
        String actualText = demoPage.getTextInputValue();
        assertEquals("Text input should match the entered value", expectedText, actualText);
        System.out.println("Test Passed: Text Input Field- MAPS is boring ");
    }

    @Test
    public void testSvgColor() throws InterruptedException {
        String rgbColor = demoPage.getSvgRectColor();
        assertNotNull("SVG color should not be null", rgbColor);
        Thread.sleep(2000);
        System.out.println("Test Passed: SVG Color - " + rgbColor);
    }

    @Test
    public void testIframeCheckbox() throws InterruptedException {
        demoPage.toggleIframeCheckbox();
        Thread.sleep(2000);
        assertTrue("Checkbox should be checked", demoPage.isIframeCheckboxChecked());
        System.out.println("Test Passed: iFrame Checkbox- checked");
    }

    @Test
    public void testSelectDropdown() throws InterruptedException {
        demoPage.selectDropdownOption("Set to 50%");
        Thread.sleep(2000);
        assertTrue("Meter value should be 50", demoPage.isMeterValueEqual("50"));
        System.out.println("Test Passed: Select Dropdown- Meter value is 50");
    }

    @Test
    public void testPrintAllHyperlinkTexts() throws InterruptedException {
        demoPage.getAllHyperlinkTexts().forEach(System.out::println);
        Thread.sleep(2000);
        System.out.println("Test Passed: All Hyperlink Texts");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}