package com.example.blog.keytest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class KeywordDrivenTest {

    WebDriver driver;

    public void setup() {
        // Налаштуй шлях до драйвера Chrome
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Yehor\\Desktop\\Sem 2\\Test\\Lab 1\\chrome\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public void executeTest(List<TestStep> steps) {
        for (TestStep step : steps) {
            switch (step.getKeyword()) {
                case "Open URL":
                    driver.get(step.getTargetElement());
                    break;
                case "Input Text":
                    driver.findElement(getBy(step.getTargetElement())).sendKeys(step.getInputData());
                    break;
                case "Click Button":
                    driver.findElement(getBy(step.getTargetElement())).click();
                    break;
                case "Verify Text":
                    boolean found = driver.getPageSource().contains(step.getInputData());
                    if (!found) {
                        throw new AssertionError("Text not found: " + step.getInputData());
                    }
                    break;

            }
        }
    }

    private By getBy(String locator) {
        if (locator.startsWith("id=")) return By.id(locator.substring(3));
        if (locator.startsWith("xpath=")) return By.xpath(locator.substring(6));
        if (locator.startsWith("css=")) return By.cssSelector(locator.substring(4));
        // інші локатори
        throw new IllegalArgumentException("Unknown locator: " + locator);
    }

    public void tearDown() {
        driver.quit();
    }
}
