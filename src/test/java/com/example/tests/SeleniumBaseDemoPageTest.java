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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SeleniumBaseDemoPageTest {
    private WebDriver driver;
    @SuppressWarnings("unused")
    private WebDriverWait wait;
    private DemoPage demoPage;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/deveshwari/Desktop/chromedriver-mac-arm64/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        demoPage = new DemoPage(driver);
        
        driver.get("https://seleniumbase.io/demo_page");
    }


    @Test
    public boolean testTextInputField() {
        String text = "MAPS is boring";
        demoPage.setTextInput(text);
        
        // Wait for 10 seconds
        try {
            System.out.println("Waiting for 10 seconds...");
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        String actualText = demoPage.getTextInputValue();
        boolean testPassed = text.equals(actualText);
        
        assertEquals("Text input should match", text, actualText);
        
        System.out.println("Test result: " + (testPassed ? "PASSED" : "FAILED"));
        System.out.println("Expected text: " + text);
        System.out.println("Actual text: " + actualText);
        
        return testPassed;
    }

    @Test
    public void testSvgColor() {
        String rgbColor = demoPage.getSvgRectColor();
        System.out.println("SVG rect color: " + rgbColor);
        // You can add assertions here if needed
    }

    @Test
    public void testIframeCheckbox() {
        demoPage.toggleIframeCheckbox();
        assertTrue("Checkbox should be checked", demoPage.isIframeCheckboxChecked());
    }

    @Test
    public void testSelectDropdown() {
        demoPage.selectDropdownOption("Set to 50%");
        assertEquals("Meter value should be 50", "50", demoPage.getMeterValue());
    }

    @Test
    public void testPrintHyperlinks() {
        List<String> hyperlinkTexts = demoPage.getHyperlinkTexts();
        System.out.println("Hyperlink texts:");
        hyperlinkTexts.forEach(System.out::println);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

class DemoPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public DemoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void setTextInput(String text) {
        WebElement textInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("myTextInput")));
        textInput.clear();
        textInput.sendKeys(text);
    }

    public String getTextInputValue() {
        WebElement textInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("myTextInput")));
        return textInput.getAttribute("value");
    }

    public String getSvgRectColor() {
        WebElement svgRect = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("svg rect")));
        return svgRect.getCssValue("fill");
    }

    public void toggleIframeCheckbox() {
        driver.switchTo().frame("myFrame1");
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("checkBox1")));
        checkbox.click();
        driver.switchTo().defaultContent();
    }

    public boolean isIframeCheckboxChecked() {
        driver.switchTo().frame("myFrame1");
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("checkBox1")));
        boolean isChecked = checkbox.isSelected();
        driver.switchTo().defaultContent();
        return isChecked;
    }

    public void selectDropdownOption(String option) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("mySelect")));
        Select select = new Select(dropdown);
        select.selectByVisibleText(option);
    }

    public String getMeterValue() {
        WebElement meter = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("meterBar")));
        return meter.getAttribute("value");
    }

    public List<String> getHyperlinkTexts() {
        List<WebElement> hyperlinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));
        return hyperlinks.stream().map(WebElement::getText).toList();
    }
}