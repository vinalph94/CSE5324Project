package com.example.mediassist.dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mediassist.R;
import com.example.mediassist.databinding.ActivityDashboardBinding;


public class DashboardActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        navController.navigateUp();

        Intent intent = getIntent();
        //String role = intent.getStringExtra("role");
        String role = "4";

        if (role == "1") {
            navController.navigate(R.id.SuperAdminDashboardFragment);
        } else if (role == "2") {
            navController.navigate(R.id.ClinicAdminDashboard);
        } else if (role == "3") {
            navController.navigate(R.id.DoctorDashboard);
        } else if (role == "4") {
            navController.navigate(R.id.PatientDashboard);
        } else {
        }
        //Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}