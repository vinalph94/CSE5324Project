package com.example.mediassist.clinic.models;

public class ClinicModel {
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
}
