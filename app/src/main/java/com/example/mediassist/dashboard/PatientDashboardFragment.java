package com.example.mediassist.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mediassist.R;
import com.example.mediassist.appointment.ScheduleAppointmentActivity;
import com.example.mediassist.appointmentacceptstatus.AcceptAppointmentMainActivity;
import com.example.mediassist.appointmentdenystatus.DenyAppointmentMainActivity;
import com.example.mediassist.appointmentstatus.AppointmentListActivity;
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

        CardView makeappointment = binding.clinicDoctorCard;
        CardView pendingappointment = binding.clinicPendingAppointmentsCard;
        CardView acceptedappointment = binding.clinicAcceptedAppointmentsCard;
        CardView denyappointment = binding.clinicCancelledAppointmentsCard;

        makeappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ScheduleAppointmentActivity.class);
                startActivity(intent);

            }
        });

        pendingappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AppointmentListActivity.class);
                startActivity(intent);

            }
        });

        acceptedappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AcceptAppointmentMainActivity.class);
                startActivity(intent);

            }
        });

        denyappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), DenyAppointmentMainActivity.class);
                startActivity(intent);

            }
        });

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_patient_dashboard, container, false);
        return binding.getRoot();
    }
}