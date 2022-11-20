package com.example.mediassist.category;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mediassist.R;
import com.example.mediassist.category.models.CategoryModel;
import com.example.mediassist.databinding.AddCategoryBinding;
import com.example.mediassist.util.CheckForEmptyCallBack;
import com.example.mediassist.util.CustomTextWatcher;
import com.example.mediassist.util.CustomToast;
import com.example.mediassist.util.ToastStatus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class AddCategoryFragment extends Fragment implements CheckForEmptyCallBack {

    private AddCategoryBinding binding;
    private FirebaseFirestore db;
    private Button saveButton;
    private Button editButton;
    private Button deleteButton;
    private EditText categoryName;
    private EditText categoryDescription;
    private TextView category_name_error;
    private String name;
    private String description;
    String assign_clinic;
    private Bundle bundle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        binding = AddCategoryBinding.inflate(inflater, container, false);
        bundle = getArguments();
        CategoryModel category = (CategoryModel) (bundle != null ? bundle.getSerializable("category") : null);

        Spinner spinner = (Spinner) binding.spinnerCategory;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.programming_languages, R.layout.spinner_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        categoryName = binding.categoryNameText;
        categoryDescription = binding.categoryDetailsText;
        category_name_error = binding.categoryNameErrorText;
        saveButton = binding.categorySaveButton;
        editButton = binding.categoryEditButton;
        deleteButton = binding.categoryDeleteButton;

        if (category != null) {
            categoryName.setText(category.getName());
            if (category.getDescription() != null) {
                categoryDescription.setText(category.getDescription());
            }

            // assign_clinic = spinner.getSelectedItem().toString();

            saveButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);

        }


        categoryName.addTextChangedListener(new CustomTextWatcher(category_name_error, AddCategoryFragment.this));
        checkCategoryData();

        saveButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              assign_clinic = spinner.getSelectedItem().toString();
                                              description = categoryDescription.getText().toString();
                                              CategoryModel category = new CategoryModel(name, description, assign_clinic, "");
                                              uploadCategory(category);
                                          }
                                      }


        );


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //phoneNumberEditText.setText("");
                deleteData(name);
            }
        });

        return binding.getRoot();

    }

    private void deleteData(String phoneNumber) {
        db.collection("categories")
                .whereEqualTo("name", name)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String docId = documentSnapshot.getId();
                            db.collection(("categories"))
                                    .document(docId)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            new CustomToast(getContext(), getActivity(),
                                                    name + " Deleted Successfully !", ToastStatus.SUCCESS).show();
                                            // navigate to add clinic screen
                                            categoryName.setText("");
                                            categoryDescription.setText("");

                                            saveButton.setVisibility(View.VISIBLE);
                                            editButton.setVisibility(View.GONE);
                                            deleteButton.setVisibility(View.GONE);
                                            category_name_error.setVisibility(View.GONE);


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            new CustomToast(getContext(), getActivity(),
                                                    name + " Failed to delete !", ToastStatus.SUCCESS).show();
                                        }
                                    });
                        }
                    }
                });
    }

    private void checkCategoryData() {
        name = categoryName.getText().toString();
        if (!(name.isEmpty())) {
            saveButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary_color)));
            saveButton.setEnabled(true);
        }
    }

    public void uploadCategory(CategoryModel category) {
        db.collection("categories").add(category).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AddCategoryFragment_to_CategoryListFragment);
                        new CustomToast(getContext(), getActivity(),
                                name + " Stored Successfully !", ToastStatus.SUCCESS).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        new CustomToast(getContext(), getActivity(), "Error - ", ToastStatus.FAILURE).show();
                    }
                });


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ((CategoryActivity) getActivity()).setActionBarTitle("Add Category");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void checkForEmpty() {
        checkCategoryData();
    }
}