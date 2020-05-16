/*
 * Copyright (c) 2020. Michael Erichsen.
 */

package net.myerichsen.toiletpaper.ui.home;

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

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.TPDbAdapter;
import net.myerichsen.toiletpaper.ui.products.ProductModel;

import java.util.List;
import java.util.Objects;

import static net.myerichsen.toiletpaper.ui.home.HomeFragment.BRAND;

/**
 * A fragment representing a list af brands
 */
public class BrandFragment extends Fragment {
    private final TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private Context context;
    private View root;
    private String brand;
    private View snackView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BrandFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment
     */
    public static BrandFragment newInstance() {
        return new BrandFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            brand = getArguments().getString(BRAND);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_brand, container, false);
        context = getContext();
        snackView = requireActivity().findViewById(android.R.id.content);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TPDbAdapter adapter = new TPDbAdapter(context);

        if (getArguments() != null) {
            brand = getArguments().getString(BRAND);
        }

        List<ProductModel> lpm;

        try {
            if (!Objects.requireNonNull(brand).endsWith("%")) {
                brand += "%";
            }
            lpm = adapter.getProductModels("BRAND LIKE ?", brand);
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
            snackbar.show();
            requireActivity().onBackPressed();
            return;
        }

        // Not found
        if ((lpm == null) || (lpm.size()) == 0) {
            Snackbar snackbar = Snackbar
                    .make(snackView, getString(R.string.brand_not_found), Snackbar.LENGTH_LONG);
            snackbar.show();
            requireActivity().onBackPressed();
            return;
        }

        // Only one found
        if (lpm.size() == 1) {
            brand = lpm.get(0).getBrand();
            Bundle result = new Bundle();
            result.putString(BRAND, brand);
            getParentFragmentManager().setFragmentResult("brandRequestKey", result);
            requireActivity().onBackPressed();
            return;
        }

        // More than one found
        final TableLayout brandTableLayout = root.findViewById(R.id.brandTableLayout);

        for (int i = 0; i < lpm.size(); i++) {
            addTableRow(brandTableLayout, lpm.get(i).getBrand());
        }
    }

    private void addTableRow(TableLayout tableLayout, String brand) {
        TableRow tableRow;
        tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2); //Border between rows
        tableRow.addView(addCell(brand));
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

                    Bundle result = new Bundle();
                    result.putString(BRAND, brand);
                    getParentFragmentManager().setFragmentResult("brandRequestKey", result);
                    requireActivity().onBackPressed();
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
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        cell.addView(tv);
        return cell;
    }
}
