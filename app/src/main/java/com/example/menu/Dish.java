package com.example.menu;

public class Dish {
    private String name;
    private String imagePath;

    public Dish(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }
}
