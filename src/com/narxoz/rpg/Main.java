package com.narxoz.rpg;

import java.util.List;

import com.narxoz.rpg.battle.RaidEngine;
import com.narxoz.rpg.battle.RaidResult;
import com.narxoz.rpg.bridge.AreaSkill;
import com.narxoz.rpg.bridge.FireEffect;
import com.narxoz.rpg.bridge.IceEffect;
import com.narxoz.rpg.bridge.ShadowEffect;
import com.narxoz.rpg.bridge.SingleTargetSkill;
import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.EnemyUnit;
import com.narxoz.rpg.composite.HeroUnit;
import com.narxoz.rpg.composite.PartyComposite;
import com.narxoz.rpg.composite.RaidGroup;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Homework 4 Demo: Bridge + Composite ===\n");

        HeroUnit warrior = new HeroUnit("Arthas", 140, 30);
        HeroUnit mage = new HeroUnit("Jaina", 90, 40);
        EnemyUnit goblin = new EnemyUnit("Goblin Scout", 70, 20);
        EnemyUnit orc = new EnemyUnit("Orc Warrior", 120, 25);
        EnemyUnit boss = new EnemyUnit("Raid Boss", 300, 50);
        
        PartyComposite heroes = new PartyComposite("Hero Party");
        heroes.add(warrior);
        heroes.add(mage);

        PartyComposite enemiesFrontline = new PartyComposite("Frontline Squad");
        enemiesFrontline.add(goblin);
        enemiesFrontline.add(orc);

        RaidGroup enemyRaid = new RaidGroup("Enemy Army");
        enemyRaid.add(enemiesFrontline); 
        enemyRaid.add(boss);           

        System.out.println("--- Hierarchy Structure ---");
        heroes.printTree("");
        enemyRaid.printTree("");

        Skill fireSlash = new SingleTargetSkill("Slash", 25, new FireEffect());
        Skill iceSlash = new SingleTargetSkill("Slash", 25, new IceEffect());
        Skill fireArea = new AreaSkill("Firestorm", 15, new FireEffect());
        Skill shadowSingle = new SingleTargetSkill("Shadow Bolt", 40, new ShadowEffect());
        Skill shadowArea = new AreaSkill("Shadow Nova", 20, new ShadowEffect());

        System.out.println("\n--- Bridge Pattern Combinations ---");
        System.out.println("1. Same Skill, Diff Effects: " + fireSlash.getEffectName() + " vs " + iceSlash.getEffectName());
        System.out.println("2. Same Effect, Diff Skills: " + shadowSingle.getSkillName() + " vs " + shadowArea.getSkillName());

        
    }
}
