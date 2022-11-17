package com.example.mediassist.doctor.models;

public class DoctorModel {
    private String name;
    private String phone_number;
    private String address;

    public DoctorModel(String name, String phone_number, String address){
        this.name = name;
        this.phone_number = phone_number;
        this.address = address;
    }

    public String getDoctor_name(){
        return name;
    }

    public String getDoctor_phone_number(){
        return phone_number;
    }

    public String getDoctor_address(){
        return address;
    }

    public void setDoctor_name(String name){
        this.name=name;
    }
}
