package net.myerichsen.toiletpaper.ui.suppliers;

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

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.SupplierActivity;
import net.myerichsen.toiletpaper.TPDbAdapter;

import java.util.List;
import java.util.Objects;

public class SupplierFragment extends Fragment {
    private final TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private View root;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.supplier_fragment, container, false);
        context = getContext();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TPDbAdapter helper = new TPDbAdapter(context);

        final TableLayout tableLayout = root.findViewById(R.id.supplierTableLayout);

        TableRow tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2);
        tableRow.addView(addCell("Butik"));
        tableRow.addView(addCell("Kæde"));
        tableLayout.addView(tableRow);

        List<SupplierModel> lsd;
        try {
            lsd = helper.getAllSupplierData();
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(requireActivity().findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        if (lsd.size() == 0) {
            Snackbar snackbar = Snackbar
                    .make(requireActivity().findViewById(android.R.id.content), "No data in table", Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        for (int i = 0; i < lsd.size(); i++) {
            SupplierModel sd = lsd.get(i);

            tableRow = new TableRow(context);
            tableRow.setBackgroundColor(Color.BLACK);
            tableRow.setPadding(2, 2, 2, 2); //Border between rows

            tableRow.addView(addCell(sd.getSupplier()));
            tableRow.addView(addCell(sd.getChain()));
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
                    String supplier = tv.getText().toString();
                    Snackbar snackbar = Snackbar
                            .make(requireActivity().findViewById(android.R.id.content), supplier + " was clicked", Snackbar.LENGTH_LONG);
                    snackbar.show();

                    Intent supplierIntent = new Intent(context, SupplierActivity.class);
                    supplierIntent.putExtra("net.myrichsen.toiletpaper.SUPPLIER", supplier);
                    startActivity(supplierIntent);
                } catch (NumberFormatException e) {
                    Snackbar snackbar = Snackbar
                            .make(requireActivity().findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        };
    }

    private LinearLayout addCell(String cellData) {
        llp.setMargins(2, 2, 2, 2);

        LinearLayout cell;//New Cell
        cell = new LinearLayout(context);
        cell.setBackgroundColor(Color.WHITE);
        cell.setLayoutParams(llp);

        TextView tv = new TextView(context);
        tv.setText(cellData);
        tv.setPadding(0, 0, 4, 3);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        cell.addView(tv);
        return cell;

    }

}
