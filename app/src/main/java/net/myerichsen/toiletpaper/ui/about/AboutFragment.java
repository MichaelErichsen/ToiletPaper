package net.myerichsen.toiletpaper.ui.about;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import net.myerichsen.toiletpaper.BuildConfig;
import net.myerichsen.toiletpaper.R;

import java.util.Objects;

/*
 * Copyright (c) 2020. Michael Erichsen. The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

/**
 * Display static information about the program
 */
public class AboutFragment extends Fragment {

    /**
     * Required empty public constructor
     */
    public AboutFragment() {
    }

    /**
     * @param inflater           Layout inflater
     * @param container          View Group
     * @param savedInstanceState Saved Instance state
     * @return The inflated view of the about fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        Context context = getContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(context));
        float fontSize = Float.parseFloat(preferences.getString("fontsize", "24"));

        ActionBar ab = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        TextView versionTextView = root.findViewById(R.id.versionTextView);
        versionTextView.setText(getString(R.string.version_text, BuildConfig.VERSION_NAME));
        versionTextView.setTextSize(fontSize);

        ((TextView) root.findViewById(R.id.textView)).setTextSize(fontSize);
        ((TextView) root.findViewById(R.id.textView18)).setTextSize(fontSize);
        ((TextView) root.findViewById(R.id.textView2)).setTextSize(fontSize);
        ((TextView) root.findViewById(R.id.textView3)).setTextSize(fontSize);
        ((TextView) root.findViewById(R.id.textView4)).setTextSize(fontSize);
        ((TextView) root.findViewById(R.id.textView5)).setTextSize(fontSize);
        ((TextView) root.findViewById(R.id.textView6)).setTextSize(fontSize);
        ((TextView) root.findViewById(R.id.textView7)).setTextSize(fontSize);
        ((TextView) root.findViewById(R.id.ftsTextView)).setTextSize(fontSize);
        ((TextView) root.findViewById(R.id.graphTextView)).setTextSize(fontSize);

        return root;
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
