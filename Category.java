package com.example.trivial;

import androidx.annotation.NonNull;

public class Category {
    public static final int  GENERALE = 1;
    public static final int TRI = 2;
    public static final int CONSOMMATION = 3;
    public static final int POLLUTION = 4;
    private int id;
    private String name;
    public Category() {
    }
    public Category(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
