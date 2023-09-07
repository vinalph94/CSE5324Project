package com.example.mediassist.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mediassist.R;
import com.example.mediassist.databinding.ActivityDashboardBinding;
import com.example.mediassist.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;


public class DashboardActivity extends AppCompatActivity {

    public static String doctor_id = "";
    public static String clinic_id = "";
    public static String role = "";
    private AppBarConfiguration appBarConfiguration;
    private ActivityDashboardBinding binding;
    private NavController navController;
    private FirebaseFirestore db;
    private Bundle bundle;

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

                if (value != null) {
                    for (QueryDocumentSnapshot snapshot : value) {
                        if (Objects.equals(snapshot.getId(), userId)) {
                            role = snapshot.get("role").toString();

                            if (Objects.equals(role, "1")) {
                                navController.navigate(R.id.SuperAdminDashboardFragment);
                            } else if (Objects.equals(role, "2")) {
                                String user_id = snapshot.getId();//U5zlEy1xUbVlcc5ooBe5cHX2iGh2
                                db.collection("clinicAdmins").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                        if (value != null) {
                                            for (QueryDocumentSnapshot snapshot : value) {
                                                if (Objects.equals(snapshot.get("id"), user_id)) {
                                                    clinic_id = snapshot.get("assign_clinic").toString();//9gOZFM9TuqdKJqqSE8eN
                                                    navController.navigate(R.id.ClinicAdminDashboard, bundle);
                                                }

                                            }
                                        }

                                    }
                                });
                                navController.navigate(R.id.ClinicAdminDashboard, bundle);

                            } else if (Objects.equals(role, "3")) {
                                String user_id = snapshot.getId();
                                db.collection("doctors").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                        String id = "";

                                        if (value != null) {
                                            for (QueryDocumentSnapshot snapshot : value) {
                                                if (Objects.equals(snapshot.get("id"), userId)) {
                                                    id = snapshot.get("id").toString();
                                                    if (Objects.equals(id, user_id)) {
                                                        doctor_id = snapshot.getId().toString();
                                                        // bundle = new Bundle();
                                                        // bundle.putString("doctor_id", doctor_id);
                                                        navController.navigate(R.id.DoctorDashboard, bundle);
                                                    }
                                                }

                                            }
                                        }

                                    }
                                });
                                navController.navigate(R.id.DoctorDashboard, bundle);

                            } else if (Objects.equals(role, "4")) {
                                navController.navigate(R.id.PatientDashboard);
                            }
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

                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);

                startActivity(intent);
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