package com.example.mediassist.category;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mediassist.R;
import com.example.mediassist.dashboard.DashboardActivity;
import com.example.mediassist.databinding.AddCategoryBinding;
import com.example.mediassist.login.LoginActivity;

public class AddCategoryFragment extends Fragment {

    private AddCategoryBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AddCategoryBinding.inflate(inflater, container, false);
        binding.categorySaveButton.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {

                                                            if (TextUtils.isEmpty(binding.categoryNameText.getText())) {
                                                                binding.categoryNameErrorText.setVisibility(View.VISIBLE);
                                                            }
                                                        }
                                                    }


        );


        return binding.getRoot();

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

}