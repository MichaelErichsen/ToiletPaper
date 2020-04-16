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
import androidx.lifecycle.ViewModelProvider;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.SupplierDbAdapter;

import java.util.List;

public class SupplierFragment extends Fragment {
    SupplierDbAdapter helper;
    final TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private View root;

    // TODO Crashes when called
    public static SupplierFragment newInstance() {
        return new SupplierFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.supplier_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupplierViewModel mViewModel = new ViewModelProvider(this).get(SupplierViewModel.class);
        // TODO: Use the ViewModel

        Context context = getContext();
        helper = new SupplierDbAdapter(context);

        LinearLayout tableListLayout = root.findViewById(R.id.supplierTableLayout);

        LinearLayout cell;

        TableLayout tableLayout = new TableLayout(context);

        TableRow tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2); //Border between rows
        tableRow.addView(addCell("UID"));
        tableRow.addView(addCell("Butik"));
        tableRow.addView(addCell("URI"));
        tableLayout.addView(tableRow);

        List<SupplierData> lsd = helper.getAllData(context);
        SupplierData sd;

        for (int i = 0; i < lsd.size(); i++) {
            sd = lsd.get(i);

            tableRow = new TableRow(context);
            tableRow.setBackgroundColor(Color.BLACK);
            tableRow.setPadding(2, 2, 2, 2); //Border between rows

            tableRow.addView(addCell(Integer.toString(SupplierData.getUid())));
            tableRow.addView(addCell(SupplierData.getSupplier()));
            tableRow.addView(addCell(SupplierData.getUri()));
            tableLayout.addView(tableRow);
        }

        tableListLayout.addView(tableLayout);


    }

    private LinearLayout addCell(String cellData) {
        llp.setMargins(2, 2, 2, 2);//2px right-margin
        Context context = getContext();

        LinearLayout cell;//New Cell
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
