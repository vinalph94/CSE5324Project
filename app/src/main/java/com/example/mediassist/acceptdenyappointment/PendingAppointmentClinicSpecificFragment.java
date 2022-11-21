package com.example.mediassist.acceptdenyappointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.appointment.models.AppointmentModel;
import com.example.mediassist.appointmentstatus.PendingAppointmentAdapter;
import com.example.mediassist.databinding.PendingAppointmentClinicSpecificFragmentBinding;
import com.example.mediassist.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class PendingAppointmentClinicSpecificFragment extends Fragment {

    private PendingAppointmentClinicSpecificFragmentBinding binding;
    private FirebaseFirestore db;
    private ArrayList<AppointmentModel> courseArrayList = new ArrayList<AppointmentModel>();
    private String patient_id;
    private String patient_name;
    private String doctor_id;
    private String doctor_name;
    private String clinic_id;
    private String category_id;
    private String slot_date;
    private String slot_time;
    private String status;
    private AppointmentModel appointment;
    private PendingAppointmentAdapter courseAdapter;
    private Bundle bundle;
    private String clinicAdminClinic_id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();
        binding = PendingAppointmentClinicSpecificFragmentBinding.inflate(inflater, container, false);

        RecyclerView courseRV = binding.idRVCoursePendingAppointmentClinicSpecific;
        // Inflate the layout for this fragment

        /*db.collection("clinicAdmins").whereEqualTo("id", LoginActivity.patientUid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                System.out.println("value1 :"+LoginActivity.patientUid+" :-----"+task.isSuccessful());
                System.out.println("value1 :"+task.getResult().size());
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        System.out.println("snapshot value : "+snapshot);
                        System.out.println("snapshot value exists: "+snapshot.exists());
                        System.out.println("email : " + snapshot.getString("email"));
                        System.out.println("snapshot : " + snapshot.getString("assign_clinic"));
                        clinicAdminClinic_id = snapshot.getString("assign_clinic");
                        System.out.println("snapshot clinicAdminClinic_id : " + clinicAdminClinic_id);
                    }
                }
            }
        });*/

        db.collection("clinicAdmins").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (QueryDocumentSnapshot snapshot : value) {
                    System.out.println("snapshotid : "+snapshot.getString("id"));
                    System.out.println("LoginActivity.patientUid : "+LoginActivity.patientUid);
                    if (LoginActivity.patientUid.equals(snapshot.getString("id")) ){
                        clinicAdminClinic_id = snapshot.getString("assign_clinic");
                    }
                }
            }
        }
        );

        db.collection("appointments").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                System.out.println("clinicAdminClinic_id value :"+value+":---"+clinicAdminClinic_id+" :-----"+value.getDocuments());
                System.out.println("appointments value2 :"+value.size());
                String clinicId = "";
                String status = "";
                courseArrayList.clear();
                for (QueryDocumentSnapshot snapshot : value) {
                        clinicId = snapshot.get("clinic_id").toString();
                        status = snapshot.get("status").toString();
                        System.out.println("clinicId value2 :"+clinicId);
                        if (clinicId.equals(clinicAdminClinic_id) && status.equals("Pending")){
                            System.out.println("clinic exist");
                            patient_id = snapshot.getString("patient_id");
                            patient_name = snapshot.getString("patient_name");
                            doctor_id = snapshot.getString("doctor_id");
                            doctor_name = snapshot.getString("doctor_name");
                            clinic_id = snapshot.getString("clinic_id");
                            category_id = snapshot.getString("category_id");
                            slot_date = snapshot.getString("slot_date");
                            slot_time = snapshot.getString("slot_time");
                            status = snapshot.getString("status");
                            appointment = (new AppointmentModel(patient_id, patient_name, doctor_id, doctor_name, clinic_id, category_id, slot_date, slot_time, status));
                            appointment.setId(snapshot.getId());
                            courseArrayList.add(appointment);
                        } else {
                            System.out.println("Doesmt exist");
                        }



                }
                courseAdapter = new PendingAppointmentAdapter(getContext(), courseArrayList, new PendingAppointmentAdapter.PendingAppointmentItemListener() {
                    @Override
                    public void onAdapterItemClick(AppointmentModel appointment) {
                        navigateToAddFragment(appointment);

                    }

                });
                courseAdapter.notifyDataSetChanged();

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false);

                courseRV.setLayoutManager(linearLayoutManager);
                courseRV.setAdapter(courseAdapter);
            }
        });


        return binding.getRoot();
    }

/*
        db.collection("appointments").whereEqualTo("status", "Pending").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    System.out.println("clinicAdminClinic_id value :"+clinicAdminClinic_id+" :-----"+task.isSuccessful());
                    System.out.println("appointments value2 :"+task.getResult().size());
                    if(task.isSuccessful()) {
                        courseArrayList.clear();
                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                            System.out.println("snapshot value 2 : "+snapshot);
                            System.out.println("snapshot value exists 2: "+snapshot.exists());
                            patient_id = snapshot.getString("patient_id");
                            patient_name = snapshot.getString("patient_name");
                            doctor_id = snapshot.getString("doctor_id");
                            doctor_name = snapshot.getString("doctor_name");
                            clinic_id = snapshot.getString("clinic_id");
                            category_id = snapshot.getString("category_id");
                            slot_date = snapshot.getString("slot_date");
                            slot_time = snapshot.getString("slot_time");
                            status = snapshot.getString("status");
                            System.out.println("conetents:--> "  +patient_id+patient_name+doctor_id+doctor_name
                                    +clinic_id+category_id+slot_date+slot_time+status);
                            appointment = (new AppointmentModel(patient_id, patient_name, doctor_id, doctor_name, clinic_id, category_id, slot_date, slot_time, status));
                            appointment.setId(snapshot.getId());
                            courseArrayList.add(appointment);
                        }
                    }
                    courseAdapter = new PendingAppointmentAdapter(getContext(), courseArrayList, new PendingAppointmentAdapter.PendingAppointmentItemListener() {
                        @Override
                        public void onAdapterItemClick(AppointmentModel appointment) {
                            navigateToAddFragment(appointment);

                        }

                    });
                    courseAdapter.notifyDataSetChanged();

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                            LinearLayoutManager.VERTICAL, false);

                    courseRV.setLayoutManager(linearLayoutManager);
                    courseRV.setAdapter(courseAdapter);
                }
            });


        return binding.getRoot();
    }*/


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void navigateToAddFragment(AppointmentModel appointment) {
        bundle = new Bundle();
        bundle.putSerializable("appointment", appointment);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_PendingAppointmentClinicSpecificFragment_to_CancelAppointmentClinicSpecificFragment, bundle);
    }
}