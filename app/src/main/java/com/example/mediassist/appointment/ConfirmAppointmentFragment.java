package com.example.mediassist.appointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.appointment.models.AppointmentModel;
import com.example.mediassist.appointmentstatus.AppointmentListActivity;
import com.example.mediassist.databinding.ConfirmAppointmentFragmentBinding;
import com.example.mediassist.doctor.models.DoctorModel;
import com.example.mediassist.login.LoginActivity;
import com.example.mediassist.util.CustomToast;
import com.example.mediassist.util.ToastStatus;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class ConfirmAppointmentFragment extends Fragment {

    public static String selectedTime;
    Button appoitnmentButton;
    FirebaseFirestore db;
    private ConfirmAppointmentFragmentBinding binding;
    private ArrayList<String> courseArrayList = new ArrayList<String>();
    private TimeSlotsAdapter timeSlotsAdapter;
    private TextView eventDateTV;
    private TextView morning_btn;
    private TextView evening_btn;
    private Bundle bundle;

    private AppointmentModel appointmentModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = ConfirmAppointmentFragmentBinding.inflate(inflater, container, false);
        RecyclerView courseRV = binding.idTimeSlotsRv;
        morning_btn=binding.morningBtn;
        evening_btn=binding.eveningBtn;
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

        timeSlotsAdapter = new TimeSlotsAdapter(getContext(), courseArrayList, new TimeSlotsAdapter.ClinicItemListener() {
            @Override
            public void onAdapterItemClick(String selectedSlot) {
                selectedTime = selectedSlot;
            }

        });
        timeSlotsAdapter.notifyDataSetChanged();

        appoitnmentButton = binding.bookAptBtn;
        appoitnmentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                //store the additional fields(signup fields) in firebase
                appointmentModel = new AppointmentModel(LoginActivity.patientUid, LoginActivity.patientUsername, doctor.getId(), doctor.getDoctor_name(), doctor.getClinic_id(), doctor.getCategory_id(),
                        eventDateTV.getText().toString(), selectedTime, "Pending");

                db.collection("appointments").add(appointmentModel)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                new CustomToast(getContext(), getActivity(), "Appointment booked successfully", ToastStatus.SUCCESS).show();
                                // Navigation.findNavController(binding.getRoot()).navigate(R.id.PatientDashboard);
                                Intent intent = new Intent(getActivity(), ScheduleAppointmentActivity.class);
                                startActivity(intent);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NotNull Exception e) {
                                new CustomToast(getContext(), getActivity(), "Error while booking Appointment", ToastStatus.FAILURE).show();

                                //display a failure message
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