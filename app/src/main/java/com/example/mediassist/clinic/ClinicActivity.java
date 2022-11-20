package com.example.mediassist.clinic;

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
import com.example.mediassist.databinding.ActivityClinicBinding;

public class ClinicActivity extends AppCompatActivity {

    public Button btnAdd;
    private AppBarConfiguration appBarConfiguration;
    private ActivityClinicBinding binding;
    private TextView textTitle;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityClinicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        textTitle = binding.textTitle;
        btnAdd = binding.btnAdd;
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_clinic);
        setActionBarTitle("Clinics");

        bundle = new Bundle();
        bundle.putString("amount", "80");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_ClinicListFragment_to_AddClinicFragment, bundle);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_clinic);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setActionBarTitle(String title) {
        textTitle.setText(title);
    }
}