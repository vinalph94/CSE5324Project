package com.example.mediassist.clinic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mediassist.R;
import com.example.mediassist.clinic.models.ClinicModel;
import com.example.mediassist.dashboard.DashboardActivity;
import com.example.mediassist.databinding.AddClinicBinding;
import com.example.mediassist.util.CheckForEmptyCallBack;
import com.example.mediassist.util.CustomTextWatcher;
import com.example.mediassist.util.CustomToast;
import com.example.mediassist.util.ToastStatus;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class AddClinicFragment extends Fragment implements CheckForEmptyCallBack {

    private AddClinicBinding binding;
    private FirebaseFirestore db;
    private EditText nameEditText;
    private EditText detailsEditText;
    private EditText phoneNumberEditText;
    private EditText streetEditText;
    private TextView cityEditText;
    private TextView countyEditText;
    private EditText zipcodeEditText;
    private TextView clinicNameError;
    private TextView phoneNumberEditTextError;
    private TextView streetEditTextError;
    private TextView cityEditTextError;
    private TextView countyEditTextError;
    private TextView zipcodeEditTextError;
    private Button saveButton;
    private Button editButton;
    private Button deleteButton;
    private String name;
    private String details;
    private String phoneNumber;
    private String street;
    private int zipcode;
    private Bundle bundle;
    private ClinicModel clinic;
    private String id;
    private Locale locale;
    private Spinner countrySpinner;
    private ArrayAdapter<String> countrySpinnerAdapter;
    private ArrayList<String> countriesNamesList;
    private String country;
    private String city;
    private String county;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();

        binding = AddClinicBinding.inflate(inflater, container, false);

        bundle = getArguments();
        clinic = (ClinicModel) (bundle != null ? bundle.getSerializable("clinic") : null);


        nameEditText = binding.clinicNameText;
        detailsEditText = binding.clinicDetailsText;
        phoneNumberEditText = binding.clinicPhoneNumberText;
        streetEditText = binding.clinicStreetText;
        cityEditText = binding.clinicCityText;
        countyEditText = binding.clinicCountyText;
        zipcodeEditText = binding.clinicZipcodeText;
        clinicNameError = binding.clinicNameErrorText;
        phoneNumberEditTextError = binding.clinicPhoneNumberErrorText;
        streetEditTextError = binding.clinicStreetErrorText;
        cityEditTextError = binding.clinicCityErrorText;
        countyEditTextError = binding.clinicCountyErrorText;
        zipcodeEditTextError = binding.clinicZipcodeErrorText;
        saveButton = binding.clinicSaveButton;
        editButton = binding.clinicEditButton;
        deleteButton = binding.clinicDeleteButton;
        countrySpinner = (Spinner) binding.spinnerCountry;


        countriesNamesList = new ArrayList<String>();
        String[] isoCountryCodes = Locale.getISOCountries();
        for (String countryCode : isoCountryCodes) {

            locale = new Locale("", countryCode);
            countriesNamesList.add(locale.getDisplayCountry());
        }
        Collections.sort(countriesNamesList);
        countrySpinnerAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_items, countriesNamesList);
        countrySpinnerAdapter.setDropDownViewResource(R.layout.spinner_items);
        countrySpinner.setAdapter(countrySpinnerAdapter);


        ((ClinicActivity) getActivity()).btnAdd.setVisibility(View.GONE);
        if (clinic != null) {
            id = clinic.getId();
            nameEditText.setText(clinic.getName());
            phoneNumberEditText.setText(clinic.getPhone_number());
            streetEditText.setText(clinic.getStreet());
            cityEditText.setText(clinic.getCity());
            countyEditText.setText(clinic.getCounty());
            zipcodeEditText.setText(String.valueOf(clinic.getZipcode()));

            for (int position = 0; position < countrySpinner.getCount(); position++) {
                if (countrySpinner.getItemAtPosition(position).equals(clinic.getCountry())) {
                    countrySpinner.setSelection(position);
                }
            }
            if (clinic.getDescription() != null) {
                detailsEditText.setText(clinic.getDescription());
            }
            saveButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);


        }

        nameEditText.addTextChangedListener(new CustomTextWatcher(clinicNameError, AddClinicFragment.this));
        phoneNumberEditText.addTextChangedListener(new CustomTextWatcher(phoneNumberEditTextError, AddClinicFragment.this));
        streetEditText.addTextChangedListener(new CustomTextWatcher(streetEditTextError, AddClinicFragment.this));
        cityEditText.addTextChangedListener(new CustomTextWatcher(streetEditTextError, AddClinicFragment.this));
        countyEditTextError.addTextChangedListener(new CustomTextWatcher(streetEditTextError, AddClinicFragment.this));
        zipcodeEditText.addTextChangedListener(new CustomTextWatcher(zipcodeEditTextError, AddClinicFragment.this));


        checkClinicData();


        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                country = countriesNamesList.get(i);
                checkClinicData();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkClinicData();

                clinic = new ClinicModel(name, details, phoneNumber, street, city, county, country, zipcode);
                uploadClinic(clinic);


            }
        });


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //phoneNumberEditText.setText("");
                checkClinicData();

                clinic = new ClinicModel(name, details, phoneNumber, street, city, county, country, zipcode);
                updateClinic(id, clinic);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //phoneNumberEditText.setText("");
                checkClinicData();
                clinic = new ClinicModel(name, details, phoneNumber, street, city, county, country, zipcode);

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteData(id);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //Do your No progress
                                break;
                        }
                    }
                };
                AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
                ab.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });


        return binding.getRoot();

    }


    private void checkClinicData() {
        name = nameEditText.getText().toString();
        details = detailsEditText.getText().toString();
        phoneNumber = phoneNumberEditText.getText().toString();
        street = streetEditText.getText().toString();
        city = cityEditText.getText().toString();
        county = countyEditText.getText().toString();

        if (!(TextUtils.isEmpty(zipcodeEditText.getText().toString()))) {
            zipcode = Integer.parseInt(zipcodeEditText.getText().toString());
        }
        if (!(name.isEmpty()) && !(phoneNumber.isEmpty()) && !(street.isEmpty()) &&
                !(city.isEmpty()) && !(county.isEmpty()) &&
                !(TextUtils.isEmpty(zipcodeEditText.getText().toString()))) {
            saveButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary_color)));
            saveButton.setEnabled(true);
        }
    }

    public void uploadClinic(ClinicModel clinic) {
        db.collection("clinics").add(clinic).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AddClinicFragment_to_ClinicListFragment);
                new CustomToast(getContext(), getActivity(), name + " Saved Successfully", ToastStatus.SUCCESS).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new CustomToast(getContext(), getActivity(), "Error - " + e, ToastStatus.FAILURE).show();
            }
        });


    }


    public void updateClinic(String clinicId, ClinicModel clinic) {

        db.collection(("clinics")).document(clinicId).set(clinic).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AddClinicFragment_to_ClinicListFragment);
                new CustomToast(getContext(), getActivity(), name + " Updated Successfully", ToastStatus.SUCCESS).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new CustomToast(getContext(), getActivity(), " Failed to edit " + name, ToastStatus.FAILURE).show();
            }
        });
    }

    private void deleteData(String clinicId) {

        db.collection(("clinics")).document(clinicId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AddClinicFragment_to_ClinicListFragment);
                new CustomToast(getContext(), getActivity(), name + " Deleted Successfully", ToastStatus.DELETE).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new CustomToast(getContext(), getActivity(), " Failed to delete " + name, ToastStatus.FAILURE).show();
            }
        });


    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (clinic == null) {
            ((ClinicActivity) getActivity()).setActionBarTitle("Add Clinic");
        } else {
            ((ClinicActivity) getActivity()).setActionBarTitle("Edit Clinic");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        ((ClinicActivity) getActivity()).setActionBarTitle("Add Clinic");
        ((ClinicActivity) getActivity()).btnAdd.setVisibility(View.VISIBLE);

    }


    @Override
    public void checkForEmpty() {
        checkClinicData();
    }
}