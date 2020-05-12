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
 * Copyright (c) 2020. Michael Erichsen. The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

public class SettingsFragment extends PreferenceFragmentCompat {
    private Snackbar snackbar;
    private View snackView;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        snackView = requireActivity().findViewById(android.R.id.content);
        setPreferencesFromResource(R.xml.preferences, rootKey);

        try {
            TPDbAdapter adapter = new TPDbAdapter(getContext());
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());

            // TODO Implement simple and advanced screens
            SwitchPreferenceCompat advancedScreenPreference = findPreference("advancedscreen");
            Objects.requireNonNull(advancedScreenPreference).setOnPreferenceChangeListener(advancedScreenOnPreferenceChangeListener());

            Preference initialLoadPreference = findPreference("initialload");
            Objects.requireNonNull(initialLoadPreference).setOnPreferenceClickListener(initLoadOnClickListener(adapter));

            DropDownPreference fontSizePreference = findPreference("fontsize");
            String fontsize = preferences.getString("fontsize", "0");
            if (!fontsize.equals("0")) {
                Objects.requireNonNull(fontSizePreference).setSummary(getString(R.string.fontsize_preference_summary) + fontsize);
            }

            DropDownPreference defaultSupplierPreference = findPreference("defaultsupplier");
            Objects.requireNonNull(defaultSupplierPreference).setSummary(preferences.getString("defaultsupplier",
                    "Vælg foretrukken butik fra databasen"));
            populateSupplierDropDown(adapter, defaultSupplierPreference);
        } catch (Exception e) {
            snackbar = Snackbar
                    .make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void populateSupplierDropDown(TPDbAdapter adapter, DropDownPreference defaultSupplierPreference) {
        List<SupplierModel> lsm;
        SupplierModel sm;

        try {
            lsm = adapter.getSupplierModels();
        } catch (Exception e) {
            Snackbar snackbar = Snackbar
                    .make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        int count = lsm.size();

        if (count == 0) {
            Snackbar snackbar = Snackbar
                    .make(snackView, "No data in table", Snackbar.LENGTH_LONG);
            snackbar.show();
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
                return false;
            }
        };
    }

    private Preference.OnPreferenceClickListener initLoadOnClickListener(final TPDbAdapter adapter) {
        return new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                try {
                    adapter.doInitialLoad();
                    snackbar = Snackbar
                            .make(snackView, R.string.initial_load_done, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return true;
                } catch (Exception e) {
                    snackbar = Snackbar
                            .make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return false;
                }
            }
        };
    }
}
