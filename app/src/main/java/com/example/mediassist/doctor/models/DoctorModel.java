package com.example.mediassist.doctor.models;

public class DoctorModel {
    private String name;
    private String phonenumber;
    private String email;
    private String assignspecialization;
    private String assignclinic;


    public DoctorModel(String name, String phonenumber, String email, String assignspecialization, String assignclinic) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
        this.assignspecialization = assignspecialization;
        this.assignclinic = assignclinic;

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
}
