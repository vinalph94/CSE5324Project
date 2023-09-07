package com.example.mediassist.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mediassist.acceptappointmentadmin.AcceptAppointmentAdminMainActivity;
import com.example.mediassist.acceptdenyappointmentadmin.AcceptDenyAppointmentAdminMainActivity;
import com.example.mediassist.category.CategoryActivity;
import com.example.mediassist.clinic.ClinicActivity;
import com.example.mediassist.clinicadmin.ClinicAdminActivity;
import com.example.mediassist.databinding.ClinicAdminLayoutBinding;
import com.example.mediassist.databinding.SuperAdminDashboardBinding;
import com.example.mediassist.denyappointmentadmin.DenyAppointmentAdminSpecificMainActivity;
import com.example.mediassist.doctor.DoctorActivity;


public class SuperAdminDashboardFragment extends Fragment {

    private SuperAdminDashboardBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = SuperAdminDashboardBinding.inflate(inflater, container, false);
        ClinicAdminLayoutBinding menuBinding = binding.clinicAdminLayout;
        CardView clinicCard = binding.clinicCard;
        CardView clinicAdminCard = binding.clinicAdminCard;
        CardView clinicCategoryCard = menuBinding.clinicCategoryCard;
        CardView clinicDoctorCard = menuBinding.clinicDoctorCard;
        CardView clinicAcceptCard = menuBinding.clinicAcceptedAppointmentsCard;
        CardView clinicPendingCard = menuBinding.clinicPendingAppointmentsCard;
        CardView clinicCancelCard = menuBinding.clinicCancelledAppointmentsCard;

        clinicCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ClinicActivity.class);
                startActivity(intent);
            }
        });
        clinicAdminCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ClinicAdminActivity.class);
                startActivity(intent);
            }
        });
        clinicCategoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
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

        clinicAcceptCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AcceptAppointmentAdminMainActivity.class);
                startActivity(intent);
            }
        });
        clinicPendingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AcceptDenyAppointmentAdminMainActivity.class);
                startActivity(intent);
            }
        });

        clinicCancelCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DenyAppointmentAdminSpecificMainActivity.class);
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