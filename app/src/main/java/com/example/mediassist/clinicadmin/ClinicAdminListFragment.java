package com.example.mediassist.clinicadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.appointment.models.AppointmentModel;
import com.example.mediassist.appointmentacceptstatus.AcceptAppointmentAdapter;
import com.example.mediassist.clinic.models.ClinicModel;
import com.example.mediassist.clinicadmin.models.ClinicAdminModel;
import com.example.mediassist.databinding.ClinicAdminListBinding;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ClinicAdminListFragment extends Fragment {

    private ClinicAdminListBinding binding;
    private FirebaseFirestore db;
    private ArrayList<ClinicAdminModel> courseArrayList = new ArrayList<ClinicAdminModel>();
    private String name;
    private String phoneNumber;
    private String email;
    private String assignClinic;
    private ClinicAdminAdapter courseAdapter;
    private Bundle bundle;

    private ClinicAdminModel clinicadmin;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        db = FirebaseFirestore.getInstance();
        binding = ClinicAdminListBinding.inflate(inflater, container, false);
        RecyclerView courseRV = binding.idRVCourseClinicAdmin;


        db.collection("clinicAdmins").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                courseArrayList.clear();
                for (QueryDocumentSnapshot snapshot : value) {
                    name = snapshot.getString("name");
                    phoneNumber = snapshot.getString("phone_number");
                    email = snapshot.getString("email");
                    assignClinic = snapshot.getString("assign_clinic");
                    clinicadmin = new ClinicAdminModel(name, phoneNumber, email, assignClinic);
                    clinicadmin.setId(snapshot.getId());
                    courseArrayList.add(clinicadmin);


                }
                courseAdapter = new ClinicAdminAdapter(getContext(), courseArrayList, new ClinicAdminAdapter.ClinicAdminItemListener() {
                    @Override
                    public void onAdapterItemClick(ClinicAdminModel clinicadmin) {
                        navigateToAddFragment(clinicadmin);
                    }

                });
                courseAdapter.notifyDataSetChanged();

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false);


                courseRV.setLayoutManager(linearLayoutManager);
                courseRV.setAdapter(courseAdapter);

            }
        });

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void navigateToAddFragment(ClinicAdminModel clinicadmin) {
        bundle = new Bundle();
        bundle.putSerializable("clinicadmin", clinicadmin);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_ClinicAdminListFragment_to_AddClinicAdminFragment, bundle);
    }

}