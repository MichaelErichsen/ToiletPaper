/*
 * Copyright (c) 2020. Michael Erichsen.
 */

package net.myerichsen.toiletpaper;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class SettingsActivity extends AppCompatActivity {

    // FIXME Back arrow displayed, but ignored
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Context context = getApplicationContext();
        final TPDbAdapter helper = new TPDbAdapter(context);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.settings, new SettingsFragment())
//                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ImageButton initLoadBrn = findViewById(R.id.initLoadBtn);
        initLoadBrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.doInitialLoad();
            }
        });

        // TODO Implement preferences
        RadioGroup inputFormatRg = findViewById(R.id.inputFormatRg);
        inputFormatRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Snackbar snackbar;

                switch (checkedId) {
                    case R.id.simpleInputRb:
                        snackbar = Snackbar
                                .make(findViewById(android.R.id.content), "Not implemented", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        break;
                    case R.id.advancedInputRb:
                        snackbar = Snackbar
                                .make(findViewById(android.R.id.content), "Only option implemented", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        break;
                    default:
                        snackbar = Snackbar
                                .make(findViewById(android.R.id.content), "Invalid button: " + checkedId, Snackbar.LENGTH_LONG);
                        snackbar.show();
                        break;
                }
            }
        });
    }

//    public static class SettingsFragment extends PreferenceFragmentCompat {
//        @Override
//        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
//            setPreferencesFromResource(R.xml.root_preferences, rootKey);
//        }
//    }
}