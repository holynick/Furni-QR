package com.example.ckfyp.furniqr;

/**
 * Created by Nick Yau on 3/18/2017.
 */

public class cartfurnitureitem {
    String id;
    String name;
    float cost;
    String colour;
    String texttile;
    int quantity;
    float amount;
    String image;
    boolean selected;

    public cartfurnitureitem(String id, String name, float cost, String colour, String texttile, int quantity, float amount, String image) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.colour = colour;
        this.texttile = texttile;
        this.quantity = quantity;
        this.amount = amount;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public cartfurnitureitem(){}

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
