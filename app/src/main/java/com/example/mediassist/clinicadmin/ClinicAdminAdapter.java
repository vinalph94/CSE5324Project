package com.example.mediassist.clinicadmin;

import static java.util.Objects.nonNull;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.clinicadmin.models.ClinicAdminModel;

import java.util.ArrayList;

public class ClinicAdminAdapter extends RecyclerView.Adapter<ClinicAdminAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<ClinicAdminModel> ClinicAdminModelArrayList;
    private ClinicAdminItemListener clinicadminItemListener;

    // Constructor
    public ClinicAdminAdapter(Context context, ArrayList<ClinicAdminModel> ClinicAdminModelArrayList, ClinicAdminItemListener clinicadminItemListener) {
        this.context = context;
        this.ClinicAdminModelArrayList = ClinicAdminModelArrayList;
        this.clinicadminItemListener = clinicadminItemListener;
    }

    @NonNull
    @Override
    public ClinicAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ClinicAdminAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClinicAdminAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        ClinicAdminModel model = ClinicAdminModelArrayList.get(position);
        if (nonNull(model.getName())) {
            holder.clinic_admin_name.setText(String.format("%s", model.getName()));
        }

        if (nonNull(model.getClinic_name())) {
            holder.clinic_assign.setText(String.format("%s", model.getClinic_name()));
        }
        if (nonNull(model.getPhone_number())) {
            holder.clinic_admin_phone_number.setText(String.format("%s", model.getPhone_number()));
        }
        holder.itemView.setOnClickListener(view -> {
            clinicadminItemListener.onAdapterItemClick(ClinicAdminModelArrayList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return ClinicAdminModelArrayList.size();
    }

    public interface ClinicAdminItemListener {
        void onAdapterItemClick(ClinicAdminModel clinicadmin);
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView clinic_admin_name;
        private final TextView clinic_admin_phone_number;
        private final TextView clinic_assign;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clinic_admin_name = itemView.findViewById(R.id.textview1);
            clinic_admin_phone_number = itemView.findViewById(R.id.textview2);
            clinic_assign = itemView.findViewById(R.id.textview3);
        }
    }
}
