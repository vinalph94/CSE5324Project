package com.example.mediassist.dashboard;


import static java.util.Objects.nonNull;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.category.models.CategoryModel;

import java.util.ArrayList;

public class CategoryCardAdapter extends RecyclerView.Adapter<CategoryCardAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<CategoryModel> CategoryModelArrayList;
    private CategoryItemListener categoryItemListener;

    // Constructor
    public CategoryCardAdapter(Context context, ArrayList<CategoryModel> CategoryModelArrayList, CategoryItemListener categoryItemListener) {
        this.context = context;
        this.CategoryModelArrayList = CategoryModelArrayList;
        this.categoryItemListener = categoryItemListener;
    }

    @NonNull
    @Override
    public CategoryCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false);
        return new CategoryCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCardAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        CategoryModel model = CategoryModelArrayList.get(position);
        if (nonNull(model.getIconId())) {
            int resId = context.getResources().getIdentifier(model.getIconId(), "drawable", context.getPackageName());
            holder.icon.setImageResource(resId);
        }
        if (nonNull(model.getDescription())) {
            holder.category_name.setText(String.format("%s", model.getName()));
        }

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
        private final ImageView icon;
        private final TextView category_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.category_id);
            category_name = itemView.findViewById(R.id.category_name);

        }
    }
}
