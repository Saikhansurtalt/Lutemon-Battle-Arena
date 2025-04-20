package com.example.lutemonbattlearena.models;

import java.io.Serializable;

public abstract class Lutemon implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String name, color;
    protected int attack, defense, maxHp, currentHp, experience;

    // ⏳ Cooldown
    protected long cooldownEndTime = 0;

    // 🛡️ Defend toggle (temporary guard)
    private boolean defending = false;

    public Lutemon(String name, String color, int attack, int defense, int maxHp) {
        this.name = name;
        this.color = color;
        this.attack = attack;
        this.defense = defense;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.experience = 0;
    }

    // ⚔️ Train
    public void gainExperience() {
        experience++;
        attack++;
    }

    // 🧬 Evolve
    public void evolve() {
        attack += 2;
        defense += 1;
        maxHp += 5;
        resetHealth();
        experience = 0;
    }

    public void resetHealth() {
        currentHp = maxHp;
    }

    // ✅ Use defending status to reduce incoming damage
    public void takeDamage(int incomingDamage) {
        int actualDamage = incomingDamage;

        if (defending) {
            actualDamage = Math.max(0, incomingDamage - defense);
            defending = false; // Defend only works once
        }

        currentHp -= actualDamage;
        if (currentHp < 0) currentHp = 0;
    }

    public boolean isAlive() {
        return currentHp > 0;
    }

    public int getAttackPower() {
        return attack + experience;
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return (experience / 3) + 1;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getColor() {
        return color;
    }

    // ⏳ Cooldown
    public boolean isOnCooldown() {
        return System.currentTimeMillis() < cooldownEndTime;
    }

    public void setCooldownMinutes(int minutes) {
        cooldownEndTime = System.currentTimeMillis() + minutes * 60_000;
    }

    public long getCooldownEndTime() {
        return cooldownEndTime;
    }

    // 🛡️ Defend for 1 turn only
    public void defend() {
        this.defending = true;
    }

    public boolean isDefending() {
        return defending;
    }

    public void setDefending(boolean defending) {
        this.defending = defending;
    }

    public String getStats() {
        return name + " (" + color + ")\nATK: " + attack + " | DEF: " + defense +
                "\nHP: " + currentHp + "/" + maxHp +
                (defending ? "\n🛡️ Defending" : "");
    }
}
