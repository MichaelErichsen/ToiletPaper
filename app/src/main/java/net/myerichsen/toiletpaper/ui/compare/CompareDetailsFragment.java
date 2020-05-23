package net.myerichsen.toiletpaper.ui.compare;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import net.myerichsen.toiletpaper.ui.products.ProductModel;

import java.util.List;
import java.util.Objects;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

/**
 * Display a sorted product list for compare
 */
public class CompareDetailsFragment extends Fragment {
    private static final String SORT_KEY = "sortKey";
    private static final String SORT_FILTER = "sortFilter";
    private final TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private String sortKey;
    private String sortFilter;
    private Context context;
    private View root;

    /**
     * Required empty public constructor
     */
    public CompareDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment
     */
    @SuppressWarnings("unused")
    public static CompareDetailsFragment newInstance() {
        return new CompareDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_compare_details, container, false);

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

        context = getContext();
        TPDbAdapter adapter = new TPDbAdapter(context);

        if (getArguments() != null) {
            sortKey = getArguments().getString(SORT_KEY);
            sortFilter = getArguments().getString(SORT_FILTER);
        }

        final TableLayout tableLayout = root.findViewById(R.id.compareTableLayout);
        TableRow tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2);

        String s;
        switch (sortKey) {
            case "PAPER_WEIGHT":
                s = "Papirvægt";
                break;
            case "KILO_PRICE":
                s = "Kilopris";
                break;
            case "METER_PRICE":
                s = "Meterpris";
                break;
            case "SHEET_PRICE":
                s = "Arkpris";
                break;
            default:
                s = "?";
                break;
        }

        tableRow.addView(addCell(s));
        tableRow.addView(addCell(getString(R.string.brand)));
        tableRow.addView(addCell(getString(R.string.item_no)));
        tableRow.addView(addCell("Uid"));
        tableLayout.addView(tableRow);

        List<ProductModel> lpm;

        try {
            lpm = adapter.getProductModelsSorted(sortKey, sortFilter);
        } catch (Exception e) {
            Snackbar.make(root.findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG).show();
            return;
        }

        if (lpm.size() == 0) {
            try {
                Snackbar.make(root.findViewById(android.R.id.content), R.string.no_products_found, Snackbar.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        for (ProductModel pd : lpm) {
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
                    Snackbar.make(root.findViewById(android.R.id.content),
                            getString(R.string.invalid_sort_key) + sortKey, Snackbar.LENGTH_LONG).show();
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

                    CompareDetailsFragmentDirections.ActionNavCompareDetailsToNavProductDetails action =
                            CompareDetailsFragmentDirections.actionNavCompareDetailsToNavProductDetails(uid);
                    Navigation.findNavController(v).navigate(action);
                } catch (Exception e) {
                    Snackbar.make(requireActivity().findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG).show();
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
