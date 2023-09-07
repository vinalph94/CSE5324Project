package com.example.mediassist.category;

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
import com.example.mediassist.clinicadmin.ClinicAdminActivity;
import com.example.mediassist.dashboard.DashboardActivity;
import com.example.mediassist.databinding.ActivityCategoryBinding;

public class CategoryActivity extends AppCompatActivity {

    public Button btnAdd;
    private AppBarConfiguration appBarConfiguration;
    private ActivityCategoryBinding binding;
    private TextView textTitle;
    private Bundle bundle;
    public Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        textTitle = binding.textTitle;
        btnBack = binding.btnBack;
        btnAdd = binding.clinicBtnAdd;
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_category);
        setActionBarTitle("Categories");


        bundle = new Bundle();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                navController.navigateUp();
                navController.navigate(R.id.action_CategoryListFragment_to_AddCategoryFragment);
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, DashboardActivity.class);
                intent.putExtra("userId", patientUid);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_category);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setActionBarTitle(String title) {
        textTitle.setText(title);
    }
}