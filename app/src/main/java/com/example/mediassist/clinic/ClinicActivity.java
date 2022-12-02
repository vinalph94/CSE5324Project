package com.example.mediassist.clinic;

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
import com.example.mediassist.dashboard.DashboardActivity;
import com.example.mediassist.dashboard.SuperAdminDashboardFragment;
import com.example.mediassist.databinding.ActivityClinicBinding;
import com.example.mediassist.login.LoginActivity;


public class ClinicActivity extends AppCompatActivity {

    public Button btnAdd;
    private AppBarConfiguration appBarConfiguration;
    private ActivityClinicBinding binding;
    private TextView textTitle;
    private Bundle bundle;
    public Button btnBack;
    public NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityClinicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        textTitle = binding.textTitle;
        btnAdd = binding.btnAdd;
        btnBack = binding.btnBack;
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_clinic);
        setActionBarTitle("Clinics");

        bundle = new Bundle();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_ClinicListFragment_to_AddClinicFragment, bundle);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClinicActivity.this, DashboardActivity.class);
                intent.putExtra("userId", patientUid);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_clinic);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    public void setActionBarTitle(String title) {
        textTitle.setText(title);
    }
}