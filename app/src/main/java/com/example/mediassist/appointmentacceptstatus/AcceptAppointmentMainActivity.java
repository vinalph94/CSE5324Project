package com.example.mediassist.appointmentacceptstatus;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mediassist.databinding.ActivityAcceptAppointmentMainBinding;

import com.example.mediassist.R;

public class AcceptAppointmentMainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAcceptAppointmentMainBinding binding;
    private TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAcceptAppointmentMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textTitle = binding.textTitle;

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_accept_appointment_main);



    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_accept_appointment_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void setActionBarTitle(String title) {
        textTitle.setText(title);
    }
}