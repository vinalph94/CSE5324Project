package com.example.mediassist.appointment;

import static java.util.Objects.nonNull;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;

import java.util.ArrayList;

public class TimeSlotsAdapter extends RecyclerView.Adapter<TimeSlotsAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<String> clinicModelArrayList;
    private ClinicItemListener clinicItemListener;

    // Constructor
    public TimeSlotsAdapter(Context context, ArrayList<String> ClinicModelArrayList, ClinicItemListener clinicItemListener) {
        this.context = context;
        this.clinicModelArrayList = ClinicModelArrayList;
        this.clinicItemListener = clinicItemListener;
    }

    @NonNull
    @Override
    public TimeSlotsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_slot_button, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotsAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        String model = clinicModelArrayList.get(position);
        if (nonNull(model)) {
            holder.timeSlitBtn.setText(String.format("%s", model));
        }
        /*if(ConfirmAppointmentFragment.selectedTime != null &&
                model.equals(ConfirmAppointmentFragment.selectedTime) ){
            System.out.println("Highlighting selected time "+model);
            holder.timeSlitBtn.setBackgroundColor(Color.rgb(64, 142, 155));
        }*/


        holder.itemView.setOnClickListener(view -> {

            //holder.timeSlitBtn.setBackgroundColor(Color.GRAY);
            holder.timeSlitBtn.setTextColor(Color.WHITE);
            holder.timeSlitBtn.setBackgroundColor(Color.rgb(64, 142, 155));
            //view.findViewById(R.id.timeslotBtn).setBackgroundColor(Color.rgb(64, 142, 155));
            clinicItemListener.onAdapterItemClick(clinicModelArrayList.get(position));
            System.out.println("clinicModelArrayList.get(position) " + clinicModelArrayList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return clinicModelArrayList.size();
    }

    public interface ClinicItemListener {
        void onAdapterItemClick(String clinic);
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button timeSlitBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeSlitBtn = itemView.findViewById(R.id.timeslotBtn);

        }


    }
}
