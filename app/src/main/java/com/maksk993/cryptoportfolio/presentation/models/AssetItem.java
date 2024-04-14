package com.maksk993.cryptoportfolio.presentation.models;

public class AssetItem {
    private String name;
    private float price;
    private int image;

    public AssetItem(String name, float price, int image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public AssetItem(String name, float price) {
        this.name = name;
        this.price = price;
        this.image = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
