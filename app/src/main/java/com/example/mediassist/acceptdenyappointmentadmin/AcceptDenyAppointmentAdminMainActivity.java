package com.example.mediassist.acceptdenyappointmentadmin;

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
import com.example.mediassist.acceptappointmentadmin.AcceptAppointmentAdminMainActivity;
import com.example.mediassist.acceptdenyappointment.AcceptDenyAppointmentActivity;
import com.example.mediassist.clinic.ClinicActivity;
import com.example.mediassist.dashboard.DashboardActivity;
import com.example.mediassist.databinding.ActivityAcceptDenyAppointmentAdminMainBinding;

public class AcceptDenyAppointmentAdminMainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAcceptDenyAppointmentAdminMainBinding binding;
    private TextView textTitle;
    public Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAcceptDenyAppointmentAdminMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        btnBack = binding.btnBack;
        textTitle = binding.textTitle;

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_accept_deny_appointment_admin_main);
        setActionBarTitle("Pending Appointments");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcceptDenyAppointmentAdminMainActivity.this, DashboardActivity.class);
                intent.putExtra("userId", patientUid);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_accept_deny_appointment_admin_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setActionBarTitle(String title) {
        textTitle.setText(title);
    }
}