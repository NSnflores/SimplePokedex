package com.apps.salu.endlessscrollrecycler;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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

    public static boolean containsPokemon(){
        return singleton.queue.size()>0;
    }
    public static void init(MainActivity mainActivity){
        singleton = new PokemonQueue();
        singleton.mainActivity = mainActivity;
    }
    private PokemonQueue(){
        new PokemonGetter().execute();
    }
    public static Pokemon getPokemon(){
        return singleton.queue.remove();
    }

    class PokemonGetter extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... ids) {
            try {
                HttpGet httpget = new HttpGet(pokemonSource);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpget);
                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    Log.e("Status", "200");
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject jsono = new JSONObject(data);
                    JSONArray array = jsono.getJSONArray("pokemons");
                    for(int i = 0; i < array.length(); i++) {
                        JSONObject obje = array.getJSONObject(i);
                        int id = Integer.parseInt(obje.getString("id"));
                        String name = obje.getString("name");
                        float weight = (float)obje.getDouble("weight");
                        Pokemon pokemon = new Pokemon()
                                .toWeight(weight)
                                .toName(name)
                                .toId(id)
                                .toPath("file:///android_asset/pp"+id+".png");
                        singleton.queue.add(pokemon);
                    }
                    return true;
                }
                Log.e("Status", "Not 200");
            } catch (Exception e) {
                Log.e("Exception", "on request");
                e.printStackTrace();
            }
            return false;
        }
        protected void onPostExecute(Boolean status) {
            if(status) {
                for (int i = 0; i < 20; i++)
                    singleton.mainActivity.pokemons.add(getPokemon());
                adapter.notifyDataSetChanged();
                mainActivity.progressBar.setVisibility(View.GONE);
            }
            else {
                Log.e("Loading status", "FAILED,  retrying");
                new PokemonGetter().execute();
            }
        }
    }
}
