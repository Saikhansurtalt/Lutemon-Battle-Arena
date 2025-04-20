package com.example.lutemonbattlearena.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lutemonbattlearena.R;
import com.example.lutemonbattlearena.models.*;
import com.example.lutemonbattlearena.storage.LutemonStorage;

public class CreateLutemonActivity extends AppCompatActivity {

    private EditText editName;
    private RadioButton rbWhite, rbGreen, rbPink, rbOrange, rbBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lutemon);

        editName = findViewById(R.id.editTextLutemonName);
        rbWhite = findViewById(R.id.radioWhite);
        rbGreen = findViewById(R.id.radioGreen);
        rbPink = findViewById(R.id.radioPink);
        rbOrange = findViewById(R.id.radioOrange);
        rbBlack = findViewById(R.id.radioBlack);

        // Back to menu button logic
        TextView backToMenu = findViewById(R.id.backToMenu);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Closes this activity and goes back to MainActivity
            }
        });
    }

    public void createLutemon(View view) {
        String name = editName.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Enter a name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Manual check since RadioButtons are in LinearLayouts
        String color = null;
        if (rbWhite.isChecked()) color = "White";
        else if (rbGreen.isChecked()) color = "Green";
        else if (rbPink.isChecked()) color = "Pink";
        else if (rbOrange.isChecked()) color = "Orange";
        else if (rbBlack.isChecked()) color = "Black";

        if (color == null) {
            Toast.makeText(this, "Select a Lutemon type", Toast.LENGTH_SHORT).show();
            return;
        }

        Lutemon newLutemon = null;

        switch (color) {
            case "White":
                newLutemon = new White(name);
                break;
            case "Green":
                newLutemon = new Green(name);
                break;
            case "Pink":
                newLutemon = new Pink(name);
                break;
            case "Orange":
                newLutemon = new Orange(name);
                break;
            case "Black":
                newLutemon = new Black(name);
                break;
        }

        if (newLutemon != null) {
            LutemonStorage.getInstance().addLutemon(newLutemon);
            Toast.makeText(this, color + " Lutemon \"" + name + "\" created!", Toast.LENGTH_SHORT).show();

            // ✅ Reset input
            editName.setText("");
            rbWhite.setChecked(false);
            rbGreen.setChecked(false);
            rbPink.setChecked(false);
            rbOrange.setChecked(false);
            rbBlack.setChecked(false);
        }
    }
}
