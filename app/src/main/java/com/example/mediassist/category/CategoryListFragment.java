package com.example.mediassist.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.category.models.CategoryModel;
import com.example.mediassist.databinding.CategoryListBinding;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CategoryListFragment extends Fragment {

    private CategoryListBinding binding;
    private FirebaseFirestore db;
    private ArrayList<CategoryModel> courseArrayList = new ArrayList<CategoryModel>();
    private String name;
    private String description;
    private String clinic_id;
    private CategoryAdapter courseAdapter;
    private Bundle bundle;
    private CategoryModel category;
    private String icon_id;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        db = FirebaseFirestore.getInstance();
        binding = CategoryListBinding.inflate(inflater, container, false);
        RecyclerView courseRV = binding.idRVCourseCategory;

//        ((CategoryActivity) getActivity()).setActionBarTitle("Categories");

        db.collection("categories").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                courseArrayList.clear();
                for (QueryDocumentSnapshot snapshot : value) {
                    name = snapshot.getString("name");
                    if (snapshot.getString("description") != null) {
                        description = snapshot.getString("description");
                    }
                    clinic_id = snapshot.getString("clinic_id");
                    icon_id = snapshot.getString("icon_id");
                    category = (new CategoryModel(name, description,icon_id, clinic_id));
                    category.setId(snapshot.getId());
                    courseArrayList.add(category);

                }
                courseAdapter = new CategoryAdapter(getContext(), courseArrayList, new CategoryAdapter.CategoryItemListener() {
                    @Override
                    public void onAdapterItemClick(CategoryModel category) {
                        navigateToAddFragment(category);
                    }

                });
                courseAdapter.notifyDataSetChanged();

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false);


                courseRV.setLayoutManager(linearLayoutManager);
                courseRV.setAdapter(courseAdapter);

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

    private void navigateToAddFragment(CategoryModel category) {
        bundle = new Bundle();
        bundle.putSerializable("category", category);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_CategoryListFragment_to_AddCategoryFragment, bundle);
    }
}