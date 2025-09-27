package com.axion.anticheat;

import be.waterdog.waterdogpe.player.ProxiedPlayer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String LOG_FILE = "AxionAntiCheat.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Logs a violation to console and to file.
     *
     * @param player  The player who triggered the violation
     * @param reason  Reason for the violation (e.g., "Speed hack")
     */
    public static void log(ProxiedPlayer player, String reason) {
        String time = LocalDateTime.now().format(FORMATTER);
        String message = "[" + time + "] [AXIO] Player: " + player.getName() + " | Reason: " + reason;

        // Log to console
        AntiCheatPlugin.getInstance().getLogger().info(message);

        // Log to file
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs a general info message to console and file
     *
     * @param message The message to log
     */
    public static void info(String message) {
        String time = LocalDateTime.now().format(FORMATTER);
        String logMessage = "[" + time + "] [AXIO] " + message;

        // Console
        AntiCheatPlugin.getInstance().getLogger().info(logMessage);

        // File
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
