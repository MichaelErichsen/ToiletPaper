package net.myerichsen.toiletpaper.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.R;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {
    private static Toolbar myToolbar;
    private static Spinner layersSpinner;
    private static EditText rollLengthEditText;
    private static EditText sheetLengthEditText;
    private static CheckBox sheetLengthCheckBox;
    private static EditText rollSheetsEditText;
    private static CheckBox rollLengthCheckBox;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Scrollable fields
        layersSpinner = root.findViewById(R.id.layersSpinner);
        layersSpinner.setPrompt("Indtast");
        ArrayList<String> layerArrayList = new ArrayList<>(Arrays.asList("2",
                "3",
                "4",
                "5"));
        ArrayAdapter layerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, layerArrayList);
        layerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        layersSpinner.setAdapter(layerArrayAdapter);
        layersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, layersSpinner.getSelectedItem().toString() + " valgt", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        rollSheetsEditText = root.findViewById(R.id.rollSheetsEditText);
        sheetLengthEditText = root.findViewById(R.id.sheetLengthEditText);
        sheetLengthCheckBox = root.findViewById(R.id.sheetLengthCheckBox);
        rollLengthEditText = root.findViewById(R.id.rollLengthEditText);
        rollLengthCheckBox = root.findViewById(R.id.rollLengthCheckBox);
        rollLengthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rollLengthCheckBox.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return root;
    }

}