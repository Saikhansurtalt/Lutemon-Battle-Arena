package com.example.lutemonbattlearena.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lutemonbattlearena.R;
import com.example.lutemonbattlearena.models.Lutemon;
import com.example.lutemonbattlearena.storage.LutemonStorage;

import java.util.ArrayList;
import java.util.Locale;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.ViewHolder> {

    private ArrayList<Lutemon> lutemons;
    private Context context;
    private Handler handler = new Handler();

    public TrainAdapter(Context context, ArrayList<Lutemon> lutemons) {
        this.context = context;
        this.lutemons = lutemons;
        startCooldownUpdater();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textStats, textCooldown;
        ImageView imageLutemon;
        Button btnTrain, btnEvolve, btnRename, btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            imageLutemon = itemView.findViewById(R.id.imageLutemon);
            textStats = itemView.findViewById(R.id.textStats);
            textCooldown = itemView.findViewById(R.id.textCooldown);
            btnTrain = itemView.findViewById(R.id.btnTrain);
            btnEvolve = itemView.findViewById(R.id.btnEvolve);
            btnRename = itemView.findViewById(R.id.btnRename);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_train_lutemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lutemon l = lutemons.get(position);
        holder.textStats.setText(l.getStats());

        int imageRes = getImageForColor(l.getColor());
        holder.imageLutemon.setImageResource(imageRes);

        updateCooldownText(holder.textCooldown, l);
        boolean onCooldown = l.isOnCooldown();

        holder.btnTrain.setEnabled(!onCooldown);
        holder.btnEvolve.setEnabled(!onCooldown && l.getExperience() >= 3);

        holder.btnTrain.setOnClickListener(v -> {
            l.gainExperience();
            l.setCooldownMinutes(2);
            LutemonStorage.getInstance().saveLutemons(context);
            notifyItemChanged(position);
            Toast.makeText(context, "Trained " + l.getName(), Toast.LENGTH_SHORT).show();
        });

        holder.btnEvolve.setOnClickListener(v -> {
            l.evolve();
            l.setCooldownMinutes(5);
            LutemonStorage.getInstance().saveLutemons(context);
            notifyItemChanged(position);
            Toast.makeText(context, l.getName() + " evolved!", Toast.LENGTH_SHORT).show();
        });

        holder.btnRename.setOnClickListener(v -> {
            EditText input = new EditText(context);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setText(l.getName());

            new AlertDialog.Builder(context)
                    .setTitle("Rename Lutemon")
                    .setView(input)
                    .setPositiveButton("OK", (dialog, which) -> {
                        l.setName(input.getText().toString());
                        LutemonStorage.getInstance().saveLutemons(context);
                        notifyItemChanged(position);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Lutemon?")
                    .setMessage("Are you sure you want to delete " + l.getName() + "?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        LutemonStorage.getInstance().getAll().values().remove(l);
                        lutemons.remove(position);
                        LutemonStorage.getInstance().saveLutemons(context);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, lutemons.size());
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return lutemons.size();
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

    private void updateCooldownText(TextView textView, Lutemon lutemon) {
        if (!lutemon.isOnCooldown()) {
            textView.setText("Cooldown: Ready");
        } else {
            long millisLeft = lutemon.getCooldownEndTime() - System.currentTimeMillis();
            long seconds = millisLeft / 1000;
            long minutes = seconds / 60;
            long remSeconds = seconds % 60;
            textView.setText(String.format(Locale.getDefault(), "Cooldown: %dm %ds", minutes, remSeconds));
        }
    }

    private void startCooldownUpdater() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged(); // refresh cooldown displays
                handler.postDelayed(this, 1000); // every second
            }
        }, 1000);
    }
}
