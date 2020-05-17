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
 * About fragment.
 */
public class AboutFragment extends Fragment {

    /**
     * Required empty public constructor
     */
    public AboutFragment() {
    }

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

        TextView textView = root.findViewById(R.id.textView);
        textView.setTextSize(fontSize);
        TextView textView18 = root.findViewById(R.id.textView18);
        textView18.setTextSize(fontSize);
        TextView textView2 = root.findViewById(R.id.textView2);
        textView2.setTextSize(fontSize);
        TextView textView3 = root.findViewById(R.id.textView3);
        textView3.setTextSize(fontSize);
        TextView textView4 = root.findViewById(R.id.textView4);
        textView4.setTextSize(fontSize);
        TextView textView5 = root.findViewById(R.id.textView5);
        textView5.setTextSize(fontSize);
        TextView textView6 = root.findViewById(R.id.textView6);
        textView6.setTextSize(fontSize);
        TextView textView7 = root.findViewById(R.id.textView7);
        textView7.setTextSize(fontSize);
        ((TextView) root.findViewById(R.id.ftsTextView)).setTextSize(fontSize);

        // TODO Also handle graphTextView
        return root;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
