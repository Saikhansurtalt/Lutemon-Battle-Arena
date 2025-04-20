package com.example.lutemonbattlearena.battle;

import com.example.lutemonbattlearena.models.Lutemon;

public class Battle {
    public static String startBattle(Lutemon a, Lutemon b) {
        StringBuilder log = new StringBuilder();
        while (a.isAlive() && b.isAlive()) {
            b.takeDamage(a.getAttackPower());
            log.append(a.getStats()).append(" attacks ").append(b.getName()).append("\n");

            if (!b.isAlive()) {
                log.append(b.getName()).append(" has died.\n");
                a.gainExperience();
                break;
            }

            a.takeDamage(b.getAttackPower());
            log.append(b.getStats()).append(" retaliates!\n");

            if (!a.isAlive()) {
                log.append(a.getName()).append(" has died.\n");
                b.gainExperience();
                break;
            }
        }
        return log.toString();
    }
}
