package com.example.lutemonbattlearena.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lutemonbattlearena.R;
import com.example.lutemonbattlearena.models.*;
import com.example.lutemonbattlearena.storage.LutemonStorage;

import java.util.ArrayList;
import java.util.Random;

public class BattleArenaActivity extends AppCompatActivity {

    private Lutemon[] playerTeam = new Lutemon[2];
    private Lutemon[] enemyTeam = new Lutemon[2];
    private int activePlayer = 0;
    private int activeEnemy = 0;

    private TextView battleLog, playerStats, enemyStats;
    private ImageView playerImage, enemyImage;
    private ProgressBar playerHpBar, enemyHpBar;

    private Animation shakeAnim;
    private Random random = new Random();
    private MediaPlayer hitSound, koSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_arena);

        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake);
        hitSound = MediaPlayer.create(this, R.raw.hit);
        koSound = MediaPlayer.create(this, R.raw.ko);

        battleLog = findViewById(R.id.battleLog);
        playerStats = findViewById(R.id.playerStats);
        enemyStats = findViewById(R.id.enemyStats);
        playerImage = findViewById(R.id.playerImage);
        enemyImage = findViewById(R.id.enemyImage);
        playerHpBar = findViewById(R.id.playerHpBar);
        enemyHpBar = findViewById(R.id.enemyHpBar);

        ArrayList<Lutemon> all = new ArrayList<>(LutemonStorage.getInstance().getAll().values());

        String name1 = getIntent().getStringExtra("player1");
        String name2 = getIntent().getStringExtra("player2");

        for (Lutemon l : all) {
            if (l.getName().equals(name1)) playerTeam[0] = l;
            else if (l.getName().equals(name2)) playerTeam[1] = l;
        }

        enemyTeam[0] = generateRandomLutemon("AI One");
        enemyTeam[1] = generateRandomLutemon("AI Two");

        updateUI();

        findViewById(R.id.btnAttack).setOnClickListener(v -> {
            Lutemon player = playerTeam[activePlayer];
            Lutemon enemy = enemyTeam[activeEnemy];
            int dmg = player.getAttackPower();
            enemy.takeDamage(dmg);
            playHit(enemyImage);
            log(player.getName() + " attacks " + enemy.getName() + " for " + dmg + "!");
            if (!enemy.isAlive()) {
                log(enemy.getName() + " has fainted!");
                playKO();
                if (enemyTeam[0].isAlive() || enemyTeam[1].isAlive()) {
                    activeEnemy = (activeEnemy == 0) ? 1 : 0;
                    log("Enemy switched to " + enemyTeam[activeEnemy].getName() + "!");
                } else {
                    log("🎉 You win the battle!");
                    disableButtons();
                    return;
                }
            }
            updateUI();
            enemyTurn();
        });

        findViewById(R.id.btnDefend).setOnClickListener(v -> {
            playerTeam[activePlayer].defend();
            log(playerTeam[activePlayer].getName() + " is guarding!");
            updateUI();
            enemyTurn();
        });

        findViewById(R.id.btnChange).setOnClickListener(v -> {
            int prev = activePlayer;
            activePlayer = (activePlayer == 0) ? 1 : 0;
            if (!playerTeam[activePlayer].isAlive()) {
                activePlayer = prev;
                log("That Lutemon has fainted! Stay with current.");
            } else {
                log("You switched to " + playerTeam[activePlayer].getName() + "!");
                updateUI();
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void enemyTurn() {
        Lutemon enemy = enemyTeam[activeEnemy];
        Lutemon player = playerTeam[activePlayer];

        if (!enemy.isAlive()) return;

        if (random.nextBoolean()) {
            int dmg = enemy.getAttackPower();
            player.takeDamage(dmg);
            playHit(playerImage);
            log(enemy.getName() + " attacks " + player.getName() + " for " + dmg + "!");
            if (!player.isAlive()) {
                log(player.getName() + " has fainted!");
                playKO();
                if (playerTeam[0].isAlive() || playerTeam[1].isAlive()) {
                    activePlayer = (activePlayer == 0) ? 1 : 0;
                    log("You switched to " + playerTeam[activePlayer].getName() + "!");
                } else {
                    log("💀 You lost the battle!");
                    disableButtons();
                }
            }
        } else {
            enemy.defend();
            log(enemy.getName() + " is guarding!");
        }
        updateUI();
    }

    private void disableButtons() {
        findViewById(R.id.btnAttack).setEnabled(false);
        findViewById(R.id.btnDefend).setEnabled(false);
        findViewById(R.id.btnChange).setEnabled(false);
    }

    private void updateUI() {
        Lutemon p = playerTeam[activePlayer];
        Lutemon e = enemyTeam[activeEnemy];

        playerStats.setText(p.getStats());
        enemyStats.setText(e.getStats());

        playerImage.setImageResource(getImageForColor(p.getColor()));
        enemyImage.setImageResource(getImageForColor(e.getColor()));

        playerHpBar.setMax(p.getMaxHp());
        playerHpBar.setProgress(p.getCurrentHp());

        enemyHpBar.setMax(e.getMaxHp());
        enemyHpBar.setProgress(e.getCurrentHp());
    }

    private void log(String text) {
        String current = battleLog.getText().toString();
        battleLog.setText(current + "\n" + text);
    }

    private void playHit(ImageView target) {
        target.startAnimation(shakeAnim);
        if (hitSound != null) hitSound.start();
    }

    private void playKO() {
        if (koSound != null) koSound.start();
    }

    private Lutemon generateRandomLutemon(String namePrefix) {
        String[] colors = {"White", "Green", "Pink", "Orange", "Black"};
        String color = colors[random.nextInt(colors.length)];
        String name = namePrefix + " " + color;

        switch (color) {
            case "White": return new White(name);
            case "Green": return new Green(name);
            case "Pink": return new Pink(name);
            case "Orange": return new Orange(name);
            case "Black": return new Black(name);
            default: return new White(name);
        }
    }

    private int getImageForColor(String color) {
        switch (color) {
            case "White": return R.drawable.lutemon_white;
            case "Green": return R.drawable.lutemon_green;
            case "Pink": return R.drawable.lutemon_pink;
            case "Orange": return R.drawable.lutemon_orange;
            case "Black": return R.drawable.lutemon_black;
            default: return R.drawable.lutemon_white;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (hitSound != null) hitSound.release();
        if (koSound != null) koSound.release();
    }
}
