package com.example.fooddelivery.model;

import java.io.Serializable;

public class Info implements Serializable {

    private String name;
    private String description;
    private Long phone;
    private String email;

    public Info() {
    }

    public Info(String name, String description, Long phone, String email) {
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.email = email;
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

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
