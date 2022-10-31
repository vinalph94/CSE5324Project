package com.example.mediassist.doctor;

import android.os.Bundle;

import com.example.mediassist.databinding.ActivityDoctorBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mediassist.R;

public class DoctorActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDoctorBinding binding;
    private TextView textTitle;
    public Button btnAddDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textTitle = binding.textTitleDoctor;
        btnAddDoctor = binding.btnAddDoctor;

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_doctor);
        setActionBarTitle("Doctors");
        btnAddDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigateUp();
                navController.navigate(R.id.action_DoctorListFragment_to_AddDoctorFragment);
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