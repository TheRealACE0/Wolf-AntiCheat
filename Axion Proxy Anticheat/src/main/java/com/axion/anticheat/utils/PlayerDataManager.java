package com.axion.anticheat;

import be.waterdog.waterdogpe.player.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.axion.anticheat.PlayerDataManager.PlayerData;

public class PlayerDataManager {
    
    // Stores data for each player
    private static final Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    // Initialize
    public static void init() {
        playerDataMap.clear();
    }

    // Get or create player data
    public static PlayerData get(ProxiedPlayer player) {
        return playerDataMap.computeIfAbsent(player.getUniqueId(), uuid -> new PlayerData(player));
    }

    // Remove player data (e.g., on disconnect)
     public static void remove(ProxiedPlayer player) {
        playerDataMap.remove(player.getUniqueId());
    }

    // Clear all data (on plugin disable)
      public static void clearAllData() {
        playerDataMap.clear();
    }

    // PlayerData inner class
    public static class PlayerData {
        private final ProxiedPlayer player;

     // Movement tracking
        private double lastX, lastY, lastZ;
        private long lastMoveTime;
    
    // Violation counters
        private int speedViolations = 0;
        private int flyViolations = 0;
        private int combatViolations = 0;
        private int chatViolations = 0;
    
      // Chat tracking
        private long lastMessageTime = 0;
        private String lastMessage = "";
    
         public PlayerData(ProxiedPlayer player) {
            this.player = player;
            this.lastX = player.getX();
            this.lastY = player.getY();
            this.lastZ = player.getZ();
            this.lastMoveTime = System.currentTimeMillis();
        }

          // Getters and setters
        public ProxiedPlayer getPlayer() {
            return player;
        }

            public double getLastX() { return lastX; }
        public double getLastY() { return lastY; }
        public double getLastZ() { return lastZ; }
        public void setLastPosition(double x, double y, double z) {
            this.lastX = x;
            this.lastY = y;
            this.lastZ = z;
        }

         public long getLastMoveTime() { return lastMoveTime; }
        public void setLastMoveTime(long lastMoveTime) { this.lastMoveTime = lastMoveTime; }

        public int getSpeedViolations() { return speedViolations; }
        public void addSpeedViolation() { this.speedViolations++; }

        public int getFlyViolations() { return flyViolations; }
        public void addFlyViolation() { this.flyViolations++; }

        public int getCombatViolations() { return combatViolations; }
        public void addCombatViolation() { this.combatViolations++; }

        public int getChatViolations() { return chatViolations; }
        public void addChatViolation() { this.chatViolations++; }

        public long getLastMessageTime() { return lastMessageTime; }
        public void setLastMessageTime(long lastMessageTime) { this.lastMessageTime = lastMessageTime; }

        public String getLastMessage() { return lastMessage; }
        public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }
    }
}