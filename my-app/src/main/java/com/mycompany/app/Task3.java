package com.mycompany.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Task3 {
    private static final String WEATHER_URL =
            "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44"
            + "&hourly=temperature_2m,rain"
            + "&current=cloud_cover"
            + "&timezone=Europe%2FMoscow"
            + "&forecast_days=1"
            + "&wind_speed_unit=ms";

    public static void writeForecast(WebDriver driver, String outputPath) throws Exception {
        driver.get(WEATHER_URL);

        String jsonText = driver.findElement(By.tagName("pre")).getText();
        JSONObject root = parseJson(jsonText);
        JSONObject hourly = (JSONObject) root.get("hourly");

        JSONArray times = (JSONArray) hourly.get("time");
        JSONArray temperatures = (JSONArray) hourly.get("temperature_2m");
        JSONArray rains = (JSONArray) hourly.get("rain");

        String table = buildTable(times, temperatures, rains);
        saveText(outputPath, table);
    }

    private static JSONObject parseJson(String text) throws Exception {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(text);
    }

    private static String buildTable(JSONArray times, JSONArray temperatures, JSONArray rains) {
        StringBuilder sb = new StringBuilder();
        sb.append("№\tДата/время\tТемпература\tОсадки (мм)").append(System.lineSeparator());

        int count = Math.min(times.size(), Math.min(temperatures.size(), rains.size()));
        for (int i = 0; i < count; i++) {
            sb.append(i + 1).append('\t')
              .append(times.get(i)).append('\t')
              .append(temperatures.get(i)).append('\t')
              .append(rains.get(i))
              .append(System.lineSeparator());
        }
        return sb.toString();
    }

    private static void saveText(String outputPath, String content) throws IOException {
        File outFile = new File(outputPath);
        File parent = outFile.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (FileWriter writer = new FileWriter(outFile, false)) {
            writer.write(content);
        }
    }
}