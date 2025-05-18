package com.example.blog.simpletest;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasicSeleniumTest {

    WebDriver driver;

    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Yehor\\Desktop\\Sem 2\\Test\\Lab 1\\chrome\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    public void executeTest(List<SimpleTestStep> steps) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (SimpleTestStep step : steps) {
            switch (step.getKeyword()) {
                case "Open URL":
                    driver.get(step.getInputData());
                    break;
                case "Input Text":
                    if (step.getTargetElement() == null || step.getTargetElement().isEmpty()) {
                        throw new IllegalArgumentException("Target element is required for Input Text");
                    }
                    wait.until(ExpectedConditions.visibilityOfElementLocated(getBy(step.getTargetElement())));
                    driver.findElement(getBy(step.getTargetElement())).sendKeys(step.getInputData());
                    break;
                case "Click Button":
                    if (step.getTargetElement() == null || step.getTargetElement().isEmpty()) {
                        throw new IllegalArgumentException("Target element is required for Click Button");
                    }
                    wait.until(ExpectedConditions.elementToBeClickable(getBy(step.getTargetElement())));
                    driver.findElement(getBy(step.getTargetElement())).click();
                    break;
                case "Verify Text":
                    if (step.getInputData() == null || step.getInputData().isEmpty()) {
                        throw new IllegalArgumentException("Input data is required for Verify Text");
                    }
                    boolean found = driver.getPageSource().contains(step.getInputData());
                    if (!found) {
                        throw new AssertionError("Text not found: " + step.getInputData());
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown keyword: " + step.getKeyword());
            }
        }
    }

    private By getBy(String locator) {
        if (locator.startsWith("id=")) return By.id(locator.substring(3));
        if (locator.startsWith("xpath=")) return By.xpath(locator.substring(6));
        if (locator.startsWith("css=")) return By.cssSelector(locator.substring(4));
        if (locator.startsWith("name=")) return By.name(locator.substring(5));
        if (locator.startsWith("tagName=")) return By.tagName(locator.substring(8));
        throw new IllegalArgumentException("Unknown locator: " + locator);
    }


    public void tearDown() {
        driver.quit();
    }
}
