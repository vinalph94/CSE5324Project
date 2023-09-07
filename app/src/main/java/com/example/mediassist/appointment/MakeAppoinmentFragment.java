package com.example.mediassist.appointment;

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
import com.example.mediassist.category.CategoryAdapter;
import com.example.mediassist.category.models.CategoryModel;
import com.example.mediassist.databinding.DoctorListBinding;
import com.example.mediassist.doctor.models.DoctorModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class MakeAppoinmentFragment extends Fragment {
    DoctorModel doctor;
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
    private String clinic_name;
    private String category_name;
    private ProgressBar loading_spinner;
    private LinearLayoutCompat layout;
    private GifImageView emptyImage;
    private TextView emptyMessage;


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
        loading_spinner = (ProgressBar) binding.doctorListProgressBar;
        emptyImage = binding.doctorEmptyGif;
        emptyMessage = binding.doctorNotFoundText;
        layout = binding.doctorListLayout;
        loading_spinner.setVisibility(View.VISIBLE);


        RecyclerView courseRV = binding.idRVCourseDoctor;

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                db.collection("doctors").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        courseArrayList.clear();
                        if(value!=null) {
                            for (QueryDocumentSnapshot snapshot : value) {
                                doctorname = snapshot.getString("doctor_name");
                                doctorphoneNumber = snapshot.getString("doctor_phone_number");
                                doctoremail = snapshot.getString("doctor_email");
                                clinic_name = snapshot.getString("clinic_name");
                                category_name = snapshot.getString("category_name");
                                assignspecialization = snapshot.getString("category_id");
                                assignclinic = snapshot.getString("clinic_id");
                                doctor = (new DoctorModel(doctorname, doctorphoneNumber, doctoremail, assignspecialization, assignclinic, clinic_name,category_name));
                                doctor.setId(snapshot.getId());
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
                loading_spinner.setVisibility(View.GONE);
            }
        }, 1000);



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