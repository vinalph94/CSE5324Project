package com.example.mediassist.appointment;

import static java.util.Objects.nonNull;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.clinic.models.ClinicModel;

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


        holder.itemView.setOnClickListener(view -> {
            clinicItemListener.onAdapterItemClick(clinicModelArrayList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return clinicModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button timeSlitBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeSlitBtn = itemView.findViewById(R.id.timeslotBtn);

        }


    }

    public interface ClinicItemListener {
        void onAdapterItemClick(String clinic);
    }
}
