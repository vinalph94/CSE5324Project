package com.example.mediassist.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.mediassist.databinding.CategoryListBinding;

import java.util.ArrayList;

public class CategoryListFragment extends Fragment {

    private CategoryListBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = CategoryListBinding.inflate(inflater, container, false);






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