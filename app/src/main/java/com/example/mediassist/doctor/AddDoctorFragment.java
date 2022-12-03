package com.example.mediassist.doctor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mediassist.R;
import com.example.mediassist.category.models.CategoryModel;
import com.example.mediassist.clinic.models.ClinicModel;

import com.example.mediassist.databinding.AddDoctorBinding;
import com.example.mediassist.doctor.models.DoctorModel;
import com.example.mediassist.signup.RegisterUserModel;
import com.example.mediassist.util.CheckForEmptyCallBack;
import com.example.mediassist.util.CustomTextWatcher;
import com.example.mediassist.util.CustomToast;
import com.example.mediassist.util.ToastStatus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AddDoctorFragment extends Fragment implements CheckForEmptyCallBack {

    private AddDoctorBinding binding;
    private FirebaseFirestore db;
    private TextView doctor_name_error_text;
    private TextView doctor_phone_number_error_text;
    private TextView doctor_email_error_text;
    private EditText doctorName;
    private EditText doctorPhoneNumber;
    private EditText doctorEmail;
    private String category_id;
    private String clinic_id;
    private Button saveButton;
    private Button editButton;
    private Button deleteButton;
    private String clinic_name;
    private String category_name;
    private String name;
    private String phone_number;
    private String email;
    private Bundle bundle;
    private Spinner clinicSpinner;
    private Spinner categorySpinner;
    private ArrayList<ClinicModel> clinicsList;
    private ArrayList<CategoryModel> categoryList;
    private ClinicModel clinic;
    private CategoryModel category;
    private ArrayAdapter<ClinicModel> clinicSpinnerAdapter;
    private ArrayAdapter<CategoryModel> categorySpinnerAdapter;
    private DoctorModel doctor;
    private String id;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        binding = AddDoctorBinding.inflate(inflater, container, false);

        bundle = getArguments();
        doctor = (DoctorModel) (bundle != null ? bundle.getSerializable("doctor") : null);

        clinicSpinner = (Spinner) binding.ClinicSpinner;
        categorySpinner = (Spinner) binding.SpecializationSpinner;


        doctorName = binding.doctorNameText;
        doctorPhoneNumber = binding.doctorPhoneNumberText;
        doctorEmail = binding.doctorEmailText;
        saveButton = binding.doctorSaveButton;
        editButton = binding.doctorEditButton;
        deleteButton = binding.doctorDeleteButton;
        doctor_name_error_text = binding.doctorNameErrorText;
        doctor_phone_number_error_text = binding.doctorPhoneNumberErrorText;
        doctor_email_error_text = binding.doctorEmailErrorText;

        mAuth = FirebaseAuth.getInstance();

        ((DoctorActivity) getActivity()).btnAddDoctor.setVisibility(View.GONE);

        if (doctor != null) {
            id = doctor.getId();
            doctorName.setText(doctor.getDoctor_name());
            doctorPhoneNumber.setText(doctor.getDoctor_phone_number());
            doctorEmail.setText(doctor.getDoctor_email());
            clinic_id = doctor.getClinic_id();
            clinic_name = doctor.getClinic_name();
            category_name= doctor.getCategory_name();
            category_id = doctor.getCategory_id();
            saveButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);

        }

        clinicsList = new ArrayList<ClinicModel>();
        db.collection("clinics").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for (QueryDocumentSnapshot snapshot : value) {
                    String details = "";
                    String name = snapshot.getString("name");
                    String phoneNumber = snapshot.getString("phone_number");
                    if (snapshot.getString("description") != null) {
                        details = snapshot.getString("description");
                    }
                    String street = snapshot.getString("street");
                    String city = snapshot.getString("city");
                    String county = snapshot.getString("county");
                    String country = snapshot.getString("country");
                    int zipcode = snapshot.getLong("zipcode").intValue();

                    clinic = new ClinicModel(name, details, phoneNumber, street, city, county, country, zipcode);
                    clinic.setId(snapshot.getId());
                    clinicsList.add(clinic);

                }
                clinicSpinnerAdapter = new ArrayAdapter<ClinicModel>(getContext(), R.layout.spinner_items, clinicsList);
                clinicSpinnerAdapter.setDropDownViewResource(R.layout.spinner_items);
                clinicSpinner.setAdapter(clinicSpinnerAdapter);
                getDoctorClinicForEdit(clinicSpinnerAdapter);


            }
        });

        categoryList = new ArrayList<CategoryModel>();
        db.collection("categories").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
