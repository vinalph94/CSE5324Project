package com.example.mediassist.clinicadmin;

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
import com.example.mediassist.clinic.ClinicAdapter;
import com.example.mediassist.clinic.models.ClinicModel;
import com.example.mediassist.clinicadmin.models.ClinicAdminModel;
import com.example.mediassist.databinding.ClinicAdminListBinding;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class ClinicAdminListFragment extends Fragment {

    private ClinicAdminListBinding binding;
    private FirebaseFirestore db;
    private ArrayList<ClinicAdminModel> courseArrayList = new ArrayList<ClinicAdminModel>();
    private String name;
    private String phoneNumber;
    private String email;
    private String assignClinic;
    private String clinic_name;
    private String user_id;
    private ClinicAdminAdapter courseAdapter;
    private Bundle bundle;
    private ClinicAdminModel clinicadmin;
    private ProgressBar loading_spinner;
    private LinearLayoutCompat layout;
    private GifImageView emptyImage;
    private TextView emptyMessage;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        db = FirebaseFirestore.getInstance();
        binding = ClinicAdminListBinding.inflate(inflater, container, false);
        RecyclerView courseRV = binding.idRVCourseClinicAdmin;

        loading_spinner = (ProgressBar) binding.clinicAdminListProgressBar;
        emptyImage = binding.gifClinicAdmin;
        emptyMessage = binding.clinicAdminNotFoundText;
        layout = binding.clinicAdminListLayout;
        loading_spinner.setVisibility(View.VISIBLE);

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                db.collection("clinicAdmins").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        courseArrayList.clear();
                        if(value!=null) {
                            for (QueryDocumentSnapshot snapshot : value) {
                                name = snapshot.getString("name");
                                phoneNumber = snapshot.getString("phone_number");
                                email = snapshot.getString("email");
                                assignClinic = snapshot.getString("assign_clinic");
                                clinic_name = snapshot.getString("clinic_name");
                                user_id = snapshot.getString("id");
                                clinicadmin = new ClinicAdminModel(name, phoneNumber, email, assignClinic, clinic_name, user_id);
                                clinicadmin.setId(snapshot.getId());
                                courseArrayList.add(clinicadmin);
                            }
                        }

                        if (courseArrayList.size() == 0) {
                            layout.setGravity(CENTER);
                            emptyImage.setVisibility(View.VISIBLE);
                            emptyMessage.setVisibility(View.VISIBLE);
                        } else {
                            layout.setGravity(START);
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
                loading_spinner.setVisibility(View.GONE);


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

    private void navigateToAddFragment(ClinicAdminModel clinicadmin) {
        bundle = new Bundle();
        bundle.putSerializable("clinicadmin", clinicadmin);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_ClinicAdminListFragment_to_AddClinicAdminFragment, bundle);
    }

}