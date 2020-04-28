package net.myerichsen.toiletpaper.ui.home;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.BrandActivity;
import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.ScanActivity;
import net.myerichsen.toiletpaper.TPDbAdapter;
import net.myerichsen.toiletpaper.ui.products.ProductModel;
import net.myerichsen.toiletpaper.ui.suppliers.SupplierModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    private final static int REQUEST_CODE_1 = 1;
    private final static int REQUEST_CODE_2 = 2;

    private TPDbAdapter helper;
    private View root;
    private Activity activity;

    private EditText itemNoEditText;
    private EditText brandEditText;
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
    private ProductModel pm;
    private String brand;

    private static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private View.OnClickListener scanBtnOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scanIntent = new Intent(getContext(), ScanActivity.class);
                scanIntent.putExtra("net.myerichsen.toiletpaper.ITEMNO", "");
                startActivityForResult(scanIntent, REQUEST_CODE_1);
            }
        };
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        activity = getActivity();
        Context context = getContext();
        helper = new TPDbAdapter(context);
        pm = new ProductModel();

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
        ArrayAdapter<String> layerArrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(context), android.R.layout.simple_spinner_item, layerArrayList);
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

        List<SupplierModel> lsm = new ArrayList<>();
        ArrayList<String> supplierArrayList = new ArrayList<>();

        boolean goOn = true;

        try {
            lsm = helper.getSupplierModels();
        } catch (Exception e) {
            goOn = false;
        }

        if ((goOn) && (lsm.size() == 0)) {
            goOn = false;
        }

        if (goOn) {
            for (int i = 0; i < lsm.size(); i++) {
                supplierArrayList.add(lsm.get(i).getSupplier());
            }
        }

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

        // Buttons
        AppCompatImageButton calculateBtn = root.findViewById(R.id.calculateBtn);
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(activity);
                String message = getString(R.string.calculation_failed);
                if (calculate()) {
                    message = getString(R.string.calculation_suceeded);
                }
                Snackbar snackbar = Snackbar
                        .make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        AppCompatImageButton saveBtn = root.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(activity);
                String message;

                try {
                    pm = populateProductModelFromLayout();
                    helper.insertData(pm);
                    message = getString(R.string.home_fragment_save_message);
                } catch (Exception e) {
                    message = e.getMessage();
                }
                Snackbar snackbar = Snackbar
                        .make(requireActivity().findViewById(android.R.id.content), Objects.requireNonNull(message), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        AppCompatImageButton pricerunnerBtn = root.findViewById(R.id.pricerunnerBtn);
        pricerunnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pricerunner.dk/results?q=toiletpapir"));
                startActivity(browserIntent);
            }
        });

        AppCompatImageButton scanBtn = root.findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(scanBtnOnClickListener());

        AppCompatImageButton searchItemNoBtn = root.findViewById(R.id.searchItemNoBtn);
        searchItemNoBtn.setOnClickListener(searchItemNoBtnOnclickListener());

        AppCompatImageButton searchBrandBtn = root.findViewById(R.id.searchBrandBtn);
        searchBrandBtn.setOnClickListener(searchBrandBtnOnclickListener());

        return root;
    }

    private View.OnClickListener searchItemNoBtnOnclickListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                hideSoftKeyboard(activity);
                List<ProductModel> lpm = helper.getProductModels("ITEM_NO=?", itemNoEditText.getText().toString());
                if (lpm.size() == 0) {
                    Snackbar snackbar = Snackbar
                            .make(requireActivity().findViewById(android.R.id.content),
                                    R.string.itemno_not_found, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                }

                populateLayoutFromProductModel(lpm.get(0));
            }
        };
    }

    private View.OnClickListener searchBrandBtnOnclickListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                hideSoftKeyboard(activity);
                brand = brandEditText.getText().toString();
                if (brand.equals("")) {
                    Snackbar snackbar = Snackbar
                            .make(requireActivity().findViewById(android.R.id.content),
                                    R.string.enter_brand_prompt, Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    Intent brandIntent = new Intent(getContext(), BrandActivity.class);
                    brandIntent.putExtra("net.myerichsen.toiletpaper.BRAND", brand);
                    startActivityForResult(brandIntent, REQUEST_CODE_2);
                }
            }
        };
    }

    private void populateLayoutFromProductModel(ProductModel pm) {
        try {
            layersSpinner.setSelection(getIndex(layersSpinner, String.valueOf(pm.getLayers())));
            packageRollsEditText.setText(String.valueOf(pm.getPackageRolls()));
            rollSheetsEditText.setText(String.valueOf(pm.getRollSheets()));
            sheetWidthEditText.setText(String.valueOf(pm.getSheetWidth()));
            sheetLengthEditText.setText(String.valueOf(pm.getSheetLength()));
            sheetLengthCheckBox.setChecked(pm.getSheetLength_c() != 0);
            rollLengthEditText.setText(String.valueOf(pm.getRollLength()));
            rollLengthCheckBox.setChecked(pm.getRollLength_c() != 0);
            packagePriceEditText.setText(String.valueOf(pm.getPackagePrice()));
            rollPriceEditText.setText(String.valueOf(pm.getRollPrice()));
            rollPriceCheckBox.setChecked(pm.getRollPrice_c() != 0);
            paperWeightEditText.setText(String.valueOf(pm.getPaperWeight()));
            paperWeightCheckBox.setChecked(pm.getPaperWeight_c() != 0);
            kiloPriceEditText.setText(String.valueOf(pm.getKiloPrice()));
            kiloPriceCheckBox.setChecked(pm.getKiloPrice_c() != 0);
            meterPriceEditText.setText(String.valueOf(pm.getMeterPrice()));
            meterPriceCheckBox.setChecked(pm.getMeterPrice_c() != 0);
            sheetPriceEditText.setText(String.valueOf(pm.getSheetPrice()));
            sheetPriceCheckBox.setChecked(pm.getSheetPrice_c() != 0);
            suppliersSpinner.setSelection(getIndex(suppliersSpinner, String.valueOf(pm.getSupplier())));
            commentEditText.setText(pm.getComments());
            itemNoEditText.setText(pm.getItemNo());
            brandEditText.setText(pm.getBrand());
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(requireActivity().findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;
    }

    private int getIntFromLayout(EditText et) {
        String s = et.getText().toString();

        if (s.equals("")) {
            return 0;
        } else {
            return Integer.parseInt(s);
        }
    }

    private int getIntFromLayout(CheckBox cb) {
        return (cb.isChecked() ? 1 : 0);
    }

    private Float getFloatFromLayout(EditText et) {
        String s = et.getText().toString();

        if (s.equals("")) {
            return (float) 0;
        } else {
            return Float.parseFloat(s);
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

    private ProductModel populateProductModelFromLayout() {
        ProductModel pm = new ProductModel();

        try {
            pm.setItemNo(getStringFromLayout(itemNoEditText));
            pm.setBrand(getStringFromLayout(brandEditText));
            pm.setLayers(getIntFromLayout(layersSpinner));
            pm.setPackageRolls(getIntFromLayout(packageRollsEditText));
            pm.setRollSheets(getIntFromLayout(rollSheetsEditText));
            pm.setSheetWidth(getIntFromLayout(sheetWidthEditText));
            pm.setSheetLength(getIntFromLayout(sheetLengthEditText));
            pm.setSheetLength_c(getIntFromLayout(sheetLengthCheckBox));
            pm.setRollLength(getIntFromLayout(rollLengthEditText));
            pm.setRollLength_c(getIntFromLayout(rollLengthCheckBox));
            pm.setPackagePrice(getFloatFromLayout(packagePriceEditText));
            pm.setRollPrice(getFloatFromLayout(rollPriceEditText));
            pm.setRollPrice_c(getIntFromLayout(rollPriceCheckBox));
            pm.setPaperWeight(getFloatFromLayout(paperWeightEditText));
            pm.setPaperWeight_c(getIntFromLayout(paperWeightCheckBox));
            pm.setKiloPrice(getFloatFromLayout(kiloPriceEditText));
            pm.setKiloPrice_c(getIntFromLayout(kiloPriceCheckBox));
            pm.setMeterPrice(getFloatFromLayout(meterPriceEditText));
            pm.setMeterPrice_c(getIntFromLayout(meterPriceCheckBox));
            pm.setSheetPrice(getFloatFromLayout(sheetPriceEditText));
            pm.setSheetPrice_c(getIntFromLayout(sheetPriceCheckBox));
            pm.setSupplier(getStringFromLayout(suppliersSpinner));
            pm.setComments(getStringFromLayout(commentEditText));
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(requireActivity().findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        return pm;
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
     */
    // TODO Input: g per rulle
    private boolean calculate() {
        // Sheet length = roll length / sheets pet roll (OK)
        boolean fSheetLength = false;
        try {
            fSheetLength = divide(rollLengthEditText, rollLengthCheckBox, rollSheetsEditText, null,
                    sheetLengthEditText, sheetLengthCheckBox, 1000);
        } catch (Exception ignored) {
        }

        // Roll length = sheet length * sheets per roll (OK)
        boolean fRollLength = false;
        try {
            fRollLength = multiply(sheetLengthEditText, sheetLengthCheckBox, rollSheetsEditText, null,
                    rollLengthEditText, rollLengthCheckBox, 1000);
        } catch (Exception ignored) {
        }

        // Price per roll = price per package / rolls per package (OK)
        boolean fRollPrice = false;
        try {
            fRollPrice = divide(packagePriceEditText, null, packageRollsEditText, null,
                    rollPriceEditText, rollPriceCheckBox, 1);
        } catch (Exception ignored) {
        }

        // TODO Paper weight (g per m2) ???

        // TODO Kilo price ???
//            boolean fKiloPrice = multiply(sheetLengthEditText, sheetLengthCheckBox, rollSheetsEditText, null, rollLengthEditText, rollLengthCheckBox, 100);

        // TODO Price per meter = price per package / rolls per package / roll length
        boolean fMeterPrice = false;
        try {
            fMeterPrice = divide(packagePriceEditText, null,
                    packageRollsEditText, null,
                    rollLengthEditText, rollLengthCheckBox,
                    meterPriceEditText, meterPriceCheckBox, 1);
        } catch (Exception ignored) {
        }

        // TODO Price per sheet = price per package / rolls pr package / sheets per roll
        boolean fSheetPrice = false;
        try {
            fSheetPrice = divide(packagePriceEditText, null, packageRollsEditText, null,
                    rollSheetsEditText, null, rollLengthEditText, rollLengthCheckBox, 1);
        } catch (Exception ignored) {
        }

        return fMeterPrice | fRollLength | fRollPrice | fSheetLength | fSheetPrice;

//        return fKiloPrice | fMeterPrice | fPaperWeight | fRollLength | fRollPrice | fSheetLength | fSheetPrice;
    }

    private boolean multiply(EditText multiplicand, CheckBox cb1, EditText multiplier, CheckBox cb2, EditText product, CheckBox cb3, int precision) {
        String s1, s2;
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

            if ((!s3.isEmpty()) && (Integer.parseInt(s3) > 0)) {
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
        Objects.requireNonNull(cb3).setChecked(true);
        return true;
    }

    private boolean divide(EditText dividend, CheckBox cb1, EditText divisor, CheckBox cb2, EditText quotient, CheckBox cb3, int precision) {
        String s1, s2;
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

            if ((!s3.isEmpty()) && (Integer.parseInt(s3) > 0)) {
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
        float i3 = Float.parseFloat(s1) * precision / Float.parseFloat(s2);
        quotient.setText(String.valueOf(i3));
        Objects.requireNonNull(cb3).setChecked(true);
        return true;
    }

    private boolean divide(EditText dividend, CheckBox cb1, EditText divisor, CheckBox cb2,
                           EditText divisor2, CheckBox cb3,
                           EditText quotient, CheckBox cb4, int precision) {
        String s1, s2, s3, s4;

        // First test if calculated
        if ((cb1 != null) && (cb1.isSelected())) {
            return false;
        }
        if ((cb2 != null) && (cb2.isSelected())) {
            return false;
        }
        if ((cb3 != null) && (cb3.isSelected())) {
            return false;
        }

        if ((cb4 != null) && !(cb4.isSelected())) {
            s4 = quotient.getText().toString();

            if ((!s4.isEmpty()) && (Integer.parseInt(s4) > 0)) {
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
        s3 = divisor2.getText().toString();
        if ((s3.isEmpty()) || (s3.equals("0"))) {
            return false;
        }

        // Now do the calculation
        float fq = Float.parseFloat(s1) * precision / Float.parseFloat(s2) / Float.parseFloat(s3);
        quotient.setText(String.valueOf(fq));
        Objects.requireNonNull(cb4).setChecked(true);
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
                    String messageReturn = dataIntent.getStringExtra("net.myerichsen.toiletpaper.ITEMNO");

                    List<ProductModel> lpm = helper.getProductModels("ITEM_NO=?", messageReturn);

                    if (lpm.size() == 0) {
                        Snackbar snackbar = Snackbar
                                .make(requireActivity().findViewById(android.R.id.content),
                                        R.string.itemno_not_found, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        populateLayoutFromProductModel(lpm.get(0));
                    }
                }
                break;

            // This request code is set by startActivityForResult(intent, REQUEST_CODE_2) method.
            case REQUEST_CODE_2:
                String messageReturn = dataIntent.getStringExtra("net.myerichsen.toiletpaper.BRAND");

                if (resultCode == RESULT_OK) {
                    List<ProductModel> lpm = helper.getProductModels("BRAND=?", messageReturn);

                    if (lpm.size() == 0) {
                        Snackbar snackbar = Snackbar
                                .make(requireActivity().findViewById(android.R.id.content),
                                        R.string.brand_not_found, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        populateLayoutFromProductModel(lpm.get(0));
                    }
                } else {
                    Snackbar snackbar = Snackbar
                            .make(requireActivity().findViewById(android.R.id.content), Objects.requireNonNull(messageReturn), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                break;
        }
    }

// --Commented out by Inspection START (27-04-2020 17:46):
//    public void addProduct(View view) {
//        ProductModel pm = new ProductModel();
//
////        String itemNo = ((EditText) root.findViewById(R.id.itemNoEditText)).getText().toString();
////        if (itemNo.isEmpty()) {
////            itemNo = "";
////        }
////        pm.setItemNo(itemNo);
//        helper.insertData(pm);
//        Snackbar snackbar = Snackbar
//                .make(requireActivity().findViewById(android.R.id.content), R.string.product_added, Snackbar.LENGTH_LONG);
//        snackbar.show();
//    }
// --Commented out by Inspection STOP (27-04-2020 17:46)


// --Commented out by Inspection START (27-04-2020 17:46):
//    public void getDataByItemNo(View view) {
//        EditText itemNoEditText = root.findViewById(R.id.itemNoEditText);
//        ProductModel pm = helper.getProductDataByItemNo(itemNoEditText.getText().toString());
//        Snackbar snackbar = Snackbar
//                .make(requireActivity().findViewById(android.R.id.content), "Found item no. " + pm.getItemNo(), Snackbar.LENGTH_LONG);
//        snackbar.show();
//    }
// --Commented out by Inspection STOP (27-04-2020 17:46)


// --Commented out by Inspection START (27-04-2020 17:46):
//    public void getDataByBrand(View view) {
//        EditText brandEditText = root.findViewById(R.id.brandEditText);
//        ProductModel pm = helper.getProductDataByBrand(brandEditText.getText().toString());
//        Snackbar snackbar = Snackbar
//                .make(requireActivity().findViewById(android.R.id.content), "Found brand. " + pm.getItemNo(), Snackbar.LENGTH_LONG);
//        snackbar.show();
//    }
// --Commented out by Inspection STOP (27-04-2020 17:46)
}
