package com.example.mediassist.scheduleappointmentdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mediassist.R;
import com.example.mediassist.databinding.ActivityScheduledAppointmentForDoctorMainBinding;

public class ScheduledAppointmentForDoctorMainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityScheduledAppointmentForDoctorMainBinding binding;
    private TextView textTitle;
    //  public static String doctor_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScheduledAppointmentForDoctorMainBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();
        ///   doctor_id = intent.getStringExtra("doctor_id");
        setContentView(binding.getRoot());
        textTitle = binding.textTitle;

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_scheduled_appointment_for_doctor_main);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_scheduled_appointment_for_doctor_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setActionBarTitle(String title) {
        textTitle.setText(title);
    }
}