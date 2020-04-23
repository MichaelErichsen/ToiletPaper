/*
 * Copyright (c) 2020. Michael Erichsen.
 */

/**
 * TODO Get partial brand name from intent
 * SELECT brand from virtual table
 * If only one, then return that name as intent
 * Else:
 * Display brand activity screen with list of brands
 * If one is selected then return that as intent
 */

package net.myerichsen.toiletpaper;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BrandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
    }
}
