package com.example.mediassist.clinicadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mediassist.R;
import com.example.mediassist.databinding.AddClinicAdminBinding;

public class AddClinicAdminFragment extends Fragment{

    private AddClinicAdminBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AddClinicAdminBinding.inflate(inflater, container, false);

//        binding.typesFilter.setAdapter(adapter);
//        binding.typesFilter.setText("All Types");

        // get reference to the string array that we just created

        Spinner spinner = (Spinner) binding.spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.programming_languages, R.layout.spinner_list);
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // get reference to the autocomplete text view
        spinner.setAdapter(adapter);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ClinicAdminActivity) getActivity()).setActionBarTitle("Add Clinic Admin");


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}