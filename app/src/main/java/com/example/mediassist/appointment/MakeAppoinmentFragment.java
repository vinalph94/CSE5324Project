package com.example.mediassist.appointment;

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
import com.example.mediassist.databinding.DoctorListBinding;
import com.example.mediassist.doctor.DoctorAdapter;
import com.example.mediassist.doctor.models.DoctorModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MakeAppoinmentFragment extends Fragment {
    private DoctorListBinding binding;
    private FirebaseFirestore db;
    private ArrayList<DoctorModel> courseArrayList = new ArrayList<DoctorModel>();
    private String doctorname;
    private String doctorphoneNumber;
    private String doctoremail;
    private String assignspecialization;
    private String assignclinic;
    private MakeAppointmentAdapter courseAdapter;
    private Bundle bundle;
    DoctorModel doctor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        db = FirebaseFirestore.getInstance();

        binding = DoctorListBinding.inflate(inflater, container, false);

        RecyclerView courseRV = binding.idRVCourseDoctor;

        db.collection("doctors").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                courseArrayList.clear();
                for (QueryDocumentSnapshot snapshot : value) {
                    doctorname = snapshot.getString("doctor_name");
                    doctorphoneNumber = snapshot.getString("doctor_phone_number");
                    doctoremail = snapshot.getString("doctor_email");
                    assignspecialization = snapshot.getString("category_id");
                    assignclinic = snapshot.getString("clinic_id");
                    doctor= (new DoctorModel(doctorname, doctorphoneNumber, doctoremail, assignspecialization, assignclinic));
                    doctor.setId(snapshot.getId());
                    courseArrayList.add(doctor);

                }
                courseAdapter = new MakeAppointmentAdapter(getContext(), courseArrayList, new MakeAppointmentAdapter.MakeAppointmentItemListener() {
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

        return binding.getRoot();

    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_make_appoinment, container, false);
    }*/

   /* public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((ScheduleAppointmentActivity) getActivity()).setActionBarTitle("Make an Appointment");
    }*/
   public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
       super.onViewCreated(view, savedInstanceState);


   }

    private void navigateToAddFragment(DoctorModel doctor) {
        bundle = new Bundle();
        bundle.putSerializable("doctor", doctor);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_MakeAppointment_to_ScheduleAppointment, bundle);
    }
}