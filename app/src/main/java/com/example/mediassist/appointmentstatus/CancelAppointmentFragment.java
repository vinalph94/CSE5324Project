package com.example.mediassist.appointmentstatus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mediassist.R;
import com.example.mediassist.appointment.ScheduleAppointmentActivity;
import com.example.mediassist.appointment.models.AppointmentModel;
import com.example.mediassist.clinic.models.ClinicModel;
import com.example.mediassist.databinding.CancelAppointmentFragmentBinding;
import com.example.mediassist.doctor.models.DoctorModel;
import com.example.mediassist.util.CustomToast;
import com.example.mediassist.util.ToastStatus;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CancelAppointmentFragment extends Fragment {

    private CancelAppointmentFragmentBinding binding;
    private ArrayList<AppointmentModel> courseArrayList = new ArrayList<AppointmentModel>();
    private TextView docNameText;
    private TextView DateText;
    private TextView TimeText;
    private String id;
    private Bundle bundle;
    private FirebaseFirestore db;
    private AppointmentModel appointment;
    private PendingAppointmentAdapter courseAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        db = FirebaseFirestore.getInstance();
        binding = CancelAppointmentFragmentBinding.inflate(inflater, container, false);
        docNameText = binding.label1value;
        DateText=binding.label2value;
        TimeText=binding.label3value;

        bundle = getArguments();
         appointment = (AppointmentModel) (bundle != null ? bundle.getSerializable("appointment") : null);

        if (appointment != null) {

            id=appointment.getId();
            docNameText.setText(appointment.getDoctor_name());
            DateText.setText(appointment.getSlot_date());
            TimeText.setText(appointment.getSlot_time());

        }

        binding.cancelAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               updateAppointmentStatus(id,appointment);
                    }
                });

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppointmentListActivity) getActivity()).setActionBarTitle("Cancel Appointment");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updateAppointmentStatus(String appointmentId, AppointmentModel appointment) {

        db.collection(("appointments")).document(appointmentId).update("status","Cancelled").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_CancelAppointmentFragment_to_navappointmentlist);
                new CustomToast(getContext(), getActivity(),  " Appointment Cancelled", ToastStatus.SUCCESS).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new CustomToast(getContext(), getActivity(), " Failed to cancel appointment " , ToastStatus.FAILURE).show();
            }
        });
    }

}