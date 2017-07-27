package com.apps.salu.endlessscrollrecycler;


/**
 * Created by Noe on 7/20/17.
 */

public class Pokemon {

    private String spritePath;
    private String name;
    private float weight;
    private int id;
    private String info;

    public String getInfo() { return info; }

    public void setInfo(String info) { this.info = info; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpritePath() {
        return spritePath;
    }

    public String getName() {
        return name;
    }

    public float getWeight() {
        return weight;
    }

    public void setSpritePath(String spritePath) {
        this.spritePath = spritePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Pokemon(){}

    public Pokemon toId(int id){
        this.id = id;
        return this;
    }
    public Pokemon toName(String name){
        this.name = name;
        return this;
    }
    public Pokemon toWeight(float weight){
        this.weight = weight;
        return this;
    }
    public Pokemon toPath(String path){
        this.spritePath = path;
        return this;
    }
    public Pokemon toInfo(String info){
        this.info = info;
        return this;
    }
}
