package com.example.mediassist.clinic.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class ClinicModel implements Serializable {
    @Exclude
    private String id;
    private String name;
    private String phone_number;
    private String address;
    private String description;
    private int zipcode;


    public ClinicModel(String name, String phone_number, String address, String description, int zipcode) {
        this.name = name;
        this.phone_number = phone_number;
        this.address = address;
        this.description = description;
        this.zipcode = zipcode;
    }

    public ClinicModel() {
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
