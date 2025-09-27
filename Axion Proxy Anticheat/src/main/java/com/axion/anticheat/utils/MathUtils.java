package com.axion.anticheat.utils;

import be.waterdog.waterdogpe.player.ProxiedPlayer;

public class MathUtils {

    /**
     * Calculates 3D distance between two players.
     */
    public static double distance(ProxiedPlayer p1, ProxiedPlayer p2) {
        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        double dz = p1.getZ() - p2.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * Calculates 2D horizontal distance (XZ plane) between two players.
     */
    public static double distanceXZ(ProxiedPlayer p1, ProxiedPlayer p2) {
        double dx = p1.getX() - p2.getX();
        double dz = p1.getZ() - p2.getZ();
        return Math.sqrt(dx * dx + dz * dz);
    }

    /**
     * Calculates distance between two coordinates
     */
    public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        double dz = z1 - z2;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
