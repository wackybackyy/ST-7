package com.mycompany.app;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Task3 {
    public static void saveForecast(WebDriver webDriver) {
        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44"
                    + "&hourly=temperature_2m,rain"
                    + "&current=cloud_cover"
                    + "&timezone=Europe%2FMoscow"
                    + "&forecast_days=1"
                    + "&wind_speed_unit=ms";

            webDriver.get(url);

            WebElement elem = webDriver.findElement(By.tagName("pre"));
            String jsonStr = elem.getText();

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);
            JSONObject hourly = (JSONObject) obj.get("hourly");

            JSONArray time = (JSONArray) hourly.get("time");
            JSONArray temperature = (JSONArray) hourly.get("temperature_2m");
            JSONArray rain = (JSONArray) hourly.get("rain");

            File resultDir = new File("../result");
            if (!resultDir.exists()) {
                resultDir.mkdirs();
            }

            File outFile = new File(resultDir, "forecast.txt");
            try (PrintWriter writer = new PrintWriter(new FileWriter(outFile))) {
                writer.println("№\tДата/время\tТемпература\tОсадки (мм)");
                for (int i = 0; i < time.size(); i++) {
                    writer.printf("%d\t%s\t%s\t%s%n",
                            i + 1,
                            time.get(i),
                            temperature.get(i),
                            rain.get(i));
                }
            }

            System.out.println("Forecast saved to result/forecast.txt");
        } catch (Exception e) {
            System.out.println("Task3 Error");
            System.out.println(e.toString());
        }
    }
}