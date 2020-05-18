package net.myerichsen.toiletpaper.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.TPDbAdapter;
import net.myerichsen.toiletpaper.ui.products.ProductModel;
import net.myerichsen.toiletpaper.ui.suppliers.SupplierModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

public class HomeFragment extends Fragment {
    public static final String BRAND = "brand";
    public static final String ITEM_NO = "itemNo";

    private TPDbAdapter adapter;
    private Activity activity;
    private Snackbar snackbar;
    private View snackView;

    private TextInputEditText itemNoEditText;
    private TextInputEditText brandEditText;
    private Spinner layersSpinner;
    private TextInputEditText packageRollsEditText;
    private TextInputEditText rollSheetsEditText;
    private TextInputEditText sheetWidthEditText;
    private TextInputEditText sheetLengthEditText;
    private CheckBox sheetLengthCheckBox;
    private TextInputEditText rollLengthEditText;
    private CheckBox rollLengthCheckBox;
    private TextInputEditText packagePriceEditText;
    private TextInputEditText rollPriceEditText;
    private CheckBox rollPriceCheckBox;
    private TextInputEditText paperWeightEditText;
    private CheckBox paperWeightCheckBox;
    private TextInputEditText rollWeightEditText;
    private CheckBox rollWeightCheckBox;
    private TextInputEditText kiloPriceEditText;
    private CheckBox kiloPriceCheckBox;
    private TextInputEditText meterPriceEditText;
    private CheckBox meterPriceCheckBox;
    private TextInputEditText sheetPriceEditText;
    private CheckBox sheetPriceCheckBox;
    private Spinner suppliersSpinner;
    private TextInputEditText commentEditText;
    private ProductModel pm;
    private String brand;
    private String itemNo;

    private static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        activity = getActivity();
        Context context = getContext();
        snackView = requireActivity().findViewById(android.R.id.content);

