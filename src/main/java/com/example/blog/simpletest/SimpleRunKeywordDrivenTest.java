package com.example.blog.simpletest;

import java.util.ArrayList;
import java.util.List;

public class SimpleRunKeywordDrivenTest {

    public static void main(String[] args) {
        BasicSeleniumTest test = new BasicSeleniumTest();
        test.setup();

        try {
            List<SimpleTestStep> steps = new ArrayList<>();

            steps.add(new SimpleTestStep("Open URL", null, "http://localhost:8080/auth/login"));
            steps.add(new SimpleTestStep("Input Text", "id=email", "maryegoo@gmail.com"));
            steps.add(new SimpleTestStep("Input Text", "id=password", "123456"));
            steps.add(new SimpleTestStep("Click Button", "css=button.btn-primary", null));
            steps.add(new SimpleTestStep("Verify Text", null, "Усі пости"));

            test.executeTest(steps);
            System.out.println("Test executed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            test.tearDown();
        }
    }
}
