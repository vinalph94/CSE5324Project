package com.example.mediassist.clinicadmin.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class ClinicAdminModel implements Serializable {

    private String id;
    private String name;
    private String phone_number;
    private String email;
    private String assign_clinic;
    private String clinic_name;


    public ClinicAdminModel(String name, String phone_number, String email, String assign_clinic, String clinic_name, String id) {
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.assign_clinic = assign_clinic;
        this.clinic_name = clinic_name;
        this.id = id;

    }

    public ClinicAdminModel() {
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

    public String getEmail() {
        return email;
    }

    public void getEmail(String email) {
        this.email = email;
    }

    public String getAssign_clinic() {
        return assign_clinic;
    }

    public void setAssign_clinic(String assign_clinic) {
        this.assign_clinic = assign_clinic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String name) {
        this.clinic_name = name;
    }
}
