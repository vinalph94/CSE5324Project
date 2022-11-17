package com.example.mediassist.clinic;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mediassist.R;
import com.example.mediassist.dashboard.DashboardActivity;
import com.example.mediassist.databinding.AddClinicBinding;
import com.example.mediassist.login.LoginActivity;

public class AddClinicFragment extends Fragment {

    private AddClinicBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AddClinicBinding.inflate(inflater, container, false);
        binding.clinicSaveButton.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {

                                                            if (TextUtils.isEmpty(binding.clinicNameText.getText())) {
                                                                binding.clinicNameErrorText.setVisibility(View.VISIBLE);
                                                            } else if (TextUtils.isEmpty(binding.clinicPhoneNumberText.getText())) {
                                                                binding.clinicPhoneNumberErrorText.setVisibility(View.VISIBLE);
                                                            } else if (TextUtils.isEmpty(binding.clinicAddressText.getText())) {
                                                                binding.clinicAddressErrorText.setVisibility(View.VISIBLE);
                                                            } else if (TextUtils.isEmpty(binding.clinicZipcodeText.getText())) {
                                                                binding.clinicPhoneNumberErrorText.setVisibility(View.VISIBLE);
                                                            }
                                                        }
                                                    }


        );


        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ((ClinicActivity) getActivity()).setActionBarTitle("Add Clinic");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}