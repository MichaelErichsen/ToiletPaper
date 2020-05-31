/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

package net.myerichsen.toiletpaper.ui.suppliers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.TPDbAdapter;

import java.util.Objects;

/**
 * Display and maintain supplier details
 */
public class SupplierDetailsFragment extends Fragment {
    private static final String SUPPLIER = "supplier";
    private View root;
    private String supplier;
    private View snackView;
    private TPDbAdapter adapter;
    private EditText supplierDetailSupplierEditText;
    private EditText supplierDetailChainEditText;
    private TextView supplierDetailTimestampTextView;

    public SupplierDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment
     */
    @SuppressWarnings("unused")
    public static SupplierDetailsFragment newInstance() {
        return new SupplierDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_supplier_details, container, false);

        return root;
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getContext();
        snackView = requireActivity().findViewById(android.R.id.content);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(context));
        float fontSize = Float.parseFloat(preferences.getString("fontsize", "24"));
        adapter = new TPDbAdapter(context);

        supplierDetailSupplierEditText = root.findViewById(R.id.supplierDetailSupplierEditText);
        supplierDetailSupplierEditText.setTextSize(fontSize);
        supplierDetailChainEditText = root.findViewById(R.id.supplierDetailChainEditText);
        supplierDetailChainEditText.setTextSize(fontSize);
        supplierDetailTimestampTextView = root.findViewById(R.id.supplierDetailTimestampTextView);
        supplierDetailTimestampTextView.setTextSize(fontSize);

        ImageButton supplierDetailAddBtn = root.findViewById(R.id.supplierDetailAddBtn);
        supplierDetailAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupplierModel sm = new SupplierModel();
                sm.setSupplier(supplierDetailSupplierEditText.getText().toString());
                sm.setChain(supplierDetailChainEditText.getText().toString());
                adapter.insertData(sm);
                Snackbar.make(snackView, R.string.supplier_added, Snackbar.LENGTH_LONG).show();
            }
        });
        ImageButton supplierDetailDeleteBtn = root.findViewById(R.id.supplierDetailDeleteBtn);
        supplierDetailDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    adapter.deleteSupplier(supplierDetailSupplierEditText.getText().toString());
                    Snackbar.make(snackView, R.string.supplier_deleted, Snackbar.LENGTH_LONG).show();
                    supplierDetailSupplierEditText.setText("");
                    supplierDetailChainEditText.setText("");
                    supplierDetailTimestampTextView.setText("");
                } catch (Exception e) {
                    Snackbar.make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        if (getArguments() != null) {
            supplier = getArguments().getString(SUPPLIER);
        }

        try {
            SupplierModel sm = adapter.getSupplierModels("SUPPLIER=?", supplier).get(0);
            supplierDetailSupplierEditText.setText(sm.getSupplier());
            supplierDetailChainEditText.setText(sm.getChain());
            supplierDetailTimestampTextView.setText(sm.getTimestamp());
        } catch (Exception e) {
            Snackbar.make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG).show();
        }
    }
}
