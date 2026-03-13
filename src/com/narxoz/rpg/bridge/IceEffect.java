package com.narxoz.rpg.bridge;

public class IceEffect implements EffectImplementor {
    @Override
    public int computeDamage(int basePower) {
        return (int) Math.round(basePower * 1.1);
    }

    @Override
    public String getEffectName() {
        return "Ice";
    }
}
