package com.example.mediassist.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mediassist.databinding.DoctorDashboardBinding;
import com.example.mediassist.scheduleappointmentdoctor.ScheduledAppointmentForDoctorMainActivity;


public class DoctorDashboardFragment extends Fragment {

    private DoctorDashboardBinding binding;
    private Bundle bundle;
    private String doctor_id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DoctorDashboardBinding.inflate(inflater, container, false);

        CardView clinic_pending_appointments_card = binding.clinicPendingAppointmentsCard;
        bundle = getArguments();

        doctor_id = (bundle != null ? bundle.getString("doctor_id") : null);

        clinic_pending_appointments_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScheduledAppointmentForDoctorMainActivity.class);
                intent.putExtra("doctor_id", doctor_id);
                startActivity(intent);
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

}