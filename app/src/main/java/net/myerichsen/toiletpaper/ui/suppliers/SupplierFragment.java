package net.myerichsen.toiletpaper.ui.suppliers;

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

import java.util.List;

public class SupplierFragment extends Fragment {
    private final TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private TPDbAdapter helper;
    private View root;
    private Context context;

    // TODO Details activity to add or delete suppliers

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
        helper = new TPDbAdapter(context);

        final TableLayout tableLayout = root.findViewById(R.id.supplierTableLayout);

        TableRow tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2);
        tableRow.addView(addCell("Butik"));
        tableRow.addView(addCell("Kæde"));
        tableLayout.addView(tableRow);

        List<SupplierData> lsd;
        try {
            lsd = helper.getAllSupplierData(context);
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(getActivity().findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        if (lsd.size() == 0) {
            Snackbar snackbar = Snackbar
                    .make(getActivity().findViewById(android.R.id.content), "No data in table", Snackbar.LENGTH_LONG);
            snackbar.show();
            helper.doInitialLoad();
            return;
        }

        for (int i = 0; i < lsd.size(); i++) {
            SupplierData sd = lsd.get(i);

            tableRow = new TableRow(context);
            tableRow.setBackgroundColor(Color.BLACK);
            tableRow.setPadding(2, 2, 2, 2); //Border between rows

            tableRow.addView(addCell(sd.getSupplier()));
            tableRow.addView(addCell(sd.getChain()));
            tableLayout.addView(tableRow);
        }
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
