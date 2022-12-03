package com.example.mediassist.appointment;

import static com.example.mediassist.appointment.CalendarUtils.daysInWeekArray;
import static com.example.mediassist.appointment.CalendarUtils.monthYearFromDate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.databinding.ScheduleAppointmentFragmentBinding;
import com.example.mediassist.doctor.models.DoctorModel;
import com.example.mediassist.util.CustomToast;
import com.example.mediassist.util.ToastStatus;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.time.LocalDate;
import java.util.ArrayList;


public class ScheduleAppointmentFragment extends Fragment implements CalendarAdapter.OnItemListener {

    public static String docName;
    public static String docSpec;
    public static String docClinic;
    Button appoitnmentButton;
    Button prevDateButton;
    Button nextDateButton;
    FirebaseFirestore db1;
    private ScheduleAppointmentFragmentBinding binding;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    private TextView docNameText;
    private TextView docDetailsText;
    private TextView docSpecialistText;
    private TextView hospitalText;
    private Bundle bundle;
    private DoctorModel doctor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = ScheduleAppointmentFragmentBinding.inflate(inflater, container, false);

        db1 = FirebaseFirestore.getInstance();
        CalendarUtils.selectedDate = LocalDate.now();

        initWidgets();
        //setDoctorDetails();
        setWeekView();

        docNameText = binding.docName;
        docDetailsText = binding.docDetails;
        docSpecialistText = binding.docSpecialist;

        bundle = getArguments();
        DoctorModel doctor = (DoctorModel) (bundle != null ? bundle.getSerializable("doctor") : null);

        if (doctor != null) {
            docNameText.setText(doctor.getDoctor_name());
            System.out.println("doctor.getCategory : " + doctor.getCategory_id());
            System.out.println("doctor.getClinic_id() : " + doctor.getClinic_id());
            setDocCategoryName(doctor.getCategory_id());
            setClinicName(doctor.getClinic_id());

        }

        appoitnmentButton = binding.bookAptBtn;
        appoitnmentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (CalendarUtils.selectedDate.compareTo(LocalDate.now()) >= 0) {
                    System.out.println("Selected date is greater than or equal to today");
                    navigateToAddFragment(doctor);
                } else {
                    System.out.println("Selected date is before day");
                    new CustomToast(getContext(), getActivity(), "ASelected date should be today or latter", ToastStatus.FAILURE).show();
                }
            }
        });

        prevDateButton = binding.previousWeekAction;
        prevDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
                setWeekView();
            }
        });
        nextDateButton = binding.nextWeekAction;
        nextDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
                setWeekView();
            }
        });
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ScheduleAppointmentActivity) getActivity()).setActionBarTitle("Schedule Appointment");


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initWidgets() {
        calendarRecyclerView = binding.calendarRecyclerView;
        monthYearText = binding.monthYearTV;
       /* ImageButton searchBackButn = (ImageButton) binding.searchBackButton;
        searchBackButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to search doctor screen

                Intent i = new Intent(getContext(), DashboardActivity.class);
                startActivity(i);


            }
        });*/
    }

    private void setClinicName(String clinicid) {
        System.out.println("setClinicName id : " + clinicid);
        DocumentReference allClinicRefs = db1.collection("clinics").document(clinicid);
        System.out.println("allClinicRefs.getId() : " + allClinicRefs.getId());
        allClinicRefs.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value!=null){
                    String clinicName = value.getString("name");
                    System.out.println("clinicName : " + clinicName);
                    docSpecialistText.setText(String.format("%s clinic", clinicName));
                }
            }
        });
    }

    private void setDocCategoryName(String catid) {
        DocumentReference allCategoriesRefs = db1.collection("categories").document(catid);
        System.out.println("allCategoriesRefs.getId() : " + allCategoriesRefs.getId());
        allCategoriesRefs.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value!=null) {
                    String description = value.getString("description");
                    System.out.println("description : " + description);
                    docDetailsText.setText(description);
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

    /*
        public void previousWeekAction(View view) {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
            setWeekView();
        }

        public void nextWeekAction(View view) {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
            setWeekView();
        }*/
    private void navigateToAddFragment(DoctorModel doctor) {
        bundle = new Bundle();
        bundle.putSerializable("doctor", doctor);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_ScheduleAppointment_to_ConfirmAppointment, bundle);

    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    public void bookTimeSlotAction(View view) {

    }
}