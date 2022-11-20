package com.example.mediassist.appointment.models;

import com.example.mediassist.appointment.ConfirmAppointmentFragment;
import com.example.mediassist.login.LoginActivity;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class AppointmentModel implements Serializable {
    private String patientId;
    private String patientName;
    private String doctorId;
    private String doctorName;
    private String clinic;
    private String specialization;
    private String slotdate;
    private String slottime;
    @Exclude
      private String id;


    public AppointmentModel(String patientId, String patientName, String doctorId, String doctorName, String clinic, String specialization, String slotdate, String slottime) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.clinic = clinic;
        this.specialization = specialization;
        this.slotdate = slotdate;
        this.slottime = slottime;

    }

    public AppointmentModel() {
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getSlotdate() {
        return slotdate;
    }

    public void setSlotdate(String slotdate) {
        this.slotdate = slotdate;
    }

    public String getSlottime() {
        return slottime;
    }

    public void setSlottime(String slottime) {
        this.slottime = slottime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
