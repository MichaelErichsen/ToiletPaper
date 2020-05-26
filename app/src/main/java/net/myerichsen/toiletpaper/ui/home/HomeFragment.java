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
import androidx.annotation.Nullable;
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

/**
 * Home screen of the toilet paper program.
 * <p>
 * This is used to display and maintain product data.
 * </p>
 */
public class HomeFragment extends Fragment {
    public static final String BRAND = "brand";
    public static final String ITEM_NO = "itemNo";

    private TPDbAdapter adapter;
    private Activity activity;
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
    private TextInputEditText packageWeightEditText;
    private CheckBox packageWeightCheckBox;
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

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null. This will be called between
     * {@link #onCreate(Bundle)} and {@link #onViewCreated(View, Bundle)}.
     *
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = getActivity();
        Context context = getContext();
        snackView = requireActivity().findViewById(android.R.id.content);

        // Set font sizes

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(context));
        float fontSize = Float.parseFloat(preferences.getString("fontsize", "24"));
        ((TextInputEditText) view.findViewById(R.id.brandEditText)).setTextSize(fontSize);
        ((TextInputEditText) view.findViewById(R.id.commentEditText)).setTextSize(fontSize);
        ((TextInputEditText) view.findViewById(R.id.itemNoEditText)).setTextSize(fontSize);
        ((TextInputEditText) view.findViewById(R.id.kiloPriceEditText)).setTextSize(fontSize);
        ((Button) view.findViewById(R.id.kiloPriceCheckBox)).setTextSize(fontSize);
        ((TextInputEditText) view.findViewById(R.id.meterPriceEditText)).setTextSize(fontSize);
        ((Button) view.findViewById(R.id.meterPriceCheckBox)).setTextSize(fontSize);
        ((TextInputEditText) view.findViewById(R.id.packagePriceEditText)).setTextSize(fontSize);
        ((TextInputEditText) view.findViewById(R.id.packageRollsEditText)).setTextSize(fontSize);
        ((TextInputEditText) view.findViewById(R.id.paperWeightEditText)).setTextSize(fontSize);
        ((Button) view.findViewById(R.id.paperWeightCheckBox)).setTextSize(fontSize);
        ((TextInputEditText) view.findViewById(R.id.packageWeightEditText)).setTextSize(fontSize);
        ((Button) view.findViewById(R.id.packageWeightCheckBox)).setTextSize(fontSize);
        ((TextInputEditText) view.findViewById(R.id.rollWeightEditText)).setTextSize(fontSize);
        ((Button) view.findViewById(R.id.rollWeightCheckBox)).setTextSize(fontSize);
        ((TextInputEditText) view.findViewById(R.id.rollLengthEditText)).setTextSize(fontSize);
        ((Button) view.findViewById(R.id.rollLengthCheckBox)).setTextSize(fontSize);
        ((TextInputEditText) view.findViewById(R.id.rollPriceEditText)).setTextSize(fontSize);
        ((Button) view.findViewById(R.id.rollPriceCheckBox)).setTextSize(fontSize);
        ((TextInputEditText) view.findViewById(R.id.rollSheetsEditText)).setTextSize(fontSize);
        ((TextInputEditText) view.findViewById(R.id.sheetLengthEditText)).setTextSize(fontSize);
        ((Button) view.findViewById(R.id.sheetLengthCheckBox)).setTextSize(fontSize);
        ((TextInputEditText) view.findViewById(R.id.sheetPriceEditText)).setTextSize(fontSize);
        ((Button) view.findViewById(R.id.sheetPriceCheckBox)).setTextSize(fontSize);
        ((TextInputEditText) view.findViewById(R.id.sheetWidthEditText)).setTextSize(fontSize);

        adapter = new TPDbAdapter(context);
        pm = new ProductModel();

        // Item no
        itemNoEditText = view.findViewById(R.id.itemNoEditText);

        // Brand
        brandEditText = view.findViewById(R.id.brandEditText);

        // Layers
        layersSpinner = view.findViewById(R.id.layersSpinner);
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
        packageRollsEditText = view.findViewById(R.id.packageRollsEditText);

        // Roll sheets
        rollSheetsEditText = view.findViewById(R.id.rollSheetsEditText);

        // Sheet width
        sheetWidthEditText = view.findViewById(R.id.sheetWidthEditText);

