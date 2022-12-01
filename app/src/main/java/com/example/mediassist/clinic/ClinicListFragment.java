package com.example.mediassist.clinic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.clinic.models.ClinicModel;
import com.example.mediassist.databinding.ClinicListBinding;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ClinicListFragment extends Fragment {

    private ClinicListBinding binding;
    private FirebaseFirestore db;
    private ArrayList<ClinicModel> courseArrayList = new ArrayList<ClinicModel>();
    private String name;
    private String details;
    private String phoneNumber;
    private String street;
    private String city;
    private String county;
    private String country;
    private int zipcode;
    private ClinicAdapter courseAdapter;
    private Bundle bundle;
    private ClinicModel clinic;
    private ProgressBar loading_spinner;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        db = FirebaseFirestore.getInstance();

        binding = ClinicListBinding.inflate(inflater, container, false);
        RecyclerView courseRV = binding.idRVCourse;
        loading_spinner = (ProgressBar)binding.clinicListProgressBar;
        loading_spinner.setVisibility(View.VISIBLE);


        db.collection("clinics").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                courseArrayList.clear();
                if (value != null) {
                    for (QueryDocumentSnapshot snapshot : value) {
                        name = snapshot.getString("name");
                        phoneNumber = snapshot.getString("phone_number");
                        if (snapshot.getString("description") != null) {
                            details = snapshot.getString("description");
                        }
                        street = snapshot.getString("street");
                        city = snapshot.getString("city");
                        county = snapshot.getString("county");
                        country = snapshot.getString("country");

                        zipcode = snapshot.getLong("zipcode").intValue();
                        clinic = new ClinicModel(name, details, phoneNumber, street, city, county, country, zipcode);
                        clinic.setId(snapshot.getId());
                        courseArrayList.add(clinic);

                    }
                }
                courseAdapter = new ClinicAdapter(getContext(), courseArrayList, new ClinicAdapter.ClinicItemListener() {
                    @Override
                    public void onAdapterItemClick(ClinicModel clinic) {
                        navigateToAddFragment(clinic);
                    }

                });
                courseAdapter.notifyDataSetChanged();

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false);


                courseRV.setLayoutManager(linearLayoutManager);
                courseRV.setAdapter(courseAdapter);

            }
        });
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //do what you want
            }
        }, 5000);

        return binding.getRoot();

    }


    public void onCreate(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading_spinner.setVisibility(View.VISIBLE);


    }



    public void on(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading_spinner.setVisibility(View.GONE);


    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading_spinner.setVisibility(View.GONE);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void navigateToAddFragment(ClinicModel clinic) {
        bundle = new Bundle();
        bundle.putSerializable("clinic", clinic);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_ClinicListFragment_to_AddClinicFragment, bundle);
    }
}