if(value!=null) {
    for (QueryDocumentSnapshot snapshot : value) {
        String details = "";
        String name = snapshot.getString("name");
        if (snapshot.getString("description") != null) {
            details = snapshot.getString("description");
        }
        String clinic_id = snapshot.getString("clinic_id");
        String icon_id = snapshot.getString("icon_id");
        category = new CategoryModel(name, details, clinic_id, icon_id);
        category.setId(snapshot.getId());
        categoryList.add(category);

    }
}
                categorySpinnerAdapter = new ArrayAdapter<CategoryModel>(getContext(), R.layout.spinner_items, categoryList);
                categorySpinnerAdapter.setDropDownViewResource(R.layout.spinner_items);
                categorySpinner.setAdapter(categorySpinnerAdapter);
                getDoctorCategoryForEdit(categorySpinnerAdapter);

            }
        });
        doctorName.addTextChangedListener(new CustomTextWatcher(doctor_name_error_text, AddDoctorFragment.this));
        doctorPhoneNumber.addTextChangedListener(new CustomTextWatcher(doctor_phone_number_error_text, AddDoctorFragment.this));
        doctorEmail.addTextChangedListener(new CustomTextWatcher(doctor_email_error_text, AddDoctorFragment.this));
        checkDoctorData();

        clinicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                clinic_id = clinicsList.get(i).getId();
                clinic_name = clinicsList.get(i).getName();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                category_id = categoryList.get(i).getId();
                category_name=categoryList.get(i).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDoctorData();
                DoctorModel doctor = new DoctorModel(name, phone_number, email, category_id, clinic_id, clinic_name,category_name);
                uploadDoctor(doctor);

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDoctorData();
                doctor = new DoctorModel(name, phone_number, email, category_id, clinic_id,clinic_name,category_name);
                updateDoctor(id, doctor);
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //phoneNumberEditText.setText("");

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


    private void checkDoctorData() {
        name = doctorName.getText().toString();
        phone_number = doctorPhoneNumber.getText().toString();
        email = doctorEmail.getText().toString();

        if (!(name.isEmpty()) && !(phone_number.isEmpty()) && !(email.isEmpty())) {
            saveButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary_color)));
            saveButton.setEnabled(true);
        }
    }

    public void uploadDoctor(DoctorModel doctor) {
        String password = doctor.getDoctor_name().substring(0, 4) + doctor.getDoctor_phone_number().substring(doctor.getDoctor_phone_number().length() - 4);
        mAuth.createUserWithEmailAndPassword(doctor.getDoctor_email(), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    RegisterUserModel registerUserModel = new RegisterUserModel(doctor.getDoctor_name(), doctor.getDoctor_email(), doctor.getDoctor_phone_number(), password, "3");
                    DocumentReference documentReference = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    doctor.setId(documentReference.getId());
                    documentReference.set(registerUserModel).addOnSuccessListener(new OnSuccessListener<Void>() {

                        @Override
                        public void onSuccess(Void unused) {
                            db.collection("doctors").add(doctor).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AddDoctorFragment_to_DoctorActivity);
                                    new CustomToast(getContext(), getActivity(), name + " Stored Successfully !", ToastStatus.SUCCESS).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    new CustomToast(getContext(), getActivity(), "Error - ", ToastStatus.FAILURE).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                } else {

                }

            }
        });


    }

    public void updateDoctor(String doctorId, DoctorModel doctor) {

        db.collection(("doctors")).document(doctorId).set(doctor).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AddDoctorFragment_to_DoctorActivity);
                new CustomToast(getContext(), getActivity(), name + " Updated Successfully !", ToastStatus.SUCCESS).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new CustomToast(getContext(), getActivity(), "Failed to edit", ToastStatus.FAILURE).show();
            }
        });


    }

    private void deleteData(String doctorId) {
        db.collection(("doctors")).document(doctorId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AddDoctorFragment_to_DoctorActivity);
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

        if (doctor == null) {
            ((DoctorActivity) getActivity()).setActionBarTitle("Add Doctor");
        } else {
            ((DoctorActivity) getActivity()).setActionBarTitle("Edit Doctor");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        ((DoctorActivity) getActivity()).setActionBarTitle("Add Doctor");
        ((DoctorActivity) getActivity()).btnAddDoctor.setVisibility(View.VISIBLE);
    }

    private void getDoctorClinicForEdit(ArrayAdapter<ClinicModel> clinicSpinnerAdapter) {

        if (doctor != null) {
            for (int position = 0; position < clinicSpinnerAdapter.getCount(); position++) {
                if (((ClinicModel) clinicSpinner.getItemAtPosition(position)).getId().equals(doctor.getClinic_id())) {
                    clinicSpinner.setSelection(position);
                }
            }
        }


    }

    private void getDoctorCategoryForEdit(ArrayAdapter<CategoryModel> categorySpinnerAdapter) {

        if (doctor != null) {
            for (int position = 0; position < categorySpinnerAdapter.getCount(); position++) {
                if (((CategoryModel) categorySpinner.getItemAtPosition(position)).getId().equals(doctor.getCategory_id())) {
                    categorySpinner.setSelection(position);
                }
            }
        }


    }

    @Override
    public void checkForEmpty() {
        checkDoctorData();
    }
}