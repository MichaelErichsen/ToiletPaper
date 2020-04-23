/*
 * Copyright (c) 2020. Michael Erichsen.
 */

package net.myerichsen.toiletpaper;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.ui.suppliers.SupplierModel;

import java.util.Objects;

public class SupplierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);
        Context context = getApplicationContext();
        final TPDbAdapter helper = new TPDbAdapter(context);

        final EditText supplierDetailSupplierEditText = findViewById(R.id.supplierDetailSupplierEditText);
        final EditText supplierDetailChainEditText = findViewById(R.id.supplierDetailChainEditText);
        final TextView supplierDetailTimestampTextView = findViewById(R.id.supplierDetailTimestampTextView);
        ImageButton supplierDetailAddBtn = findViewById(R.id.supplierDetailAddBtn);
        supplierDetailAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupplierModel sm = new SupplierModel();
                sm.setSupplier(supplierDetailSupplierEditText.getText().toString());
                sm.setChain(supplierDetailChainEditText.getText().toString());
                helper.insertData(sm);
                Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), R.string.supplier_added, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        ImageButton supplierDetailDeleteBtn = findViewById(R.id.supplierDetailDeleteBtn);
        supplierDetailDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    helper.deleteSupplier(supplierDetailSupplierEditText.getText().toString());
                } catch (Exception e) {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        // Get supplier from Intent
        if (!getIntent().hasExtra("net.myrichsen.toiletpaper.SUPPLIER")) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "No intent extra data", Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        try {
            String supplier = Objects.requireNonNull(getIntent().getExtras()).getString("net.myrichsen.toiletpaper.SUPPLIER");
            SupplierModel sm = helper.getSupplierModelBySupplier(supplier);
            supplierDetailSupplierEditText.setText(sm.getSupplier());
            supplierDetailChainEditText.setText(sm.getChain());
            supplierDetailTimestampTextView.setText(sm.getTimestamp());
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
}