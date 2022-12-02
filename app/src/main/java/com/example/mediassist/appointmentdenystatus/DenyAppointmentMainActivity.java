package com.example.mediassist.appointmentdenystatus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mediassist.R;
import com.example.mediassist.databinding.ActivityDenyAppointmentMainBinding;

public class DenyAppointmentMainActivity extends AppCompatActivity {

    public static String doctor_id;
    private AppBarConfiguration appBarConfiguration;
    private ActivityDenyAppointmentMainBinding binding;
    private TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDenyAppointmentMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textTitle = binding.textTitle;
        Intent intent = getIntent();
        doctor_id = intent.getStringExtra("doctor_id");


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_deny_appointment_main);


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_deny_appointment_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setActionBarTitle(String title) {
        textTitle.setText(title);
    }
}