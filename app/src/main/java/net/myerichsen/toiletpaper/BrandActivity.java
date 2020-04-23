/*
 * Copyright (c) 2020. Michael Erichsen.
 */

package net.myerichsen.toiletpaper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.ui.products.ProductModel;

import java.util.List;
import java.util.Objects;

public class BrandActivity extends AppCompatActivity {
    private final TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private Context context;
    private String brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        Context context = getApplicationContext();
        final TPDbAdapter adapter = new TPDbAdapter(context);

        if (!getIntent().hasExtra("net.myerichsen.toiletpaper.BRAND")) {
            Intent intent = new Intent();
            intent.putExtra("net.myerichsen.toiletpaper.BRAND", "Intet varemærke modtaget");
            setResult(RESULT_CANCELED, intent);
            finish();
            return;
        }

        brand = Objects.requireNonNull(getIntent().getExtras()).getString("net.myerichsen.toiletpaper.BRAND");
        List<ProductModel> lpm = null;

        try {
            if (!brand.endsWith("%")) {
                brand += "%";
            }
            lpm = adapter.getProductData("BRAND LIKE ?", brand);
        } catch (Exception e) {
            Intent intent = new Intent();
            intent.putExtra("net.myerichsen.toiletpaper.BRAND", (e.getMessage()));
            setResult(RESULT_CANCELED, intent);
            finish();
            return;
        }

        // Not found
        if ((lpm == null) || (lpm.size()) == 0) {
            Intent intent = new Intent();
            intent.putExtra("net.myerichsen.toiletpaper.BRAND", "Varemærke " + brand + " findes ikke");
            setResult(RESULT_CANCELED, intent);
            finish();
            return;
        }

        // Only one found
        if (lpm.size() == 1) {
            brand = lpm.get(0).getBrand();
            Intent intent = new Intent();
            intent.putExtra("net.myerichsen.toiletpaper.BRAND", brand);
            setResult(RESULT_OK, intent);
            finish();
            return;
        }

        final TableLayout brandTableLayout = findViewById(R.id.brandTableLayout);
//        TableRow tableRow = new TableRow(context);
//        tableRow.setBackgroundColor(Color.BLACK);
//        tableRow.setPadding(2, 2, 2, 2);
//        tableRow.addView(addCell(context, getString(R.string.brand)));
//        brandTableLayout.addView(tableRow);

        // More than one found
        for (int i = 0; i < lpm.size(); i++) {
            try {
                addTableRow(brandTableLayout, lpm.get(i).getBrand());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addTableRow(TableLayout tableLayout, String brand) {
        TableRow tableRow;
        tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2); //Border between rows
        tableRow.addView(addCell(context, brand));
        tableRow.setClickable(true);
        tableRow.setOnClickListener(tableRowOnclickListener());
        tableLayout.addView(tableRow);
    }

    private View.OnClickListener tableRowOnclickListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    TableRow selectedRow = (TableRow) v;
                    LinearLayout ll = (LinearLayout) selectedRow.getChildAt(0);
                    TextView tv = (TextView) ll.getChildAt(0);
                    brand = tv.getText().toString();
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), brand + " was clicked", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } catch (NumberFormatException e) {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        };
    }

    private LinearLayout addCell(Context context, String cellData) {
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("net.myerichsen.toiletpaper.BRAND", "?");
        setResult(RESULT_OK, intent);
        finish();
    }
}
