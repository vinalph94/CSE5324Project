package com.example.mediassist.clinic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mediassist.R;
import com.example.mediassist.databinding.AddClinicBinding;

public class AddClinicFragment extends Fragment {

    private AddClinicBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AddClinicBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddClinicFragment.this)
                        .navigate(R.id.action_AddClinicFragment_to_ClinicListFragment);
            }
        });

        ((ClinicActivity) getActivity()).setActionBarTitle("Add Clinic");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}