package com.example.blog.keytest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvTestStepReader {

    public static List<TestStep> readStepsFromCsv(String filePath) throws Exception {
        List<TestStep> steps = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Пропускаємо заголовок
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1); // -1 для збереження порожніх полів
                String keyword = parts.length > 0 ? parts[0].trim() : null;
                String targetElement = parts.length > 1 ? parts[1].trim() : null;
                String inputData = parts.length > 2 ? parts[2].trim() : null;

                if (targetElement != null && targetElement.isEmpty()) targetElement = null;
                if (inputData != null && inputData.isEmpty()) inputData = null;

                if (keyword != null && !keyword.isEmpty()) {
                    steps.add(new TestStep(keyword, targetElement, inputData));
                }
            }
        }
        return steps;
    }
}
