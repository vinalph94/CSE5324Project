package com.example.mediassist.resetpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mediassist.databinding.FragmentGetEmailForForgotPwdBinding;
import com.example.mediassist.login.LoginActivity;
import com.example.mediassist.util.CustomToast;
import com.example.mediassist.util.ToastStatus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class GetEmailForForgotPwd extends Fragment {

    FirebaseAuth firebaseAuth;
    private FragmentGetEmailForForgotPwdBinding binding;
    private EditText userEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGetEmailForForgotPwdBinding.inflate(inflater, container, false);
        userEmail = binding.emailForgotPwd;


        firebaseAuth = FirebaseAuth.getInstance();
        return binding.getRoot();

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.continueButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userEmail.getText().toString();

                //NavHostFragment.findNavController(GetEmailForForgotPwd.this).navigate(R.id.action_get_email_for_forgot_pwd_to_OtpForForgotPassword);
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            new CustomToast(getContext(), getActivity(), " Password sent to your email successfully", ToastStatus.SUCCESS).show();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            new CustomToast(getContext(), getActivity(), "Error - " + task.getException().getMessage(), ToastStatus.FAILURE).show();
                        }

                    }
                });

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}