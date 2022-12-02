package com.example.mediassist.acceptappointmentclinicadmin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mediassist.R;
import com.example.mediassist.databinding.ActivityAcceptAppointmentClinicAdminSpecificBinding;

public class AcceptAppointmentClinicAdminSpecificActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAcceptAppointmentClinicAdminSpecificBinding binding;
    private TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAcceptAppointmentClinicAdminSpecificBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textTitle = binding.textTitle;


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_accept_appointment_clinic_admin_specific);


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_accept_appointment_clinic_admin_specific);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setActionBarTitle(String title) {
        textTitle.setText(title);
    }
}