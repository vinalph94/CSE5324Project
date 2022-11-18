package com.example.mediassist.clinicadmin;

import com.example.mediassist.clinic.models.ClinicModel;
import com.example.mediassist.clinicadmin.models.ClinicAdminModel;
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
import com.example.mediassist.databinding.AddClinicAdminBinding;

public class AddClinicAdminFragment extends Fragment{

    private AddClinicAdminBinding binding;
    private FirebaseFirestore db;
    private EditText clinicAdminName;
    private EditText clinicAdminPhoneNumber;
    private EditText clinicAdminEmail;
    private TextView  clinic_admin_name_error;
    private TextView clinic_admin_phone_number_error;
    private TextView clinic_admin_email_error;
    //private String clinicAdminAssignClinic;
    private Button save;
    private Button edit;
    private Button delete;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        db = FirebaseFirestore.getInstance();
        binding = AddClinicAdminBinding.inflate(inflater, container, false);

        Spinner spinner = (Spinner) binding.spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.programming_languages, R.layout.spinner_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        clinicAdminName = binding.clinicAdminNameText;
        clinicAdminPhoneNumber = binding.clinicAdminPhoneNumberText;
        clinicAdminEmail = binding.clinicAdminEmailText;
        clinic_admin_name_error = binding.clinicAdminNameErrorText;
        clinic_admin_phone_number_error = binding.clinicAdminPhoneNumberErrorText;
        clinic_admin_email_error = binding.clinicAdminEmailErrorText;
        save = binding.clinicAdminSaveButton;
        edit = binding.clinicAdminEditButton;
        delete = binding.clinicAdminDeleteButton;


        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clinic_admin_name_error.setVisibility(View.GONE);
                clinic_admin_phone_number_error.setVisibility(View.GONE);
                clinic_admin_email_error.setVisibility(View.GONE);

                String name = clinicAdminName.getText().toString();
                String phone_number = clinicAdminPhoneNumber.getText().toString();
                String email = clinicAdminEmail.getText().toString();
                String assign_clinic = spinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(name)) {
                    clinic_admin_name_error.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(phone_number)) {
                    clinic_admin_phone_number_error.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(email)) {
                    clinic_admin_email_error.setVisibility(View.VISIBLE);
                } else {
                    ClinicAdminModel clinicadmin = new ClinicAdminModel(name, phone_number, email, assign_clinic);
                    uploadClinicAdmin(clinicadmin);
                }
            }
        });


        return binding.getRoot();


    }
    public void uploadClinicAdmin(ClinicAdminModel clinicadmin) {
        db.collection("clinicAdmins")
                .add(clinicadmin)
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
        ((ClinicAdminActivity) getActivity()).setActionBarTitle("Add Clinic Admin");


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}