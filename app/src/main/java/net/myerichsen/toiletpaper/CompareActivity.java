/*
 * Copyright (c) 2020. Michael Erichsen.
 */

package net.myerichsen.toiletpaper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.ui.products.ProductModel;

import java.util.List;
import java.util.Objects;

public class CompareActivity extends AppCompatActivity {
    private final TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);


        context = getApplicationContext();
        TPDbAdapter helper = new TPDbAdapter(context);

        String sortKey = Objects.requireNonNull(getIntent().getExtras()).getString("net.myerichsen.toiletpaper.SORT_KEY");
        String sortFilter = getIntent().getExtras().getString("net.myerichsen.toiletpaper.SORT_FILTER");

        final TableLayout tableLayout = findViewById(R.id.compareTableLayout);
        TableRow tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2);
        tableRow.addView(addCell(sortKey));
        tableRow.addView(addCell(getString(R.string.brand)));
        tableRow.addView(addCell(getString(R.string.item_no)));
        tableRow.addView(addCell("Uid"));
        tableLayout.addView(tableRow);

        List<ProductModel> lpd;

        try {
            lpd = helper.getProductModelsSorted(sortKey, sortFilter);
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        if (lpd.size() == 0) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), R.string.no_products_found, Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        for (int i = 0; i < lpd.size(); i++) {
            ProductModel pd = lpd.get(i);

            tableRow = new TableRow(context);
            tableRow.setBackgroundColor(Color.BLACK);
            tableRow.setPadding(2, 2, 2, 2); //Border between rows

            switch (Objects.requireNonNull(sortKey)) {
                case "PAPER_WEIGHT":
                    tableRow.addView(addCell(Float.toString(pd.getPaperWeight())));
                    break;
                case "KILO_PRICE":
                    tableRow.addView(addCell(Float.toString(pd.getKiloPrice())));
                    break;
                case "METER_PRICE":
                    tableRow.addView(addCell(Float.toString(pd.getMeterPrice())));
                    break;
                case "SHEET_PRICE":
                    tableRow.addView(addCell(Float.toString(pd.getSheetPrice())));
                    break;
                default:
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content),
                                    getString(R.string.invalid_sort_key) + sortKey, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    break;
            }

            tableRow.addView(addCell(pd.getBrand()));
            tableRow.addView(addCell(pd.getItemNo()));
            tableRow.addView(addCell(pd.getUid()));
            tableRow.setClickable(true);
            tableRow.setOnClickListener(tableRowOnclickListener());
            tableLayout.addView(tableRow);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener tableRowOnclickListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    TableRow selectedRow = (TableRow) v;
                    LinearLayout ll = (LinearLayout) selectedRow.getChildAt(3);
                    TextView tv = (TextView) ll.getChildAt(0);
                    int uid = Integer.parseInt(tv.getText().toString());
                    Intent productIntent = new Intent(context, ProductActivity.class);
                    productIntent.putExtra("net.myrichsen.toiletpaper.UID", String.valueOf(uid));
                    startActivity(productIntent);
                } catch (NumberFormatException e) {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        };
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

}