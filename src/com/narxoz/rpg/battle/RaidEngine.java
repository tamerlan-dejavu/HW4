package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;
import java.util.List;
import java.util.Random;

public class RaidEngine {
    private static RaidEngine instance;
    private Random random = new Random(1L);

    private RaidEngine() {
    }

    public static RaidEngine getInstance() {
        if (instance == null) {
            instance = new RaidEngine();
        }
        return instance;
    }

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, List<Skill> teamASkills, List<Skill> teamBSkills) {
        if (teamA == null || teamB == null || teamASkills.isEmpty() || teamBSkills.isEmpty()) {
            throw new IllegalArgumentException("Teams and skill lists cannot be null or empty");
        }

        RaidResult result = new RaidResult();
        result.addLine("=== Raid Battle Start ===");
        result.addLine("Team A: " + teamA.getName() + " vs Team B: " + teamB.getName());
        result.addLine("--------------------------");

        int rounds = 0;
        while (teamA.isAlive() && teamB.isAlive() && rounds < 100) {
            rounds++;
            result.addLine("--- Round " + rounds + " ---");
            if (teamA.isAlive() ) {
                int healthBefore = teamB.getHealth(); 
                Skill chosenSkill = teamASkills.get(random.nextInt(teamASkills.size()));
                
                chosenSkill.cast(teamB);
                
                int totalDamage = healthBefore - teamB.getHealth(); 
                result.addLine(teamA.getName() + " uses " + chosenSkill.getSkillName() + " [" +  chosenSkill.getEffectName() + "] on " +  teamB.getName() + " for "+ totalDamage);

                if (!teamB.isAlive()) {
                    result.addLine(" >>> " + teamB.getName() + " has been defeated!");
                }
            } 

            if (teamB.isAlive()) {
                int healthBefore = teamA.getHealth(); 
                Skill chosenSkill = teamBSkills.get(random.nextInt(teamBSkills.size()));
                
                chosenSkill.cast(teamA); 
                
                int totalDamage = healthBefore - teamA.getHealth(); 
                result.addLine( teamB.getName() + " uses " + chosenSkill.getSkillName() + " [" +  chosenSkill.getEffectName() + "] on " +  teamA.getName() + " for "+ totalDamage);

                if (!teamA.isAlive()) {
                    result.addLine(" >>> " + teamA.getName() + " has been defeated!");
                }
            } 
            
            result.addLine("Status: " + teamA.getName() + " HP=" + teamA.getHealth() + " | " + teamB.getName() + " HP=" + teamB.getHealth());
        }

        result.setRounds(rounds);
        determineWinner(teamA, teamB, result);
        
        return result;
    }

    private void determineWinner(CombatNode teamA, CombatNode teamB, RaidResult result) {
        if (teamA.isAlive() && !teamB.isAlive()) {
            result.setWinner(teamA.getName());
        } else if (teamB.isAlive() && !teamA.isAlive()) {
            result.setWinner(teamB.getName());
        } else {
            result.setWinner("Draw");
        }
        result.addLine("\n=== Battle finished. Winner: " + result.getWinner() + " ===");
    }
}
