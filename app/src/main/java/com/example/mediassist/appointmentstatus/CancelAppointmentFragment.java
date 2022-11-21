package com.example.mediassist.appointmentstatus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mediassist.databinding.CancelAppointmentFragmentBinding;

public class CancelAppointmentFragment extends Fragment {

    private CancelAppointmentFragmentBinding binding;
    private TextView docNameText;
    private TextView DateText;
    private TextView TimeText;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = CancelAppointmentFragmentBinding.inflate(inflater, container, false);
        docNameText = binding.label1value;
        DateText=binding.label2value;
        TimeText=binding.label3value;

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cancelAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}