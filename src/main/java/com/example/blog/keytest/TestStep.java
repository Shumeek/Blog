package com.example.blog.keytest;

public class TestStep {
    private String keyword;
    private String targetElement;
    private String inputData;

    public TestStep(String keyword, String targetElement, String inputData) {
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
