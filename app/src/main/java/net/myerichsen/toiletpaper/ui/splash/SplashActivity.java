package net.myerichsen.toiletpaper.ui.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import net.myerichsen.toiletpaper.BuildConfig;
import net.myerichsen.toiletpaper.MainActivity;
import net.myerichsen.toiletpaper.R;

import java.util.Objects;

/*
 * Copyright (c) 2020. Michael Erichsen. The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getApplicationContext()));
        boolean splashFlag = preferences.getBoolean("splashdisplay", true);

        if (splashFlag) {
            TextView splashTitleTextView = findViewById(R.id.splashTitleTextView);
            splashTitleTextView.setText(getString(R.string.version_text, BuildConfig.VERSION_NAME));

            Thread myThread = new Thread() {
                @Override
                public void run() {

                    try {
                        sleep(1200);
                    } catch (InterruptedException ignored) {
                    }
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            myThread.start();
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