        // Set font sizes

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(context));
        float fontSize = Float.parseFloat(preferences.getString("fontsize", "24"));
        ((TextInputEditText) root.findViewById(R.id.brandEditText)).setTextSize(fontSize);
        ((TextInputEditText) root.findViewById(R.id.commentEditText)).setTextSize(fontSize);
        ((TextInputEditText) root.findViewById(R.id.itemNoEditText)).setTextSize(fontSize);
        ((TextInputEditText) root.findViewById(R.id.kiloPriceEditText)).setTextSize(fontSize);
        ((Button) root.findViewById(R.id.kiloPriceCheckBox)).setTextSize(fontSize);
        ((TextInputEditText) root.findViewById(R.id.meterPriceEditText)).setTextSize(fontSize);
        ((Button) root.findViewById(R.id.meterPriceCheckBox)).setTextSize(fontSize);
        ((TextInputEditText) root.findViewById(R.id.packagePriceEditText)).setTextSize(fontSize);
        ((TextInputEditText) root.findViewById(R.id.packageRollsEditText)).setTextSize(fontSize);
        ((TextInputEditText) root.findViewById(R.id.paperWeightEditText)).setTextSize(fontSize);
        ((Button) root.findViewById(R.id.paperWeightCheckBox)).setTextSize(fontSize);
        ((TextInputEditText) root.findViewById(R.id.rollWeightEditText)).setTextSize(fontSize);
        ((Button) root.findViewById(R.id.rollWeightCheckBox)).setTextSize(fontSize);
        ((TextInputEditText) root.findViewById(R.id.rollLengthEditText)).setTextSize(fontSize);
        ((Button) root.findViewById(R.id.rollLengthCheckBox)).setTextSize(fontSize);
        ((TextInputEditText) root.findViewById(R.id.rollPriceEditText)).setTextSize(fontSize);
        ((Button) root.findViewById(R.id.rollPriceCheckBox)).setTextSize(fontSize);
        ((TextInputEditText) root.findViewById(R.id.rollSheetsEditText)).setTextSize(fontSize);
        ((TextInputEditText) root.findViewById(R.id.sheetLengthEditText)).setTextSize(fontSize);
        ((Button) root.findViewById(R.id.sheetLengthCheckBox)).setTextSize(fontSize);
        ((TextInputEditText) root.findViewById(R.id.sheetPriceEditText)).setTextSize(fontSize);
        ((Button) root.findViewById(R.id.sheetPriceCheckBox)).setTextSize(fontSize);
        ((TextInputEditText) root.findViewById(R.id.sheetWidthEditText)).setTextSize(fontSize);

        adapter = new TPDbAdapter(context);
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
        layersSpinner.setOnItemSelectedListener(getSpinnerListener());

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
                if (sheetLengthEditText.hasFocus())
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
                if (rollLengthEditText.hasFocus())
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
                if (rollPriceEditText.hasFocus())
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
                if (paperWeightEditText.hasFocus())
                    paperWeightCheckBox.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // roll weight
        rollWeightEditText = root.findViewById(R.id.rollWeightEditText);
        rollWeightCheckBox = root.findViewById(R.id.rollWeightCheckBox);
        rollWeightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (rollWeightEditText.hasFocus())
                    rollWeightCheckBox.setChecked(false);
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
                if (kiloPriceEditText.hasFocus())
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
                if (meterPriceEditText.hasFocus())
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
                if (sheetPriceEditText.hasFocus())
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
        int spinnerIndex = 0;

        try {
            lsm = adapter.getSupplierModels();
        } catch (Exception e) {
            goOn = false;
        }

        if ((goOn) && (lsm.size() == 0)) {
            goOn = false;
        }

        if (goOn) {
            String item;
            String defaultSupplier = preferences.getString("defaultsupplier", "");

            for (int i = 0; i < lsm.size(); i++) {
                item = lsm.get(i).getSupplier();
                supplierArrayList.add(item);

                if (item.equals(defaultSupplier)) {
                    spinnerIndex = i;
                }
            }
        }

        ArrayAdapter<String> supplierArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, supplierArrayList);
        supplierArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        suppliersSpinner.setAdapter(supplierArrayAdapter);
        suppliersSpinner.setSelection(spinnerIndex);
        suppliersSpinner.setOnItemSelectedListener(getSpinnerListener());

        // Comments
        commentEditText = root.findViewById(R.id.commentEditText);

        // Buttons
        AppCompatImageButton calculateBtn = root.findViewById(R.id.calculateBtn);
        calculateBtn.setOnClickListener(calculateBtnOnClickListener());

        AppCompatImageButton saveBtn = root.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(saveBtnOnClickListener());

        AppCompatImageButton pricerunnerBtn = root.findViewById(R.id.pricerunnerBtn);
        pricerunnerBtn.setOnClickListener(priceRunnerOnClickListener());

        AppCompatImageButton scanBtn = root.findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(scanBtnOnClickListener());

        AppCompatImageButton searchItemNoBtn = root.findViewById(R.id.searchItemNoBtn);
        searchItemNoBtn.setOnClickListener(searchItemNoBtnOnclickListener());

        AppCompatImageButton searchBrandBtn = root.findViewById(R.id.searchBrandBtn);
        searchBrandBtn.setOnClickListener(searchBrandBtnOnclickListener());

        return root;
    }

    private AdapterView.OnItemSelectedListener getSpinnerListener() {
        return new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
    }

    private View.OnClickListener calculateBtnOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(activity);
                String message = getString(R.string.calculation_failed);
                if (calculate()) {
                    message = getString(R.string.calculation_suceeded);
                }
                snackbar = Snackbar
                        .make(snackView, message, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        };
    }

    private View.OnClickListener priceRunnerOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pricerunner.dk/results?q=toiletpapir"));
                startActivity(browserIntent);
            }
        };
    }

    private View.OnClickListener saveBtnOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(activity);

                try {
                    pm = populateProductModelFromLayout();
                    adapter.insertData(pm);
                    snackbar = Snackbar
                            .make(snackView, Objects.requireNonNull(getString(R.string.home_fragment_save_message)),
                                    Snackbar.LENGTH_LONG);
                    snackbar.show();
                } catch (Exception e) {
                    snackbar = Snackbar
                            .make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        };
    }

    private View.OnClickListener searchItemNoBtnOnclickListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                hideSoftKeyboard(activity);
                itemNo = itemNoEditText.getText().toString();
                if (itemNo.equals("")) {
                    snackbar = Snackbar
                            .make(snackView,
                                    R.string.enter_itemno_prompt, Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    HomeFragmentDirections.ActionNavHomeToNavItemNo action =
                            HomeFragmentDirections.actionNavHomeToNavItemNo(itemNo);
                    Navigation.findNavController(v).navigate(action);
                }
            }
        };
    }

    private View.OnClickListener searchBrandBtnOnclickListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                hideSoftKeyboard(activity);
                brand = brandEditText.getText().toString();
                if (brand.equals("")) {
                    snackbar = Snackbar
                            .make(snackView,
                                    R.string.enter_brand_prompt, Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    HomeFragmentDirections.ActionNavHomeToNavBrand action =
                            HomeFragmentDirections.actionNavHomeToNavBrand(brand);
                    Navigation.findNavController(v).navigate(action);
                }
            }
        };
    }

    private View.OnClickListener scanBtnOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = HomeFragmentDirections.actionNavHomeToNavScan();
                Navigation.findNavController(v).navigate(action);
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
            snackbar = Snackbar
                    .make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
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

    private int getIntFromLayout(TextInputEditText et) {
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

    private Float getFloatFromLayout(TextInputEditText et) {
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

    private String getStringFromLayout(TextInputEditText et) {
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
            pm.setRollLength(getFloatFromLayout(rollLengthEditText));
            pm.setRollLength_c(getIntFromLayout(rollLengthCheckBox));
            pm.setPackagePrice(getFloatFromLayout(packagePriceEditText));
            pm.setRollPrice(getFloatFromLayout(rollPriceEditText));
            pm.setRollPrice_c(getIntFromLayout(rollPriceCheckBox));
            pm.setPaperWeight(getFloatFromLayout(paperWeightEditText));
            pm.setPaperWeight_c(getIntFromLayout(paperWeightCheckBox));
            pm.setRollWeight(getFloatFromLayout(rollWeightEditText));
            pm.setRollWeight_c(getIntFromLayout(rollWeightCheckBox));
            pm.setKiloPrice(getFloatFromLayout(kiloPriceEditText));
            pm.setKiloPrice_c(getIntFromLayout(kiloPriceCheckBox));
            pm.setMeterPrice(getFloatFromLayout(meterPriceEditText));
            pm.setMeterPrice_c(getIntFromLayout(meterPriceCheckBox));
            pm.setSheetPrice(getFloatFromLayout(sheetPriceEditText));
            pm.setSheetPrice_c(getIntFromLayout(sheetPriceCheckBox));
            pm.setSupplier(getStringFromLayout(suppliersSpinner));
            pm.setComments(getStringFromLayout(commentEditText));
        } catch (Exception e) {
            snackbar = Snackbar
                    .make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        return pm;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.setFragmentResultListener("itemNoRequestKey", this,
                itemNoFragmentResultListener());
        fragmentManager.setFragmentResultListener("brandRequestKey", this,
                brandFragmentResultListener());
    }

    private FragmentResultListener itemNoFragmentResultListener() {
        return new FragmentResultListener() {

            /**
             * Callback used to handle results passed between fragments.
             *
             * @param itemNoRequestKey key used to store the result
             * @param bundle result passed to the callback
             */
            @Override
            public void onFragmentResult(@NonNull String itemNoRequestKey, @NonNull Bundle bundle) {
                String result = bundle.getString(ITEM_NO);

                try {
                    List<ProductModel> lpm = adapter.getProductModels("ITEM_NO=?", result);

                    if (lpm.size() == 0) {
                        itemNoEditText.setText(result);
                        populateLayoutFromProductModel(new ProductModel());
                        snackbar = Snackbar
                                .make(snackView,
                                        R.string.itemno_not_found, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        populateLayoutFromProductModel(lpm.get(0));
                    }
                } catch (Exception e) {
                    snackbar = Snackbar
                            .make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        };
    }

    private FragmentResultListener brandFragmentResultListener() {
        return new FragmentResultListener() {

            /**
             * Callback used to handle results passed between fragments.
             *
             * @param brandRequestKey key used to store the result
             * @param bundle result passed to the callback
             */
            @Override
            public void onFragmentResult(@NonNull String brandRequestKey, @NonNull Bundle bundle) {
                String result = bundle.getString(BRAND);

                try {
                    List<ProductModel> lpm = adapter.getProductModels("BRAND=?", result);

                    if (lpm.size() == 0) {
                        snackbar = Snackbar
                                .make(snackView,
                                        R.string.brand_not_found, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        populateLayoutFromProductModel(lpm.get(0));
                    }
                } catch (Exception e) {
                    snackbar = Snackbar
                            .make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        };
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Calculate all calculable fields
     */
    private boolean calculate() {
        // Sheet length = roll length / sheets pet roll (OK)
        boolean fSheetLength = false;
        try {
            fSheetLength = divide(rollLengthEditText,
                    rollSheetsEditText,
                    sheetLengthEditText, sheetLengthCheckBox, 1000);
        } catch (Exception ignored) {
        }
        if (!fSheetLength) {
            sheetLengthEditText.setText("0");
            sheetLengthCheckBox.setChecked(false);
        }

        // Roll length = sheet length * sheets per roll (OK)
        boolean fRollLength = false;
        try {
            fRollLength = multiply(sheetLengthEditText, rollSheetsEditText,
                    rollLengthEditText, rollLengthCheckBox, 1000);
        } catch (Exception ignored) {
        }
        if (!fRollLength) {
            rollLengthEditText.setText("0.0");
            rollLengthCheckBox.setChecked(false);
        }

        // Price per roll = price per package / rolls per package (OK)
        boolean fRollPrice = false;
        try {
            fRollPrice = divide(packagePriceEditText, packageRollsEditText,
                    rollPriceEditText, rollPriceCheckBox, 1);
        } catch (Exception ignored) {
        }
        if (!fRollPrice) {
            rollPriceEditText.setText("0.0");
            rollPriceCheckBox.setChecked(false);
        }

        // TODO Paper weight (g per m2) ???

        /**
         * Package weight 1088 g
         * Roll weigth = 1088 /  packagerolls 8 = 136 g
         * Sheet weight = 136 / rollsheets 255 = 0,5333... g
         *
         * Sheet area = sheet length 125 * sheet width 97 = 12125 mm2
         * Sheets per m2 = 1 / 12125 * 1000000 = 82,47
         *
         * g per m2 = 0,533.. * 82,47 = 44 g per m2
         */

        // TODO Roll weight

        // TODO Kilo price ???
//            boolean fKiloPrice = multiply(sheetLengthEditText, sheetLengthCheckBox, rollSheetsEditText, null, rollLengthEditText, rollLengthCheckBox, 100);

        // TODO Price per meter = price per package / rolls per package / roll length
        /**
         * Package price 34.95 kr
         * Package rolls 9
         * Roll Price 34.95 / 9 = 3,88 kr
         *
         * Roll length = 31 m
         * Meter price = 3,88 / 31 = 0,125
         */
        boolean fMeterPrice = false;
        try {
            fMeterPrice = divide(packagePriceEditText,
                    packageRollsEditText,
                    rollLengthEditText,
                    meterPriceEditText, meterPriceCheckBox, 1);
        } catch (Exception ignored) {
        }
        if (!fMeterPrice) {
            meterPriceEditText.setText("0.0");
            meterPriceCheckBox.setChecked(false);
        }

        // TODO Price per sheet = price per package / rolls pr package / sheets per roll
        boolean fSheetPrice = false;
        try {
            fSheetPrice = divide(packagePriceEditText,
                    packageRollsEditText,
                    rollSheetsEditText,
                    sheetPriceEditText, sheetPriceCheckBox, 1);
        } catch (Exception ignored) {
        }
        if (!fSheetPrice) {
            sheetPriceEditText.setText("0.0");
            sheetPriceCheckBox.setChecked(false);
        }

        return fSheetLength | fSheetPrice | fRollPrice | fMeterPrice | fSheetPrice;
    }

    private boolean multiply(TextInputEditText multiplicand, TextInputEditText multiplier, TextInputEditText product, CheckBox cb, int precision) {
        String s1, s2;
        String s3;

        if ((cb != null) && !(cb.isChecked())) {
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
        Objects.requireNonNull(cb).setChecked(true);
        return true;
    }

    private boolean divide(TextInputEditText dividend, TextInputEditText divisor, TextInputEditText quotient, CheckBox cb, int precision) {
        String s1, s2;
        String s3;

        if ((cb != null) && !(cb.isChecked())) {
            s3 = quotient.getText().toString();

            if ((!s3.isEmpty()) && (Float.parseFloat(s3) > 0)) {
                return false;
            }
        }

        // Then test for zero values
        s1 = dividend.getText().toString();
        if ((s1.isEmpty()) || (s1.equals("0") || (s1.equals("0.0")))) {
            return false;
        }
        s2 = divisor.getText().toString();
        if ((s2.isEmpty()) || (s2.equals("0") || (s2.equals("0.0")))) {
            return false;
        }

        // Now do the calculation
        float i3 = Float.parseFloat(s1) * precision / Float.parseFloat(s2);
        quotient.setText(String.valueOf(i3));
        Objects.requireNonNull(cb).setChecked(true);
        return true;
    }

    private boolean divide(TextInputEditText dividend,
                           TextInputEditText divisor,
                           TextInputEditText divisor2,
                           TextInputEditText quotient, CheckBox cb, int precision) {
        String s1, s2, s3, s4;

        if ((cb != null) && !(cb.isChecked())) {
            s4 = quotient.getText().toString();

            if ((!s4.isEmpty()) && (Float.parseFloat(s4) > 0)) {
                return false;
            }
        }

        // Then test for zero values
        s1 = dividend.getText().toString();
        if ((s1.isEmpty()) || (s1.equals("0") || (s1.equals("0.0")))) {
            return false;
        }
        s2 = divisor.getText().toString();
        if ((s2.isEmpty()) || (s2.equals("0") || (s2.equals("0.0")))) {
            return false;
        }
        s3 = divisor2.getText().toString();
        if ((s3.isEmpty()) || (s3.equals("0") || (s3.equals("0.0")))) {
            return false;
        }

        // Now do the calculation
        float fq = Float.parseFloat(s1) * precision / Float.parseFloat(s2) / Float.parseFloat(s3);
        quotient.setText(String.valueOf(fq));
        Objects.requireNonNull(cb).setChecked(true);
        return true;
    }
}
