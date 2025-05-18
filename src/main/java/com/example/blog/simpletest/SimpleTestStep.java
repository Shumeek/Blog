package com.example.blog.simpletest;

public class SimpleTestStep {
    private String keyword;
    private String targetElement;
    private String inputData;

    public SimpleTestStep(String keyword, String targetElement, String inputData) {
        this.keyword = keyword;
        this.targetElement = targetElement;
        this.inputData = inputData;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getTargetElement() {
        return targetElement;
    }

    public String getInputData() {
        return inputData;
    }
}
