package com.example.mediassist.clinic.models;

public class ClinicModel {
    private String name;
    private String phone_number;
    private String address;

    public ClinicModel(String name, String phone_number, String address){
        this.name = name;
        this.phone_number = phone_number;
        this.address = address;
    }

    public String getClinic_name(){
        return name;
    }

    public String getClinic_phone_number(){
        return phone_number;
    }

    public String getClinic_address(){
        return address;
    }

    public void setClinic_name(String name){
        this.name=name;
    }
}
