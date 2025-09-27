package com.axion.anticheat.movement;

import be.waterdog.waterdogpe.event.EventHandler;
import be.waterdog.waterdogpe.event.player.PlayerMoveEvent;
import be.waterdog.waterdogpe.player.ProxiedPlayer;
import com.axion.anticheat.Logger;
import com.axion.anticheat.PlayerDataManager;
import com.axion.anticheat.PlayerDataManager.PlayerData;

public class FlyCheck {

    // Maximum vertical movement allowed per tick (tweak based on server mechanics)
    private static final double MAX_VERTICAL_SPEED = 1.5; // blocks per tick, example value

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        ProxiedPlayer player = event.getPlayer();
        PlayerData data = PlayerDataManager.get(player);

        double dy = event.getTo().getY() - data.getLastY();
        long currentTime = System.currentTimeMillis();
        double timeDiff = (currentTime - data.getLastMoveTime()) / 1000.0; // seconds

        if (timeDiff <= 0) timeDiff = 0.05; // avoid division by zero

        double verticalSpeed = Math.abs(dy / timeDiff);

        // Check fly violation
        if (verticalSpeed > MAX_VERTICAL_SPEED) {
            data.addFlyViolation();
            Logger.log(player, "Fly hack detected! Vertical speed: " + String.format("%.2f", verticalSpeed));

            // Optional: auto-kick after multiple violations
            if (data.getFlyViolations() >= 5) {
                player.kick("Fly hacking detected!");
            }
        }

        // Update last position and time
        data.setLastPosition(event.getTo().getX(), event.getTo().getY(), event.getTo().getZ());
        data.setLastMoveTime(currentTime);
    }
}
