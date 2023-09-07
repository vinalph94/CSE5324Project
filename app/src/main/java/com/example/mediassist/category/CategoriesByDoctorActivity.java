package com.example.mediassist.category;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mediassist.R;

public class CategoriesByDoctorActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private com.example.mediassist.databinding.ActivityCategoriesByDoctorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = com.example.mediassist.databinding.ActivityCategoriesByDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_categories_by_doctor);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}