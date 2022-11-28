package com.example.mediassist.denyappointmentclinicadmin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mediassist.R;
import com.example.mediassist.databinding.ActivityDenyApoointmentClinicAdminSpecificMainBinding;

public class DenyAppointmentClinicAdminSpecificMainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDenyApoointmentClinicAdminSpecificMainBinding binding;
    private TextView textTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDenyApoointmentClinicAdminSpecificMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textTitle = binding.textTitle;

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_deny_appointment_clinic_admin_specific_main);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_deny_appointment_clinic_admin_specific_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void setActionBarTitle(String title) {
        textTitle.setText(title);
    }
}