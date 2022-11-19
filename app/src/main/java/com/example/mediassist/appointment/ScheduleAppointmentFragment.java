package com.example.mediassist.appointment;

import static com.example.mediassist.appointment.CalendarUtils.daysInWeekArray;
import static com.example.mediassist.appointment.CalendarUtils.monthYearFromDate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.clinicadmin.models.ClinicAdminModel;
import com.example.mediassist.dashboard.DashboardActivity;
import com.example.mediassist.databinding.ScheduleAppointmentFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;


public class ScheduleAppointmentFragment extends Fragment implements CalendarAdapter.OnItemListener{

    private ScheduleAppointmentFragmentBinding binding;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    private TextView docNameText;
    private TextView docDetailsText;
    private TextView docSpecialistText;
    private TextView hospitalText;
    Button appoitnmentButton;
    FirebaseFirestore db1;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ScheduleAppointmentFragmentBinding.inflate(inflater, container, false);

        db1 = FirebaseFirestore.getInstance();
        CalendarUtils.selectedDate = LocalDate.now();

        initWidgets();
        setDoctorDetails();
        setWeekView();

        appoitnmentButton = binding.bookAptBtn;
        appoitnmentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               // Navigation.findNavController(binding.getRoot()).navigate(R.id.action_FirstFragment_to_Second2Fragment);

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

    private void initWidgets() {
        calendarRecyclerView = binding.calendarRecyclerView;
        monthYearText = binding.monthYearTV;
        ImageButton searchBackButn = (ImageButton) binding.searchBackButton;
        searchBackButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to search doctor screen

                Intent i = new Intent(getContext(), DashboardActivity.class);
                startActivity(i);


            }
        });
    }

    private void setDoctorDetails() {
        docNameText = binding.docName;
        docDetailsText = binding.docDetails;
        docSpecialistText = binding.docSpecialist;
        hospitalText = binding.hospital;

       /* Intent intent = getIntent();
        String docId = intent.getStringExtra("id");
        System.out.println(" doctor id "+docId);*/
        String docid = "vin561@gmail.com";

        CollectionReference allUsersRefs = db1.collection("user");
        Query userPhoneQuery = allUsersRefs.whereEqualTo("username", docid);
        userPhoneQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            String docname = document.getString("name");
                            String docspec = document.getString("specialization");
                            String docclinic = document.getString("clinic");
                            System.out.println("docName : " + docname);
                            System.out.println("docSpec : " + docspec);
                            System.out.println("docClinic : " + docclinic);

                            docNameText.setText(docname);
                            docDetailsText.setText(docname);
                            docSpecialistText.setText(docspec);
                            hospitalText.setText(docclinic);
                        }
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                    Toast.makeText(getContext(), "User Registered successfully", Toast.LENGTH_SHORT).show();
                    // Toast.makeText(BookAppointmentActivity.this, "Error in booking. Please try again", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }

    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        //setEventAdpater();
    }


    public void previousWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    public void bookTimeSlotAction(View view) {

    }
}