package com.apps.salu.endlessscrollrecycler;

import android.os.AsyncTask;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.Queue;

import static com.apps.salu.endlessscrollrecycler.EndlessScrollListener.adapter;

/**
 * Created by Noe on 7/23/17.
 */

public class PokemonQueue {
    public Queue<Pokemon> queue = new LinkedList<Pokemon>();
    private static PokemonQueue singleton;
    private MainActivity mainActivity;
    private static String pokemonSource = "https://api.myjson.com/bins/v5m1r";

    public static boolean containsPokemon() {
        return singleton.queue.size() > 0;
    }

    public static void init(MainActivity mainActivity) {
        singleton = new PokemonQueue();
        singleton.mainActivity = mainActivity;
    }

    private PokemonQueue() {
        new PokemonGetter().execute();
    }

    public static Pokemon getPokemon() {
        return singleton.queue.remove();
    }

    class PokemonGetter extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... ids) {
            try {
                String data = Helpers.httpGET(pokemonSource);
                JSONObject jsono = new JSONObject(data);
                JSONArray array = jsono.getJSONArray("pokemons");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obje = array.getJSONObject(i);
                    int id = Integer.parseInt(obje.getString("id"));
                    String name = obje.getString("name");
                    float weight = (float) obje.getDouble("weight");
                    Pokemon pokemon = new Pokemon()
                            .toWeight(weight)
                            .toName(name)
                            .toId(id)
                            .toPath("file:///android_asset/pp" + id + ".png");
                    singleton.queue.add(pokemon);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean status) {
            if (status) {
                for (int i = 0; i < 20; i++)
                    singleton.mainActivity.pokemons.add(getPokemon());
                adapter.notifyDataSetChanged();
                mainActivity.progressBar.setVisibility(View.GONE);
            } else {
                new PokemonGetter().execute();
            }
        }
    }
}
