package com.example.mediassist.appointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.appointment.models.AppointmentModel;

import com.example.mediassist.databinding.ConfirmAppointmentFragmentBinding;
import com.example.mediassist.doctor.models.DoctorModel;
import com.example.mediassist.login.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ConfirmAppointmentFragment extends Fragment {

    private ConfirmAppointmentFragmentBinding binding;
    private ArrayList<String> courseArrayList = new ArrayList<String>();
    private TimeSlotsAdapter timeSlotsAdapter;
    private TextView eventDateTV;
    Button appoitnmentButton;
    public static String selectedTime;
    private Bundle bundle;
    FirebaseFirestore db;
    private AppointmentModel appointmentModel;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState ) {


        binding = ConfirmAppointmentFragmentBinding.inflate(inflater, container, false);
        RecyclerView courseRV = binding.idTimeSlotsRv;
        eventDateTV = binding.eventDateTV;
        eventDateTV.setText(CalendarUtils.selectedDate.getDayOfWeek().name() + ", " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        db = FirebaseFirestore.getInstance();
        bundle = getArguments();
        DoctorModel doctor = (DoctorModel) (bundle != null ? bundle.getSerializable("doctor") : null);

//        has to be dynamically loaded
        courseArrayList.add("09.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("11.00 AM");
        courseArrayList.add("13.00 PM");
        courseArrayList.add("14.00 PM");
        courseArrayList.add("15.00 PM");
        courseArrayList.add("17.00 PM");
        courseArrayList.add("18.00 PM");
        courseArrayList.add("19.00 PM");
        courseArrayList.add("20.00 PM");
        courseArrayList.add("21.00 PM");
        courseArrayList.add("22.00 PM");
        /*
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("16.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");*/
        timeSlotsAdapter = new TimeSlotsAdapter(getContext(), courseArrayList, new TimeSlotsAdapter.ClinicItemListener() {
            @Override
            public void onAdapterItemClick(String selectedSlot) {
                System.out.print("--------booked slot : " + selectedSlot);
                selectedTime = selectedSlot;
            }

        });
        timeSlotsAdapter.notifyDataSetChanged();

        appoitnmentButton = binding.bookAptBtn;
        appoitnmentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                //store the additional fields(signup fields) in firebase
                              appointmentModel = new AppointmentModel(LoginActivity.patientUid, LoginActivity.patientUsername,
                        doctor.getId(), doctor.getDoctorname(), doctor.getAssignclinic(), doctor.getAssignspecialization(),
                        eventDateTV.getText().toString(), ConfirmAppointmentFragment.selectedTime);

                db.collection("appointments").add(appointmentModel)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {



                                Toast.makeText(getContext(), "Appointment booked successfully", Toast.LENGTH_SHORT).show();
                                System.out.println("Appointment booked successfully");
                                // navigate to appointment list screen

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NotNull Exception e) {

                                //display a failure message
                                Toast.makeText(getContext(), "Error while booking Appointment", Toast.LENGTH_SHORT).show();
                                System.out.println("Error while booking Appointment");
                            }
                        });
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        courseRV.setLayoutManager(layoutManager);
        courseRV.setAdapter(timeSlotsAdapter);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ScheduleAppointmentActivity) getActivity()).setActionBarTitle("Confirm Appointment");


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}