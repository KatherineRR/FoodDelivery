
package com.example.fooddelivery.model;

import java.io.Serializable;

public class Dish implements Serializable {

    private String name;
    private String rating;
    private String deliveryTime;
    private String calories;
    private String price;

    public Dish() {
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public Dish(String name, String rating, String deliveryTime, String calories, String price) {
        this.name = name;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.calories = calories;
        this.price = price;
    }
}
