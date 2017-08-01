package com.apps.salu.endlessscrollrecycler;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class PokemonInfo extends AppCompatActivity {

    private int id;
    private ImageView pokeImage;
    public TextView pokeTitle;
    public TextView pokeWeight;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_info);

        id = getIntent().getIntExtra("id", 1);
        new PokemonGetter().execute(id);

        pokeImage = (ImageView) findViewById(R.id.imagePokemon);
        pokeTitle = (TextView) findViewById(R.id.nameText);
        pokeWeight = (TextView) findViewById(R.id.weightText);
        progressBar = (ProgressBar) findViewById(R.id.progressInfo);
    }

    class PokemonGetter extends AsyncTask<Integer, Void, Pokemon> {
        @Override
        protected Pokemon doInBackground(Integer... ids){
            try {
                String url = "http://pokeapi.co/api/v2/pokemon/" + ids[0];
                String data = Helpers.httpGET(url);
                if(data=="") return null;
                JSONObject jsono = new JSONObject(data);
                String name = jsono.getString("name");
                float weight = (float) jsono.getDouble("weight");
                Pokemon pokemon = new Pokemon()
                        .toWeight(weight)
                        .toName(name)
                        .toId(ids[0])
                        .toPath("file:///android_asset/pp" + ids[0] + ".png");
                return pokemon;
            }catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Pokemon result) {
            Picasso.with(getApplicationContext())
                    .load(result.getSpritePath())
                    .into(pokeImage);
            pokeTitle.setText(result.getId() + ". " + result.getName());
            pokeWeight.setText(result.getWeight() + "lbs.");
            progressBar.setVisibility(View.GONE);
        }

    }
}
