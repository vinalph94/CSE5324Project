package com.example.mediassist.appointment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediassist.R;

public class BookScheduleActivity extends AppCompatActivity {

    private TextView eventDateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_schedule);
        initWidgets();
        eventDateTV.setText(CalendarUtils.selectedDate.getDayOfWeek().name() + ", " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
    }

    private void initWidgets() {
        eventDateTV = findViewById(R.id.eventDateTV);
    }
}