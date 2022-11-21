package com.example.mediassist.acceptdenyappointment;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mediassist.databinding.ActivityAcceptDenyAppointmentBinding;

import com.example.mediassist.R;

public class AcceptDenyAppointmentActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAcceptDenyAppointmentBinding binding;
    private TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAcceptDenyAppointmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textTitle = binding.textTitle;


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_accept_deny_appointment);
        setActionBarTitle("Pending Appointments");

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_accept_deny_appointment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void setActionBarTitle(String title) {
        textTitle.setText(title);
    }
}