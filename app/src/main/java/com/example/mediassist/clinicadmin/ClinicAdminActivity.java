package com.example.mediassist.clinicadmin;

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
import com.example.mediassist.databinding.ActivityClinicAdminBinding;

public class ClinicAdminActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityClinicAdminBinding binding;
    private TextView textTitle;
    public Button addBtn;
    public Button bck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityClinicAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textTitle = binding.textTitleClinicAdmin;
        addBtn=binding.btnAddForClinicAdmin;
        bck = binding.btnBack;

        NavController navController = Navigation.findNavController(ClinicAdminActivity.this, R.id.nav_host_fragment_content_clinic_admin);
        setActionBarTitle("Clinic Admins");

        binding.btnAddForClinicAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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