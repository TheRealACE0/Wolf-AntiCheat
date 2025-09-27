package com.axion.anticheat.movement;

import be.waterdog.waterdogpe.event.EventHandler;
import be.waterdog.waterdogpe.event.player.PlayerMoveEvent;
import be.waterdog.waterdogpe.player.ProxiedPlayer;
import com.axion.anticheat.Logger;
import com.axion.anticheat.PlayerDataManager;
import com.axion.anticheat.PlayerDataManager.PlayerData;

public class SpeedCheck {

    // Maximum allowed speed per tick (tweak based on server mechanics)
    private static final double MAX_SPEED = 5.0; // blocks per second, example value

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        ProxiedPlayer player = event.getPlayer();
        PlayerData data = PlayerDataManager.get(player);

        // Calculate distance moved
        double dx = event.getTo().getX() - data.getLastX();
        double dy = event.getTo().getY() - data.getLastY();
        double dz = event.getTo().getZ() - data.getLastZ();
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        // Time since last movement
        long currentTime = System.currentTimeMillis();
        double timeDiff = (currentTime - data.getLastMoveTime()) / 1000.0; // seconds

        if (timeDiff <= 0) timeDiff = 0.05; // avoid division by zero

        double speed = distance / timeDiff;

        // Check speed violation
        if (speed > MAX_SPEED) {
            data.addSpeedViolation();
            Logger.log(player, "Speed hack detected! Speed: " + String.format("%.2f", speed));

            // Optional: auto-kick after multiple violations
            if (data.getSpeedViolations() >= 5) {
                player.kick("Speed hacking detected!");
            }
        }

        // Update last position and time
        data.setLastPosition(event.getTo().getX(), event.getTo().getY(), event.getTo().getZ());
        data.setLastMoveTime(currentTime);
    }
}
