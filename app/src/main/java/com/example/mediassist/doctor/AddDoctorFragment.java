package com.example.mediassist.doctor;

import android.content.res.ColorStateList;
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
import androidx.navigation.Navigation;

import com.example.mediassist.R;
import com.example.mediassist.clinicadmin.AddClinicAdminFragment;
import com.example.mediassist.databinding.AddDoctorBinding;
import com.example.mediassist.doctor.models.DoctorModel;
import com.example.mediassist.util.CheckForEmptyCallBack;
import com.example.mediassist.util.CustomTextWatcher;
import com.example.mediassist.util.CustomToast;
import com.example.mediassist.util.ToastStatus;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddDoctorFragment extends Fragment implements CheckForEmptyCallBack {

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
    private Button saveButton;
    private Button editButton;
    private Button deleteButton;

    private String name;
    private String phone_number;
    private String address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        binding = AddDoctorBinding.inflate(inflater, container, false);

        Spinner spinner = (Spinner) binding.ClinicSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.programming_languages, R.layout.spinner_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinnerSpecialist = (Spinner) binding.SpecializationSpinner;
        ArrayAdapter<CharSequence> arrAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.doctors_specializarions, R.layout.spinner_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecialist.setAdapter(arrAdapter);

        doctorName = binding.doctorNameText;
        doctorPhoneNumber = binding.doctorPhoneNumberText;
        doctorAddress = binding.doctorAddressText;
        saveButton = binding.doctorSaveButton;
        editButton = binding.doctorEditButton;
        deleteButton = binding.doctorDeleteButton;
        doctor_name_error_text = binding.doctorNameErrorText;
        doctor_phone_number_error_text = binding.doctorPhoneNumberErrorText;
        doctor_address_error_text = binding.doctorAddressErrorText;

        doctorName.addTextChangedListener(new CustomTextWatcher(doctor_name_error_text, AddDoctorFragment.this));
        doctorPhoneNumber.addTextChangedListener(new CustomTextWatcher(doctor_phone_number_error_text, AddDoctorFragment.this));
        doctorAddress.addTextChangedListener(new CustomTextWatcher(doctor_address_error_text, AddDoctorFragment.this));
        checkClinicData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                doctorAssignClinic = spinner.getSelectedItem().toString();
                doctorAssignSpecialization = spinnerSpecialist.getSelectedItem().toString();

                    DoctorModel doctor = new DoctorModel(name, phone_number, address, doctorAssignSpecialization, doctorAssignClinic);
                    uploadDoctor(doctor);

            }
        });


        return binding.getRoot();

    }
    private void checkClinicData() {
        name = doctorName.getText().toString();
        phone_number = doctorPhoneNumber.getText().toString();
        address = doctorAddress.getText().toString();

        if (!(name.isEmpty()) && !(phone_number.isEmpty()) && !(address.isEmpty())) {
            saveButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary_color)));
            saveButton.setEnabled(true);
        }
    }
    public void uploadDoctor(DoctorModel doctor) {
        db.collection("doctors")
                .add(doctor)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AddDoctorFragment_to_DoctorListFragment);
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


        ((DoctorActivity) getActivity()).setActionBarTitle("Add Doctor");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void checkForEmpty() {
        checkClinicData();
    }
}