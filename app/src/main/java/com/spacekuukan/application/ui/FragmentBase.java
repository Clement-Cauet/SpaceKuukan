package com.spacekuukan.application.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.spacekuukan.application.databinding.FragmentBaseBinding;
import com.spacekuukan.application.function.Base;

public class FragmentBase extends Fragment {

    private Base base;

    private FragmentBaseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBaseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //base.getInstance();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}