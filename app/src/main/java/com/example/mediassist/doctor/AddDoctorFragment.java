package com.example.mediassist.doctor;

import com.example.mediassist.clinic.models.ClinicModel;
import com.example.mediassist.doctor.models.DoctorModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mediassist.R;
import com.example.mediassist.clinic.ClinicActivity;
import com.example.mediassist.databinding.AddDoctorBinding;

public class AddDoctorFragment extends Fragment {

    private AddDoctorBinding binding;
    private FirebaseFirestore db;
    private TextView doctor_name_error_text;
    private TextView doctor_phone_number_error_text;
    private TextView doctor_address_error_text;
    private EditText doctorName;
    private EditText doctorPhoneNumber;
    private EditText doctorAddress;
    private String doctorAssignSpecialization;
    private String doctorAssignClinic;
    private Button save;
    private Button edit;
    private Button delete;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        db = FirebaseFirestore.getInstance();
        binding = AddDoctorBinding.inflate(inflater, container, false);
        Spinner spinner = (Spinner) binding.ClinicSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.programming_languages, R.layout.spinner_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinnerSpecialist = (Spinner) binding.SpecializationSpinner;
        ArrayAdapter<CharSequence> arrAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.doctors_specializarions, R.layout.spinner_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecialist.setAdapter(arrAdapter);

        doctorName = binding.doctorNameText;
        doctorPhoneNumber = binding.doctorPhoneNumberText;
        doctorAddress = binding.doctorAddressText;
        save = binding.doctorSaveButton;
        edit = binding.doctorEditButton;
        delete = binding.doctorDeleteButton;
        doctor_name_error_text = binding.doctorNameErrorText;
        doctor_phone_number_error_text = binding.doctorPhoneNumberErrorText;
        doctor_address_error_text = binding.doctorAddressErrorText;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doctor_name_error_text.setVisibility(View.GONE);
                doctor_phone_number_error_text.setVisibility(View.GONE);
                doctor_address_error_text.setVisibility(View.GONE);

                String name = doctorName.getText().toString();
                String phone_number = doctorPhoneNumber.getText().toString();
                String address = doctorAddress.getText().toString();
                doctorAssignClinic = spinner.getSelectedItem().toString();
                doctorAssignSpecialization = spinnerSpecialist.getSelectedItem().toString();
                if (TextUtils.isEmpty(name)) {
                    doctor_name_error_text.setVisibility(View.VISIBLE);
                }
                else if(TextUtils.isEmpty(phone_number)) {
                    doctor_phone_number_error_text.setVisibility(View.VISIBLE);
                }
               else if(TextUtils.isEmpty(address)){
                    doctor_address_error_text.setVisibility(View.VISIBLE);
                }
                else{
                    DoctorModel doctor = new DoctorModel(name, phone_number, address, doctorAssignSpecialization, doctorAssignClinic);
                    uploadDoctor(doctor);
                }
            }
        });


        return binding.getRoot();

    }
    public void uploadDoctor(DoctorModel doctor) {
        db.collection("doctors")
                .add(doctor)
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


        ((DoctorActivity) getActivity()).setActionBarTitle("Add Doctor");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}