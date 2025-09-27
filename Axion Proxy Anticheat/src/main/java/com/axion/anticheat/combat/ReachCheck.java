package com.axion.anticheat.combat;

import be.waterdog.waterdogpe.event.EventHandler;
import be.waterdog.waterdogpe.event.player.PlayerAttackEvent;
import be.waterdog.waterdogpe.player.ProxiedPlayer;
import com.axion.anticheat.Logger;
import com.axion.anticheat.PlayerDataManager;
import com.axion.anticheat.PlayerDataManager.PlayerData;

public class ReachCheck {

    private static final double MAX_REACH = 4.5; // blocks, example value

    @EventHandler
    public void onPlayerAttack(PlayerAttackEvent event) {
        ProxiedPlayer attacker = event.getAttacker();
        ProxiedPlayer victim = event.getVictim();
        PlayerData data = PlayerDataManager.get(attacker);

        double dx = attacker.getX() - victim.getX();
        double dy = attacker.getY() - victim.getY();
        double dz = attacker.getZ() - victim.getZ();

        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        if (distance > MAX_REACH) {
            data.addCombatViolation();
            Logger.log(attacker, "Reach hack detected! Distance: " + String.format("%.2f", distance));

            // Optional: auto-kick
            if (data.getCombatViolations() >= 5) {
                attacker.kick("Reach hack detected!");
            }
        }
    }
}
