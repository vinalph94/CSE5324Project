package com.example.mediassist.clinic;

import static java.util.Objects.nonNull;

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

public class ClinicAdapter extends RecyclerView.Adapter<ClinicAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<ClinicModel> clinicModelArrayList;
    private ClinicItemListener clinicItemListener;

    // Constructor
    public ClinicAdapter(Context context, ArrayList<ClinicModel> ClinicModelArrayList, ClinicItemListener clinicItemListener) {
        this.context = context;
        this.clinicModelArrayList = ClinicModelArrayList;
        this.clinicItemListener = clinicItemListener;
    }

    @NonNull
    @Override
    public ClinicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClinicAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        ClinicModel model = clinicModelArrayList.get(position);
        if (nonNull(model.getName())) {
            holder.clinic_name.setText(String.format("%s", model.getName()));
        }
        if (nonNull(model.getPhone_number())) {
            holder.clinic_phone_number.setText(String.format("%s", model.getPhone_number()));
        }
        if (nonNull(model.getStreet())) {
            holder.clinic_address.setText(String.format("%s, %s, %s", model.getStreet(),model.getCity(),model.getCountry()));
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

    public interface ClinicItemListener {
        void onAdapterItemClick(ClinicModel clinic);
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView clinic_name;
        private final TextView clinic_phone_number;
        private final TextView clinic_address;
        ClinicItemListener clinicItemListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clinic_name = itemView.findViewById(R.id.textview1);
            clinic_phone_number = itemView.findViewById(R.id.textview2);
            clinic_address = itemView.findViewById(R.id.textview3);
        }


    }
}
