package com.example.mediassist.doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.clinic.models.ClinicModel;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<ClinicModel> ClinicModelArrayList;

    // Constructor
    public DoctorAdapter(Context context, ArrayList<ClinicModel> ClinicModelArrayList) {
        this.context = context;
        this.ClinicModelArrayList = ClinicModelArrayList;
    }

    @NonNull
    @Override
    public DoctorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        ClinicModel model = ClinicModelArrayList.get(position);
        holder.clinic_name.setText("" + model.getClinic_name());
        holder.clinic_phone_number.setText("" + model.getClinic_phone_number());
        holder.clinic_address.setText("" + model.getClinic_address());
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return ClinicModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView clinic_name;
        private final TextView clinic_phone_number;
        private final TextView clinic_address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clinic_name = itemView.findViewById(R.id.clinic_name);
            clinic_phone_number = itemView.findViewById(R.id.clinic_phone_number);
            clinic_address = itemView.findViewById(R.id.clinic_address);
        }
    }
}
