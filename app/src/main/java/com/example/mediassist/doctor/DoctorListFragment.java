package com.example.mediassist.doctor;

import static android.view.Gravity.CENTER;
import static android.view.Gravity.START;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.clinicadmin.ClinicAdminAdapter;
import com.example.mediassist.clinicadmin.models.ClinicAdminModel;
import com.example.mediassist.dashboard.DashboardActivity;
import com.example.mediassist.databinding.DoctorListBinding;
import com.example.mediassist.doctor.models.DoctorModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;

public class DoctorListFragment extends Fragment {

    private DoctorListBinding binding;
    private FirebaseFirestore db;
    private ArrayList<DoctorModel> courseArrayList = new ArrayList<DoctorModel>();
    private String doctor_name;
    private String doctor_phone_Number;
    private String doctor_email;
    private String assignspecialization;
    private String assignclinic;
    private DoctorAdapter courseAdapter;
    private Bundle bundle;
    private DoctorModel doctor;
    private ProgressBar loading_spinner;
    private LinearLayoutCompat layout;
    private GifImageView emptyImage;
    private TextView emptyMessage;
    private String clinic_name;
    private String category_name;
    private String role;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        db = FirebaseFirestore.getInstance();

        binding = DoctorListBinding.inflate(inflater, container, false);
        loading_spinner = (ProgressBar) binding.doctorListProgressBar;
        emptyImage = binding.doctorEmptyGif;
        emptyMessage = binding.doctorNotFoundText;
        layout = binding.doctorListLayout;
        loading_spinner.setVisibility(View.VISIBLE);


        RecyclerView courseRV = binding.idRVCourseDoctor;

        role = DashboardActivity.role;
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Objects.equals(role, "2")){
                    db.collection("doctors").whereEqualTo("clinic_id",DashboardActivity.clinic_id).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            courseArrayList.clear();
                            if (value!=null) {


                                for (QueryDocumentSnapshot snapshot : value) {
                                    doctor_name = snapshot.getString("doctor_name");
                                    doctor_phone_Number = snapshot.getString("doctor_phone_number");
                                    doctor_email = snapshot.getString("doctor_email");
                                    assignspecialization = snapshot.getString("category_id");
                                    clinic_name = snapshot.getString("clinic_name");
                                    assignclinic = snapshot.getString("clinic_id");
                                    category_name = snapshot.getString("category_name");
                                    doctor = (new DoctorModel(doctor_name, doctor_phone_Number, doctor_email, assignspecialization, assignclinic, clinic_name,
                                            category_name));
                                    doctor.setId(snapshot.getId());
                                    doctor.setClinic_id(assignclinic);
                                    doctor.setCategory_id(assignspecialization);
                                    courseArrayList.add(doctor);
                                }
                            }


                            if (courseArrayList.size() == 0) {
                                emptyImage.setVisibility(View.VISIBLE);
                                emptyMessage.setVisibility(View.VISIBLE);
                            } else {
                                layout.setGravity(START);
                            }
                            courseAdapter = new DoctorAdapter(getContext(), courseArrayList, new DoctorAdapter.DoctorItemListener() {
                                @Override
                                public void onAdapterItemClick(DoctorModel doctor) {
                                    navigateToAddFragment(doctor);
                                }

                            });
                            courseAdapter.notifyDataSetChanged();

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                                    LinearLayoutManager.VERTICAL, false);

                            courseRV.setLayoutManager(linearLayoutManager);
                            courseRV.setAdapter(courseAdapter);
                        }
                    });
                    loading_spinner.setVisibility(View.GONE);
                }
                else {
                    db.collection("doctors").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            courseArrayList.clear();
                            if (value!=null) {


                                for (QueryDocumentSnapshot snapshot : value) {
                                    doctor_name = snapshot.getString("doctor_name");
                                    doctor_phone_Number = snapshot.getString("doctor_phone_number");
                                    doctor_email = snapshot.getString("doctor_email");
                                    assignspecialization = snapshot.getString("category_id");
                                    clinic_name = snapshot.getString("clinic_name");
                                    assignclinic = snapshot.getString("clinic_id");
                                    category_name = snapshot.getString("category_name");
                                    doctor = (new DoctorModel(doctor_name, doctor_phone_Number, doctor_email, assignspecialization, assignclinic, clinic_name,
                                            category_name));
                                    doctor.setId(snapshot.getId());
                                    doctor.setClinic_id(assignclinic);
                                    doctor.setCategory_id(assignspecialization);
                                    courseArrayList.add(doctor);
                                }
                            }


                        if (courseArrayList.size() == 0) {
                            emptyImage.setVisibility(View.VISIBLE);
                            emptyMessage.setVisibility(View.VISIBLE);
                            layout.setGravity(CENTER);
                        } else {
                            layout.setGravity(START);
                        }
                        courseAdapter = new DoctorAdapter(getContext(), courseArrayList, new DoctorAdapter.DoctorItemListener() {
                            @Override
                            public void onAdapterItemClick(DoctorModel doctor) {
                                navigateToAddFragment(doctor);
                            }

                            });
                            courseAdapter.notifyDataSetChanged();

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                                    LinearLayoutManager.VERTICAL, false);

                            courseRV.setLayoutManager(linearLayoutManager);
                            courseRV.setAdapter(courseAdapter);
                        }
                    });
                    loading_spinner.setVisibility(View.GONE);
                }



            }
        }, 1000);



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

    private void navigateToAddFragment(DoctorModel doctor) {
        bundle = new Bundle();
        bundle.putSerializable("doctor", doctor);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_DoctorListFragment_to_AddDoctorFragment, bundle);
    }

}