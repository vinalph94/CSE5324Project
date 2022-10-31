package com.example.mediassist.clinicadmin;

import android.os.Bundle;

import com.example.mediassist.databinding.ActivityClinicAdminBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.mediassist.R;

public class ClinicAdminActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityClinicAdminBinding binding;
    private TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityClinicAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textTitle = binding.textTitleClinicAdmin;

        NavController navController = Navigation.findNavController(ClinicAdminActivity.this, R.id.nav_host_fragment_content_clinic_admin);
        setActionBarTitle("Clinic Admins");

        binding.btnAddForClinicAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigateUp();
                navController.navigate(R.id.action_ClinicAdminListFragment_to_AddClinicAdminFragment);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_clinic_admin);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void setActionBarTitle(String title) {
        textTitle.setText(title);
    }
}