package com.example.mediassist.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mediassist.R;
import com.example.mediassist.clinic.ClinicActivity;
import com.example.mediassist.databinding.AddDoctorBinding;

public class AddDoctorFragment extends Fragment {

    private AddDoctorBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AddDoctorBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddDoctorFragment.this)
                        .navigate(R.id.action_Second2Fragment_to_First2Fragment);
            }
        });
        ((DoctorActivity) getActivity()).setActionBarTitle("Add Doctor");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}