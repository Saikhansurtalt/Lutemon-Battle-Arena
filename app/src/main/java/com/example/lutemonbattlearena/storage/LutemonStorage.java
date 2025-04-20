package com.example.lutemonbattlearena.storage;

import android.content.Context;

import com.example.lutemonbattlearena.models.Lutemon;

import java.io.*;
import java.util.HashMap;

public class LutemonStorage {
    private static LutemonStorage instance;

    private HashMap<Integer, Lutemon> lutemons = new HashMap<>();
    private int nextId = 0;

    private LutemonStorage() {
    }

    public static LutemonStorage getInstance() {
        if (instance == null) {
            instance = new LutemonStorage();
        }
        return instance;
    }

    public void addLutemon(Lutemon lutemon) {
        lutemons.put(nextId++, lutemon);
    }

    public void removeLutemon(int id) {
        lutemons.remove(id);
    }

    public Lutemon getById(int id) {
        return lutemons.get(id);
    }

    public HashMap<Integer, Lutemon> getAll() {
        return lutemons;
    }

    // 💾 Save Lutemons to local file
    public void saveLutemons(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("lutemons.dat", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(lutemons);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 🔁 Load Lutemons from file
    public void loadLutemons(Context context) {
        try {
            FileInputStream fis = context.openFileInput("lutemons.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            lutemons = (HashMap<Integer, Lutemon>) ois.readObject();
            // Update nextId to avoid collision
            nextId = lutemons.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
            ois.close();
            fis.close();
        } catch (Exception e) {
            // If file doesn't exist yet, just start fresh
            lutemons = new HashMap<>();
            nextId = 0;
            e.printStackTrace();
        }
    }
}
