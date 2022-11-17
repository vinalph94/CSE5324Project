package com.example.mediassist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mediassist.clinic.ClinicActivity;
import com.example.mediassist.clinicadmin.ClinicAdminActivity;
import com.example.mediassist.databinding.WelcomeBinding;
import com.example.mediassist.login.LoginActivity;

public class WelcomeFragment extends Fragment {

    private WelcomeBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = WelcomeBinding.inflate(inflater, container, false);
        Button signInButton = binding.signinButtonView;
        Button signUpButton = binding.signupButtonView;
        signInButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }

        );
        signUpButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(getActivity(), ClinicAdminActivity.class);
                                                startActivity(intent);
                                            }
                                        }

        );
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