package com.apps.salu.endlessscrollrecycler;

import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Noe on 7/23/17.
 */

public class PokemonQueue {
    public Queue<Pokemon> queue = new LinkedList<Pokemon>();
    private static PokemonQueue singleton;

    public static boolean containsPokemon(){
        return singleton.queue.size()>0;
    }
    public static void init(){
        singleton = new PokemonQueue();
    }
    private PokemonQueue(){
        for (String str : PokemonUtils.pokemonList.split("\\n")) {
            String[] values = str.split(",");
            int id = Integer.parseInt(values[0]);
            Pokemon pokemon = new Pokemon()
                    .toId(id)
                    .toName(values[1])
                    .toWeight(Float.parseFloat(values[4]))
                    .toPath("file:///android_asset/pp"+id+".png");
            queue.add(pokemon);
            Log.e("Pokemon", pokemon.getName()+"  "+pokemon.getId()+" "+pokemon.getSpritePath());
        }
    };
    public static Pokemon getPokemon(){
        return singleton.queue.remove();
    }

}
