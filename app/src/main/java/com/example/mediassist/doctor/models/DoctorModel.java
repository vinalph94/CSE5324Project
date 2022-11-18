package com.example.mediassist.doctor.models;

public class DoctorModel {
    private String name;
    private String phone_number;
    private String address;
    private String assign_specialization;
    private String assign_clinic;


    public DoctorModel(String name, String phone_number, String address, String assign_specialization, String assign_clinic) {
        this.name = name;
        this.phone_number = phone_number;
        this.address = address;
        this.assign_specialization = assign_specialization;
        this.assign_clinic = assign_clinic;

    }

    public String getDoctor_name() {
        return name;
    }

    public String getDoctor_phone_number() {
        return phone_number;
    }

    public String getDoctor_address() {
        return address;
    }

    public String getAssign_specialization() {
        return assign_specialization;
    }

    public String getAssign_clinic() {
        return assign_clinic;
    }

    public void setDoctor_name(String name) {
        this.name = name;
    }

    public void setDoctor_phone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setDoctor_address(String address) {
        this.address = address;
    }

    public void setAssign_specialization(String assign_specialization) {
        this.assign_specialization = assign_specialization;
    }

    public void setAssign_clinic(String assign_clinic) {
        this.assign_clinic = assign_clinic;
    }
}
