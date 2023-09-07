package com.example.mediassist.category.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    @Exclude
    private String id;
    private String name;
    private String description;
    private String clinic_id;
    private String icon_id;
    private int count;


    public CategoryModel(String name, String description, String icon_id, String clinic_id) {
        this.name = name;
        this.description = description;
        this.icon_id = icon_id;
        this.clinic_id = clinic_id;
    }

    public CategoryModel() {
    }


    @Override
    public String toString() {
        return this.name; // What to display in the Spinner list.
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

    public String getClinicId() {
        return clinic_id;
    }

    public void setClinicId(String clinic_id) {
        this.clinic_id = clinic_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getIconId() {
        return icon_id;
    }

    public void setIconId(String icon_id) {
        this.icon_id = icon_id;
    }


    public int getIDoctorsCount() {
        return count;
    }

    public void setDoctorsCount(int count) {
        this.count = count;
    }
}
