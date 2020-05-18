package net.myerichsen.toiletpaper.ui.prices;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import net.myerichsen.toiletpaper.R;

import java.util.Objects;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PriceSelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PriceSelectFragment extends Fragment {
    private TextInputEditText pItemNoEditText;
    private TextInputEditText pBrandEditText;
    private Snackbar snackbar;
    private View snackView;

    public PriceSelectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static PriceSelectFragment newInstance() {
        PriceSelectFragment fragment = new PriceSelectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_price_select, container, false);
        snackView = requireActivity().findViewById(android.R.id.content);

        pItemNoEditText = root.findViewById(R.id.pItemNoEditText);
        pBrandEditText = root.findViewById(R.id.pBrandEditText);
        AppCompatImageButton priceSelectionBtn = root.findViewById(R.id.priceSelectionBtn);

        priceSelectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String itemNo = Objects.requireNonNull(pItemNoEditText.getText()).toString();
                    String brand = Objects.requireNonNull(pBrandEditText.getText()).toString();

                    if (itemNo.equals("") && brand.equals("")) {
                        complain();
                    } else {
                        PriceSelectFragmentDirections.ActionNavPriceSelectToNavPricesList action =
                                PriceSelectFragmentDirections.actionNavPriceSelectToNavPricesList(Objects.requireNonNull(pItemNoEditText.getText()).toString(),
                                        Objects.requireNonNull(pBrandEditText.getText()).toString());
                        Navigation.findNavController(v).navigate(action);
                    }
                } catch (Exception e) {
                    snackbar = Snackbar
                            .make(requireActivity().findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        AppCompatImageButton graphSelectionBtn = root.findViewById(R.id.graphSelectionBtn);
        graphSelectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String itemNo = Objects.requireNonNull(pItemNoEditText.getText()).toString();
                    String brand = Objects.requireNonNull(pBrandEditText.getText()).toString();

                    if (itemNo.equals("") && brand.equals("")) {
                        complain();
                    } else {
                        PriceSelectFragmentDirections.ActionNavPriceSelectToNavPriceGraph action =
                                PriceSelectFragmentDirections.actionNavPriceSelectToNavPriceGraph((Objects.requireNonNull(pItemNoEditText.getText()).toString()),
                                        Objects.requireNonNull(pBrandEditText.getText()).toString());
                        Navigation.findNavController(v).navigate(action);
                    }
                } catch (Exception e) {
                    Snackbar snackbar = Snackbar
                            .make(requireActivity().findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        return root;
    }

    private void complain() {
        Activity activity = getActivity();

        if (Objects.requireNonNull(activity).getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
        snackbar = Snackbar
                .make(snackView, "Indtast varenummer eller varemærke", Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}