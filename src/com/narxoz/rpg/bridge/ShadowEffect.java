package com.narxoz.rpg.bridge;

public class ShadowEffect implements EffectImplementor {
    @Override
    public int computeDamage(int basePower) {
        return (int) Math.round(basePower * 1.3);
    }

    @Override
    public String getEffectName() {
        return "Shadow";
    }
}
