package com.mycompany.app;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {
    private static final String DRIVER_PATH =
            "C:\\Program Files\\chromedriver-win64\\chromedriver.exe";
    private static final String PASSWORD_URL =
            "https://www.calculator.net/password-generator.html";

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        try {
            String password = fetchGeneratedPassword(driver);
            System.out.println("Password generated: " + password);

            String ip = Task2.fetchIp(driver);
            System.out.println("Client IPv4: " + ip);

            Task3.writeForecast(driver, "../result/forecast.txt");
            System.out.println("Forecast file: result/forecast.txt");
        } catch (Exception e) {
            System.out.println("Execution error");
            System.out.println(e.toString());
        } finally {
            driver.quit();
        }
    }

    private static String fetchGeneratedPassword(WebDriver driver) {
        driver.get(PASSWORD_URL);

        String bodyText = driver.findElement(By.tagName("body")).getText();
        List<String> candidates = splitIntoLines(bodyText);

        for (String line : candidates) {
            if (looksLikePassword(line)) {
                return line;
            }
        }
        return "";
    }

    private static List<String> splitIntoLines(String text) {
        String[] lines = text.split("\\R");
        List<String> result = new ArrayList<>();
        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty()) {
                result.add(trimmed);
            }
        }
        return result;
    }

    private static boolean looksLikePassword(String value) {
        if (value.length() < 8) {
            return false;
        }

        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        boolean hasSpace = false;

        for (char ch : value.toCharArray()) {
            if (Character.isLowerCase(ch)) {
                hasLower = true;
            } else if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (Character.isWhitespace(ch)) {
                hasSpace = true;
            } else {
                hasSpecial = true;
            }
        }

        return !hasSpace && hasLower && hasUpper && hasDigit && hasSpecial;
    }
}