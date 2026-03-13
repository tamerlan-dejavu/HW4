package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;

public class AreaSkill extends Skill {
    public AreaSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target) {
        // TODO: Area Bridge action
        // Apply resolved damage to a composite target.
        // Tip: Let Composite classes decide how to distribute AOE damage.
        if (target == null || !target.isAlive()) return;

        int damage = resolvedDamage();
        System.out.println("Casting AOE " + getSkillName() + " (" + getEffectName() + ")");

        applyAoe(target, damage);
    }

    private void applyAoe(CombatNode node, int damage) {
        if (node.getChildren().isEmpty()) {
        node.takeDamage(damage);
        } 
        else {
            for (CombatNode child : node.getChildren()) {
                if (child.isAlive()) {
                applyAoe(child, damage);
                }
            }
        }
    }
}
