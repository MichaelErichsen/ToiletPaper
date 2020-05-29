/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

package net.myerichsen.toiletpaper.ui.settings;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import com.google.android.material.snackbar.Snackbar;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.TPDbAdapter;
import net.myerichsen.toiletpaper.ui.suppliers.SupplierModel;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

/**
 * Settings screen
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    private View snackView;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        snackView = requireActivity().findViewById(android.R.id.content);
        setPreferencesFromResource(R.xml.preferences, rootKey);
        ProgressBar initialLoadProgressBar = requireActivity().findViewById(R.id.initialLoadProgressBar);
        initialLoadProgressBar.setVisibility(View.GONE);

        try {
            TPDbAdapter adapter = new TPDbAdapter(getContext());
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());

            SwitchPreferenceCompat advancedScreenPreference = findPreference("advancedscreen");
            Objects.requireNonNull(advancedScreenPreference).setOnPreferenceChangeListener(advancedScreenOnPreferenceChangeListener());

            Preference initialLoadPreference = findPreference("initialload");
            Objects.requireNonNull(initialLoadPreference).setOnPreferenceClickListener(initLoadOnClickListener());

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

    private Preference.OnPreferenceClickListener initLoadOnClickListener() {
        return new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                try {
                    InitLoadTask initLoadTask = new InitLoadTask((SettingsActivity) getActivity());
                    initLoadTask.execute();
//                    Snackbar.make(snackView, R.string.initial_load_done, Snackbar.LENGTH_LONG).show();
                    return true;
                } catch (Exception e) {
                    Snackbar.make(snackView, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG).show();
                    return false;
                }
            }

        };
    }

    private static class InitLoadTask extends AsyncTask<Void, Void, String> {
        private final WeakReference<SettingsActivity> activityReference;
        private final TPDbAdapter adapter;
        private ProgressBar initialLoadProgressBar;
        private final View snackView;

        // only retain a weak reference to the activity
        InitLoadTask(SettingsActivity context) {
            activityReference = new WeakReference<>(context);
            adapter = new TPDbAdapter(context);
            initialLoadProgressBar = context.findViewById(R.id.initialLoadProgressBar);
            snackView = context.findViewById(android.R.id.content);
        }

        /**
         * Runs on the UI thread before {@link #doInBackground}.
         * Invoked directly by {@link #execute} or {@link #executeOnExecutor}.
         * The default version does nothing.
         *
         * @see #onPostExecute
         * @see #doInBackground
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Snackbar.make(snackView, R.string.please_be_a_patient, Snackbar.LENGTH_LONG).show();
            initialLoadProgressBar.setVisibility(View.VISIBLE);
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This will normally run on a background thread. But to better
         * support testing frameworks, it is recommended that this also tolerates
         * direct execution on the foreground thread, as part of the {@link #execute} call.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param voids The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected String doInBackground(Void... voids) {
            try {
                adapter.doInitialLoad();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.
         * To better support testing frameworks, it is recommended that this be
         * written to tolerate direct execution as part of the execute() call.
         * The default version does nothing.</p>
         *
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param s The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            initialLoadProgressBar.setVisibility(View.GONE);
            initialLoadProgressBar = null;
            Snackbar.make(snackView, R.string.initial_load_done, Snackbar.LENGTH_LONG).show();
            // get a reference to the activity if it is still there
//            SettingsActivity activity = activityReference.get();
//            if (activity == null || activity.isFinishing()) return;
        }
    }
}