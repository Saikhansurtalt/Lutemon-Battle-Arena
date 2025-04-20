package com.example.lutemonbattlearena.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lutemonbattlearena.R;
import com.example.lutemonbattlearena.adapters.TrainAdapter;
import com.example.lutemonbattlearena.models.Lutemon;
import com.example.lutemonbattlearena.storage.LutemonStorage;

import java.util.ArrayList;

public class TrainLutemonActivity extends AppCompatActivity {

    private ArrayList<Lutemon> lutemons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_lutemon);

        lutemons = new ArrayList<>(LutemonStorage.getInstance().getAll().values());

        RecyclerView recyclerView = findViewById(R.id.recyclerTrain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TrainAdapter(this, lutemons));

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}
