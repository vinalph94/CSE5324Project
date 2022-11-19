package com.example.mediassist.appointment;

import static java.util.Objects.nonNull;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.doctor.DoctorAdapter;
import com.example.mediassist.doctor.models.DoctorModel;

import java.util.ArrayList;

public class MakeAppointmentAdapter extends RecyclerView.Adapter<MakeAppointmentAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<DoctorModel> DoctorModelArrayList;

    // Constructor
    public MakeAppointmentAdapter(Context context, ArrayList<DoctorModel> DoctorModelArrayList) {
        this.context = context;
        this.DoctorModelArrayList = DoctorModelArrayList;

    }



    @NonNull
    @Override
    public MakeAppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new MakeAppointmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MakeAppointmentAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        DoctorModel model = DoctorModelArrayList.get(position);
        if (nonNull(model.getDoctorname())) {
            holder.doctorname.setText(String.format("%s", model.getDoctorname()));
        }
        if (nonNull(model.getAssignclinic())){
            holder.assignclinic.setText(String.format("%s", model.getAssignclinic()));
        }
        if (nonNull(model.getAssignspecialization())) {
            holder.assignspecialization.setText(String.format("%s", model.getAssignspecialization()));
        }


    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return DoctorModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView doctorname;
        private final TextView assignclinic;
        private final TextView assignspecialization;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorname = itemView.findViewById(R.id.textview1);
            assignclinic = itemView.findViewById(R.id.textview2);
            assignspecialization = itemView.findViewById(R.id.textview3);
        }
    }
}

