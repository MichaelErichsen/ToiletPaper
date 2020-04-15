package net.myerichsen.toiletpaper.ui.products;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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

import net.myerichsen.toiletpaper.ProductData;
import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.myDbAdapter;

import java.util.List;

public class ProductFragment extends Fragment {
    myDbAdapter helper;
    TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private View root;

    private ProductViewModel mViewModel;

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
        mViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        // TODO: Use the ViewModel
        Context context = getContext();
        helper = new myDbAdapter(context);

        LinearLayout tableListLayout = root.findViewById(R.id.productTableLayout);

        LinearLayout cell;

        TableLayout tableLayout = new TableLayout(context);

        TableRow tableRow = new TableRow(context);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(2, 2, 2, 2); //Border between rows
        tableRow.addView(addCell(context, "UID"));
        tableRow.addView(addCell(context, "Varenummer"));
        tableRow.addView(addCell(context, "Varemærke"));
        tableLayout.addView(tableRow);

        List<ProductData> lpd = helper.getAllData(context);
        ProductData pd;

        for (int i = 0; i < lpd.size(); i++) {
            pd = lpd.get(i);

            tableRow = new TableRow(context);
            tableRow.setBackgroundColor(Color.BLACK);
            tableRow.setPadding(2, 2, 2, 2); //Border between rows

            tableRow.addView(addCell(context, Integer.toString(ProductData.getUid())));
            tableRow.addView(addCell(context, ProductData.getItemNo()));
            tableRow.addView(addCell(context, ProductData.getBrand()));
            tableLayout.addView(tableRow);
        }

        tableListLayout.addView(tableLayout);


    }

    private TextView addCell(Context context, String toString) {
        TextView cell = new TextView(context);
        cell.setText(toString);
        return cell;
    }

}
