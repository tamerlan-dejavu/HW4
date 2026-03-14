package com.narxoz.rpg.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartyComposite implements CombatNode {
    private final String name;
    private final List<CombatNode> children = new ArrayList<>();

    public PartyComposite(String name) {
        this.name = name;
    }

    public void add(CombatNode node) {
        children.add(node);
    }

    public void remove(CombatNode node) {
        children.remove(node);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        int healthOfAll = 0;
        for(CombatNode child : children){
            healthOfAll += child.getHealth();
        }
        return healthOfAll;
    }

    @Override
    public int getAttackPower() {
        int attackOfAll = 0;
        for(CombatNode child : children){
            attackOfAll += child.getAttackPower();
        }
        return attackOfAll;
    }

    @Override
    public void takeDamage(int amount) {
        List<CombatNode> aliveChildren = getAliveChildren();
        if (aliveChildren.isEmpty()) {
            return;
        }
        int numAlive = aliveChildren.size();
        int damagePerChild = amount / numAlive;
        int remainder = amount % numAlive;
        for (int i = 0; i < numAlive; i++) {
            int damage = damagePerChild;
            if (i < remainder) {
                damage += 1;
            }
            aliveChildren.get(i).takeDamage(damage);
        }
    }

    @Override
    public boolean isAlive() {
        for (CombatNode child : children) {
            if (child.isAlive()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<CombatNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void printTree(String indent) {
        String status = isAlive() ? "ALIVE" : "DEFEATED";
        System.out.println(indent + "+ " + name + " [" + status + ", TotalATK=" + getAttackPower() + "]");
        for (CombatNode child : children) {
            child.printTree(indent + "   ");
        }
    }

    private List<CombatNode> getAliveChildren() {
        List<CombatNode> alive = new ArrayList<>();
        for (CombatNode child : children) {
            if (child.isAlive()) {
                alive.add(child);
            }
        }
        return alive;
    }
}
