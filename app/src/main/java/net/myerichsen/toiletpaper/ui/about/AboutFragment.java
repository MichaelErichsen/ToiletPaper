/*
 * Copyright (c) 2020. Michael Erichsen.
 */

package net.myerichsen.toiletpaper.ui.about;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.database.ProductDbAdapter;
import net.myerichsen.toiletpaper.database.SupplierDbAdapter;
import net.myerichsen.toiletpaper.ui.products.ProductData;
import net.myerichsen.toiletpaper.ui.suppliers.SupplierData;

/**
 * About fragment.
 * Also includes an inital load function
 */
public class AboutFragment extends Fragment {
    private View root;

    /**
     * Required empty public constructor
     */
    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_about, container, false);

        AppCompatImageButton testBtn = root.findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadInitialData();
            }
        });
        return root;
    }

    /**
     * Load initial data into tables
     */
    private void loadInitialData() {
        Snackbar snackbar;
        Context context = getContext();


        float rl = (float) 29.1;
        float rp = (float) 5.125;
        float kp = (float) 31.64;
        float mp = (float) 0.1761;
        float sp = (float) 0.022;
        ProductData pd = new ProductData("5700384289095", "Irma Tusindfryd Toiletpapir",
                3, 8, 233, 97, 125, 0, rl,
                0, 41, 0, rp, 1, 48,
                0, kp, 0, mp, 1, sp, 1,
                "Coop (Kvickly/Brugsen/Fakta/Irma)", "Sælges hos Irma og Brugsen");

        try {
            ProductDbAdapter pHelper = new ProductDbAdapter(context);
            pHelper.insertData(pd);
        } catch (Exception e) {
            snackbar = Snackbar
                    .make(getActivity().findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        SupplierData sd;
        try {
            SupplierDbAdapter sHelper = new SupplierDbAdapter(context);

            sd = new SupplierData("Aldi", "www.aldi.dk");
            sHelper.insertData(sd);
            sd = new SupplierData("Coop (Kvickly/Brugsen/Fakta/Irma)", "www.coop.dk");
            sHelper.insertData(sd);
            sd = new SupplierData("Dagrofa (Meny/Min Købmand/Let-Køb/Spar", "www.dagrofa.dk");
            sHelper.insertData(sd);
            sd = new SupplierData("Lidl", "www.lidl.dk");
            sHelper.insertData(sd);
            sd = new SupplierData("REMA 1000", "www.rema1000.dk");
            sHelper.insertData(sd);
            sd = new SupplierData("Salling (Bilka/Føtex/Netto)", "www.sallinggroup.com");
            sHelper.insertData(sd);
        } catch (Exception e) {
            snackbar = Snackbar
                    .make(getActivity().findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        snackbar = Snackbar
                .make(getActivity().findViewById(android.R.id.content), "Initielle data er loadet", Snackbar.LENGTH_LONG);
        snackbar.show();


    }
}
