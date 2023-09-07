package com.example.mediassist.doctor;

import static com.example.mediassist.login.LoginActivity.patientUid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mediassist.R;
import com.example.mediassist.clinicadmin.ClinicAdminActivity;
import com.example.mediassist.dashboard.DashboardActivity;
import com.example.mediassist.databinding.ActivityDoctorBinding;

public class DoctorActivity extends AppCompatActivity {

    public Button btnAddDoctor;
    private AppBarConfiguration appBarConfiguration;
    private ActivityDoctorBinding binding;
    private TextView textTitle;
    public Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textTitle = binding.textTitle;
        btnAddDoctor = binding.btnAdd;
        btnBack = binding.btnBack;

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_doctor);
        setActionBarTitle("Doctors");
        btnAddDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigateUp();
                navController.navigate(R.id.action_DoctorListFragment_to_AddDoctorFragment);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorActivity.this, DashboardActivity.class);
                intent.putExtra("userId", patientUid);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_doctor);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setActionBarTitle(String title) {
        textTitle.setText(title);
    }
}