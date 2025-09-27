package com.axion.anticheat.chat;

import be.waterdog.waterdogpe.event.EventHandler;
import be.waterdog.waterdogpe.event.player.PlayerChatEvent;
import be.waterdog.waterdogpe.player.ProxiedPlayer;
import com.axion.anticheat.Logger;
import com.axion.anticheat.PlayerDataManager;
import com.axion.anticheat.PlayerDataManager.PlayerData;

import java.util.Arrays;
import java.util.List;

public class BadWordCheck {

    // List of prohibited words (can later be moved to config)
    private static final List<String> BAD_WORDS = Arrays.asList(
            "badword1",
            "badword2",
            "badword3"
    );

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        ProxiedPlayer player = event.getPlayer();
        PlayerData data = PlayerDataManager.get(player);
        String message = event.getMessage().toLowerCase();

        for (String word : BAD_WORDS) {
            if (message.contains(word)) {
                // Increment violation counter
                data.addChatViolation();

                // Log the violation
                Logger.log(player, "Used prohibited word: " + word);

                // Cancel the message
                event.setCancelled(true);

                // Optional: warn or kick after multiple violations
                if (data.getChatViolations() >= 5) {
                    player.kick("You have been kicked for using prohibited language!");
                }

                break; // Stop checking after first bad word
            }
        }
    }
}
