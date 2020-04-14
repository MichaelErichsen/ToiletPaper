package net.myerichsen.toiletpaper;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

// TODO List of item numbers, suppliers and brand names

public class ListActivity extends AppCompatActivity {
    myDbAdapter helper;
    TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        helper = new myDbAdapter(this);

        LinearLayout tableListLayout = findViewById(R.id.tableListLayout);

        LinearLayout cell;

        TableLayout tableLayout = new TableLayout(this);

        TableRow tableRow = new TableRow(this);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setPadding(0, 0, 0, 2); //Border between rows
        tableRow.addView(addCell("UID"));
        tableRow.addView(addCell("Varenummer"));
        tableRow.addView(addCell("Varemærke"));
        tableLayout.addView(tableRow);

        List<ProductData> lpd = helper.getAllData(getApplicationContext());
        ProductData pd;

        for (int i = 0; i < lpd.size(); i++) {
            pd = lpd.get(i);

            tableRow = new TableRow(this);
            tableRow.setBackgroundColor(Color.BLACK);
            tableRow.setPadding(0, 0, 0, 2); //Border between rows

            tableRow.addView(addCell(Integer.toString(ProductData.getUid())));
            tableRow.addView(addCell(ProductData.getItemNo()));
            tableRow.addView(addCell(ProductData.getBrand()));
            tableLayout.addView(tableRow);
        }

        tableListLayout.addView(tableLayout);

    }

    private LinearLayout addCell(String cellData) {
        llp.setMargins(0, 0, 2, 0);//2px right-margin

        LinearLayout cell;//New Cell
        cell = new LinearLayout(this);
        cell.setBackgroundColor(Color.WHITE);
        cell.setLayoutParams(llp);//2px border on the right for the cell

        TextView tv = new TextView(ListActivity.this);
        tv.setText(cellData);
        tv.setPadding(0, 0, 4, 3);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        cell.addView(tv);
        return cell;
    }

}
