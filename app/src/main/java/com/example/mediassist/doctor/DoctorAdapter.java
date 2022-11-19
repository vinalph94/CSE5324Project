package com.example.mediassist.doctor;

import static java.util.Objects.nonNull;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.doctor.models.DoctorModel;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<DoctorModel> DoctorModelArrayList;
    private DoctorItemListener doctorItemListener;
    // Constructor
    public DoctorAdapter(Context context, ArrayList<DoctorModel> DoctorModelArrayList,DoctorItemListener doctorItemListener) {
        this.context = context;
        this.DoctorModelArrayList = DoctorModelArrayList;
        this.doctorItemListener= doctorItemListener;
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
        DoctorModel model = DoctorModelArrayList.get(position);
        if (nonNull(model.getDoctorname())) {
            holder.textview1.setText(String.format("%s", model.getDoctorname()));
        }
        if (nonNull(model.getAssignclinic())){
            holder.assignclinic.setText(String.format("%s", model.getAssignclinic()));
        }
        if (nonNull(model.getDoctoremail())) {
            holder.email.setText(String.format("%s", model.getDoctoremail()));
        }

        holder.itemView.setOnClickListener(view -> {
            doctorItemListener.onAdapterItemClick(DoctorModelArrayList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return DoctorModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textview1;
        private final TextView assignclinic;
        private final TextView email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview1 = itemView.findViewById(R.id.textview1);
            assignclinic = itemView.findViewById(R.id.textview2);
            email = itemView.findViewById(R.id.textview3);
        }
    }
    public interface DoctorItemListener{
         void onAdapterItemClick(DoctorModel doctor);
    }
}
