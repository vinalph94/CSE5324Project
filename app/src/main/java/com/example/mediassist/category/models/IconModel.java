package com.example.mediassist.category.models;

import com.google.firebase.firestore.Exclude;

public class IconModel {
    @Exclude
    private String id;
    private String name;


    public IconModel(String name) {
        this.name = name;

    }

    public IconModel() {
    }

    @Override
    public String toString() {
        return this.name; // What to display in the Spinner list.
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
}
