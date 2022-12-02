package com.example.mediassist.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mediassist.acceptappointmentclinicadmin.AcceptAppointmentClinicAdminSpecificActivity;
import com.example.mediassist.acceptdenyappointment.AcceptDenyAppointmentActivity;
import com.example.mediassist.category.CategoryActivity;
import com.example.mediassist.databinding.ClinicAdminLayoutBinding;
import com.example.mediassist.denyappointmentclinicadmin.DenyAppointmentClinicAdminSpecificMainActivity;
import com.example.mediassist.doctor.DoctorActivity;


public class ClinicAdminDashboardFragment extends Fragment {

    private ClinicAdminLayoutBinding binding;
    private Bundle bundle;
    private String clinic_id;
    private String clinicAdminClinic_id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = ClinicAdminLayoutBinding.inflate(inflater, container, false);

        clinic_id = (bundle != null ? bundle.getString("clinic_id") : null);
        CardView clinicCategoryCard = binding.clinicCategoryCard;
        CardView clinicDoctorCard = binding.clinicDoctorCard;
        CardView clinicPendingAppointmentCard = binding.clinicPendingAppointmentsCard;
        CardView clinicAcceptedAppointmentsCard = binding.clinicAcceptedAppointmentsCard;
        CardView clinicCancelledAppointmentsCard = binding.clinicCancelledAppointmentsCard;

        clinicCategoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
//                intent.putExtra("clinic_id",clinic_id);
                startActivity(intent);
            }
        });

        clinicDoctorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DoctorActivity.class);
                startActivity(intent);
            }
        });

        clinicPendingAppointmentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clinicAdminClinic_id = DashboardActivity.clinic_id;
                Intent intent = new Intent(getActivity(), AcceptDenyAppointmentActivity.class);
                intent.putExtra("clinic_id", clinic_id);
                startActivity(intent);
            }
        });

        clinicAcceptedAppointmentsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AcceptAppointmentClinicAdminSpecificActivity.class);
                intent.putExtra("clinic_id", clinic_id);
                startActivity(intent);

            }
        });

        clinicCancelledAppointmentsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), DenyAppointmentClinicAdminSpecificMainActivity.class);
                intent.putExtra("clinic_id", clinic_id);
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