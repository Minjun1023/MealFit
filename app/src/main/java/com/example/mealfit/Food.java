package com.example.mealfit;

public class Food {
    private String foodName;
    private double energy;
    private double protein;
    private double fat;
    private double carbohydrate;

    public Food(String foodName, double energy, double protein, double fat, double carbohydrate) {
        this.foodName = foodName;
        this.energy = energy;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getEnergy() {
        return energy;
    }

    public double getProtein() {
        return protein;
    }

    public double getFat() {
        return fat;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }
}
