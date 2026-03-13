package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;

public class AreaSkill extends Skill {
    public AreaSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target) {
        if (target == null || !target.isAlive()) return;

    // 1. Печатаем ОДИН РАЗ перед началом атаки по площади
        System.out.println("Casting AOE " + getSkillName() + " (" + getEffectName() + ")");

    // 2. Запускаем рекурсию, которая наносит урон МОЛЧА
        applyRecursiveAreaDamage(target, resolvedDamage());
    }

    private void applyRecursiveAreaDamage(CombatNode node, int damage) {
    if (node.getChildren().isEmpty()) {
        node.takeDamage(damage); 
    } else {
        for (CombatNode child : node.getChildren()) {
            if (child.isAlive()) {
                applyRecursiveAreaDamage(child, damage);
            }
        }
    }
}
}
