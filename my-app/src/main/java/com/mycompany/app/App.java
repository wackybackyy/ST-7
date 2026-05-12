package com.mycompany.app;

import java.time.Duration;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");

        WebDriver webDriver = new ChromeDriver();
        try {
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            webDriver.get("https://www.calculator.net/password-generator.html");

            String pageText = webDriver.findElement(By.tagName("body")).getText();

            Pattern pattern = Pattern.compile(
                    "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d\\s]).{8,}$");

            String generatedPassword = "";
            String[] lines = pageText.split("\\R");

            for (String line : lines) {
                String candidate = line.trim();
                if (pattern.matcher(candidate).matches()) {
                    generatedPassword = candidate;
                    break;
                }
            }

            System.out.println("Generated password: " + generatedPassword);

            Task2.printIpAddress(webDriver);
            Task3.saveForecast(webDriver);
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        } finally {
            webDriver.quit();
        }
    }
}