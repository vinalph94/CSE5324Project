package com.example.mediassist.scheduleappointmentdoctor;

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
import com.example.mediassist.databinding.SchduledAppointmentForDoctorFragmentBinding;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ScheduleAppointmentForDoctorFragment extends Fragment {
    private FirebaseFirestore db;
    private SchduledAppointmentForDoctorFragmentBinding binding;
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
    private String docid;
    private AppointmentModel appointment;
    private AcceptAppointmentAdapter courseAdapter;
    private Bundle bundle;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        db = FirebaseFirestore.getInstance();
        binding = SchduledAppointmentForDoctorFragmentBinding.inflate(inflater, container, false);
        RecyclerView courseRV = binding.idRVCourseAcceptAppointmentDoctor;


        //   DocumentReference documentReference = db.collection("doctors").whereEqualTo("id",LoginActivity.patientUid).getFirestore().document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        docid = DashboardActivity.doctor_id;

        db.collection("appointments").whereEqualTo("status", "Accepted").whereEqualTo("doctor_id", docid).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                courseArrayList.clear();
                if(value!=null) {
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
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AcceptAppointmentFragmentnt_to_nav_appointmentaccept, bundle);
    }

}