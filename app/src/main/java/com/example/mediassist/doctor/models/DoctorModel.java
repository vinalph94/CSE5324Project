package com.example.mediassist.doctor.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class DoctorModel implements Serializable {
    private String doctor_name;
    private String doctor_phone_number;
    private String doctor_email;
    private String category_id;
    private String clinic_id;
    private double dist;
    @Exclude
    private String id;

    public DoctorModel(String doctor_name, String doctor_phone_number, String doctor_email, String category_id, String clinic_id) {
        this.doctor_name = doctor_name;
        this.doctor_phone_number = doctor_phone_number;
        this.doctor_email = doctor_email;
        this.category_id = category_id;
        this.clinic_id = clinic_id;
    }

    public DoctorModel() {
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_phone_number() {
        return doctor_phone_number;
    }

    public void setDoctor_phone_number(String doctor_phone_number) {
        this.doctor_phone_number = doctor_phone_number;
    }

    public String getDoctor_email() {
        return doctor_email;
    }

    public void setDoctor_email(String doctor_email) {
        this.doctor_email = doctor_email;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getClinic_id() {
        return clinic_id;
    }

    public void setClinic_id(String clinic_id) {
        this.clinic_id = clinic_id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setDistance(double dist) {
        this.dist = dist;
    }

    public String getId() {
        return id;
    }
}
