package com.example.mediassist.resetpassword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mediassist.databinding.FragmentOtpForForgotPasswordBinding;


public class OtpForForgotPassword extends Fragment {
    private FragmentOtpForForgotPasswordBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentOtpForForgotPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.otpContinueBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   NavHostFragment.findNavController(OtpForForgotPassword.this)
                //        .navigate(R.id.action_otp_for_forgot_password_to_ResetPassword);
            }
        });

    }
}