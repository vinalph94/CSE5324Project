package com.example.mediassist.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.example.mediassist.R;
import com.example.mediassist.category.CategoryActivity;
import com.example.mediassist.clinic.ClinicActivity;
import com.example.mediassist.clinicadmin.ClinicAdminActivity;
import com.example.mediassist.databinding.ClinicAdminLayoutBinding;
import com.example.mediassist.databinding.SuperAdminDashboardBinding;
import com.example.mediassist.login.LoginActivity;


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