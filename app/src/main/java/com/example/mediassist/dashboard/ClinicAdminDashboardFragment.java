package com.example.mediassist.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mediassist.databinding.ClinicAdminLayoutBinding;


public class ClinicAdminDashboardFragment extends Fragment {

    private ClinicAdminLayoutBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = ClinicAdminLayoutBinding.inflate(inflater, container, false);

        CardView clinicCategoryCard = binding.clinicCategoryCard;
        CardView clinicDoctorCard = binding.clinicDoctorCard;

//        clinicCategoryCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), CategoryActivity.class);
//                startActivity(intent);
//            }
//        });

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