package com.heroes.united.common.items;

public class Tiers {
    private static final double TIER1 = 80.0D;
    private static final double TIER2 = 85.0D;
    private static final double TIER3 = 90.0D;
    private static final double TIER4 = 95.0D;

    public Tiers() {
    }

    public static double getProtection(int tier) {
        return (new double[]{80.0D, 85.0D, 90.0D, 95.0D})[tier - 1] / 100.0D;
    }
}
