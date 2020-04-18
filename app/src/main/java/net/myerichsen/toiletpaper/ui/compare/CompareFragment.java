/*
 * Copyright (c) 2020. Michael Erichsen.
 */

package net.myerichsen.toiletpaper.ui.compare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.myerichsen.toiletpaper.CompareActivity;
import net.myerichsen.toiletpaper.R;

import java.util.ArrayList;

public class CompareFragment extends Fragment {
    private View root;
    private Spinner filterSpinner;
    private Context context;
    private View compareBtn;

    // TODO Radio button for sort column
    // TODO Filter spinner for supplier and "All"
    public static CompareFragment newInstance() {
        return new CompareFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.compare_fragment, container, false);
        context = getContext();

        filterSpinner = root.findViewById(R.id.filterSpinner);
        ArrayList<String> layerArrayList = new ArrayList<>();
        // TODO Get from table
        layerArrayList.add("ALL");
        layerArrayList.add("Bilka");
        layerArrayList.add("Føtex");
        layerArrayList.add("Kvickly");
        ArrayAdapter<String> layerArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, layerArrayList);
        layerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(layerArrayAdapter);
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        compareBtn = root.findViewById(R.id.compareBtn);
        compareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compareIntent = new Intent(context, CompareActivity.class);
                // TODO Pass filter and sort key
                //              startIntent.putExtra("net.myrichsen.mysecondapplication.SOMETHING", "HELLO, WORLD");
                startActivity(compareIntent);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
