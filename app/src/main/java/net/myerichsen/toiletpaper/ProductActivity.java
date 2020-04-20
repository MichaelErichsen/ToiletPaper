/*
 * Copyright (c) 2020. Michael Erichsen.
 */

package net.myerichsen.toiletpaper;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.myerichsen.toiletpaper.ui.products.ProductModel;

import java.util.Objects;

/**
 * Display product details
 */
public class ProductActivity extends AppCompatActivity {
    private final TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private Context context;

    // TODO Handle delete button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        context = getApplicationContext();

        TPDbAdapter helper = new TPDbAdapter(context);

        final TableLayout tableLayout = findViewById(R.id.productDetailTableLayout);
        TableRow tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2);
        tableRow.addView(addCell("Tekst"));
        tableRow.addView(addCell("Værdi"));
        tableLayout.addView(tableRow);

        if (!getIntent().hasExtra("net.myrichsen.toiletpaper.UID")) {
            // No data
            return;
        }

        String uid = Objects.requireNonNull(getIntent().getExtras()).getString("net.myrichsen.toiletpaper.UID");

        ProductModel pd = helper.getProductDataByUid(uid);

        addTableRow(tableLayout, "Uid", pd.getUid());
        addTableRow(tableLayout, "Item no", pd.getItemNo());
        addTableRow(tableLayout, "Brand", pd.getBrand());
        addTableRow(tableLayout, "Layers", pd.getLayers());
        addTableRow(tableLayout, "Package rolls", pd.getPackageRolls());
        addTableRow(tableLayout, "Roll sheets", pd.getRollSheets());
        addTableRow(tableLayout, "Sheet width", pd.getSheetWidth());
        addTableRow(tableLayout, "Sheet length", pd.getSheetLength());
        addTableRow(tableLayout, "Roll Length", pd.getRollLength());
        addTableRow(tableLayout, "Package price", pd.getPackagePrice());
        addTableRow(tableLayout, "Roll price", pd.getRollPrice());
        addTableRow(tableLayout, "Paper weight", pd.getPaperWeight());
        addTableRow(tableLayout, "Kilo price", pd.getKiloPrice());
        addTableRow(tableLayout, "Meter price", pd.getMeterPrice());
        addTableRow(tableLayout, "Sheet price", pd.getSheetPrice());
        addTableRow(tableLayout, "Supplier", pd.getSupplier());
        addTableRow(tableLayout, "Comments", pd.getComments());
        addTableRow(tableLayout, "Timestamp", pd.getTimestamp());
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
