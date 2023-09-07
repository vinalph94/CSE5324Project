package com.example.mediassist.acceptdenyappointment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mediassist.R;
import com.example.mediassist.acceptdenyappointmentadmin.AcceptDenyAppointmentAdminMainActivity;
import com.example.mediassist.appointment.models.AppointmentModel;
import com.example.mediassist.appointmentstatus.PendingAppointmentAdapter;
import com.example.mediassist.databinding.CancelAppointmentClinicSpecificFragmentBinding;
import com.example.mediassist.util.CustomToast;
import com.example.mediassist.util.ToastStatus;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CancelAppointmentClinicSpecificFragment extends Fragment {

    private CancelAppointmentClinicSpecificFragmentBinding binding;
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
        binding = CancelAppointmentClinicSpecificFragmentBinding.inflate(inflater, container, false);
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

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                updateAppointmentDeclinedStatus(id, appointment);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //Do your No progress
                                break;
                        }
                    }
                };
                AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
                ab.setMessage("Are you sure to cancel the appointment?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


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
        ((AcceptDenyAppointmentActivity) getActivity()).setActionBarTitle("Cancel Appointment");

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
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_cancel_appointment_clinic_specific_fragment_to_nav_acceptdenyappointment);
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
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_cancel_appointment_clinic_specific_fragment_to_nav_acceptdenyappointment);
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