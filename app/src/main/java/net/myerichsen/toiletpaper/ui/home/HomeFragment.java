package net.myerichsen.toiletpaper.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.ScanActivity;
import net.myerichsen.toiletpaper.database.ProductDbAdapter;
import net.myerichsen.toiletpaper.ui.products.ProductData;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

// FIXME Back button has disappeared from navigation
// FIXME Same UID for each new row
// TODO Understand relation between Fragment and ViewModel
public class HomeFragment extends Fragment {
    private final static int REQUEST_CODE_1 = 1;

    private ProductDbAdapter helper;
    private View root;
    private Context context;

    private AppCompatImageButton calculateBtn;
    private AppCompatImageButton saveBtn;
    private AppCompatImageButton supplierBtn;
    private AppCompatImageButton pricerunnerBtn;
    private EditText itemNoEditText;
    private AppCompatImageButton scanBtn;
    private AppCompatImageButton searchItemNoBtn;
    private EditText brandEditText;
    private AppCompatImageButton searchBrandBtn;
    private Spinner layersSpinner;
    private EditText packageRollsEditText;
    private EditText rollSheetsEditText;
    private EditText sheetWidthEditText;
    private EditText sheetLengthEditText;
    private CheckBox sheetLengthCheckBox;
    private EditText rollLengthEditText;
    private CheckBox rollLengthCheckBox;
    private EditText packagePriceEditText;
    private EditText rollPriceEditText;
    private CheckBox rollPriceCheckBox;
    private EditText paperWeightEditText;
    private CheckBox paperWeightCheckBox;
    private EditText kiloPriceEditText;
    private CheckBox kiloPriceCheckBox;
    private EditText meterPriceEditText;
    private CheckBox meterPriceCheckBox;
    private EditText sheetPriceEditText;
    private CheckBox sheetPriceCheckBox;
    private Spinner suppliersSpinner;
    private EditText commentEditText;
    private TextView messageTextView;
    private ProductData pd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        context = getContext();
        helper = new ProductDbAdapter(context);
        pd = new ProductData();

