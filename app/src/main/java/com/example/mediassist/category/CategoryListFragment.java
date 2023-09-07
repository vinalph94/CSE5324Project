package com.example.mediassist.category;

import static android.view.Gravity.CENTER;
import static android.view.Gravity.START;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediassist.R;
import com.example.mediassist.category.models.CategoryModel;
import com.example.mediassist.clinicadmin.ClinicAdminAdapter;
import com.example.mediassist.clinicadmin.models.ClinicAdminModel;
import com.example.mediassist.databinding.CategoryListBinding;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

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
    private ProgressBar loading_spinner;
    private LinearLayoutCompat layout;
    private GifImageView emptyImage;
    private TextView emptyMessage;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        db = FirebaseFirestore.getInstance();
        binding = CategoryListBinding.inflate(inflater, container, false);
        RecyclerView courseRV = binding.idRVCourseCategory;

//        ((CategoryActivity) getActivity()).setActionBarTitle("Categories");
        loading_spinner = (ProgressBar) binding.categoryListProgressBar;
        emptyImage = binding.categoryEmptyGif;
        emptyMessage = binding.categoryNotFoundText;
        layout = binding.categoryListLayout;
        loading_spinner.setVisibility(View.VISIBLE);

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                db.collection("categories").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        courseArrayList.clear();
                        if(value!=null) {
                            for (QueryDocumentSnapshot snapshot : value) {
                                name = snapshot.getString("name");
                                if (snapshot.getString("description") != null) {
                                    description = snapshot.getString("description");
                                }
                                clinic_id = snapshot.getString("clinicId");
                                icon_id = snapshot.getString("iconId");
                                category = (new CategoryModel(name, description, icon_id, clinic_id));
                                category.setId(snapshot.getId());
                                category.setIconId(icon_id);
                                category.setClinicId(clinic_id);
                                courseArrayList.add(category);

                            }
                        }
                        if (courseArrayList.size() == 0) {
                            emptyImage.setVisibility(View.VISIBLE);
                            emptyMessage.setVisibility(View.VISIBLE);
                            layout.setGravity(CENTER);
                        } else {
                            layout.setGravity(START);
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
                loading_spinner.setVisibility(View.GONE);


            }
        }, 1000);




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