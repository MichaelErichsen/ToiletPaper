package net.myerichsen.toiletpaper.ui.home;

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
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class BrandModel {

    /**
     * An array of brand items.
     */
    public List<BrandItem> ITEMS;

    public BrandModel(Context context, String brand) {
        ITEMS = new ArrayList<>();

        TPDbAdapter adapter = new TPDbAdapter(context);

        List<ProductModel> lpm;
        if ((brand != null) && (!brand.equals(""))) {

            if (!(brand).endsWith("%")) {
                brand += "%";
            }

            lpm = adapter.getProductModels("BRAND LIKE ?", brand, "TIME_STAMP");
        } else return;

        if (lpm.size() == 0) {
            return;
        }

        for (ProductModel pm : lpm) {
            addItem(new BrandItem(pm.getItemNo(),
                    pm.getBrand(), pm.getSupplier(),
                    pm.getTimestamp(), pm.getUid()));
        }
    }

    private void addItem(BrandItem item) {
        ITEMS.add(item);
    }

    /**
     * A brand item representing a piece of content.
     */
    public static class BrandItem {
        public final String itemNo;
        public final String brand;
        public final String supplier;
        public final String timeStamp;
        public final int uid;

        BrandItem(String itemNo, String brand, String supplier, String timeStamp, int uid) {
            this.itemNo = itemNo;
            this.brand = brand;
            this.supplier = supplier;
            this.timeStamp = timeStamp;
            this.uid = uid;
        }

        @Override
        public String toString() {
            return itemNo + ", " + supplier + ", " + timeStamp;
        }

    }
}
