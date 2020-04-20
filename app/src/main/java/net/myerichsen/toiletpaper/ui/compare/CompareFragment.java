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
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.CompareActivity;
import net.myerichsen.toiletpaper.R;

import java.util.ArrayList;

public class CompareFragment extends Fragment {
    private Context context;
    private String sortKey;
    private String sortFilter = "ALL";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.compare_fragment, container, false);
        context = getContext();

        Spinner filterSpinner = root.findViewById(R.id.filterSpinner);
        ArrayList<String> layerArrayList = new ArrayList<>();
        // TODO Get from table
        layerArrayList.add("ALL");
        layerArrayList.add("Rema Vejby");
        layerArrayList.add("Spar Vejby Strand");
        layerArrayList.add("Kvickly Helsinge");
        ArrayAdapter<String> layerArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, layerArrayList);
        layerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(layerArrayAdapter);
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortFilter = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sortKey = "PAPER_WEIGHT";

        RadioGroup rg = root.findViewById(R.id.sortKeyRadioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1:
                        sortKey = "PAPER_WEIGHT";
                        break;
                    case R.id.radioButton2:
                        sortKey = "KILO_PRICE";
                        break;
                    case R.id.radioButton3:
                        sortKey = "METER_PRICE";
                        break;
                    case R.id.radioButton4:
                        sortKey = "SHEET_PRICE";
                        break;
                    default:
                        Snackbar snackbar = Snackbar
                                .make(getActivity().findViewById(android.R.id.content), "Unexpected value: " + checkedId, Snackbar.LENGTH_LONG);
                        snackbar.show();
                        throw new IllegalStateException("Unexpected value: " + checkedId);
                }
            }
        });

        View compareBtn = root.findViewById(R.id.compareBtn);
        compareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compareIntent = new Intent(context, CompareActivity.class);
                compareIntent.putExtra("net.myerichsen.toiletpaper.SORT_FILTER", sortFilter);
                compareIntent.putExtra("net.myerichsen.toiletpaper.SORT_KEY", sortKey);
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
