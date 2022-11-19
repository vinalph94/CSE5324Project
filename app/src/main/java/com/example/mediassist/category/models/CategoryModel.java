package com.example.mediassist.category.models;

public class CategoryModel  {

    private String name;
    private String description;
    private String assignclinic;


    public CategoryModel(String name, String description,  String assignclinic) {
        this.name = name;
        this.description = description;
        this.assignclinic = assignclinic;
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


}
