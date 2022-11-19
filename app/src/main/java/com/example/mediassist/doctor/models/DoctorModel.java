package com.example.mediassist.doctor.models;

import java.io.Serializable;

public class DoctorModel implements Serializable {
    private String name;
    private String phonenumber;
    private String email;
    private String assignspecialization;
    private String assignclinic;
    private String id;


    public DoctorModel(String name, String phonenumber, String email, String assignspecialization, String assignclinic, String id) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
        this.assignspecialization = assignspecialization;
        this.assignclinic = assignclinic;
        this.id = id;
    }

    public DoctorModel() {
    }

    public String getDoctorname() {
        return name;
    }

    public String getDoctorphonenumber() {
        return phonenumber;
    }

    public String getDoctoremail() {
        return email;
    }

    public String getAssignspecialization() {
        return assignspecialization;
    }

    public String getAssignclinic() {
        return assignclinic;
    }

    public void setDoctorname(String name) {
        this.name = name;
    }

    public void setDoctorphonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setDoctoremail(String email) {
        this.email = email;
    }

    public void setAssignspecialization(String assignspecialization) {
        this.assignspecialization = assignspecialization;
    }

    public void setAssignclinic(String assignclinic) {
        this.assignclinic = assignclinic;
    }

    public String setId() {
        return id;
    }

    public String getId() {
        return id;
    }
}
