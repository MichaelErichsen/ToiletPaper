package net.myerichsen.toiletpaper.ui.prices;

import android.content.Context;

import net.myerichsen.toiletpaper.TPDbAdapter;
import net.myerichsen.toiletpaper.ui.products.ProductModel;

import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

/**
 * Helper class for providing content for user interfaces created by
 * Android template wizards.
 */
public class PriceModel {

    /**
     * An array of price items.
     */
    public final List<PriceItem> ITEMS;

    public PriceModel(Context context, String itemNo, String brand) {
        ITEMS = new ArrayList<>();

        TPDbAdapter adapter = new TPDbAdapter(context);

        List<ProductModel> lpm;
        if ((itemNo != null) && (!itemNo.equals(""))) {
            lpm = adapter.getProductModels("ITEM_NO=?", itemNo, "TIME_STAMP");
        } else if ((brand != null) && (!brand.equals(""))) {
            lpm = adapter.getProductModels("BRAND=?=?", brand, "TIME_STAMP");
        } else return;

        for (ProductModel pm : lpm) {
            addItem(new PriceItem(pm.getItemNo(), pm.getBrand(), String.valueOf(pm.getPackagePrice()),
                    pm.getTimestamp(), pm.getUid()));
        }
    }

    private void addItem(PriceItem item) {
        ITEMS.add(item);
    }

    /**
     * An item representing a piece of content.
     */
    public static class PriceItem {
        public final String itemNo;
        public final String brand;
        public final String packagePrice;
        public final String timeStamp;
        public final int uid;

        PriceItem(String itemNo, String brand, String packagePrice, String timeStamp, int uid) {
            this.itemNo = itemNo;
            this.brand = brand;
            this.packagePrice = packagePrice;
            this.timeStamp = timeStamp;
            this.uid = uid;
        }

        @Override
        public String toString() {
            return brand + ", " + packagePrice + ", " + timeStamp;
        }
    }
}
