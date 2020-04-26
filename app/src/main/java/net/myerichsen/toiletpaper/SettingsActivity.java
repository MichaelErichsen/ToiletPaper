/*
 * Copyright (c) 2020. Michael Erichsen.
 */

package net.myerichsen.toiletpaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Context context = getApplicationContext();
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final TPDbAdapter adapter = new TPDbAdapter(context);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ImageButton initLoadBtn = findViewById(R.id.initLoadBtn);
        initLoadBtn.setOnClickListener(initLoadOnClickListener(adapter));

        RadioGroup inputFormatRg = findViewById(R.id.inputFormatRg);
        boolean advancedInputKey = sharedPref.getBoolean(getString(R.string.advanced_input_key), false);
        inputFormatRg.check(advancedInputKey ? R.id.advancedInputRb : R.id.simpleInputRb);
        inputFormatRg.setOnCheckedChangeListener(inputFormatOnCheckedListener());
    }

    // FIXME Strange return code when empty
    private View.OnClickListener initLoadOnClickListener(final TPDbAdapter adapter) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                try {
                    adapter.initialSupplierLoad();
                } catch (Exception e) {
                    sb.append(e.getMessage()).append("\n");
                }

                try {
                    adapter.initialProductLoad();
                    sb.append(R.string.initial_load_done);

                } catch (Exception e) {
                    sb.append(e.getMessage());
                }
                Snackbar snackbar = Snackbar
                        .make(findViewById(android.R.id.content), sb.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        };
    }

    private RadioGroup.OnCheckedChangeListener inputFormatOnCheckedListener() {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences.Editor editor = sharedPref.edit();
                Snackbar snackbar;

                switch (checkedId) {
                    case R.id.simpleInputRb:
                        editor.putBoolean(getString(R.string.advanced_input_key), false);
                        editor.apply();
                        break;
                    case R.id.advancedInputRb:
                        editor.putBoolean(getString(R.string.advanced_input_key), true);
                        editor.apply();
                        break;
                    default:
                        snackbar = Snackbar
                                .make(findViewById(android.R.id.content), "Invalid button: " + checkedId, Snackbar.LENGTH_LONG);
                        snackbar.show();
                        break;
                }
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}