        // Sheet length
        sheetLengthEditText = view.findViewById(R.id.sheetLengthEditText);
        sheetLengthCheckBox = view.findViewById(R.id.sheetLengthCheckBox);
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
        rollLengthEditText = view.findViewById(R.id.rollLengthEditText);
        rollLengthCheckBox = view.findViewById(R.id.rollLengthCheckBox);
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
        packagePriceEditText = view.findViewById(R.id.packagePriceEditText);

        // Roll price
        rollPriceEditText = view.findViewById(R.id.rollPriceEditText);
        rollPriceCheckBox = view.findViewById(R.id.rollPriceCheckBox);
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
        paperWeightEditText = view.findViewById(R.id.paperWeightEditText);
        paperWeightCheckBox = view.findViewById(R.id.paperWeightCheckBox);
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

        // Package weight
        packageWeightEditText = view.findViewById(R.id.packageWeightEditText);
        packageWeightCheckBox = view.findViewById(R.id.packageWeightCheckBox);
        packageWeightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (packageWeightEditText.hasFocus())
                    packageWeightCheckBox.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // roll weight
        rollWeightEditText = view.findViewById(R.id.rollWeightEditText);
        rollWeightCheckBox = view.findViewById(R.id.rollWeightCheckBox);
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
        kiloPriceEditText = view.findViewById(R.id.kiloPriceEditText);
        kiloPriceCheckBox = view.findViewById(R.id.kiloPriceCheckBox);
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
        meterPriceEditText = view.findViewById(R.id.meterPriceEditText);
        meterPriceCheckBox = view.findViewById(R.id.meterPriceCheckBox);
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
        sheetPriceEditText = view.findViewById(R.id.sheetPriceEditText);
        sheetPriceCheckBox = view.findViewById(R.id.sheetPriceCheckBox);
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
        suppliersSpinner = view.findViewById(R.id.suppliersSpinner);

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
        commentEditText = view.findViewById(R.id.commentEditText);

        // Buttons
        AppCompatImageButton scanBtn = view.findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(scanBtnOnClickListener());

        AppCompatImageButton searchItemNoBtn = view.findViewById(R.id.searchItemNoBtn);
        searchItemNoBtn.setOnClickListener(searchItemNoBtnOnClickListener());

        AppCompatImageButton searchBrandBtn = view.findViewById(R.id.searchBrandBtn);
        searchBrandBtn.setOnClickListener(searchBrandBtnOnClickListener());

        AppCompatImageButton calculateBtn = view.findViewById(R.id.calculateBtn);
        calculateBtn.setOnClickListener(calculateBtnOnClickListener());

        AppCompatImageButton saveBtn = view.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(saveBtnOnClickListener());

        AppCompatImageButton googleBtn = view.findViewById(R.id.googleBtn);
        googleBtn.setOnClickListener(googleOnClickListener());

        AppCompatImageButton pricerunnerBtn = view.findViewById(R.id.pricerunnerBtn);
        pricerunnerBtn.setOnClickListener(priceRunnerOnClickListener());
    }

    private View.OnClickListener googleOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    brand = Objects.requireNonNull(brandEditText.getText()).toString();

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/search?q=\"" + brand + "\""));
                    startActivity(browserIntent);
                } catch (Exception e) {
                    Snackbar.make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG).show();
                }
            }
        };
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
                Snackbar.make(snackView, message, Snackbar.LENGTH_LONG).show();
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
                    Snackbar.make(snackView, Objects.requireNonNull(getString(R.string.home_fragment_save_message)),
                            Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {
                    Snackbar.make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG).show();
                }
            }
        };
    }

    private View.OnClickListener searchItemNoBtnOnClickListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                hideSoftKeyboard(activity);
                itemNo = Objects.requireNonNull(itemNoEditText.getText()).toString();
                if (itemNo.equals("")) {
                    Snackbar.make(snackView,
                            R.string.enter_itemno_prompt, Snackbar.LENGTH_LONG).show();
                } else {
                    HomeFragmentDirections.ActionNavHomeToNavItemNo action =
                            HomeFragmentDirections.actionNavHomeToNavItemNo(itemNo);
                    Navigation.findNavController(v).navigate(action);
                }
            }
        };
    }

    private View.OnClickListener searchBrandBtnOnClickListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                hideSoftKeyboard(activity);
                brand = Objects.requireNonNull(brandEditText.getText()).toString();
                if (brand.equals("")) {
                    Snackbar.make(snackView,
                            R.string.enter_brand_prompt, Snackbar.LENGTH_LONG).show();
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
            packageWeightEditText.setText(String.valueOf(pm.getPackageWeight()));
            packageWeightCheckBox.setChecked(pm.getPackageWeight_c() != 0);
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
            Snackbar.make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG).show();
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
        String s = Objects.requireNonNull(et.getText()).toString();

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
        String s = Objects.requireNonNull(et.getText()).toString();

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
        return Objects.requireNonNull(et.getText()).toString();
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
            pm.setPackageWeight(getFloatFromLayout(packageWeightEditText));
            pm.setPackageWeight_c(getIntFromLayout(packageWeightCheckBox));
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
            Snackbar.make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG).show();
        }

        return pm;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
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
                        Snackbar.make(snackView,
                                R.string.itemno_not_found, Snackbar.LENGTH_LONG).show();
                    } else {
                        populateLayoutFromProductModel(lpm.get(0));
                    }
                } catch (Exception e) {
                    Snackbar.make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG).show();
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
                        Snackbar.make(snackView,
                                R.string.brand_not_found, Snackbar.LENGTH_LONG).show();
                    } else {
                        populateLayoutFromProductModel(lpm.get(0));
                    }
                } catch (Exception e) {
                    Snackbar.make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG).show();
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

