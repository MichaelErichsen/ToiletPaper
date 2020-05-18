package net.myerichsen.toiletpaper.ui.products;

import android.content.Context;
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
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.TPDbAdapter;

import java.util.List;
import java.util.Objects;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

/**
 * Display a list of Products
 */
public class ProductFragment extends Fragment {
    private final TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private View root;
    private Context context;
    private View snackView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_product, container, false);
        context = getContext();
        snackView = requireActivity().findViewById(android.R.id.content);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TPDbAdapter adapter = new TPDbAdapter(context);

        final TableLayout tableLayout = root.findViewById(R.id.productTableLayout);
        TableRow tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2);
        tableRow.addView(addCell("Varemærke"));
        tableRow.addView(addCell("Varenummer"));
        tableRow.addView(addCell("Løbenr."));
        tableLayout.addView(tableRow);

        List<ProductModel> lpm;
        try {
            lpm = adapter.getProductModels();
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        if (lpm.size() == 0) {
            Snackbar snackbar = Snackbar
                    .make(snackView, "Tabellen er tom", Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        for (ProductModel pm : lpm) {
            tableRow = new TableRow(context);
            tableRow.setBackgroundColor(Color.BLACK);
            tableRow.setPadding(2, 2, 2, 2); //Border between rows

            tableRow.addView(addCell(pm.getBrand()));
            tableRow.addView(addCell(pm.getItemNo()));
            tableRow.addView(addCell(Integer.toString(pm.getUid())));
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
                    LinearLayout ll = (LinearLayout) selectedRow.getChildAt(2);
                    TextView tv = (TextView) ll.getChildAt(0);
                    int uid = Integer.parseInt(tv.getText().toString());

                    ProductFragmentDirections.ActionNavProductsToNavProductDetails action = ProductFragmentDirections.actionNavProductsToNavProductDetails(uid);
                    Navigation.findNavController(v).navigate(action);
                } catch (Exception e) {
                    Snackbar snackbar = Snackbar
                            .make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
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
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        cell.addView(tv);
        return cell;
    }
}