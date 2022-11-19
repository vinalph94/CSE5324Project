package com.example.mediassist.doctor;

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
import com.example.mediassist.databinding.AddDoctorBinding;
import com.example.mediassist.doctor.models.DoctorModel;
import com.example.mediassist.util.CheckForEmptyCallBack;
import com.example.mediassist.util.CustomTextWatcher;
import com.example.mediassist.util.CustomToast;
import com.example.mediassist.util.ToastStatus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class AddDoctorFragment extends Fragment implements CheckForEmptyCallBack {

    private AddDoctorBinding binding;
    private FirebaseFirestore db;
    private TextView doctor_name_error_text;
    private TextView doctor_phone_number_error_text;
    private TextView doctor_email_error_text;
    private EditText doctorName;
    private EditText doctorPhoneNumber;
    private EditText doctorEmail;
    private String doctorAssignSpecialization;
    private String doctorAssignClinic;
    private Button saveButton;
    private Button editButton;
    private Button deleteButton;

    private String name;
    private String phone_number;
    private String email;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        binding = AddDoctorBinding.inflate(inflater, container, false);

        bundle = getArguments();
        DoctorModel doctor = (DoctorModel) (bundle != null ? bundle.getSerializable("doctor") : null);

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
        doctorEmail = binding.doctorEmailText;
        saveButton = binding.doctorSaveButton;
        editButton = binding.doctorEditButton;
        deleteButton = binding.doctorDeleteButton;
        doctor_name_error_text = binding.doctorNameErrorText;
        doctor_phone_number_error_text = binding.doctorPhoneNumberErrorText;
        doctor_email_error_text = binding.doctorEmailErrorText;

        if (doctor != null) {
            doctorName.setText(doctor.getDoctorname());
            doctorPhoneNumber.setText(doctor.getDoctorphonenumber());
            doctorEmail.setText(doctor.getDoctoremail());
            doctorAssignClinic = doctor.getAssignclinic();
            doctorAssignSpecialization = doctor.getAssignspecialization();
            saveButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);

        }

        doctorName.addTextChangedListener(new CustomTextWatcher(doctor_name_error_text, AddDoctorFragment.this));
        doctorPhoneNumber.addTextChangedListener(new CustomTextWatcher(doctor_phone_number_error_text, AddDoctorFragment.this));
        doctorEmail.addTextChangedListener(new CustomTextWatcher(doctor_email_error_text, AddDoctorFragment.this));
        checkDoctorData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorAssignClinic = spinner.getSelectedItem().toString();
                doctorAssignSpecialization = spinnerSpecialist.getSelectedItem().toString();

                DoctorModel doctor = new DoctorModel(name, phone_number, email, doctorAssignSpecialization, doctorAssignClinic, "");
                uploadDoctor(doctor);

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //phoneNumberEditText.setText("");
                deleteData(phone_number);
            }
        });
        return binding.getRoot();

    }

    private void deleteData(String phoneNumber) {
        db.collection("doctors").whereEqualTo("doctorphonenumber", phoneNumber).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String docId = documentSnapshot.getId();
                    db.collection(("doctors")).document(docId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            new CustomToast(getContext(), getActivity(), name + " Deleted Successfully !", ToastStatus.SUCCESS).show();
                            // navigate to add clinic screen
                            doctorName.setText("");
                            doctorPhoneNumber.setText("");
                            doctorEmail.setText("");
                            saveButton.setVisibility(View.VISIBLE);
                            editButton.setVisibility(View.GONE);
                            deleteButton.setVisibility(View.GONE);
                            doctor_name_error_text.setVisibility(View.GONE);
                            doctor_phone_number_error_text.setVisibility(View.GONE);
                            doctor_email_error_text.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            new CustomToast(getContext(), getActivity(), name + " Failed to delete !", ToastStatus.SUCCESS).show();
                        }
                    });
                }
            }
        });
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
        db.collection("doctors").add(doctor).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AddDoctorFragment_to_DoctorListFragment);
                new CustomToast(getContext(), getActivity(), name + " Stored Successfully !", ToastStatus.SUCCESS).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
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
        checkDoctorData();
    }
}