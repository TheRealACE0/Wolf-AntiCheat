package com.axion.anticheat.utils;

import be.waterdog.waterdogpe.plugin.ProxyPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class ConfigManager {

    private static final String CONFIG_FILE = "AxionAntiCheat.properties";
    private static Properties properties = new Properties();

    public static void load(ProxyPlugin plugin) {
        File file = new File(plugin.getDataFolder(), CONFIG_FILE);
        if (!file.exists()) {
            try {
                plugin.getDataFolder().mkdirs();
                file.createNewFile();

                // Set default values
                properties.setProperty("max_speed", "5.0");
                properties.setProperty("max_vertical_speed", "1.5");
                properties.setProperty("max_cps", "20");
                properties.setProperty("max_reach", "4.5");
                properties.setProperty("min_message_delay", "1000");
                properties.setProperty("max_repeat_count", "3");
                properties.setProperty("max_chat_violations", "5");
                properties.setProperty("bad_words", "badword1,badword2,badword3");

                try (FileWriter writer = new FileWriter(file)) {
                    properties.store(writer, "Axion AntiCheat Configuration");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (FileReader reader = new FileReader(file)) {
                properties.load(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static double getDouble(String key, double defaultValue) {
        return Double.parseDouble(properties.getProperty(key, String.valueOf(defaultValue)));
    }

    public static int getInt(String key, int defaultValue) {
        return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
    }

    public static long getLong(String key, long defaultValue) {
        return Long.parseLong(properties.getProperty(key, String.valueOf(defaultValue)));
    }

    public static List<String> getStringList(String key) {
        String value = properties.getProperty(key, "");
        return List.of(value.split(","));
    }

    public static String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
