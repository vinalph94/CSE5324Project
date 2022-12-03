package com.example.mediassist.appointmentacceptstatus;

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
import com.example.mediassist.appointment.models.AppointmentModel;
import com.example.mediassist.clinic.ClinicAdapter;
import com.example.mediassist.clinic.models.ClinicModel;
import com.example.mediassist.databinding.AcceptAppointmentFragmentBinding;
import com.example.mediassist.login.LoginActivity;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class AcceptAppointmentFragment extends Fragment {

    private AcceptAppointmentFragmentBinding binding;
    private FirebaseFirestore db;
    private ArrayList<AppointmentModel> courseArrayList = new ArrayList<AppointmentModel>();
    private String patient_id;
    private String patient_name;
    private String doctor_id;
    private String doctor_name;
    private String clinic_id;
    private String category_id;
    private String slot_date;
    private String slot_time;
    private String status;
    private AppointmentModel appointment;
    private AcceptAppointmentAdapter courseAdapter;
    private Bundle bundle;
    private ProgressBar loading_spinner;
    private LinearLayoutCompat layout;
    private GifImageView emptyImage;
    private TextView emptyMessage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();
        binding = AcceptAppointmentFragmentBinding.inflate(inflater, container, false);
        loading_spinner = (ProgressBar) binding.categoryListProgressBar;
        emptyImage = binding.clinicEmptyGif;
        emptyMessage = binding.clinicNotFoundText;
        layout = binding.linearLayout;
        loading_spinner.setVisibility(View.VISIBLE);

        RecyclerView courseRV = binding.idRVCourseAcceptAppointment;
        // Inflate the layout for this fragment

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                db.collection("appointments").whereEqualTo("status", "Accepted").whereEqualTo("patient_id", LoginActivity.patientUid).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        courseArrayList.clear();
                        if(value!=null) {
                            for (QueryDocumentSnapshot snapshot : value) {
                                patient_id = snapshot.getString("patient_id");
                                patient_name = snapshot.getString("patient_name");
                                doctor_id = snapshot.getString("doctor_id");
                                doctor_name = snapshot.getString("doctor_name");
                                clinic_id = snapshot.getString("clinic_id");
                                category_id = snapshot.getString("category_id");
                                slot_date = snapshot.getString("slot_date");
                                slot_time = snapshot.getString("slot_time");
                                status = snapshot.getString("status");
                                appointment = (new AppointmentModel(patient_id, patient_name, doctor_id, doctor_name, clinic_id, category_id, slot_date, slot_time, status));
                                appointment.setId(snapshot.getId());
                                courseArrayList.add(appointment);
                            }
                        }

                        if (courseArrayList.size() == 0) {
                            emptyImage.setVisibility(View.VISIBLE);
                            emptyMessage.setVisibility(View.VISIBLE);
                        } else {
                            layout.setGravity(START);
                        }
                        courseAdapter = new AcceptAppointmentAdapter(getContext(), courseArrayList, new AcceptAppointmentAdapter.AcceptAppointmentItemListener() {
                            @Override
                            public void onAdapterItemClick(AppointmentModel appointment) {
                                //navigateToAddFragment(appointment);

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

    private void navigateToAddFragment(AppointmentModel appointment) {
        bundle = new Bundle();
        bundle.putSerializable("appointment", appointment);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AcceptAppointmentFragmentnt_to_nav_appointmentaccept, bundle);
    }
}