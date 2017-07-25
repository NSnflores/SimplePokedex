package com.apps.salu.endlessscrollrecycler;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Noe on 7/10/17.
 */

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.MyViewHolder>{
    private List<Pokemon> pokemons;
    private Activity context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView weight;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.nameText);
            weight = (TextView) view.findViewById(R.id.weightText);
            image = (ImageView) view.findViewById(R.id.imagePokemon);
        }
    }

    public PokemonAdapter(List<Pokemon> pokemons, Activity context) {
        this.pokemons = pokemons;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemonview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        int id = pokemons.get(position).getId();
        String name = pokemons.get(position).getName();
        holder.title.setText(id + ". " + name);
        String weig = pokemons.get(position).getWeight()+"lbs";
        holder.weight.setText(weig);
        String ima = pokemons.get(position).getSpritePath();
        Log.e("IMAGE",ima);
        Picasso.with(context)
                .load(pokemons.get(position).getSpritePath())
                .resize(100,100)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

}
