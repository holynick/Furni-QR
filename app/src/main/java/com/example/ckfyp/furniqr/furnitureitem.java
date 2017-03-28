package com.example.ckfyp.furniqr;

/**
 * Created by Nick Yau on 3/17/2017.
 */

public class furnitureitem {

    String id;
    String name;
    String description;
    float cost;
    String colour;
    String texttile;
    String image;

    public furnitureitem(String id, String name, String description, float cost, String colour, String texttile, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.colour = colour;
        this.texttile = texttile;
        this.image = image;
    }

    public furnitureitem(){}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getTexttile() {
        return texttile;
    }

    public void setTexttile(String texttile) {
        this.texttile = texttile;
    }
}
