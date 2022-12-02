package com.example.mediassist.denyappointmentadmin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mediassist.R;
import com.example.mediassist.databinding.ActivityDenyAppointmentAdminSpecificMainBinding;

public class DenyAppointmentAdminSpecificMainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDenyAppointmentAdminSpecificMainBinding binding;
    private TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDenyAppointmentAdminSpecificMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textTitle = binding.textTitle;


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_deny_appointment_admin_specific_main);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_deny_appointment_admin_specific_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setActionBarTitle(String title) {
        textTitle.setText(title);
    }
}