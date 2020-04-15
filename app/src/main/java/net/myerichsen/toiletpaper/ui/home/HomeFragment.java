package net.myerichsen.toiletpaper.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.ProductData;
import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.ScanActivity;
import net.myerichsen.toiletpaper.myDbAdapter;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    private final static int REQUEST_CODE_1 = 1;

    private HomeViewModel homeViewModel;
    private myDbAdapter helper;
    private ProductData productData;

    private View root;
    private EditText itemNoEditText;
    private EditText sheetLengthEditText;
    private CheckBox sheetLengthCheckBox;
    private EditText rollLengthEditText;
    private EditText rollSheetsEditText;
    private CheckBox rollLengthCheckBox;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        Context context = getContext();
        helper = new myDbAdapter(context);
        productData = new ProductData();

        itemNoEditText = root.findViewById(R.id.itemNoEditText);

        AppCompatImageButton scanBtn = root.findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int requestCode = RESULT_OK;
                Intent openScanIntent = new Intent(getContext(), ScanActivity.class);
                openScanIntent.putExtra("item_no", "");
                startActivityForResult(openScanIntent, REQUEST_CODE_1);
            }
        });

        Spinner layersSpinner = root.findViewById(R.id.layersSpinner);
        ArrayList<String> layerArrayList = new ArrayList<>();
        layerArrayList.add("2");
        layerArrayList.add("3");
        layerArrayList.add("4");
        layerArrayList.add("5");
        ArrayAdapter<String> layerArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, layerArrayList);
        layerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        layersSpinner.setAdapter(layerArrayAdapter);
        layersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        rollSheetsEditText = root.findViewById(R.id.rollSheetsEditText);
        sheetLengthEditText = root.findViewById(R.id.sheetLengthEditText);
        sheetLengthCheckBox = root.findViewById(R.id.sheetLengthCheckBox);
        rollLengthEditText = root.findViewById(R.id.rollLengthEditText);
        rollLengthCheckBox = root.findViewById(R.id.rollLengthCheckBox);
        rollLengthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rollLengthCheckBox.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Spinner suppliersSpinner = root.findViewById(R.id.suppliersSpinner);
        ArrayList<String> supplierArrayList = new ArrayList<>();
        // TODO Move to SQLite table
        supplierArrayList.add("Bilka");
        supplierArrayList.add("Coop");
        supplierArrayList.add("REMA 1000");
        supplierArrayList.add("Spar");
        ArrayAdapter<String> supplierArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, supplierArrayList);
        supplierArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        suppliersSpinner.setAdapter(supplierArrayAdapter);
        suppliersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//

        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_calculate:
                calculate(item);
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // TODO Add more calculations
    private boolean calculate(MenuItem item) {
            return multiply(sheetLengthEditText, sheetLengthCheckBox, rollSheetsEditText, null, rollLengthEditText, rollLengthCheckBox, 100);
    }

    //    private int multiply(EditText dividend, EditText divisor, EditText quotient) {
    private boolean multiply(EditText multiplicand, CheckBox cb1, EditText multiplier, CheckBox cb2, EditText product, CheckBox cb3, int precision) {
        String s1, s2 = "";
        String s3;

        // First test if calculated
        if ((cb1 != null) && (cb1.isSelected())) {
            return false;
        }
        if ((cb2 != null) && (cb2.isSelected())) {
            return false;
        }


        if ((cb3 != null) && !(cb3.isSelected())) {
            s3 = product.getText().toString();

            if ((!s3.isEmpty()) && (Integer.getInteger(s3) > 0)) {
                return false;
            }
        }

        // Then test for zero values
        s1 = multiplicand.getText().toString();
        if ((s1.isEmpty()) || (s1.equals("0"))) {
            return false;
        }
        s2 = multiplier.getText().toString();
        if ((s2.isEmpty()) || (s2.equals("0"))) {
            return false;
        }

        // Now do the calculation
        float i3 = Float.parseFloat(s1) * Float.parseFloat(s2) / precision;
        product.setText(String.valueOf(i3));
        cb3.setChecked(true);
        return true;
    }

    // This method is invoked when target activity return result data back.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);

        // The returned result data is identified by requestCode.
        // The request code is specified in startActivityForResult(intent, REQUEST_CODE_1); method.
        switch (requestCode) {
            // This request code is set by startActivityForResult(intent, REQUEST_CODE_1) method.
            case REQUEST_CODE_1:
                itemNoEditText = root.findViewById(R.id.itemNoEditText);

                if (resultCode == RESULT_OK) {
                    String messageReturn = dataIntent.getStringExtra("item_no");
                    itemNoEditText.setText(messageReturn);
                    ProductData.setItemNo(itemNoEditText.getText().toString());
                }
        }
    }

    public void addProduct(View view) {
        ProductData pd = new ProductData();

        String itemNo = ((EditText) root.findViewById(R.id.itemNoEditText)).getText().toString();
        if (itemNo.isEmpty()) {
            itemNo = "";
        }
        ProductData.setItemNo(itemNo);
        helper.insertData(pd);
    }

    /**
     * @param view
     */
    public void getDataByItemNo(View view) {
        EditText itemNoEditText = root.findViewById(R.id.itemNoEditText);
        ProductData pd = helper.getDataByItemNo(itemNoEditText.getText().toString());
        Snackbar snackbar = Snackbar
                .make(view, "Found item no. " + ProductData.getItemNo(), Snackbar.LENGTH_LONG);
        snackbar.show();
        // TODO move data to activity fields
    }


    /**
     * @param view
     */
    public void getDataByBrand(View view) {
        EditText brandEditText = root.findViewById(R.id.brandEditText);
        ProductData pd = helper.getDataByBrand(brandEditText.getText().toString());
        Snackbar snackbar = Snackbar
                .make(view, "Found brand. " + ProductData.getItemNo(), Snackbar.LENGTH_LONG);
        snackbar.show();
        // TODO move data to activity fields
    }
}
