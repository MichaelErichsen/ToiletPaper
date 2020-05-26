package net.myerichsen.toiletpaper.ui.about;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import net.myerichsen.toiletpaper.BuildConfig;
import net.myerichsen.toiletpaper.R;

import java.util.Objects;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
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
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null. This will be called between
     * {@link #onCreate(Bundle)} and {@link #onViewCreated(View, Bundle)}.
     *
     * <p>It is recommended to <strong>only</strong> inflate the layout in this method and move
     * logic that operates on the returned View to {@link #onViewCreated(View, Bundle)}.
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(context));
        float fontSize = Float.parseFloat(preferences.getString("fontsize", "24"));

        TextView versionTextView = view.findViewById(R.id.versionTextView);
        versionTextView.setText(getString(R.string.version_text, BuildConfig.VERSION_NAME));
        versionTextView.setTextSize(fontSize);

        ((TextView) view.findViewById(R.id.textView)).setTextSize(fontSize);
        ((TextView) view.findViewById(R.id.textView18)).setTextSize(fontSize);
        ((TextView) view.findViewById(R.id.textView2)).setTextSize(fontSize);
        ((TextView) view.findViewById(R.id.textView3)).setTextSize(fontSize);
        ((TextView) view.findViewById(R.id.textView4)).setTextSize(fontSize);
        ((TextView) view.findViewById(R.id.textView5)).setTextSize(fontSize);
        ((TextView) view.findViewById(R.id.textView6)).setTextSize(fontSize);
        ((TextView) view.findViewById(R.id.textView7)).setTextSize(fontSize);
        ((TextView) view.findViewById(R.id.ftsTextView)).setTextSize(fontSize);
        ((TextView) view.findViewById(R.id.graphTextView)).setTextSize(fontSize);

    }
}
