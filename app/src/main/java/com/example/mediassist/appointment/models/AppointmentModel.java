package com.example.mediassist.appointment.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class AppointmentModel implements Serializable {
    private String patient_id;
    private String patient_name;
    private String doctor_id;
    private String doctor_name;
    private String clinic_id;
    private String category_id;
    private String slot_date;
    private String slot_time;
    private String status;
    @Exclude
    private String id;


    public AppointmentModel(String patient_id, String patient_name, String doctor_id, String doctor_name, String clinic_id, String category_id, String slot_date, String slot_time, String status) {
        this.patient_id = patient_id;
        this.patient_name = patient_name;
        this.doctor_id = doctor_id;
        this.doctor_name = doctor_name;
        this.clinic_id = clinic_id;
        this.category_id = category_id;
        this.slot_date = slot_date;
        this.slot_time = slot_time;
        this.status = status;

    }

    public AppointmentModel() {
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getClinic_id() {
        return clinic_id;
    }

    public void setClinic_id(String clinic_id) {
        this.clinic_id = clinic_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getSlot_date() {
        return slot_date;
    }

    public void setSlot_date(String slot_date) {
        this.slot_date = slot_date;
    }

    public String getSlot_time() {
        return slot_time;
    }

    public void setSlot_time(String slot_time) {
        this.slot_time = slot_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
