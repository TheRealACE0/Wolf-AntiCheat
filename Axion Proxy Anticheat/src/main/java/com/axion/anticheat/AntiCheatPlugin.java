package com.axion.anticheat;

import be.waterdog.waterdogpe.plugin.ProxyPlugin;
import com.axion.anticheat.movement.SpeedCheck;
import com.axion.anticheat.movement.FlyCheck;
import com.axion.anticheat.chat.SpamCheck;

public class AntiCheatPlugin extends ProxyPlugin {
    
    private static AntiCheatPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("Wolf AntiCheat Enabled!");

        // Register Event Listeners
        getProxy().getEventManager().registerListener(this, new SpeedCheck());
        getProxy().getEventManager().registerListener(this, new FlyCheck());
        getProxy().getEventManager().registerListener(this, new SpamCheck());

        // Initialize Player Data Manager
        PlayerDataManager.init();

        getLogger().info("Wolf AntiCheat is now monitoring all players.");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("Wolf AntiCheat Disabled!");

        // Clean up player data
        PlayerDataManager.ClearAllData();
    }

    public static AntiCheatPlugin getInstance() {
        return instance;
    }
}