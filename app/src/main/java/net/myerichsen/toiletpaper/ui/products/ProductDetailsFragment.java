/*
 * Copyright (c) 2020. Michael Erichsen.
 */

package net.myerichsen.toiletpaper.ui.products;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

import java.util.List;
import java.util.Objects;

/**
 * Display and maintain product details
 */
public class ProductDetailsFragment extends Fragment {
    private static final String UID = "uid";
    private final TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private int uid = -1;
    private Context context;
    private View root;
    private View snackView;

    /**
     * Required empty public constructor
     */
    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment
     */
    public static ProductDetailsFragment newInstance() {
        return new ProductDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_product_details, container, false);
        context = getContext();
        snackView = requireActivity().findViewById(android.R.id.content);
        return root;
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TPDbAdapter adapter = new TPDbAdapter(context);

        final TableLayout productDetailTableLayout = root.findViewById(R.id.productDetailTableLayout);
        TableRow tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2);
        tableRow.addView(addCell("Tekst"));
        tableRow.addView(addCell("Værdi"));
        productDetailTableLayout.addView(tableRow);

        if (getArguments() != null) {
            uid = getArguments().getInt(UID);
        }

        List<ProductModel> lpm = null;

        try {
            lpm = adapter.getProductModels("UID=?", String.valueOf(uid));
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        if (lpm == null) {
            Snackbar snackbar = Snackbar
                    .make(snackView, "Produkt nr. " + uid + " findes ikke", Snackbar.LENGTH_LONG);
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

        ImageButton productDeleteBtn = root.findViewById(R.id.productDeleteBtn);
        productDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    adapter.deleteProduct(uid);
                } catch (Exception e) {
                    Snackbar snackbar = Snackbar
                            .make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
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
