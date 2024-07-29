package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class DemoPage {
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
        return driver.findElement(By.id("myTextInput")).getAttribute("value");
    }

    public String getSvgRectColor() {
        return driver.findElement(By.cssSelector("rect")).getCssValue("fill");
    }

    public void toggleIframeCheckbox() {
        driver.switchTo().frame("myFrame3");
        driver.findElement(By.id("checkBox6")).click();
        driver.switchTo().defaultContent();
    }

    public boolean isIframeCheckboxChecked() {
        driver.switchTo().frame("myFrame3");
        boolean isChecked = driver.findElement(By.id("checkBox6")).isSelected();
        driver.switchTo().defaultContent();
        return isChecked;
    }

    public void selectDropdownOption(String option) {
        driver.findElement(By.id("mySelect")).sendKeys(option);
    }

    public boolean isMeterValueEqual(String value) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("meterLabel"), value));
    }

    public List<String> getAllHyperlinkTexts() {
        return driver.findElements(By.tagName("a")).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}