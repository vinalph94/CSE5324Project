package com.example.mediassist.category.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    @Exclude
    private String id;
    private String name;
    private String description;
    private String clinic_id;



    public CategoryModel(String name, String description, String clinic_id) {
        this.name = name;
        this.description = description;
        this.clinic_id = clinic_id;
    }

    public CategoryModel() {
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

    public String getCategoryId() {
        return clinic_id;
    }

    public void setCategoryId(String clinic_id) {
        this.clinic_id = clinic_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
