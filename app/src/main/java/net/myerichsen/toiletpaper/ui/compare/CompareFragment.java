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
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

public class CompareFragment extends Fragment {
    private String sortKey = "KILO_PRICE";
    private String sortFilter = "ALL";
    private View root;
    private Snackbar snackbar;
    private SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_compare, container, false);
        Context context = getContext();
        TPDbAdapter adapter = new TPDbAdapter(context);
        View snackView = requireActivity().findViewById(android.R.id.content);
        preferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(context));
        float fontSize = Float.parseFloat(preferences.getString("fontsize", "24"));
        sortKey = preferences.getString("sortkey", "KILO_PRICE");

        ((RadioButton) root.findViewById(R.id.radioButton1)).setTextSize(fontSize);
        ((RadioButton) root.findViewById(R.id.radioButton2)).setTextSize(fontSize);
        ((RadioButton) root.findViewById(R.id.radioButton3)).setTextSize(fontSize);
        ((RadioButton) root.findViewById(R.id.radioButton4)).setTextSize(fontSize);
        ((TextView) root.findViewById(R.id.executeCompareTextView)).setTextSize(fontSize);
        ((TextView) root.findViewById(R.id.textView8)).setTextSize(fontSize);
        ((TextView) root.findViewById(R.id.textView9)).setTextSize(fontSize);


        Spinner filterSpinner = root.findViewById(R.id.filterSpinner);

        List<SupplierModel> lsm = new ArrayList<>();
        ArrayList<String> supplierArrayList = new ArrayList<>();
        supplierArrayList.add("ALL");

        boolean goOn = true;

        try {
            lsm = adapter.getSupplierModels();
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
            snackbar.show();
            goOn = false;
        }

        if ((goOn) && (lsm.size() == 0)) {
            Snackbar snackbar = Snackbar
                    .make(snackView, "Tabellen er tom", Snackbar.LENGTH_LONG);
            snackbar.show();
            goOn = false;
        }

        if (goOn) {
            for (int i = 0; i < lsm.size(); i++) {
                supplierArrayList.add(lsm.get(i).getSupplier());
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
        rg.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener());

        switch (sortKey) {
            case "PAPER_WEIGHT":
                rg.check(R.id.radioButton1);
                break;
            case "KILO_PRICE":
                rg.check(R.id.radioButton2);
                break;
            case "METER_PRICE":
                rg.check(R.id.radioButton3);
                break;
            case "SHEET_PRICE":
                rg.check(R.id.radioButton4);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + sortKey);
        }

        TextView executeCompareTextView = root.findViewById(R.id.executeCompareTextView);

        executeCompareTextView.setOnClickListener(executeCompareOnClickListener());
        return root;
    }

    private View.OnClickListener executeCompareOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if ((sortKey.equals("KILO_PRICE")) || (sortKey.equals("METER_PRICE"))) {
                        CompareFragmentDirections.ActionNavCompareToNavCompareList action =
                                CompareFragmentDirections.actionNavCompareToNavCompareList(sortFilter, sortKey);
                        Navigation.findNavController(root).navigate(action);
                    } else {
                        CompareFragmentDirections.ActionNavCompareToNavCompareDetails action =
                                CompareFragmentDirections.actionNavCompareToNavCompareDetails(sortKey, sortFilter);
                        Navigation.findNavController(v).navigate(action);
                    }
                } catch (Exception e) {
                    Snackbar snackbar = Snackbar
                            .make(requireActivity().findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        };
    }

    private RadioGroup.OnCheckedChangeListener radioGroupOnCheckedChangeListener() {
        return new RadioGroup.OnCheckedChangeListener() {
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
                        throw new IllegalStateException("Unexpected value: " + checkedId);
                }

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("sortkey", sortKey);
                editor.apply();
            }
        };
    }
}
