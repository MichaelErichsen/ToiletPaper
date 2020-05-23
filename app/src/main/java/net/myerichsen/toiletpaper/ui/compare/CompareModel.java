package net.myerichsen.toiletpaper.ui.compare;

import android.content.Context;

import net.myerichsen.toiletpaper.R;
import net.myerichsen.toiletpaper.TPDbAdapter;
import net.myerichsen.toiletpaper.ui.products.ProductModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

/**
 * Helper class for providing content for user interfaces created by
 * Android template wizards.
 */
public class CompareModel {

    /**
     * An array of Compare items.
     */
    public List<CompareItem> ITEMS;

    public CompareModel(Context context, String sortFilter, String sortKey) {
        if (sortFilter == null)
            return;

        ITEMS = new ArrayList<>();

        TPDbAdapter adapter = new TPDbAdapter(context);

        List<ProductModel> lpm;
        if ((sortFilter.equals("ALL"))) {
            lpm = adapter.getProductModels(sortKey);
        } else {
            lpm = adapter.getProductModels("SUPPLIER=?", sortFilter, sortKey);
        }

        // Header
        addItem(new CompareItem(context.getString(R.string.item_no), context.getString(R.string.brand),
                context.getString(R.string.kilo_price), context.getString(R.string.meter_price),
                0, ""));

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(HALF_UP);

        for (ProductModel pm : lpm) {
            addItem(new CompareItem(pm.getItemNo(), pm.getBrand(), df.format(pm.getKiloPrice()),
                    df.format(pm.getMeterPrice()), pm.getUid(), sortFilter));
        }
    }

    private void addItem(CompareItem item) {
        ITEMS.add(item);
    }

    /**
     * A Compare item representing a piece of content.
     */
    public static class CompareItem {
        public final String clItemNo;
        public final String clBrand;
        public final String clKiloPrice;
        public final String clMeterPrice;
        public final int uid;
        final String sortFilter;

        CompareItem(String clItemNo, String clBrand, String clKiloPrice, String clMeterPrice, int uid,
                    String sortFilter) {
            this.clItemNo = clItemNo;
            this.clBrand = clBrand;
            this.clKiloPrice = clKiloPrice;
            this.clMeterPrice = clMeterPrice;
            this.uid = uid;
            this.sortFilter = sortFilter;
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public String toString() {
            return clBrand + ", " + clItemNo + ", " + clKiloPrice + ", " + clMeterPrice + ", " +
                    uid + ", " + sortFilter;
        }
    }
}