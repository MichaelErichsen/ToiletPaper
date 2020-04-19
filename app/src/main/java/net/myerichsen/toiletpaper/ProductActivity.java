/*
 * Copyright (c) 2020. Michael Erichsen.
 */

package net.myerichsen.toiletpaper;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.myerichsen.toiletpaper.ui.products.ProductData;

/**
 * Display product details
 */
public class ProductActivity extends AppCompatActivity {
    private final TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private View root;
    private Context context;

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

        String uid = getIntent().getExtras().getString("net.myrichsen.toiletpaper.UID");

        ProductData pd = helper.getProductDataByUid(uid);

        /**
         *         pd.setUid(UID)));
         *         pd.setLayers(LAYERS)));
         *         pd.setPackageRolls(PACKAGE_ROLLS)));
         *         pd.setRollSheets(ROLL_SHEETS)));
         *         pd.setSheetWidth(SHEET_WIDTH)));
         *         pd.setSheetLength(SHEET_LENGTH)));
         *         pd.setSheetLength_c(ROLL_LENGTH_C)));
         *         pd.setRollLength(ROLL_LENGTH)));
         *         pd.setRollLength_c(SHEET_LENGTH_C)));
         *         pd.setPackagePrice(PACKAGE_PRICE)));
         *         pd.setRollPrice(ROLL_PRICE)));
         *         pd.setRollPrice_c(TpDbHelper.ROLL_PRICE_C)));
         *         pd.setPaperWeight(PAPER_WEIGHT)));
         *         pd.setPaperWeight_c(TpDbHelper.PAPER_WEIGHT_C)));
         *         pd.setKiloPrice(KILO_PRICE)));
         *         pd.setKiloPrice_c(TpDbHelper.KILO_PRICE_C)));
         *         pd.setMeterPrice(METER_PRICE)));
         *         pd.setMeterPrice_c(TpDbHelper.METER_PRICE_C)));
         *         pd.setSheetPrice(SHEET_PRICE)));
         *         pd.setSheetPrice_c(TpDbHelper.SHEET_PRICE_C)));
         *         pd.setSupplier(SUPPLIER)));
         *         pd.setComments(COMMENTS)));
         *         pd.setItemNo(ITEM_NO)));
         *         pd.setBrand(BRAND)));
         *         pd.setTimestamp(TIME_STAMP)));
         */
        // Add a row for each value

        tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2); //Border between rows


        tableRow.addView(addCell("UID"));
        tableRow.addView(addCell(pd.getUid()));

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
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
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
