package com.example.mediassist.category.models;

import java.io.Serializable;

public class CategoryModel implements Serializable {

    private String name;
    private String description;
    private String assignclinic;
    private String id;


    public CategoryModel(String name, String description, String assignclinic, String id) {
        this.name = name;
        this.description = description;
        this.assignclinic = assignclinic;
        this.id = id;
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

    public String getAssignclinic() {
        return assignclinic;
    }

    public void setAssignclinic(String assignclinic) {
        this.assignclinic = assignclinic;
    }

    public String setId() {
        return id;
    }

    public String getId() {
        return id;
    }

}
