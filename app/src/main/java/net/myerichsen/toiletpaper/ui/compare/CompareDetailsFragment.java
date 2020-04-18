/*
 * Copyright (c) 2020. Michael Erichsen.
 */

package net.myerichsen.toiletpaper.ui.compare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import net.myerichsen.toiletpaper.R;

// TODO Table with product data sorted according to sort and filter
public class CompareDetailsFragment extends Fragment {

    private CompareDetailsViewModel mViewModel;

    public static CompareDetailsFragment newInstance() {
        return new CompareDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.compare_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CompareDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

}
