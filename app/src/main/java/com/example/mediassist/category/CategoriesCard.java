package com.example.mediassist.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.category.models.CategoryModel;
import com.example.mediassist.dashboard.CategoryCardAdapter;
import com.example.mediassist.databinding.CategoriesCardBinding;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class CategoriesCard extends Fragment {

    private CategoriesCardBinding binding;
    private RecyclerView categoriesDoctorsRv;
    private ArrayList<CategoryModel> categoriesList = new ArrayList<CategoryModel>();
    private FirebaseFirestore db;
    private CategoryModel category;
    private CategoryCardAdapter categoryCardAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = CategoriesCardBinding.inflate(inflater, container, false);
        categoriesDoctorsRv = binding.categoriesDoctorsRv;
        db = FirebaseFirestore.getInstance();
        db.collection("categories").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                categoriesList.clear();
                for (QueryDocumentSnapshot snapshot : value) {
                    String description = "";
                    String clinic_id;
                    String icon_id;
                    String name = snapshot.getString("name");
                    if (snapshot.getString("description") != null) {
                        description = snapshot.getString("description");
                    }
                    clinic_id = snapshot.getString("clinicId");
                    icon_id = snapshot.getString("iconId");
                    category = (new CategoryModel(name, description, icon_id, clinic_id));
                    category.setId(snapshot.getId());
                    category.setIconId(icon_id);
                    category.setClinicId(clinic_id);
                    categoriesList.add(category);


                    categoryCardAdapter = new CategoryCardAdapter(getContext(), categoriesList, new CategoryCardAdapter.CategoryItemListener() {
                        @Override
                        public void onAdapterItemClick(CategoryModel category) {
//                        navigateToAddFragment(doctor);
                        }

                    });
                    categoryCardAdapter.notifyDataSetChanged();

//                   filteredCategories = categoriesList.stream()
//                            .filter(category -> doctorList.stream()
//                                    .anyMatch(doctor -> category.getClinicId().equals(doctor.getClinic_id()))
//                                    )
//                            .collect(Collectors.toList());
//                    categoriesList.add((CategoryModel) filteredCategories);


                }
                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
                layoutManager.setOrientation(GridLayoutManager.VERTICAL);
                categoriesDoctorsRv.setLayoutManager(layoutManager);
                categoriesDoctorsRv.setAdapter(categoryCardAdapter);

            }
        });
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}