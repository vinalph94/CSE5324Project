package com.example.mediassist.clinicadmin;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mediassist.R;

import com.example.mediassist.clinic.models.ClinicModel;
import com.example.mediassist.clinicadmin.models.ClinicAdminModel;
import com.example.mediassist.databinding.AddClinicAdminBinding;
import com.example.mediassist.util.CheckForEmptyCallBack;
import com.example.mediassist.util.CustomTextWatcher;
import com.example.mediassist.util.CustomToast;
import com.example.mediassist.util.ToastStatus;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddClinicAdminFragment extends Fragment implements CheckForEmptyCallBack {

    private AddClinicAdminBinding binding;
    private FirebaseFirestore db;
    private EditText clinicAdminName;
    private EditText clinicAdminPhoneNumber;
    private EditText clinicAdminEmail;
    private TextView clinic_admin_name_error;
    private TextView clinic_admin_phone_number_error;
    private TextView clinic_admin_email_error;

    private Button saveButton;
    private Button editButton;
    private Button deleteButton;

    private String name;
    private String phone_number;
    private String email;
    private String assign_clinic;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        binding = AddClinicAdminBinding.inflate(inflater, container, false);
        bundle = getArguments();
        ClinicAdminModel clinicadmin= (ClinicAdminModel) (bundle != null ? bundle.getSerializable("clinicadmin") : null);


        Spinner spinner = (Spinner) binding.spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.programming_languages, R.layout.spinner_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        clinicAdminName = binding.clinicAdminNameText;
        clinicAdminPhoneNumber = binding.clinicAdminPhoneNumberText;
        clinicAdminEmail = binding.clinicAdminEmailText;
        clinic_admin_name_error = binding.clinicAdminNameErrorText;
         assign_clinic = binding.spinner.getSelectedItem().toString();
        clinic_admin_phone_number_error = binding.clinicAdminPhoneNumberErrorText;
        clinic_admin_email_error = binding.clinicAdminEmailErrorText;
        saveButton = binding.clinicAdminSaveButton;
        editButton = binding.clinicAdminEditButton;
        deleteButton = binding.clinicAdminDeleteButton;

        if (clinicadmin !=null){
            clinicAdminName.setText(clinicadmin.getName());
            clinicAdminPhoneNumber.setText(clinicadmin.getPhone_number());
            clinicAdminEmail.setText(clinicadmin.getEmail());
            assign_clinic = clinicadmin.getAssign_clinic();
            saveButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);

        }

        clinicAdminName.addTextChangedListener(new CustomTextWatcher(clinic_admin_name_error, AddClinicAdminFragment.this));
        clinicAdminPhoneNumber.addTextChangedListener(new CustomTextWatcher(clinic_admin_phone_number_error, AddClinicAdminFragment.this));
        clinicAdminEmail.addTextChangedListener(new CustomTextWatcher(clinic_admin_email_error, AddClinicAdminFragment.this));
        checkClinicAdminData();

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // assign_clinic = spinner.getSelectedItem().toString();

                ClinicAdminModel clinicadmin = new ClinicAdminModel(name, phone_number, email, assign_clinic,"");
                uploadClinicAdmin(clinicadmin);

            }
        });


        return binding.getRoot();


    }

    private void checkClinicAdminData() {
        name = clinicAdminName.getText().toString();
        phone_number = clinicAdminPhoneNumber.getText().toString();
        email = clinicAdminEmail.getText().toString();

         if (!(name.isEmpty()) && !(phone_number.isEmpty()) && !(email.isEmpty())) {
            saveButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary_color)));
            saveButton.setEnabled(true);
        }
    }

    public void uploadClinicAdmin(ClinicAdminModel clinicadmin) {
        db.collection("clinicAdmins").add(clinicadmin).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AddClinicAdminFragment_to_ClinicAdminListFragment);
                        new CustomToast(getContext(), getActivity(),
                                name + " Stored Successfully !", ToastStatus.SUCCESS).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        new CustomToast(getContext(), getActivity(), "Error - ", ToastStatus.FAILURE).show();
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

    @Override
    public void checkForEmpty() {
        checkClinicAdminData();
    }
}