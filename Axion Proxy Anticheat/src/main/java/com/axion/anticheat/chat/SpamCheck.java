package com.axion.anticheat.chat;

import be.waterdog.waterdogpe.event.EventHandler;
import be.waterdog.waterdogpe.event.player.PlayerChatEvent;
import be.waterdog.waterdogpe.player.ProxiedPlayer;
import com.axion.anticheat.Logger;
import com.axion.anticheat.PlayerDataManager;
import com.axion.anticheat.PlayerDataManager.PlayerData;

public class SpamCheck {

    // Minimum delay between messages in milliseconds (e.g., 1000ms = 1 second)
    private static final long MIN_MESSAGE_DELAY = 1000;

    // Maximum repeated message count before considering spam
    private static final int MAX_REPEAT_COUNT = 3;

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        ProxiedPlayer player = event.getPlayer();
        PlayerData data = PlayerDataManager.get(player);
        long currentTime = System.currentTimeMillis();
        String message = event.getMessage().toLowerCase();

        // Check for message spam (too fast)
        long timeSinceLastMessage = currentTime - data.getLastMessageTime();
        if (timeSinceLastMessage < MIN_MESSAGE_DELAY) {
            data.addChatViolation();
            Logger.log(player, "Chat spam detected! Sent message too quickly: " + message);
            event.setCancelled(true); // block the message
        }

        // Check for repeated messages
        if (message.equals(data.getLastMessage())) {
            data.addChatViolation();
            if (data.getChatViolations() >= MAX_REPEAT_COUNT) {
                Logger.log(player, "Repeated chat spam detected: " + message);
                event.setCancelled(true);
            }
        }

        // Update last message and timestamp
        data.setLastMessage(message);
        data.setLastMessageTime(currentTime);

        // Optional: auto-kick or warn after multiple violations
        if (data.getChatViolations() >= 5) {
            player.kick("Chat spam detected!");
        }
    }
}
