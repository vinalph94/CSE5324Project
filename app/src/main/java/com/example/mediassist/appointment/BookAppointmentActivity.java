package com.example.mediassist.appointment;

import static android.content.ContentValues.TAG;
import static com.example.mediassist.appointment.CalendarUtils.daysInWeekArray;
import static com.example.mediassist.appointment.CalendarUtils.monthYearFromDate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mediassist.R;
import com.example.mediassist.dashboard.DashboardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;


public class BookAppointmentActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {



    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    private TextView docNameText;
    private TextView docDetailsText;
    private TextView docSpecialistText;
    private TextView hospitalText;
    FirebaseFirestore db1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        db1 = FirebaseFirestore.getInstance();
        CalendarUtils.selectedDate = LocalDate.now();

        initWidgets();
        setDoctorDetails();
        setWeekView();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        ImageButton searchBackButn = (ImageButton)findViewById(R.id.searchBackButton);
        searchBackButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to search doctor screen

                Intent i = new Intent(BookAppointmentActivity.this, DashboardActivity.class);
                startActivity(i);


            }
        });
    }

    private void setDoctorDetails() {
        docNameText = findViewById(R.id.docName);
        docDetailsText = findViewById(R.id.docDetails);
        docSpecialistText = findViewById(R.id.docSpecialist);
        hospitalText = findViewById(R.id.hospital);

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
                            System.out.println("docName : "+docname);
                            System.out.println("docSpec : "+docspec);
                            System.out.println("docClinic : "+docclinic);

                            docNameText.setText(docname);
                            docDetailsText.setText(docname);
                            docSpecialistText.setText(docspec);
                            hospitalText.setText(docclinic);
                        }
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                    Toast.makeText(BookAppointmentActivity.this, "User Registered successfully", Toast.LENGTH_SHORT).show();
                    // Toast.makeText(BookAppointmentActivity.this, "Error in booking. Please try again", Toast.LENGTH_LONG).show();
                    return;
                }
            }});
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        //setEventAdpater();
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //setEventAdpater();
    }

    public void bookTimeSlotAction(View view)
    {
        startActivity(new Intent(this, BookScheduleActivity.class));
    }
    /*
    private void setEventAdpater()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }

     */
}