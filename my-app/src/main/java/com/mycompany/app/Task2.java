package com.mycompany.app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Task2 {
    private static final String IP_URL = "https://api.ipify.org/?format=json";

    public static String fetchIp(WebDriver driver) throws Exception {
        driver.get(IP_URL);

        String jsonText = driver.findElement(By.tagName("pre")).getText();
        JSONObject json = parseJson(jsonText);

        Object ipValue = json.get("ip");
        return ipValue == null ? "" : ipValue.toString();
    }

    private static JSONObject parseJson(String text) throws Exception {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(text);
    }
}