package net.myerichsen.toiletpaper.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.TPDbAdapter;
import net.myerichsen.toiletpaper.ui.suppliers.SupplierModel;

import java.util.List;
import java.util.Objects;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

/**
 * Settings screen
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    private View snackView;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        snackView = requireActivity().findViewById(android.R.id.content);
        setPreferencesFromResource(R.xml.preferences, rootKey);

        try {
            TPDbAdapter adapter = new TPDbAdapter(getContext());
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());

            SwitchPreferenceCompat advancedScreenPreference = findPreference("advancedscreen");
            Objects.requireNonNull(advancedScreenPreference).setOnPreferenceChangeListener(advancedScreenOnPreferenceChangeListener());

            Preference initialLoadPreference = findPreference("initialload");
            Objects.requireNonNull(initialLoadPreference).setOnPreferenceClickListener(initLoadOnClickListener(adapter));

            DropDownPreference fontSizePreference = findPreference("fontsize");
            String fontsize = preferences.getString("fontsize", "0");
            if (!fontsize.equals("0")) {
                Objects.requireNonNull(fontSizePreference).setSummary(getString(R.string.fontsize_preference_summary) + fontsize);
            }
            Objects.requireNonNull(fontSizePreference).setOnPreferenceChangeListener(fontSizeOnPreferenceChangeListener());

            DropDownPreference defaultSupplierPreference = findPreference("defaultsupplier");
            Objects.requireNonNull(defaultSupplierPreference).setSummary(preferences.getString("defaultsupplier",
                    "Vælg foretrukken butik fra databasen"));
            populateSupplierDropDown(adapter, defaultSupplierPreference);
            defaultSupplierPreference.setOnPreferenceChangeListener(defaultSupplierOnPreferenceChangeListener());
        } catch (Exception e) {
            Snackbar.make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG).show();
        }
    }

    private Preference.OnPreferenceChangeListener defaultSupplierOnPreferenceChangeListener() {
        return new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(getString(R.string.preferred_supplier) + newValue);
                return true;
            }
        };
    }

    private Preference.OnPreferenceChangeListener fontSizeOnPreferenceChangeListener() {
        return new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(getString(R.string.fontsize_preference_summary) + newValue);
                return true;
            }
        };
    }

    private void populateSupplierDropDown(TPDbAdapter adapter, DropDownPreference defaultSupplierPreference) {
        List<SupplierModel> lsm;
        SupplierModel sm;

        try {
            lsm = adapter.getSupplierModels();
        } catch (Exception e) {
            Snackbar.make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG).show();
            return;
        }

        int count = lsm.size();

        if (count == 0) {
            Snackbar.make(snackView, "Tabellen er tom", Snackbar.LENGTH_LONG).show();
            return;
        }

        String[] entries = new String[count];
        String[] entryValues = new String[count];

        for (int i = 0; i < count; i++) {
            sm = lsm.get(i);
            entries[i] = sm.getSupplier();
            entryValues[i] = sm.getSupplier();
        }

        defaultSupplierPreference.setEntries(entries);
        defaultSupplierPreference.setEntryValues(entryValues);
    }

    private Preference.OnPreferenceChangeListener advancedScreenOnPreferenceChangeListener() {
        return new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                return true;
            }
        };
    }

    private Preference.OnPreferenceClickListener initLoadOnClickListener(final TPDbAdapter adapter) {
        return new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                try {
                    Snackbar.make(snackView, R.string.please_be_a_patient, Snackbar.LENGTH_LONG).show();
                    adapter.doInitialLoad();
                    Snackbar.make(snackView, R.string.initial_load_done, Snackbar.LENGTH_LONG).show();
                    return true;
                } catch (Exception e) {
                    Snackbar.make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG).show();
                    return false;
                }
            }
        };
    }
}
