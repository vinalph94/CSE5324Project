package com.example.mediassist.category;

import static com.example.mediassist.util.ToastStatus.SUCCESS;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mediassist.R;
import com.example.mediassist.category.models.CategoryModel;
import com.example.mediassist.category.models.IconModel;
import com.example.mediassist.clinic.models.ClinicModel;
import com.example.mediassist.databinding.AddCategoryBinding;
import com.example.mediassist.util.CheckForEmptyCallBack;
import com.example.mediassist.util.CustomTextWatcher;
import com.example.mediassist.util.CustomToast;
import com.example.mediassist.util.ToastStatus;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


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
    private Bundle bundle;
    private String id;
    private CategoryModel category;
    private ArrayAdapter<ClinicModel> clinicSpinnerAdapter;
    private ArrayAdapter<IconModel> iconSpinnerAdapter;
    private ArrayList<ClinicModel> clinicsList;
    private ArrayList<IconModel> iconsList;
    private ClinicModel clinic;
    private IconModel icon;
    private String clinic_id;
    private Spinner clinicSpinner;
    private Spinner iconSpinner;
    private String icon_id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        binding = AddCategoryBinding.inflate(inflater, container, false);
        bundle = getArguments();
        category = (CategoryModel) (bundle != null ? bundle.getSerializable("category") : null);

        clinicSpinner = (Spinner) binding.spinnerClinic;
        iconSpinner = (Spinner) binding.spinnerIcon;


        categoryName = binding.categoryNameText;
        categoryDescription = binding.categoryDetailsText;
        category_name_error = binding.categoryNameErrorText;
        saveButton = binding.categorySaveButton;
        editButton = binding.categoryEditButton;
        deleteButton = binding.categoryDeleteButton;

        ((CategoryActivity) getActivity()).btnAdd.setVisibility(View.GONE);


        clinicsList = new ArrayList<ClinicModel>();
        iconsList = new ArrayList<IconModel>();
        db.collection("icons").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for (QueryDocumentSnapshot snapshot : value) {

                    String name = snapshot.getString("name");

                    icon = new IconModel(name);
                    icon.setId(snapshot.getId());
                    iconsList.add(icon);

                }
                iconSpinnerAdapter = new ArrayAdapter<IconModel>(getContext(), android.R.layout.simple_spinner_dropdown_item, iconsList);
                iconSpinner.setAdapter(iconSpinnerAdapter);
                getCategoryIconForEdit(iconSpinnerAdapter);

            }
        });
        db.collection("clinics").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for (QueryDocumentSnapshot snapshot : value) {
                    String details = "";
                    String name = snapshot.getString("name");
                    String phoneNumber = snapshot.getString("phone_number");
                    if (snapshot.getString("description") != null) {
                        details = snapshot.getString("description");
                    }
                    String address = snapshot.getString("address");
                    int zipcode = snapshot.getLong("zipcode").intValue();
                    clinic = new ClinicModel(name, phoneNumber, address, details, zipcode);
                    clinic.setId(snapshot.getId());
                    clinicsList.add(clinic);

                }
                clinicSpinnerAdapter = new ArrayAdapter<ClinicModel>(getContext(), android.R.layout.simple_spinner_dropdown_item, clinicsList);
                clinicSpinner.setAdapter(clinicSpinnerAdapter);
                getCategoryClinicForEdit(clinicSpinnerAdapter);

            }
        });


        if (category != null) {
            id = category.getId();
            categoryName.setText(category.getName());
            if (category.getDescription() != null) {
                categoryDescription.setText(category.getDescription());
            }
            saveButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        }
        categoryName.addTextChangedListener(new CustomTextWatcher(category_name_error, AddCategoryFragment.this));
        checkCategoryData();

        iconSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                icon_id = iconsList.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        clinicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                clinic_id = clinicsList.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              description = categoryDescription.getText().toString();
                                              CategoryModel category = new CategoryModel(name, description, icon_id, clinic_id);
                                              uploadCategory(category);
                                          }
                                      }


        );


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //phoneNumberEditText.setText("");
                deleteData(id);
            }
        });

        return binding.getRoot();

    }


    private void getCategoryIconForEdit(ArrayAdapter<IconModel> iconSpinnerAdapter) {

        for (int position = 0; position < iconSpinnerAdapter.getCount(); position++) {
            if (iconSpinner.getItemAtPosition(position).equals(category.getIconId())) {
                iconSpinner.setSelection(position);
            }
        }


    }

    private void getCategoryClinicForEdit(ArrayAdapter<ClinicModel> clinicSpinnerAdapter) {

        for (int position = 0; position < clinicSpinnerAdapter.getCount(); position++) {
            if (clinicSpinner.getItemAtPosition(position).equals(category.getClinicId())) {
                clinicSpinner.setSelection(position);
            }
        }


    }

    private void deleteData(String categoryId) {
        db.collection(("categories")).document(categoryId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AddCategoryFragment_to_CategoryActivity);
                new CustomToast(getContext(), getActivity(), name + " Deleted Successfully", ToastStatus.DELETE).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new CustomToast(getContext(), getActivity(), " Failed to delete " + name, ToastStatus.FAILURE).show();
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
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_AddCategoryFragment_to_CategoryActivity);
                new CustomToast(getContext(), getActivity(), name + " Stored Successfully !", SUCCESS).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new CustomToast(getContext(), getActivity(), "Error - ", ToastStatus.FAILURE).show();
            }
        });


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (category == null) {
            ((CategoryActivity) getActivity()).setActionBarTitle("Add Category");
        } else {
            ((CategoryActivity) getActivity()).setActionBarTitle("Edit Category");
        }

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