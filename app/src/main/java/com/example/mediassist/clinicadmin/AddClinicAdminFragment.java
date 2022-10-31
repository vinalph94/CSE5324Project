package com.example.mediassist.clinicadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mediassist.R;
import com.example.mediassist.databinding.AddClinicAdminBinding;

public class AddClinicAdminFragment extends Fragment {

    private AddClinicAdminBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AddClinicAdminBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ClinicAdminActivity) getActivity()).setActionBarTitle("Add Clinic Admin");


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}