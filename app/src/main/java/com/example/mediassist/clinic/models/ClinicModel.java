package com.example.mediassist.clinic.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class ClinicModel implements Serializable {
    @Exclude
    private String id;
    private String name;
    private String phone_number;
    private String street;
    private String city;
    private String county;
    private String country;
    private String description;
    private int zipcode;


    public ClinicModel(String name, String description, String phone_number, String street, String city, String county, String country, int zipcode) {
        this.name = name;
        this.description = description;
        this.phone_number = phone_number;
        this.street = street;
        this.city = city;
        this.county = county;
        this.country = country;
        this.zipcode = zipcode;
    }

    public ClinicModel() {
    }

    @Override
    public String toString() {
        return this.name; // What to display in the Spinner list.
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
