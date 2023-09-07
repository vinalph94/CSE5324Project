package com.example.mediassist.category;

import static java.util.Objects.nonNull;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.category.models.CategoryModel;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<CategoryModel> CategoryModelArrayList;
    private CategoryItemListener categoryItemListener;

    // Constructor
    public CategoryAdapter(Context context, ArrayList<CategoryModel> CategoryModelArrayList, CategoryItemListener categoryItemListener) {
        this.context = context;
        this.CategoryModelArrayList = CategoryModelArrayList;
        this.categoryItemListener = categoryItemListener;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        CategoryModel model = CategoryModelArrayList.get(position);
        if (nonNull(model.getName())) {
            holder.category_name.setText(String.format("%s", model.getName()));
        }
        if (nonNull(model.getDescription())) {
            holder.category_description.setText(String.format("%s", model.getDescription()));
        }
//        if (nonNull(model.getClinicId())) {
//            holder.clinic_assign.setText(String.format("%s", model.getClinicId()));
//        }
        holder.itemView.setOnClickListener(view -> {
            categoryItemListener.onAdapterItemClick(CategoryModelArrayList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return CategoryModelArrayList.size();
    }

    public interface CategoryItemListener {
        void onAdapterItemClick(CategoryModel category);
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView category_name;
        private final TextView category_description;
        private final TextView clinic_assign;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category_name = itemView.findViewById(R.id.textview1);
            category_description = itemView.findViewById(R.id.textview2);
            clinic_assign = itemView.findViewById(R.id.textview3);
        }
    }
}