//        /**
//         * Package weight 1088 g
//         * Roll weigth = 1088 /  packagerolls 8 = 136 g
//         * Sheet weight = 136 / rollsheets 255 = 0,5333... g
//         *
//         * Sheet area = sheet length 125 * sheet width 97 = 12125 mm2
//         * Sheets per m2 = 1 / 12125 * 1000000 = 82,47
//         *
//         * g per m2 = 0,533.. * 82,47 = 44 g per m2
//         */

        boolean fRollWeight = false;
        try {
            fRollWeight = divide(packageWeightEditText, packageRollsEditText, rollWeightEditText,
                    rollWeightCheckBox, 1000);
        } catch (Exception ignored) {
        }
        if (!fRollWeight) {
            rollWeightEditText.setText("0.0");
            rollWeightCheckBox.setChecked(false);
        }

        //        /**
//         * Package price 34.95 kr
//         * Package rolls 9
//         * Roll Price 34.95 / 9 = 3,88 kr
//         *
//         * Roll length = 31 m
//         * Meter price = 3,88 / 31 = 0,125
//         */
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

        return fSheetLength | fSheetPrice | fRollPrice | fMeterPrice | fSheetPrice | fRollLength;
    }

    private boolean multiply(TextInputEditText multiplicand, TextInputEditText multiplier, TextInputEditText product, CheckBox cb, @SuppressWarnings("SameParameterValue") int precision) {
        String s1, s2;
        String s3;

        if ((cb != null) && !(cb.isChecked())) {
            s3 = Objects.requireNonNull(product.getText()).toString();

            if ((!s3.isEmpty()) && (Integer.parseInt(s3) > 0)) {
                return false;
            }
        }

        // Then test for zero values
        s1 = Objects.requireNonNull(multiplicand.getText()).toString();
        if ((s1.isEmpty()) || (s1.equals("0"))) {
            return false;
        }
        s2 = Objects.requireNonNull(multiplier.getText()).toString();
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
            s3 = Objects.requireNonNull(quotient.getText()).toString();

            if ((!s3.isEmpty()) && (Float.parseFloat(s3) > 0)) {
                return false;
            }
        }

        // Then test for zero values
        s1 = Objects.requireNonNull(dividend.getText()).toString();
        if ((s1.isEmpty()) || (s1.equals("0") || (s1.equals("0.0")))) {
            return false;
        }
        s2 = Objects.requireNonNull(divisor.getText()).toString();
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
                           TextInputEditText quotient, CheckBox cb, @SuppressWarnings("SameParameterValue") int precision) {
        String s1, s2, s3, s4;

        if ((cb != null) && !(cb.isChecked())) {
            s4 = Objects.requireNonNull(quotient.getText()).toString();

            if ((!s4.isEmpty()) && (Float.parseFloat(s4) > 0)) {
                return false;
            }
        }

        // Then test for zero values
        s1 = Objects.requireNonNull(dividend.getText()).toString();
        if ((s1.isEmpty()) || (s1.equals("0") || (s1.equals("0.0")))) {
            return false;
        }
        s2 = Objects.requireNonNull(divisor.getText()).toString();
        if ((s2.isEmpty()) || (s2.equals("0") || (s2.equals("0.0")))) {
            return false;
        }
        s3 = Objects.requireNonNull(divisor2.getText()).toString();
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
