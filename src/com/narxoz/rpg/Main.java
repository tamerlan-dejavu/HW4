package com.narxoz.rpg;

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

        // Сложная вложенность: Взвод внутри Рейда
        PartyComposite enemiesFrontline = new PartyComposite("Frontline Squad");
        enemiesFrontline.add(goblin);
        enemiesFrontline.add(orc);

        RaidGroup enemyRaid = new RaidGroup("Enemy Army");
        enemyRaid.add(enemiesFrontline); // Вложенность здесь
        enemyRaid.add(boss);           // И прямой юнит рядом

        System.out.println("--- Hierarchy Structure ---");
        heroes.printTree("");
        enemyRaid.printTree("");

        // 3. Комбинации Bridge (Демонстрация независимости)
        
        // Сценарий А: Тот же навык, разные эффекты
        Skill fireSlash = new SingleTargetSkill("Slash", 25, new FireEffect());
        Skill iceSlash = new SingleTargetSkill("Slash", 25, new IceEffect());

        // Сценарий Б: Тот же эффект, разные типы навыков (Требование ТЗ)
        Skill fireArea = new AreaSkill("Firestorm", 15, new FireEffect());
        Skill shadowSingle = new SingleTargetSkill("Shadow Bolt", 40, new ShadowEffect());
        Skill shadowArea = new AreaSkill("Shadow Nova", 20, new ShadowEffect());

        System.out.println("\n--- Bridge Pattern Combinations ---");
        System.out.println("1. Same Skill, Diff Effects: " + fireSlash.getEffectName() + " vs " + iceSlash.getEffectName());
        System.out.println("2. Same Effect, Diff Skills: " + shadowSingle.getSkillName() + " vs " + shadowArea.getSkillName());

        // 4. Запуск рейда (Используем Singleton и Seed)
        System.out.println("\n--- Starting Simulation ---");
        
        // Вызываем через Singleton, как мы реализовали в RaidEngine
        RaidEngine engine = RaidEngine.getInstance().setRandomSeed(12345L);
        
        // Проведем битву: Герои используют Shadow Nova, Враги - Fire Slash
        RaidResult result = engine.runRaid(heroes, enemyRaid, shadowArea, fireSlash);

        // 5. Вывод логов
        System.out.println("\n--- Battle Log ---");
        result.getLog().forEach(System.out::println);

        System.out.println("\n--- Summary ---");
        System.out.println("Winner: " + result.getWinner());
        System.out.println("Total Rounds: " + result.getRounds());
        System.out.println(" ");
        System.out.println("Heroes tree after battle:");
        heroes.printTree("");

        System.out.println();
        System.out.println("Monsters tree after battle:");
        enemyRaid.printTree("");

        System.out.println("\n=== Demo Complete ===");
    }
}
