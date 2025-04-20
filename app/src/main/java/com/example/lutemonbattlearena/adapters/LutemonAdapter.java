package com.example.lutemonbattlearena.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lutemonbattlearena.R;
import com.example.lutemonbattlearena.models.Lutemon;
import java.util.ArrayList;

public class LutemonAdapter extends RecyclerView.Adapter<LutemonAdapter.ViewHolder> {
    private ArrayList<Lutemon> lutemons;
    private Context context;

    public LutemonAdapter(Context context, ArrayList<Lutemon> lutemons) {
        this.context = context;
        this.lutemons = lutemons;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageLutemon;
        TextView textStats;

        public ViewHolder(View itemView) {
            super(itemView);
            imageLutemon = itemView.findViewById(R.id.imageLutemon);
            textStats = itemView.findViewById(R.id.textStats);
        }
    }

    @Override
    public LutemonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lutemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LutemonAdapter.ViewHolder holder, int position) {
        Lutemon l = lutemons.get(position);
        holder.textStats.setText(l.getStats());

        int imageRes = getImageForColor(l.getColor());
        holder.imageLutemon.setImageResource(imageRes);
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
    public int getItemCount() {
        return lutemons.size();
    }
}
