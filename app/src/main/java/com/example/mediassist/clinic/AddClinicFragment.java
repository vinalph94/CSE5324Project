package com.example.mediassist.clinic;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mediassist.R;
import com.example.mediassist.clinic.models.ClinicModel;
import com.example.mediassist.databinding.AddClinicBinding;
import com.example.mediassist.util.CheckForEmptyCallBack;
import com.example.mediassist.util.CustomTextWatcher;
import com.example.mediassist.util.CustomToast;
import com.example.mediassist.util.ToastStatus;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddClinicFragment extends Fragment implements CheckForEmptyCallBack {

    private AddClinicBinding binding;
    private FirebaseFirestore db;
    private EditText nameEditText;
    private EditText detailsEditText;
    private EditText phoneNumberEditText;
    private EditText addressEditText;
    private EditText zipcodeEditText;
    private TextView clinicNameError;
    private TextView phoneNumberEditTextError;
    private TextView addressEditTextError;
    private TextView zipcodeEditTextError;
    private Button saveButton;
    private Button editButton;
    private Button deleteButton;
    private String name;
    private String details;
    private String phoneNumber;
    private String address;
    private int zipcode;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();

        binding = AddClinicBinding.inflate(inflater, container, false);

        bundle = getArguments();
        ClinicModel clinic = (ClinicModel) (bundle != null ? bundle.getSerializable("clinic") : null);


        nameEditText = binding.clinicNameText;
        detailsEditText = binding.clinicDetailsText;
        phoneNumberEditText = binding.clinicPhoneNumberText;
        addressEditText = binding.clinicAddressText;
        zipcodeEditText = binding.clinicZipcodeText;
        clinicNameError = binding.clinicNameErrorText;
        phoneNumberEditTextError = binding.clinicPhoneNumberErrorText;
        addressEditTextError = binding.clinicAddressErrorText;
        zipcodeEditTextError = binding.clinicZipcodeErrorText;
        saveButton = binding.clinicSaveButton;
        editButton = binding.clinicEditButton;
        deleteButton = binding.clinicDeleteButton;

        if (clinic != null) {
            nameEditText.setText(clinic.getName());
            phoneNumberEditText.setText(clinic.getPhone_number());
            addressEditText.setText(clinic.getAddress());
            zipcodeEditText.setText(String.valueOf(clinic.getZipcode()));
            if (clinic.getDescription() != null) {
                detailsEditText.setText(clinic.getDescription());
            }
            saveButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);

        }

        nameEditText.addTextChangedListener(new CustomTextWatcher(clinicNameError, AddClinicFragment.this));
        phoneNumberEditText.addTextChangedListener(new CustomTextWatcher(phoneNumberEditTextError, AddClinicFragment.this));
        addressEditText.addTextChangedListener(new CustomTextWatcher(addressEditTextError, AddClinicFragment.this));
        zipcodeEditText.addTextChangedListener(new CustomTextWatcher(zipcodeEditTextError, AddClinicFragment.this));


        checkClinicData();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ClinicModel clinic = new ClinicModel(name, phoneNumber, address, details, zipcode, "");
                uploadClinic(clinic);


            }
        });


        return binding.getRoot();

    }

    private void checkClinicData() {
        name = nameEditText.getText().toString();
        details = detailsEditText.getText().toString();
        phoneNumber = phoneNumberEditText.getText().toString();
        address = addressEditText.getText().toString();
        if (!(TextUtils.isEmpty(zipcodeEditText.getText().toString()))) {
            zipcode = Integer.parseInt(zipcodeEditText.getText().toString());
        }
        if (!(name.isEmpty()) && !(phoneNumber.isEmpty()) && !(address.isEmpty()) && !(TextUtils.isEmpty(zipcodeEditText.getText().toString()))) {
            saveButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary_color)));
            saveButton.setEnabled(true);
        }
    }

    public void uploadClinic(ClinicModel clinic) {
        db.collection("clinics").add(clinic).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AddClinicFragment_to_ClinicListFragment);
                new CustomToast(getContext(), getActivity(),
                        name + " Stored Successfully !", ToastStatus.SUCCESS).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new CustomToast(getContext(), getActivity(),
                        "Error - ", ToastStatus.FAILURE).show();
            }
        });


    }


    public void updateDocument(ClinicModel clinic) {
//        Toast.makeText(this, "updateDocument", Toast.LENGTH_SHORT).show();

        final DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection("clinics")
                .document(clinic.getId());


//        docRef.update(clinic)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
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


    @Override
    public void checkForEmpty() {
        checkClinicData();
    }
}