package com.example.mediassist.denyappointmentclinicadmin;

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
import com.example.mediassist.appointmentacceptstatus.AcceptAppointmentAdapter;
import com.example.mediassist.dashboard.DashboardActivity;
import com.example.mediassist.databinding.DenyAppointmentClinicAdminSpecificFragmentBinding;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DenyAppointmentClinicAdminSpecificFragment extends Fragment {

    private DenyAppointmentClinicAdminSpecificFragmentBinding binding;
    private FirebaseFirestore db;
    private ArrayList<AppointmentModel> courseArrayList = new ArrayList<AppointmentModel>();
    private Bundle bundle;
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
    private AcceptAppointmentAdapter courseAdapter;
    private String clinicAdminClinic_id;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        db = FirebaseFirestore.getInstance();
        binding = DenyAppointmentClinicAdminSpecificFragmentBinding.inflate(inflater, container, false);
        RecyclerView courseRV = binding.idRVCourseDenyAppointmentClinicAdmin;
        clinicAdminClinic_id = DashboardActivity.clinic_id;
        db.collection("appointments").whereEqualTo("status", "Declined").whereEqualTo("clinic_id", clinicAdminClinic_id).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                courseArrayList.clear();
                for (QueryDocumentSnapshot snapshot : value) {
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

                }
                courseAdapter = new AcceptAppointmentAdapter(getContext(), courseArrayList, new AcceptAppointmentAdapter.AcceptAppointmentItemListener() {
                    @Override
                    public void onAdapterItemClick(AppointmentModel appointment) {
                        //navigateToAddFragment(appointment);

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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void navigateToAddFragment(AppointmentModel appointment) {
        bundle = new Bundle();
        bundle.putSerializable("appointment", appointment);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AcceptAppointmentClinicAdminSpecificFragment_to_nav_clinicadminacceptappointment, bundle);
    }

}