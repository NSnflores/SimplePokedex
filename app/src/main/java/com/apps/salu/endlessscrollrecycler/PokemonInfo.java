package com.apps.salu.endlessscrollrecycler;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

        pokeImage = (ImageView)findViewById(R.id.imagePokemon);
        pokeTitle = (TextView)findViewById(R.id.nameText);
        pokeWeight = (TextView)findViewById(R.id.weightText);
        progressBar = (ProgressBar)findViewById(R.id.progressInfo);
    }
    class PokemonGetter extends AsyncTask<Integer, Void, Pokemon> {

        @Override
        protected Pokemon doInBackground(Integer... ids) {
            try {
                HttpGet httpget = new HttpGet("http://pokeapi.co/api/v2/pokemon/" + ids[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpget);
                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject jsono = new JSONObject(data);

                    String name = jsono.getString("name");
                    float weight = (float)jsono.getDouble("weight");
                    Pokemon pokemon = new Pokemon()
                            .toWeight(weight)
                            .toName(name)
                            .toId(ids[0])
                            .toPath("file:///android_asset/pp"+ids[0]+".png");
                    return pokemon;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Pokemon result){
            Picasso.with(getApplicationContext())
                    .load(result.getSpritePath())
                    .into(pokeImage);
            pokeTitle.setText(result.getId()+". "+result.getName());
            pokeWeight.setText(result.getWeight()+"lbs.");
            progressBar.setVisibility(View.GONE);
        }

    }
}
