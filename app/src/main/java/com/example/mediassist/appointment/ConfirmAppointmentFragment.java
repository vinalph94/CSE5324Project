package com.example.mediassist.appointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.databinding.ConfirmAppointmentFragmentBinding;

import java.util.ArrayList;


public class ConfirmAppointmentFragment extends Fragment {

    private ConfirmAppointmentFragmentBinding binding;
    private ArrayList<String> courseArrayList = new ArrayList<String>();
    private TimeSlotsAdapter timeSlotsAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ConfirmAppointmentFragmentBinding.inflate(inflater, container, false);
        RecyclerView courseRV = binding.idTimeSlotsRv;

//        has to be dynamically loaded
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        courseArrayList.add("10.00 AM");
        timeSlotsAdapter = new TimeSlotsAdapter(getContext(), courseArrayList, new TimeSlotsAdapter.ClinicItemListener() {
            @Override
            public void onAdapterItemClick(String clinic) {

            }

        });
        timeSlotsAdapter.notifyDataSetChanged();


        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        courseRV.setLayoutManager(layoutManager);
        courseRV.setAdapter(timeSlotsAdapter);
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