        // Buttons
        calculateBtn = root.findViewById(R.id.calculateBtn);
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });

        saveBtn = root.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";

                try {
                    pd = populateProductDataFromLayout();
                    helper.insertData(pd);
                    message = getString(R.string.home_fragment_save_message);
                } catch (Exception e) {
                    message = e.getMessage();
                }
                Snackbar snackbar = Snackbar
                        .make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        // TODO Implement supplier web site lookup
        supplierBtn = root.findViewById(R.id.supplierBtn);

        pricerunnerBtn = root.findViewById(R.id.pricerunnerBtn);
        pricerunnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pricerunner.dk/results?q=toiletpapir"));
                startActivity(browserIntent);
            }
        });

        scanBtn = root.findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int requestCode = RESULT_OK;
                Intent openScanIntent = new Intent(getContext(), ScanActivity.class);
                openScanIntent.putExtra("item_no", "");
                startActivityForResult(openScanIntent, REQUEST_CODE_1);
            }
        });

        // Item no
        itemNoEditText = root.findViewById(R.id.itemNoEditText);

        // Brand
        brandEditText = root.findViewById(R.id.brandEditText);

        // Layers
        layersSpinner = root.findViewById(R.id.layersSpinner);
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

        // Package rolls
        packageRollsEditText = root.findViewById(R.id.packageRollsEditText);

        // Roll sheets
        rollSheetsEditText = root.findViewById(R.id.rollSheetsEditText);

        // Sheet width
        sheetWidthEditText = root.findViewById(R.id.sheetWidthEditText);

        // Sheet length
        sheetLengthEditText = root.findViewById(R.id.sheetLengthEditText);
        sheetLengthCheckBox = root.findViewById(R.id.sheetLengthCheckBox);
        sheetLengthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sheetLengthCheckBox.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Roll length
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

        // Package price
        packagePriceEditText = root.findViewById(R.id.packagePriceEditText);

        // Roll price
        rollPriceEditText = root.findViewById(R.id.rollPriceEditText);
        rollPriceCheckBox = root.findViewById(R.id.rollPriceCheckBox);
        rollPriceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rollPriceCheckBox.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Paper weight
        paperWeightEditText = root.findViewById(R.id.paperWeightEditText);
        paperWeightCheckBox = root.findViewById(R.id.paperWeightCheckBox);
        paperWeightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                kiloPriceCheckBox.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Kilo price
        kiloPriceEditText = root.findViewById(R.id.kiloPriceEditText);
        kiloPriceCheckBox = root.findViewById(R.id.kiloPriceCheckBox);
        kiloPriceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                kiloPriceCheckBox.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Meter price
        meterPriceEditText = root.findViewById(R.id.meterPriceEditText);
        meterPriceCheckBox = root.findViewById(R.id.meterPriceCheckBox);
        meterPriceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                meterPriceCheckBox.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Sheet price
        sheetPriceEditText = root.findViewById(R.id.sheetPriceEditText);
        sheetPriceCheckBox = root.findViewById(R.id.sheetPriceCheckBox);
        sheetPriceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sheetPriceCheckBox.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Suppliers
        suppliersSpinner = root.findViewById(R.id.suppliersSpinner);
        ArrayList<String> supplierArrayList = new ArrayList<>();
        // TODO Move to SQLite table
        supplierArrayList.add("Aldi");
        supplierArrayList.add("Coop (Kvickly/Brugsen/Fakta/Irma)");
        supplierArrayList.add("Lidl");
        supplierArrayList.add("REMA 1000");
        supplierArrayList.add("Salling (Bilka/Føtex/Netto)");
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

        // Comments
        commentEditText = root.findViewById(R.id.commentEditText);

        return root;
    }

    private int getIntFromLayout(EditText et) {
        String s = et.getText().toString();

        if (s.equals("")) {
            return 0;
        } else {
            return Integer.parseInt(s);
        }
    }

    private int getIntFromLayout(Spinner spinner) {
        return Integer.parseInt((String) spinner.getSelectedItem());
    }

    private String getStringFromLayout(EditText et) {
        return et.getText().toString();
    }

    private String getStringFromLayout(Spinner spinner) {
        return (String) spinner.getSelectedItem();
    }

    private ProductData populateProductDataFromLayout() {

        // TODO Populate Productdata from layout

        ProductData pd = new ProductData();

        try {
            ProductData.setLayers(getIntFromLayout(layersSpinner));
            ProductData.setPackageRolls(getIntFromLayout(packageRollsEditText));


//        pd.setRollSheets(cursor.getInt(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.ROLL_SHEETS)));
//        pd.setSheetWidth(cursor.getInt(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.SHEET_WIDTH)));
//        pd.setSheetLength(cursor.getInt(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.SHEET_LENGTH)));
//        pd.setSheetLength_c(cursor.getInt(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.ROLL_LENGTH_C)));
//        pd.setRollLength(cursor.getInt(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.ROLL_LENGTH)));
//        pd.setRollLength_c(cursor.getInt(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.SHEET_LENGTH_C)));
//        pd.setPackagePrice(cursor.getFloat(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.PACKAGE_PRICE)));
//        pd.setRollPrice(cursor.getFloat(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.ROLL_PRICE)));
//        pd.setRollPrice_c(cursor.getInt(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.ROLL_PRICE_C)));
//        pd.setPaperWeight(cursor.getFloat(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.PAPER_WEIGHT)));
//        pd.setPaperWeight_c(cursor.getInt(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.PAPER_WEIGHT_C)));
//        pd.setKiloPrice(cursor.getFloat(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.KILO_PRICE)));
//        pd.setKiloPrice_c(cursor.getInt(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.KILO_PRICE_C)));
//        pd.setMeterPrice(cursor.getFloat(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.METER_PRICE)));
//        pd.setMeterPrice_c(cursor.getInt(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.METER_PRICE_C)));
//        pd.setSheetPrice(cursor.getFloat(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.SHEET_PRICE)));
//        pd.setSheetPrice_c(cursor.getInt(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.SHEET_PRICE_C)));
//        pd.setSupplier(cursor.getString(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.SUPPLIER)));
//        pd.setComments(cursor.getString(cursor.getColumnIndex(ProductDbAdapter.ProductHelper.COMMENTS)));
            ProductData.setItemNo(getStringFromLayout(itemNoEditText));
            ProductData.setBrand(getStringFromLayout(brandEditText));
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(getActivity().findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        return pd;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Calculate all calculable fields
     *
     * @return boolean true if one or more calculations have been done
     */
    private boolean calculate() {
        try {
            boolean fSheetLength = divide(rollLengthEditText, rollLengthCheckBox, rollSheetsEditText, null, sheetLengthEditText, sheetLengthCheckBox);

            boolean fRollLength = multiply(sheetLengthEditText, sheetLengthCheckBox, rollSheetsEditText, null, rollLengthEditText, rollLengthCheckBox, 100);

            boolean fRollPrice = divide(packagePriceEditText, null, rollSheetsEditText, null, rollPriceEditText, rollPriceCheckBox);

            // TODO Paper weight g/m2 not calculable?
            // boolean fPaperWeight = multiply(sheetLengthEditText, sheetLengthCheckBox, rollSheetsEditText, null, rollLengthEditText, rollLengthCheckBox, 100);

            // Kilo price
            boolean fKiloPrice = multiply(sheetLengthEditText, sheetLengthCheckBox, rollSheetsEditText, null, rollLengthEditText, rollLengthCheckBox, 100);

            // Meter price: package price / sheet length
            boolean fMeterPrice = divide(packagePriceEditText, null, sheetLengthEditText, sheetLengthCheckBox, meterPriceEditText, meterPriceCheckBox);

            // Sheet price: package price / rolls pr package / sheets per roll
//            EditText dummy = new EditText(context);
//            CheckBox dummyC = new CheckBox(context);
//            dummyC.setChecked(false);
//            boolean fSheetPrice1 = divide(packagePriceEditText, null, packageRollsEditText, null, dummy, dummyC);
//            boolean fSheetPrice = divide(dummy, dummyC, rollSheetsEditText, null, rollLengthEditText, rollLengthCheckBox);

            return fKiloPrice | fMeterPrice | fRollLength | fRollPrice | fSheetLength;
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(getActivity().findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
//        return fKiloPrice | fMeterPrice | fPaperWeight | fRollLength | fRollPrice | fSheetLength | fSheetPrice;
    }

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

    private boolean divide(EditText dividend, CheckBox cb1, EditText divisor, CheckBox cb2, EditText quotient, CheckBox cb3) {
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
            s3 = quotient.getText().toString();

            if ((!s3.isEmpty()) && (Integer.getInteger(s3) > 0)) {
                return false;
            }
        }

        // Then test for zero values
        s1 = dividend.getText().toString();
        if ((s1.isEmpty()) || (s1.equals("0"))) {
            return false;
        }
        s2 = divisor.getText().toString();
        if ((s2.isEmpty()) || (s2.equals("0"))) {
            return false;
        }

        // Now do the calculation
        float i3 = Float.parseFloat(s1) / Float.parseFloat(s2);
        quotient.setText(String.valueOf(i3));
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


    public void getDataByItemNo(View view) {
        EditText itemNoEditText = root.findViewById(R.id.itemNoEditText);
        ProductData pd = helper.getDataByItemNo(itemNoEditText.getText().toString());
        Snackbar snackbar = Snackbar
                .make(getActivity().findViewById(android.R.id.content), "Found item no. " + ProductData.getItemNo(), Snackbar.LENGTH_LONG);
        snackbar.show();
        // TODO move data to activity fields
    }


    public void getDataByBrand(View view) {
        EditText brandEditText = root.findViewById(R.id.brandEditText);
        ProductData pd = helper.getDataByBrand(brandEditText.getText().toString());
        Snackbar snackbar = Snackbar
                .make(getActivity().findViewById(android.R.id.content), "Found brand. " + ProductData.getItemNo(), Snackbar.LENGTH_LONG);
        snackbar.show();
        // TODO move data to activity fields
    }
}
