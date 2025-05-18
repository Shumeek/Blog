package com.example.blog.keytest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;

public class RunKeywordDrivenTest {

    public static void main(String[] args) {
        KeywordDrivenTest test = new KeywordDrivenTest();
        test.setup();

        try {
            List<TestStep> steps = CsvTestStepReader.readStepsFromCsv("C:\\Users\\Yehor\\Desktop\\Sem 2\\Test\\Lab 1\\chrome\\chromedriver.exe");
            test.executeTest(steps);
            System.out.println("Test executed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            test.tearDown();
        }
    }
}