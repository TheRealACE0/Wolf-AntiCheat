package com.axion.anticheat.combat;

import be.waterdog.waterdogpe.event.EventHandler;
import be.waterdog.waterdogpe.event.player.PlayerAttackEvent;
import be.waterdog.waterdogpe.player.ProxiedPlayer;
import com.axion.anticheat.Logger;
import com.axion.anticheat.PlayerDataManager;
import com.axion.anticheat.PlayerDataManager.PlayerData;

public class KillAuraCheck {

    // Max clicks per second (example value)
    private static final int MAX_CPS = 20;

    @EventHandler
    public void onPlayerAttack(PlayerAttackEvent event) {
        ProxiedPlayer attacker = event.getAttacker();
        PlayerData data = PlayerDataManager.get(attacker);

        long currentTime = System.currentTimeMillis();
        long timeSinceLastAttack = currentTime - data.getLastAttackTime();

        double cps = 1000.0 / timeSinceLastAttack; // clicks per second approximation

        if (cps > MAX_CPS) {
            data.addCombatViolation();
            Logger.log(attacker, "KillAura detected! CPS: " + String.format("%.2f", cps));

            // Optional: auto-kick after multiple violations
            if (data.getCombatViolations() >= 5) {
                attacker.kick("KillAura detected!");
            }
        }

        // Update last attack time
        data.setLastAttackTime(currentTime);
    }
}
