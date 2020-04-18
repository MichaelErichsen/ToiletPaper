/*
 * Copyright (c) 2020. Michael Erichsen.
 */

package net.myerichsen.toiletpaper.ui.products;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.ProductActivity;
import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.database.ProductDbAdapter;

import java.util.List;

// TODO Double tapping opens the details fragment

/**
 * Product list fragment
 */
public class ProductFragment extends Fragment {
    private final TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private View root;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.product_fragment, container, false);
        context = getContext();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProductDbAdapter helper = new ProductDbAdapter(context);

        final TableLayout tableLayout = root.findViewById(R.id.productTableLayout);
        TableRow tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2);
        tableRow.addView(addCell("UID"));
        tableRow.addView(addCell("Varenummer"));
        tableRow.addView(addCell("Varemærke"));
        tableLayout.addView(tableRow);

        List<ProductData> lpd = null;
        try {
            lpd = helper.getAllData(context);
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(getActivity().findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        if (lpd.size() == 0) {
            Snackbar snackbar = Snackbar
                    .make(getActivity().findViewById(android.R.id.content), "No data in table", Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        for (int i = 0; i < lpd.size(); i++) {
            ProductData pd = lpd.get(i);

            tableRow = new TableRow(context);
            tableRow.setBackgroundColor(Color.BLACK);
            tableRow.setPadding(2, 2, 2, 2); //Border between rows

            tableRow.addView(addCell(Integer.toString(pd.getUid())));
            tableRow.addView(addCell(pd.getItemNo()));
            tableRow.addView(addCell(pd.getBrand()));
            tableRow.setClickable(true);
            tableRow.setOnClickListener(tableRowOnclickListener());
            tableLayout.addView(tableRow);
        }
    }

    private View.OnClickListener tableRowOnclickListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    TableRow selectedRow = (TableRow) v;
                    LinearLayout ll = (LinearLayout) selectedRow.getChildAt(0);
                    TextView tv = (TextView) ll.getChildAt(0);
                    int uid = Integer.parseInt(tv.getText().toString());
                    Snackbar snackbar = Snackbar
                            .make(getActivity().findViewById(android.R.id.content), uid + " was clicked", Snackbar.LENGTH_LONG);
                    snackbar.show();

                    Intent productIntent = new Intent(context, ProductActivity.class);
                    // TODO Pass filter and sort key
                    //              startIntent.putExtra("net.myrichsen.mysecondapplication.SOMETHING", "HELLO, WORLD");
                    startActivity(productIntent);
                } catch (NumberFormatException e) {
                    Snackbar snackbar = Snackbar
                            .make(getActivity().findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG);
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
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        cell.addView(tv);
        return cell;
    }
}