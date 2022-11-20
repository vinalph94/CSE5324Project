package com.example.mediassist.resetpassword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mediassist.databinding.FragmentResetPasswordBinding;

public class ResetPassword extends Fragment {

    private FragmentResetPasswordBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentResetPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((ForgotPasswordActivity) getActivity()).setActionBarTitle("Reset Password");


        binding.SavePwdButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // NavHostFragment.findNavController(ResetPassword.this)
                //   .navigate(R.id.action_ResetPassword_to_SuccessfulResetPassword);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}