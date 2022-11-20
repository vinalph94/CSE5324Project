package com.example.mediassist.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mediassist.appointment.ScheduleAppointmentActivity;
import com.example.mediassist.databinding.FragmentPatientDashboardBinding;


public class PatientDashboardFragment extends Fragment {

    private FragmentPatientDashboardBinding binding;
    //clinic_doctor_card

    public PatientDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPatientDashboardBinding.inflate(inflater, container, false);

        CardView makeappoinment = binding.clinicDoctorCard;

        makeappoinment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_dashboard);
                // navController.navigate(R.id.MakeAppointment);
                Intent intent = new Intent(getActivity(), ScheduleAppointmentActivity.class);
                startActivity(intent);

            }
        });
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_patient_dashboard, container, false);
        return binding.getRoot();
    }
}