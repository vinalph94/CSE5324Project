package com.example.mediassist.clinic;
import com.example.mediassist.clinic.models.ClinicModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mediassist.R;
import com.example.mediassist.dashboard.DashboardActivity;
import com.example.mediassist.databinding.AddClinicBinding;
import com.example.mediassist.login.LoginActivity;

public class AddClinicFragment extends Fragment {

    private AddClinicBinding binding;
    private FirebaseFirestore db;
    private EditText clinicName;
    private EditText clinicDetails;
    private EditText clinicPhoneNumber;
    private EditText clinicAddress;
    private EditText clinicZipcode;
    private Button save;
    private Button edit;
    private Button delete;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        db = FirebaseFirestore.getInstance();

        binding = AddClinicBinding.inflate(inflater, container, false);
        clinicName = binding.clinicNameText;
        clinicDetails = binding.clinicDetailsText;
        clinicPhoneNumber = binding.clinicPhoneNumberText;
        clinicAddress = binding.clinicAddressText;
        clinicZipcode = binding.clinicZipcodeText;
        save = binding.clinicSaveButton;
        edit = binding.clinicEditButton;
        delete = binding.clinicDeleteButton;

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = clinicName.getText().toString();
                String details = clinicDetails.getText().toString();
                String phone_number = clinicPhoneNumber.getText().toString();
                String address = clinicAddress.getText().toString();
//                int zipcode = Integer.parseInt(clinicZipcode.getText().toString());

                //Validate here. Check if anything is empty and any other kind of sanitation.

                ClinicModel clinic = new ClinicModel(name,phone_number,address,details,78);
                uploadClinic(clinic);
            }
        });



        return binding.getRoot();

    }
    public void uploadClinic(ClinicModel clinic) {
        db.collection("clinics")
                .add(clinic)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), "Data Stored Successfully !", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ((ClinicActivity) getActivity()).setActionBarTitle("Add Clinic");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}