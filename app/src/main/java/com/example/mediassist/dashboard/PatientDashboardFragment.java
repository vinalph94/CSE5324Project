package com.example.mediassist.dashboard;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;

import com.example.mediassist.appointment.ScheduleAppointmentActivity;
import com.example.mediassist.appointmentacceptstatus.AcceptAppointmentMainActivity;
import com.example.mediassist.appointmentdenystatus.DenyAppointmentMainActivity;
import com.example.mediassist.appointmentstatus.AppointmentListActivity;
import com.example.mediassist.appointmentstatus.PendingAppointmentFragment;
import com.example.mediassist.databinding.FragmentPatientDashboardBinding;
import com.example.mediassist.doctor.models.DoctorModel;
import com.example.mediassist.util.CheckForEmptyCallBack;
import com.example.mediassist.util.CustomTextWatcher;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PatientDashboardFragment extends Fragment implements CheckForEmptyCallBack, LocationListener {

    private FragmentPatientDashboardBinding binding;
    private RecyclerView searchrv;
    private TextView searchText;
    private TextView error;
    private FirebaseFirestore db;
    private ArrayList<DoctorModel> doctorList = new ArrayList<DoctorModel>();
    private String doctor_name;
    private String doctor_phone_Number;
    private String doctor_email;
    private String assignspecialization;
    private String assignclinic;
    private SearchAdapter searchAdapter;
    private Bundle bundle;
    private DoctorModel doctor;
    private LocationManager locationManager;
    private Geocoder geocoder;
    private double latitude;
    private double longitude;
    private static  final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    //clinic_doctor_card

    public PatientDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPatientDashboardBinding.inflate(inflater, container, false);
        searchText = binding.searchText;
        error = binding.error;
        db = FirebaseFirestore.getInstance();
        CardView makeappointment = binding.clinicDoctorCard;
        CardView pendingappointment = binding.clinicPendingAppointmentsCard;
        CardView acceptedappointment = binding.clinicAcceptedAppointmentsCard;
        CardView denyappointment = binding.clinicCancelledAppointmentsCard;
        searchrv = binding.searchRv;
        searchText.addTextChangedListener(new CustomTextWatcher(error, PatientDashboardFragment.this));
        grantPermission();
        checkLocationIsEnabledOrNot();
        getLocation();
        longitude = 0;
        latitude = 0;
        geocoder = new Geocoder(getContext(), Locale.US);


        db.collection("doctors").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                doctorList.clear();
                for (QueryDocumentSnapshot snapshot : value) {
                    doctor_name = snapshot.getString("doctor_name");
                    doctor_phone_Number = snapshot.getString("doctor_phone_number");
                    doctor_email = snapshot.getString("doctor_email");
                    assignspecialization = snapshot.getString("category_id");
                    assignclinic = snapshot.getString("clinic_id");
                    doctor = (new DoctorModel(doctor_name, doctor_phone_Number, doctor_email, assignspecialization, assignclinic));
                    doctor.setId(snapshot.getId());
                    doctor.setClinic_id(assignclinic);
                    doctor.setCategory_id(assignspecialization);

                    if (longitude != 0 && latitude == 0) {


                        try {
                            ArrayList<Address> adresses = (ArrayList<Address>) geocoder.getFromLocationName("Arlington, Texas", 1);
                            for (Address add : adresses) {

                                double clinicLongitude = add.getLongitude();
                                double clinicLatitude = add.getLatitude();

                                double theta = clinicLongitude - longitude;
                                double dist = Math.sin((clinicLatitude * Math.PI / 180.0))
                                        * Math.sin((latitude * Math.PI / 180.0))
                                        + Math.cos((clinicLatitude * Math.PI / 180.0))
                                        * Math.cos((latitude * Math.PI / 180.0))
                                        * Math.cos((theta * Math.PI / 180.0));
                                dist = Math.acos(dist);
                                dist = (dist * 180.0 / Math.PI);
                                dist = dist * 60 * 1.1515;
                                doctor.setDistance(dist);


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    doctorList.add(doctor);
                }

            }
        });
        makeappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ScheduleAppointmentActivity.class);
                startActivity(intent);

            }
        });

        pendingappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AppointmentListActivity.class);
                startActivity(intent);

            }
        });

        acceptedappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AcceptAppointmentMainActivity.class);
                startActivity(intent);

            }
        });

        denyappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), DenyAppointmentMainActivity.class);
                startActivity(intent);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.PatientDashboard, new PendingAppointmentFragment()).commit();
                // Intent intent = new Intent(getActivity(), ScheduleAppointmentActivity.class);
                //startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_patient_dashboard, container, false);
        return binding.getRoot();
    }

    @Override
    public void checkForEmpty() {
        searchAdapter = new SearchAdapter(getContext(), doctorList, new SearchAdapter.DoctorItemListener() {
            @Override
            public void onAdapterItemClick(DoctorModel doctor) {
//                        navigateToAddFragment(doctor);
            }

        });
        searchAdapter.notifyDataSetChanged();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        searchrv.setLayoutManager(linearLayoutManager);
        searchrv.setAdapter(searchAdapter);
    }
    private void grantPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }

    }

    private void checkLocationIsEnabledOrNot() {
        LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gpsEnabled && !networkEnabled) {
            new AlertDialog.Builder(getContext()).setTitle("Enable GPS Service").setCancelable(false).setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            }).setNegativeButton("Cancel", null).show();

        }
    }

    private void getLocation() {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
            ;
        }
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

}