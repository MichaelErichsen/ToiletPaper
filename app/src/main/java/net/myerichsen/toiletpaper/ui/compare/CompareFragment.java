package net.myerichsen.toiletpaper.ui.compare;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.TPDbAdapter;
import net.myerichsen.toiletpaper.ui.suppliers.SupplierModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Copyright (c) 2020. Michael Erichsen. The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

public class CompareFragment extends Fragment {
    private String sortKey = "PAPER_WEIGHT";
    private String sortFilter = "ALL";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_compare, container, false);
        Context context = getContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(context));
        float fontSize = Float.parseFloat(preferences.getString("fontsize", "24"));
        View snackView = requireActivity().findViewById(android.R.id.content);

        TPDbAdapter adapter = new TPDbAdapter(context);

        ActionBar ab = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        ((RadioButton) root.findViewById(R.id.radioButton1)).setTextSize(fontSize);
        ((RadioButton) root.findViewById(R.id.radioButton2)).setTextSize(fontSize);
        ((RadioButton) root.findViewById(R.id.radioButton3)).setTextSize(fontSize);
        ((RadioButton) root.findViewById(R.id.radioButton4)).setTextSize(fontSize);
        ((TextView) root.findViewById(R.id.executeCompareTextView)).setTextSize(fontSize);
        ((TextView) root.findViewById(R.id.textView8)).setTextSize(fontSize);
        ((TextView) root.findViewById(R.id.textView9)).setTextSize(fontSize);

        Spinner filterSpinner = root.findViewById(R.id.filterSpinner);

        List<SupplierModel> lsd = new ArrayList<>();
        ArrayList<String> supplierArrayList = new ArrayList<>();
        supplierArrayList.add("ALL");

        boolean goOn = true;

        try {
            lsd = adapter.getSupplierModels();
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
            snackbar.show();
            goOn = false;
        }

        if ((goOn) && (lsd.size() == 0)) {
            Snackbar snackbar = Snackbar
                    .make(snackView, "Tabellen er tom", Snackbar.LENGTH_LONG);
            snackbar.show();
            goOn = false;
        }

        if (goOn) {
            for (int i = 0; i < lsd.size(); i++) {
                supplierArrayList.add(lsd.get(i).getSupplier());
            }
        }

        ArrayAdapter<String> supplierArrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(context), android.R.layout.simple_spinner_item, supplierArrayList);
        supplierArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(supplierArrayAdapter);
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortFilter = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
                                .make(requireActivity().findViewById(android.R.id.content), "Unexpected value: " + checkedId, Snackbar.LENGTH_LONG);
                        snackbar.show();
                        throw new IllegalStateException("Unexpected value: " + checkedId);
                }
            }
        });

        TextView executeCompareTextView = root.findViewById(R.id.executeCompareTextView);

        executeCompareTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CompareFragmentDirections.ActionNavCompareToNavCompareDetails action =
                            CompareFragmentDirections.actionNavCompareToNavCompareDetails(sortKey, sortFilter);
                    Navigation.findNavController(v).navigate(action);
                } catch (Exception e) {
                    Snackbar snackbar = Snackbar
                            .make(requireActivity().findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        return root;
    }
}
