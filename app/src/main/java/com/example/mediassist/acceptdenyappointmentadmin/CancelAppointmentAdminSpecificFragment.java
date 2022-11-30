package com.example.mediassist.acceptdenyappointmentadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mediassist.R;
import com.example.mediassist.acceptappointmentadmin.AcceptAppointmentAdminMainActivity;
import com.example.mediassist.appointment.models.AppointmentModel;
import com.example.mediassist.appointmentstatus.PendingAppointmentAdapter;
import com.example.mediassist.databinding.CancelAppointmentAdminSpecificFragmentBinding;
import com.example.mediassist.util.CustomToast;
import com.example.mediassist.util.ToastStatus;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CancelAppointmentAdminSpecificFragment extends Fragment {

    private CancelAppointmentAdminSpecificFragmentBinding binding;
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
        binding = CancelAppointmentAdminSpecificFragmentBinding.inflate(inflater, container, false);
        docNameText = binding.label1value;
        DateText = binding.label2value;
        TimeText = binding.label3value;

        bundle = getArguments();
        appointment = (AppointmentModel) (bundle != null ? bundle.getSerializable("appointment") : null);

        if (appointment != null) {

            id = appointment.getId();
            docNameText.setText(appointment.getDoctor_name());
            DateText.setText(appointment.getSlot_date());
            TimeText.setText(appointment.getSlot_time());

        }

        binding.cancelAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateAppointmentDeclinedStatus(id, appointment);
            }
        });

        binding.acceptAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAppointmentAcceptStatus(id, appointment);
            }
        });
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AcceptDenyAppointmentAdminMainActivity) getActivity()).setActionBarTitle("Cancel Appointment");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updateAppointmentDeclinedStatus(String appointmentId, AppointmentModel appointment) {

        db.collection(("appointments")).document(appointmentId).update("status", "Declined").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_cancel_appointment_admin_specific_fragment_to_nav_acceptdenyappointmentadmin);
                new CustomToast(getContext(), getActivity(), " Appointment Declined", ToastStatus.SUCCESS).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new CustomToast(getContext(), getActivity(), " Failed to decline appointment ", ToastStatus.FAILURE).show();
            }
        });
    }

    public void updateAppointmentAcceptStatus(String appointmentId, AppointmentModel appointment) {

        db.collection(("appointments")).document(appointmentId).update("status", "Accepted").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_cancel_appointment_admin_specific_fragment_to_nav_acceptdenyappointmentadmin);
                new CustomToast(getContext(), getActivity(), " Appointment Accepted", ToastStatus.SUCCESS).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new CustomToast(getContext(), getActivity(), " Failed to accept appointment ", ToastStatus.FAILURE).show();
            }
        });
    }

}