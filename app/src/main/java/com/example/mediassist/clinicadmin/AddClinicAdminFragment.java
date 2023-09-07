package com.example.mediassist.clinicadmin;

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
import com.example.mediassist.clinic.ClinicActivity;
import com.example.mediassist.clinic.models.ClinicModel;
import com.example.mediassist.clinicadmin.models.ClinicAdminModel;
import com.example.mediassist.databinding.AddClinicAdminBinding;
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

public class AddClinicAdminFragment extends Fragment implements CheckForEmptyCallBack {

    Spinner spinner;
    private AddClinicAdminBinding binding;
    private FirebaseFirestore db;
    private EditText clinicAdminName;
    private EditText clinicAdminPhoneNumber;
    private EditText clinicAdminEmail;
    private TextView clinic_admin_name_error;
    private TextView clinic_admin_phone_number_error;
    private TextView clinic_admin_email_error;
    private DoctorModel doctor;
    private Button saveButton;
    private Button editButton;
    private Button deleteButton;
    private ArrayAdapter<ClinicModel> clinicSpinnerAdapter;
    private ArrayList<ClinicModel> clinicsList;
    private String name;
    private String phone_number;
    private String email;
    private String assign_clinic;
    private String user_id;
    private Bundle bundle;
    private ClinicAdminModel clinicadmin;
    private String id;
    private FirebaseAuth mAuth;
    private Spinner clinicSpinner;
    private ClinicModel clinic;
    private String clinic_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        binding = AddClinicAdminBinding.inflate(inflater, container, false);
        bundle = getArguments();
        clinicadmin = (ClinicAdminModel) (bundle != null ? bundle.getSerializable("clinicadmin") : null);


        clinicSpinner = (Spinner) binding.spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.programming_languages, R.layout.spinner_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        clinicSpinner.setAdapter(adapter);
        ((ClinicAdminActivity) getActivity()).addBtn.setVisibility(View.GONE);


        clinicAdminName = binding.clinicAdminNameText;
        clinicAdminPhoneNumber = binding.clinicAdminPhoneNumberText;
        clinicAdminEmail = binding.clinicAdminEmailText;

        clinic_admin_name_error = binding.clinicAdminNameErrorText;
        clinic_admin_phone_number_error = binding.clinicAdminPhoneNumberErrorText;
        clinic_admin_email_error = binding.clinicAdminEmailErrorText;
        saveButton = binding.clinicAdminSaveButton;
        editButton = binding.clinicAdminEditButton;
        deleteButton = binding.clinicAdminDeleteButton;

        if (clinicadmin != null) {
            id = clinicadmin.getId();
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

        clinicsList = new ArrayList<ClinicModel>();
        db.collection("clinics").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
if(value!=null) {
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
}
                clinicSpinnerAdapter = new ArrayAdapter<ClinicModel>(getActivity(), R.layout.spinner_items, clinicsList);
                clinicSpinnerAdapter.setDropDownViewResource(R.layout.spinner_items);
                clinicSpinner.setAdapter(clinicSpinnerAdapter);
                getDoctorClinicForEdit(clinicSpinnerAdapter);

            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clinicadmin = new ClinicAdminModel(name, phone_number, email, assign_clinic,clinic_name,user_id);
                uploadClinicAdmin(clinicadmin);

            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //phoneNumberEditText.setText("");
                checkClinicAdminData();
                clinicadmin = new ClinicAdminModel(name, phone_number, email, assign_clinic,clinic_name,user_id);
                updateClinicAdmin(id, clinicadmin);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkClinicAdminData();
                clinicadmin = new ClinicAdminModel(name, phone_number, email, assign_clinic,clinic_name,user_id);


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteData(id, clinicadmin);
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

        clinicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
if(clinicsList.size()!=0) {
    assign_clinic = clinicsList.get(i).getId();
    clinic_name = clinicsList.get(i).getName();
}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return binding.getRoot();


    }

    private void getDoctorClinicForEdit(ArrayAdapter<ClinicModel> clinicSpinnerAdapter) {

        if (clinicadmin != null) {
            for (int position = 0; position < clinicSpinnerAdapter.getCount(); position++) {
                if (((ClinicModel) clinicSpinner.getItemAtPosition(position)).getId().equals(clinicadmin.getAssign_clinic())) {
                    clinicSpinner.setSelection(position);
                }
            }
        }


    }


    private void deleteData(String clinicadminId, ClinicAdminModel clinicadmin) {
        db.collection(("clinicAdmins")).document(clinicadminId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AddClinicAdminFragment_to_ClinicAdminListFragment);
                new CustomToast(getContext(), getActivity(), name + " Deleted Successfully", ToastStatus.DELETE).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new CustomToast(getContext(), getActivity(), " Failed to delete " + name, ToastStatus.FAILURE).show();
            }
        });
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

        String password = clinicadmin.getName().substring(0, 4) + clinicadmin.getPhone_number().substring(clinicadmin.getPhone_number().length() - 4);

        mAuth.createUserWithEmailAndPassword(clinicadmin.getEmail(), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    RegisterUserModel registerUserModel = new RegisterUserModel(clinicadmin.getName(), clinicadmin.getEmail(), clinicadmin.getPhone_number(), password, "2");
                    DocumentReference documentReference = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    clinicadmin.setId(documentReference.getId());
                    documentReference.set(registerUserModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

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
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            new CustomToast(getContext(), getActivity(), "Error - ", ToastStatus.FAILURE).show();

                        }
                    });


                } else {
                    new CustomToast(getContext(), getActivity(), "Error - ", ToastStatus.FAILURE).show();
                }

            }
        });
    }

    public void updateClinicAdmin(String clinicadminId, ClinicAdminModel clinicadmin) {

        db.collection(("clinicAdmins")).document(clinicadminId).set(clinicadmin).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AddClinicAdminFragment_to_ClinicAdminListFragment);
                new CustomToast(getContext(), getActivity(), name + " Updated Successfully", ToastStatus.SUCCESS).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new CustomToast(getContext(), getActivity(), " Failed to edit " + name, ToastStatus.FAILURE).show();
            }
        });
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (clinicadmin == null) {
            ((ClinicAdminActivity) getActivity()).setActionBarTitle("Add Clinic Admin");
        } else {
            ((ClinicAdminActivity) getActivity()).setActionBarTitle("Edit Clinic Admin");
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        ((ClinicAdminActivity) getActivity()).setActionBarTitle("Add Clinic Admin");
        ((ClinicAdminActivity) getActivity()).addBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void checkForEmpty() {
        checkClinicAdminData();
    }
}