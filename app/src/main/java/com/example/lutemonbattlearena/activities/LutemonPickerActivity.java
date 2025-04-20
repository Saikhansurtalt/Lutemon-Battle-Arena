package com.example.lutemonbattlearena.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lutemonbattlearena.R;
import com.example.lutemonbattlearena.models.Lutemon;
import com.example.lutemonbattlearena.storage.LutemonStorage;

import java.util.ArrayList;

public class LutemonPickerActivity extends AppCompatActivity {

    private Spinner spinner1, spinner2;
    private Button btnStart;
    private ArrayList<Lutemon> lutemons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lutemon_picker);

        spinner1 = findViewById(R.id.spinnerPlayer1);
        spinner2 = findViewById(R.id.spinnerPlayer2);
        btnStart = findViewById(R.id.btnStartBattle);

        lutemons = new ArrayList<>(LutemonStorage.getInstance().getAll().values());

        if (lutemons.size() < 2) {
            Toast.makeText(this, "You must create at least 2 Lutemons to battle.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        ArrayList<String> names = new ArrayList<>();
        for (Lutemon l : lutemons) {
            names.add(l.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        btnStart.setOnClickListener(v -> {
            String name1 = spinner1.getSelectedItem().toString();
            String name2 = spinner2.getSelectedItem().toString();

            if (name1.equals(name2)) {
                Toast.makeText(this, "Pick two different Lutemons!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, BattleArenaActivity.class);
            intent.putExtra("player1", name1);
            intent.putExtra("player2", name2);
            startActivity(intent);
        });

        // ✅ Back to menu button setup
        Button btnBack = findViewById(R.id.btnBackToMenu);
        btnBack.setOnClickListener(v -> finish());
    }
}
