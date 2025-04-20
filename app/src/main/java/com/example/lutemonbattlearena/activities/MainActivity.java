package com.example.lutemonbattlearena.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lutemonbattlearena.R;
import com.example.lutemonbattlearena.storage.LutemonStorage;

public class MainActivity extends AppCompatActivity {

    Button btnCreate, btnTrain, btnBattle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load saved Lutemons
        LutemonStorage.getInstance().loadLutemons(this);

        // Connect buttons
        btnCreate = findViewById(R.id.btnCreate);
        btnTrain = findViewById(R.id.btnTrain);
        btnBattle = findViewById(R.id.btnBattle);

        btnCreate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateLutemonActivity.class);
            startActivity(intent);
        });

        btnTrain.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TrainLutemonActivity.class);
            startActivity(intent);
        });

        btnBattle.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LutemonPickerActivity.class);
            startActivity(intent);
        });
    }
}
