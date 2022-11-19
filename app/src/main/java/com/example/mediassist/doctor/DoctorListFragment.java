package com.example.mediassist.doctor;

import android.app.UiAutomation;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mediassist.clinic.ClinicAdapter;
import com.example.mediassist.clinic.models.ClinicModel;
import com.example.mediassist.databinding.DoctorListBinding;

import java.util.ArrayList;

public class DoctorListFragment extends Fragment  {

    private DoctorListBinding binding;
    private ClinicAdapter courseAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = DoctorListBinding.inflate(inflater, container, false);
        ArrayList<ClinicModel> courseModelArrayList = new ArrayList<ClinicModel>();
//        courseModelArrayList.add(new ClinicModel("Doctor 1", "+16823136673","1001 UTA BLVD, Arlington, Texas, 76013"));
//        courseModelArrayList.add(new ClinicModel("Doctor 2", "+14562243376","657 BLVD, Arlington, Texas, 76018"));
//        courseModelArrayList.add(new ClinicModel("Doctor 3", "+16256673345","567 BLVD, Arlington, Texas, 76024"));

        RecyclerView courseRV = binding.idRVCourse;
        // we are initializing our adapter class and passing our arraylist to it.
        courseAdapter = new ClinicAdapter(getContext(), courseModelArrayList, new ClinicAdapter.ClinicItemListener() {
            @Override
            public void onAdapterItemClick(ClinicModel clinic) {

            }

        });

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