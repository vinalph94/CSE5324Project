package com.example.mediassist.resetpassword;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mediassist.R;
import com.example.mediassist.databinding.ActivityForgotPasswordBinding;


public class ForgotPasswordActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityForgotPasswordBinding binding;
    private TextView textTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        textTitle = binding.pwdTitle;

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_forgot_password);
        setActionBarTitle("Forgot Password");


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_forgot_password);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setActionBarTitle(String title) {
        textTitle.setText(title);
    }


}