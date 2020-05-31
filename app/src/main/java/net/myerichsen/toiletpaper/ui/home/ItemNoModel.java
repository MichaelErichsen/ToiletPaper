/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

package net.myerichsen.toiletpaper.ui.home;

import android.content.Context;

import net.myerichsen.toiletpaper.TPDbAdapter;
import net.myerichsen.toiletpaper.ui.products.ProductModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 */
public class ItemNoModel {

    /**
     * An array of ItemNo items.
     */
    public final List<ItemNoItem> ITEMS;

    public ItemNoModel(Context context, String itemNo) {
        ITEMS = new ArrayList<>();

        TPDbAdapter adapter = new TPDbAdapter(context);

        List<ProductModel> lpm;
        if ((itemNo != null) && (!itemNo.equals(""))) {

            if (!(itemNo).endsWith("%")) {
                itemNo += "%";
            }

            lpm = adapter.getProductModels("ITEM_NO LIKE ?", itemNo, "TIME_STAMP");
        } else return;

        if (lpm.size() == 0) {
            return;
        }

        for (ProductModel pm : lpm) {
            addItem(new ItemNoItem(pm.getItemNo(),
                    pm.getBrand(), pm.getSupplier(),
                    pm.getTimestamp()));
        }
    }

    private void addItem(ItemNoItem item) {
        ITEMS.add(item);
    }

    /**
     * A ItemNo item representing a piece of content.
     */
    public static class ItemNoItem {
        public final String itemNo;
        public final String brand;
        public final String supplier;
        public final String timeStamp;
        // --Commented out by Inspection (31-05-2020 12:05):public final int uid;

        ItemNoItem(String itemNo, String brand, String supplier, String timeStamp) {
            this.itemNo = itemNo;
            this.brand = brand;
            this.supplier = supplier;
            this.timeStamp = timeStamp;
//            this.uid = uid;
        }

        @Override
        public String toString() {
            return brand + ", " + supplier + ", " + timeStamp;
        }
    }
}
