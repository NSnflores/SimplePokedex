package com.apps.salu.endlessscrollrecycler;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.apps.salu.endlessscrollrecycler.EndlessScrollListener.adapter;
import static com.apps.salu.endlessscrollrecycler.EndlessScrollListener.textView;

public class MainActivity extends AppCompatActivity {
    List<Pokemon> pokemons = new ArrayList<>();
    ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PokemonQueue.init(this);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.tv1);
        RecyclerView lvItems = (RecyclerView) findViewById(R.id.lvItems);
        adapter = new PokemonAdapter(pokemons, this);
        progressBar = (ProgressBar)findViewById(R.id.progress);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        lvItems.setLayoutManager(mLayoutManager);
        lvItems.setAdapter(adapter);
        EndlessScrollListener listener = new EndlessScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(PokemonQueue.containsPokemon()) {
                    pokemons.add(PokemonQueue.getPokemon());
                    textView.setText(page + " Items");
                }
            }
        };
        lvItems.addOnScrollListener(listener);
    }
}
