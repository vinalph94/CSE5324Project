package com.example.mediassist.doctor.models;

import java.io.Serializable;

public class DoctorModel implements Serializable {
    private String name;
    private String phonenumber;
    private String email;
    private String assignspecialization;
    private String clinic_id;
    private String id;


    public DoctorModel(String name, String phonenumber, String email, String assignspecialization, String clinic_id, String id) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
        this.assignspecialization = assignspecialization;
        this.clinic_id = clinic_id;
        this.id = id;
    }

    public DoctorModel() {
    }

    public String getDoctorname() {
        return name;
    }

    public void setDoctorname(String name) {
        this.name = name;
    }

    public String getDoctorphonenumber() {
        return phonenumber;
    }

    public void setDoctorphonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getDoctoremail() {
        return email;
    }

    public void setDoctoremail(String email) {
        this.email = email;
    }

    public String getAssignspecialization() {
        return assignspecialization;
    }

    public void setAssignspecialization(String assignspecialization) {
        this.assignspecialization = assignspecialization;
    }

    public String getclinicId() {
        return clinic_id;
    }

    public void setclinicId(String clinic_id) {
        this.clinic_id = clinic_id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }
}
