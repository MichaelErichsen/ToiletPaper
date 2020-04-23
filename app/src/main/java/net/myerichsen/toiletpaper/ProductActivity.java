/*
 * Copyright (c) 2020. Michael Erichsen.
 */

package net.myerichsen.toiletpaper;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.ui.products.ProductModel;

import java.util.List;
import java.util.Objects;

/**
 * Display product details
 */
public class ProductActivity extends AppCompatActivity {
    private final TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private Context context;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        context = getApplicationContext();
        final TPDbAdapter adapter = new TPDbAdapter(context);

        final TableLayout productDetailTableLayout = findViewById(R.id.productDetailTableLayout);
        TableRow tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2);
        tableRow.addView(addCell("Tekst"));
        tableRow.addView(addCell("Værdi"));
        productDetailTableLayout.addView(tableRow);

        if (!getIntent().hasExtra("net.myrichsen.toiletpaper.UID")) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "No intent extra data", Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        uid = Objects.requireNonNull(getIntent().getExtras()).getString("net.myrichsen.toiletpaper.UID");
        List<ProductModel> lpm = null;

        try {
            lpm = adapter.getProductData("UID=?", uid);
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        if (lpm == null) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Produkt nr. " + uid + " findes ikke", Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        ProductModel pm = lpm.get(0);
        addTableRow(productDetailTableLayout, "Uid", pm.getUid());
        addTableRow(productDetailTableLayout, "Item no", pm.getItemNo());
        addTableRow(productDetailTableLayout, "Brand", pm.getBrand());
        addTableRow(productDetailTableLayout, "Layers", pm.getLayers());
        addTableRow(productDetailTableLayout, "Package rolls", pm.getPackageRolls());
        addTableRow(productDetailTableLayout, "Roll sheets", pm.getRollSheets());
        addTableRow(productDetailTableLayout, "Sheet width", pm.getSheetWidth());
        addTableRow(productDetailTableLayout, "Sheet length", pm.getSheetLength());
        addTableRow(productDetailTableLayout, "Roll Length", pm.getRollLength());
        addTableRow(productDetailTableLayout, "Package price", pm.getPackagePrice());
        addTableRow(productDetailTableLayout, "Roll price", pm.getRollPrice());
        addTableRow(productDetailTableLayout, "Paper weight", pm.getPaperWeight());
        addTableRow(productDetailTableLayout, "Kilo price", pm.getKiloPrice());
        addTableRow(productDetailTableLayout, "Meter price", pm.getMeterPrice());
        addTableRow(productDetailTableLayout, "Sheet price", pm.getSheetPrice());
        addTableRow(productDetailTableLayout, "Supplier", pm.getSupplier());
        addTableRow(productDetailTableLayout, "Comments", pm.getComments());
        addTableRow(productDetailTableLayout, "Timestamp", pm.getTimestamp());

        ImageButton productDeleteBtn = findViewById(R.id.productDeleteBtn);
        productDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    adapter.deleteProduct(Integer.parseInt((uid)));
                } catch (Exception e) {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

    }

    private void addTableRow(TableLayout tableLayout, String uid2, String uid3) {
        TableRow tableRow;
        tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2); //Border between rows
        tableRow.addView(addCell(uid2));
        tableRow.addView(addCell(uid3));
        tableLayout.addView(tableRow);
    }

    private void addTableRow(TableLayout tableLayout, String uid2, float uid3) {
        TableRow tableRow;
        tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2); //Border between rows
        tableRow.addView(addCell(uid2));
        tableRow.addView(addCell(uid3));
        tableLayout.addView(tableRow);
    }

    private void addTableRow(TableLayout tableLayout, String uid2, int uid3) {
        TableRow tableRow;
        tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2); //Border between rows
        tableRow.addView(addCell(uid2));
        tableRow.addView(addCell(uid3));
        tableLayout.addView(tableRow);
    }

    private LinearLayout addCell(String cellData) {
        llp.setMargins(2, 2, 2, 2);

        LinearLayout cell;
        cell = new LinearLayout(context);
        cell.setBackgroundColor(Color.WHITE);
        cell.setLayoutParams(llp);//2px border on the right for the cell

        TextView tv = new TextView(context);
        tv.setText(cellData);
        tv.setPadding(0, 0, 4, 3);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        cell.addView(tv);
        return cell;
    }

    private LinearLayout addCell(int cellData) {
        return addCell(String.valueOf(cellData));
    }

    private LinearLayout addCell(float cellData) {
        return addCell(String.valueOf(cellData));
    }

}
