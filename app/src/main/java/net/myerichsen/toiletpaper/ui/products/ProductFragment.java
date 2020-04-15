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
import androidx.lifecycle.ViewModelProvider;

import net.myerichsen.toiletpaper.ProductDbAdapter;
import net.myerichsen.toiletpaper.R;

import java.util.List;

public class ProductFragment extends Fragment {
    ProductDbAdapter helper;
    final TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private View root;

    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.product_fragment, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProductViewModel mViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        // TODO: Use the ViewModel
        Context context = getContext();
        helper = new ProductDbAdapter(context);

        LinearLayout tableListLayout = root.findViewById(R.id.productTableLayout);

        LinearLayout cell;

        TableLayout tableLayout = new TableLayout(context);

        TableRow tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2); //Border between rows
        tableRow.addView(addCell("UID"));
        tableRow.addView(addCell("Varenummer"));
        tableRow.addView(addCell("Varemærke"));
        tableLayout.addView(tableRow);

        List<ProductData> lpd = helper.getAllProductData(context);
        ProductData pd;

        for (int i = 0; i < lpd.size(); i++) {
            pd = lpd.get(i);

            tableRow = new TableRow(context);
            tableRow.setBackgroundColor(Color.BLACK);
            tableRow.setPadding(2, 2, 2, 2); //Border between rows

            tableRow.addView(addCell(Integer.toString(ProductData.getUid())));
            tableRow.addView(addCell(ProductData.getItemNo()));
            tableRow.addView(addCell(ProductData.getBrand()));
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
