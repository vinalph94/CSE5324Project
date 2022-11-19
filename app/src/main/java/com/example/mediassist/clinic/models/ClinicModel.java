package com.example.mediassist.clinic.models;

import java.io.Serializable;

public class ClinicModel implements Serializable {
    private String name;
    private String phone_number;
    private String address;
    private String description;
    private int zipcode;
    private String id;

    public ClinicModel(String name, String phone_number, String address, String description, int zipcode, String id) {
        this.name = name;
        this.phone_number = phone_number;
        this.address = address;
        this.description = description;
        this.zipcode = zipcode;
        this.id = id;
    }

    public ClinicModel() {
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

    public String setId() {
        return id;
    }

    public String getId() {
        return id;
    }
}
