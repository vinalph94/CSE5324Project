package com.example.mediassist.appointmentstatus;

import static java.util.Objects.nonNull;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.appointment.models.AppointmentModel;

import java.util.ArrayList;

public class PendingAppointmentAdapter extends RecyclerView.Adapter<PendingAppointmentAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<AppointmentModel> appointmentModelArrayList;
    private PendingAppointmentItemListener pendingAppointmentItemListener;


    public PendingAppointmentAdapter(Context context, ArrayList<AppointmentModel> appointmentModelArrayList, PendingAppointmentItemListener pendingAppointmentItemListener) {
        this.context = context;
        this.appointmentModelArrayList = appointmentModelArrayList;
        this.pendingAppointmentItemListener = pendingAppointmentItemListener;
    }

    @NonNull
    @Override
    public PendingAppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new PendingAppointmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingAppointmentAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        AppointmentModel model = appointmentModelArrayList.get(position);
        if (nonNull(model.getDoctor_name())) {
            holder.doctorname.setText(String.format("%s", model.getDoctor_name()));
        }
        if (nonNull(model.getSlot_date())) {
            holder.appointmentdate.setText(String.format("%s", model.getSlot_date()));
        }
        if (nonNull(model.getSlot_time())) {
            holder.appointmenttime.setText(String.format("%s", model.getSlot_time()));
        }
        holder.itemView.setOnClickListener(view -> {
            pendingAppointmentItemListener.onAdapterItemClick(appointmentModelArrayList.get(position));
        });

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return appointmentModelArrayList.size();
    }

    public interface PendingAppointmentItemListener {
        void onAdapterItemClick(AppointmentModel appointment);
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView doctorname;
        private final TextView appointmentdate;
        private final TextView appointmenttime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorname = itemView.findViewById(R.id.textview1);
            appointmentdate = itemView.findViewById(R.id.textview2);
            appointmenttime = itemView.findViewById(R.id.textview3);
        }
    }
}


