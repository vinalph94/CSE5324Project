package com.example.mediassist.clinic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;

import com.example.mediassist.clinic.models.ClinicModel;
import com.example.mediassist.databinding.ClinicListBinding;

import java.util.ArrayList;

public class ClinicListFragment extends Fragment {

    private ClinicListBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ClinicListBinding.inflate(inflater, container, false);
        RecyclerView courseRV = binding.idRVCourse;

        // Here, we have created new array list and added data to it
        ArrayList<ClinicModel> courseModelArrayList = new ArrayList<ClinicModel>();
        courseModelArrayList.add(new ClinicModel("Clinic 1", "+16823136673","1001 UTA BLVD, Arlington, Texas, 76013"));
        courseModelArrayList.add(new ClinicModel("Clinic 2", "+14562243376","657 BLVD, Arlington, Texas, 76018"));
        courseModelArrayList.add(new ClinicModel("Clinic 3", "+16256673345","567 BLVD, Arlington, Texas, 76024"));


        // we are initializing our adapter class and passing our arraylist to it.
        ClinicAdapter courseAdapter = new ClinicAdapter(getContext(), courseModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(courseAdapter);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}