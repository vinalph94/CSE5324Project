package com.example.mediassist.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mediassist.R;
import com.example.mediassist.databinding.ActivityDashboardBinding;
import com.example.mediassist.util.NavigationUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;


public class DashboardActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDashboardBinding binding;
    private NavController navController;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        ImageButton btnSignout = binding.btnSignout;
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        navController.navigateUp();

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                String role = "";
                for (QueryDocumentSnapshot snapshot : value) {
                    if  (Objects.equals(snapshot.getId(),userId) ){
                        role = snapshot.get("role").toString();
                        if (Objects.equals(role, "1")) {
                            navController.navigate(R.id.SuperAdminDashboardFragment);
                        } else if (Objects.equals(role, "2")) {
                            navController.navigate(R.id.ClinicAdminDashboard);
                        } else if (Objects.equals(role, "3")) {
                            navController.navigate(R.id.DoctorDashboard);
                        } else if (Objects.equals(role, "4")) {
                            navController.navigate(R.id.DoctorDashboard);
                        }
                    }

                }
            }
        });


        //Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();


                NavigationUtil.navigateSafe(navController, R.id.PatientDashboardFragment_to_welcomeFragment, null);
                ;

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}