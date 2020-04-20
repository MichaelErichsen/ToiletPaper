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
        TPDbAdapter helper = new TPDbAdapter(context);

        ImageButton supplierDetailAddBtn = findViewById(R.id.supplierDetailAddBtn);
        supplierDetailAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Insert
            }
        });
        ImageButton supplierDetailDeleteBtn = findViewById(R.id.supplierDetailDeleteBtn);
        supplierDetailDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TDDO Delete
            }
        });
        EditText supplierDetailSupplierEditText = findViewById(R.id.supplierDetailSupplierEditText);
        EditText supplierDetailChainEditText = findViewById(R.id.supplierDetailChainEditText);
        TextView supplierDetailTimestampTextView = findViewById(R.id.supplierDetailTimestampTextView);

        // Get supplier from Intent
        if (!getIntent().hasExtra("net.myrichsen.toiletpaper.SUPPLIER")) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "No intent extra data", Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }
        String supplier = Objects.requireNonNull(getIntent().getExtras()).getString("net.myrichsen.toiletpaper.SUPPLIER");

        SupplierModel sm = helper.getSupplierDataBySupplier(supplier);

        supplierDetailSupplierEditText.setText(sm.getSupplier());
        supplierDetailChainEditText.setText(sm.getChain());
        supplierDetailTimestampTextView.setText(sm.getTimestamp());
    }
}