/*
 * Copyright (c) 2020. Michael Erichsen.
 */

package net.myerichsen.toiletpaper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import net.myerichsen.toiletpaper.ui.products.ProductModel;
import net.myerichsen.toiletpaper.ui.suppliers.SupplierModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Database helper for product and supplier tables
 */
public class TPDbAdapter {
    private final TpDbHelper tpDbHelper;
    private final String[] pdColumns = {TpDbHelper.UID, TpDbHelper.LAYERS, TpDbHelper.PACKAGE_ROLLS,
            TpDbHelper.ROLL_SHEETS, TpDbHelper.SHEET_WIDTH, TpDbHelper.SHEET_LENGTH,
            TpDbHelper.SHEET_LENGTH_C, TpDbHelper.ROLL_LENGTH, TpDbHelper.ROLL_LENGTH_C,
            TpDbHelper.PACKAGE_PRICE, TpDbHelper.ROLL_PRICE, TpDbHelper.ROLL_PRICE_C,
            TpDbHelper.PAPER_WEIGHT, TpDbHelper.PAPER_WEIGHT_C, TpDbHelper.KILO_PRICE,
            TpDbHelper.KILO_PRICE_C, TpDbHelper.METER_PRICE, TpDbHelper.METER_PRICE_C,
            TpDbHelper.SHEET_PRICE, TpDbHelper.SHEET_PRICE_C, TpDbHelper.SUPPLIER,
            TpDbHelper.COMMENTS, TpDbHelper.ITEM_NO, TpDbHelper.BRAND,
            TpDbHelper.TIME_STAMP};
    private final String[] sdColumns = {TpDbHelper.SUPPLIER,
            TpDbHelper.CHAIN, TpDbHelper.TIME_STAMP};
    private final String[] countColumn = {"COUNT(*)"};
    private final Context context;

    /**
     * Constructor
     *
     * @param context
     */
    public TPDbAdapter(Context context) {
        tpDbHelper = new TpDbHelper(context);
        this.context = context;
    }

    /**
     * Get product data
     *
     * @param selection e.g "BRAND=?"
     * @param column
     * @return List of columns in record
     */
    public List<ProductModel> getProductModels(String selection, String column) {
        List<ProductModel> lpm = new ArrayList<>();

        SQLiteDatabase db = tpDbHelper.getReadableDatabase();

        String[] args = {column};
        Cursor cursor = db.query(TpDbHelper.TABLE_PRODUCT, pdColumns, selection, args, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                lpm.add(populateProductModel(cursor));
            }
        }
        cursor.close();

        return lpm;
    }

    /**
     * Insert a product row
     */
    public void insertData(ProductModel pm) {
        SQLiteDatabase db = tpDbHelper.getWritableDatabase();
        ContentValues contentValues = extractData(pm);
        db.insert(TpDbHelper.TABLE_PRODUCT, null, contentValues);
    }

    /**
     * Insert a supplier row
     */
    public void insertData(SupplierModel sm) {
        SQLiteDatabase db = tpDbHelper.getWritableDatabase();
        ContentValues contentValues = extractData(sm);
        db.insert(TpDbHelper.TABLE_SUPPLIER, null, contentValues);
    }

    /**
     * Get all data from product table
     *
     * @return List of columns in record
     */
    public List<ProductModel> getProductModels() {
        List<ProductModel> lpm = new ArrayList<>();

        SQLiteDatabase db = tpDbHelper.getReadableDatabase();

        Cursor cursor = db.query(TpDbHelper.TABLE_PRODUCT, pdColumns, null,
                null, null, null, TpDbHelper.BRAND);

        while (cursor.moveToNext()) {
            lpm.add(populateProductModel(cursor));
        }
        cursor.close();
        return lpm;
    }

    /**
     * Do an initial load
     */
    public void doInitialLoad() throws Exception {
        tpDbHelper.loadInitialData();
    }

    /**
     * Get all data from supplier table
     *
     * @return List of supplier data
     */
    public List<SupplierModel> getSupplierModels() throws Exception {
        List<SupplierModel> lsm = new ArrayList<>();

        try {
            SQLiteDatabase db = tpDbHelper.getReadableDatabase();

            Cursor cursor = db.query(TpDbHelper.TABLE_SUPPLIER, sdColumns, null,
                    null, null, null, TpDbHelper.SUPPLIER);

            while (cursor.moveToNext()) {
                lsm.add(populateSupplierModel(cursor));
            }

            cursor.close();
        } catch (Exception e) {
            throw e;
        }

        return lsm;
    }

    /**
     * Extract supplier data into content value object
     *
     * @param sm Supplier model
     * @return ContentValues
     */
    private ContentValues extractData(SupplierModel sm) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TpDbHelper.SUPPLIER, sm.getSupplier());
        contentValues.put(TpDbHelper.CHAIN, sm.getChain());
        contentValues.put(TpDbHelper.TIME_STAMP, sm.getTimestamp());
        return contentValues;
    }

    /**
     * Extract product data into Content Value object
     *
     * @param pm Product data
     * @return ContentValues
     */
    private ContentValues extractData(ProductModel pm) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TpDbHelper.LAYERS, pm.getLayers());
        contentValues.put(TpDbHelper.PACKAGE_ROLLS, pm.getPackageRolls());
        contentValues.put(TpDbHelper.ROLL_SHEETS, pm.getRollSheets());
        contentValues.put(TpDbHelper.SHEET_WIDTH, pm.getSheetWidth());
        contentValues.put(TpDbHelper.SHEET_LENGTH, pm.getSheetLength());
        contentValues.put(TpDbHelper.SHEET_LENGTH_C, pm.getSheetLength_c());
        contentValues.put(TpDbHelper.ROLL_LENGTH, pm.getRollLength());
        contentValues.put(TpDbHelper.ROLL_LENGTH_C, pm.getRollLength_c());
        contentValues.put(TpDbHelper.PACKAGE_PRICE, pm.getPackagePrice());
        contentValues.put(TpDbHelper.ROLL_PRICE, pm.getRollPrice());
        contentValues.put(TpDbHelper.ROLL_PRICE_C, pm.getRollPrice_c());
        contentValues.put(TpDbHelper.PAPER_WEIGHT, pm.getPaperWeight());
        contentValues.put(TpDbHelper.PAPER_WEIGHT_C, pm.getPaperWeight_c());
        contentValues.put(TpDbHelper.KILO_PRICE, pm.getKiloPrice());
        contentValues.put(TpDbHelper.KILO_PRICE_C, pm.getKiloPrice_c());
        contentValues.put(TpDbHelper.METER_PRICE, pm.getMeterPrice());
        contentValues.put(TpDbHelper.METER_PRICE_C, pm.getMeterPrice_c());
        contentValues.put(TpDbHelper.SHEET_PRICE, pm.getSheetPrice());
        contentValues.put(TpDbHelper.SHEET_PRICE_C, pm.getSheetPrice_c());
        contentValues.put(TpDbHelper.SUPPLIER, pm.getSupplier());
        contentValues.put(TpDbHelper.COMMENTS, pm.getComments());
        contentValues.put(TpDbHelper.ITEM_NO, pm.getItemNo());
        contentValues.put(TpDbHelper.BRAND, pm.getBrand());
        contentValues.put(TpDbHelper.TIME_STAMP, pm.getTimestamp());
        return contentValues;
    }

    /**
     * Populate supplier data from database table
     *
     * @param cursor Database cursor
     * @return Supplier data
     */
    private SupplierModel populateSupplierModel(Cursor cursor) {
        SupplierModel sm = new SupplierModel();
        sm.setSupplier(cursor.getString(cursor.getColumnIndex(TpDbHelper.SUPPLIER)));
        sm.setChain(cursor.getString(cursor.getColumnIndex(TpDbHelper.CHAIN)));
        sm.setTimestamp(cursor.getString(cursor.getColumnIndex(TpDbHelper.TIME_STAMP)));
        return sm;
    }

    /**
     * Populate product data from database table
     *
     * @param cursor Database cursor
     * @return product data
     */
    private ProductModel populateProductModel(Cursor cursor) {
        ProductModel pm = new ProductModel();
        pm.setUid(cursor.getInt(cursor.getColumnIndex(TpDbHelper.UID)));
        pm.setLayers(cursor.getInt(cursor.getColumnIndex(TpDbHelper.LAYERS)));
        pm.setPackageRolls(cursor.getInt(cursor.getColumnIndex(TpDbHelper.PACKAGE_ROLLS)));
        pm.setRollSheets(cursor.getInt(cursor.getColumnIndex(TpDbHelper.ROLL_SHEETS)));
        pm.setSheetWidth(cursor.getInt(cursor.getColumnIndex(TpDbHelper.SHEET_WIDTH)));
        pm.setSheetLength(cursor.getInt(cursor.getColumnIndex(TpDbHelper.SHEET_LENGTH)));
        pm.setSheetLength_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.ROLL_LENGTH_C)));
        pm.setRollLength(cursor.getInt(cursor.getColumnIndex(TpDbHelper.ROLL_LENGTH)));
        pm.setRollLength_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.SHEET_LENGTH_C)));
        pm.setPackagePrice(cursor.getFloat(cursor.getColumnIndex(TpDbHelper.PACKAGE_PRICE)));
        pm.setRollPrice(cursor.getFloat(cursor.getColumnIndex(TpDbHelper.ROLL_PRICE)));
        pm.setRollPrice_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.ROLL_PRICE_C)));
        pm.setPaperWeight(cursor.getFloat(cursor.getColumnIndex(TpDbHelper.PAPER_WEIGHT)));
        pm.setPaperWeight_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.PAPER_WEIGHT_C)));
        pm.setKiloPrice(cursor.getFloat(cursor.getColumnIndex(TpDbHelper.KILO_PRICE)));
        pm.setKiloPrice_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.KILO_PRICE_C)));
        pm.setMeterPrice(cursor.getFloat(cursor.getColumnIndex(TpDbHelper.METER_PRICE)));
        pm.setMeterPrice_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.METER_PRICE_C)));
        pm.setSheetPrice(cursor.getFloat(cursor.getColumnIndex(TpDbHelper.SHEET_PRICE)));
        pm.setSheetPrice_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.SHEET_PRICE_C)));
        pm.setSupplier(cursor.getString(cursor.getColumnIndex(TpDbHelper.SUPPLIER)));
        pm.setComments(cursor.getString(cursor.getColumnIndex(TpDbHelper.COMMENTS)));
        pm.setItemNo(cursor.getString(cursor.getColumnIndex(TpDbHelper.ITEM_NO)));
        pm.setBrand(cursor.getString(cursor.getColumnIndex(TpDbHelper.BRAND)));
        pm.setTimestamp(cursor.getString(cursor.getColumnIndex(TpDbHelper.TIME_STAMP)));
        return pm;
    }

    /**
     * Delete row from product table
     *
     * @param uid Unique identifier
     * @return
     * @throws Exception
     */
    public int deleteProduct(int uid) throws Exception {
        int rc;
        try {
            SQLiteDatabase db = tpDbHelper.getWritableDatabase();
            String s = uid + "";
            String[] whereArgs = {s};
            Toast.makeText(context, "Deleting product", Toast.LENGTH_LONG).show();

            rc = db.delete(TpDbHelper.TABLE_PRODUCT, TpDbHelper.UID + " = ?", whereArgs);
        } catch (Exception e) {
            throw e;
        }
        return rc;
    }

    /**
     * Delete row from supplier table
     *
     * @param supplier
     * @return
     */
    public int deleteSupplier(String supplier) throws Exception {
        int rc;
        try {
            SQLiteDatabase db = tpDbHelper.getWritableDatabase();
            String[] whereArgs = {supplier};
            Toast.makeText(context, "Deletng supplier", Toast.LENGTH_LONG).show();
            rc = db.delete(TpDbHelper.TABLE_SUPPLIER, TpDbHelper.SUPPLIER + " = ?", whereArgs);
        } catch (Exception e) {
            throw e;
        }
        return rc;
    }

    public List<ProductModel> getProductModelsSorted(String sortKey, String sortFilter) throws Exception {
        Cursor cursor;
        List<ProductModel> lpm = new ArrayList<>();

        try {
            SQLiteDatabase db = tpDbHelper.getReadableDatabase();

            if (sortFilter.equals("ALL")) {
                cursor = db.query(TpDbHelper.TABLE_PRODUCT, pdColumns, null,
                        null, null, null, sortKey + " DESC");
            } else {
                String[] args = {sortFilter};
                cursor = db.query(TpDbHelper.TABLE_PRODUCT, pdColumns, "SUPPLIER=?", args,
                        null, null, sortKey + " DESC");
            }

            while (cursor.moveToNext()) {
                lpm.add(populateProductModel(cursor));
            }

            cursor.close();
        } catch (Exception e) {
            throw e;
        }
        return lpm;
    }

    /**
     * Get supplier date by supplier
     *
     * @param supplier
     * @return
     */
    public List<SupplierModel> getSupplierModels(String selection, String supplier) throws Exception {
        List<SupplierModel> lsm = new ArrayList<>();

        try {
            SQLiteDatabase db = tpDbHelper.getReadableDatabase();

            String[] args = {supplier};
            Cursor cursor = db.query(TpDbHelper.TABLE_SUPPLIER, sdColumns, selection, args,
                    null, null, null);

            if (cursor.getCount() > 0) {
                if (cursor.moveToNext()) {
                    lsm.add(populateSupplierModel(cursor));
                }
            }
            cursor.close();
        } catch (Exception e) {
            throw e;
        }
        return lsm;
    }

    /**
     * Inner helper class
     */
    static class TpDbHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "TOILET_PAPER_DATABASE";
        private static final String TABLE_PRODUCT = "TABLE_PRODUCT";
        private static final String UID = "UID";
        private static final String LAYERS = "LAYERS";
        private static final String PACKAGE_ROLLS = "PACKAGE_ROLLS";
        private static final String ROLL_SHEETS = "ROLL_SHEETS";
        private static final String SHEET_WIDTH = "SHEET_WIDTH";
        private static final String SHEET_LENGTH = "SHEET_LENGTH";
        private static final String SHEET_LENGTH_C = "SHEET_LENGTH_C";
        private static final String ROLL_LENGTH = "ROLL_LENGTH";
        private static final String ROLL_LENGTH_C = "ROLL_LENGTH_C";
        private static final String PACKAGE_PRICE = "PACKAGE_PRICE";
        private static final String ROLL_PRICE = "ROLL_PRICE";
        private static final String ROLL_PRICE_C = "ROLL_PRICE_C";
        private static final String PAPER_WEIGHT = "PAPER_WEIGHT";
        private static final String PAPER_WEIGHT_C = "PAPER_WEIGHT_C";
        private static final String KILO_PRICE = "KILO_PRICE";
        private static final String KILO_PRICE_C = "KILO_PRICE_C";
        private static final String METER_PRICE = "METER_PRICE";
        private static final String METER_PRICE_C = "METER_PRICE_C";
        private static final String SHEET_PRICE = "SHEET_PRICE";
        private static final String SHEET_PRICE_C = "SHEET_PRICE_C";
        private static final String SUPPLIER = "SUPPLIER";
        private static final String COMMENTS = "COMMENTS";
        private static final String ITEM_NO = "ITEM_NO";
        private static final String BRAND = "BRAND";
        private static final String TIME_STAMP = "TIME_STAMP";

        private static final String TABLE_SUPPLIER = "TABLE_SUPPLIER";
        private static final String CHAIN = "CHAIN";

        private static final int DATABASE_Version = 3;    // Database Version
        private static final String CREATE_PRODUCT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCT +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LAYERS + " INTEGER, " +
                PACKAGE_ROLLS + " INTEGER, " +
                ROLL_SHEETS + " INTEGER, " +
                SHEET_WIDTH + " INTEGER, " +
                SHEET_LENGTH + " INTEGER, " +
                SHEET_LENGTH_C + " INTEGER, " +
                ROLL_LENGTH + " NUMERIC, " +
                ROLL_LENGTH_C + " INTEGER, " +
                PACKAGE_PRICE + " NUMERIC, " +
                ROLL_PRICE + " NUMERIC, " +
                ROLL_PRICE_C + " INTEGER, " +
                PAPER_WEIGHT + " NUMERIC, " +
                PAPER_WEIGHT_C + " INTEGER, " +
                KILO_PRICE + " NUMERIC, " +
                KILO_PRICE_C + " INTEGER, " +
                METER_PRICE + " NUMERIC, " +
                METER_PRICE_C + " INTEGER, " +
                SHEET_PRICE + " NUMERIC, " +
                SHEET_PRICE_C + " INTEGER, " +
                SUPPLIER + " TEXT, " +
                COMMENTS + " TEXT, " +
                ITEM_NO + " TEXT, " +
                BRAND + " TEXT, " +
                TIME_STAMP + " TEXT);";

        private static final String DROP_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + TABLE_PRODUCT;

        private static final String CREATE_SUPPLIER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SUPPLIER +
                " (" + SUPPLIER + " TEXT PRIMARY KEY, " +
                CHAIN + " TEXT, " +
                TIME_STAMP + " TEXT);";

        private static final String DROP_SUPPLIER_TABLE = "DROP TABLE IF EXISTS " + TABLE_SUPPLIER;

        private final Context context;

        /**
         * Constructor
         *
         * @param context
         */
        TpDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }


        /**
         * Called when database is created
         *
         * @param db
         */
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_SUPPLIER_TABLE);
                db.execSQL(CREATE_PRODUCT_TABLE);
//                db.execSQL(CREATE_VIRTUAL_PRDUCT_TABLE);
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        /**
         * Called when the database needs to be upgraded
         *
         * @param db
         * @param oldVersion
         * @param newVersion
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Toast.makeText(context, "OnUpgrade", Toast.LENGTH_LONG).show();
                db.execSQL(DROP_PRODUCT_TABLE);
                db.execSQL(DROP_SUPPLIER_TABLE);
//                db.execSQL(DROP_VIRTUAL_TABLE);
                onCreate(db);
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        /**
         * Initial data load
         */
        private void loadInitialData() throws Exception {
            try {
                TPDbAdapter tpHelper = new TPDbAdapter(context);
                SQLiteDatabase db = getWritableDatabase();

                onCreate(db);

                Cursor cursor = db.query(TABLE_SUPPLIER, tpHelper.countColumn, null,
                        null, null, null, null, null);

                if (cursor.getCount() > 0) {
                    if (cursor.moveToNext()) {
                        int count = cursor.getInt(cursor.getColumnIndex("COUNT(*)"));

                        if (count == 0) {
                            loadSuppliers(tpHelper);
                        } else {
                            Toast.makeText(context, "Supplier table not empty. Contains " + count + " suppliers",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
                cursor.close();


                cursor = db.query(TABLE_PRODUCT, tpHelper.countColumn, null,
                        null, null, null, null, null);
                if (cursor.getCount() > 0) {
                    if (cursor.moveToNext()) {
                        int count = cursor.getInt(cursor.getColumnIndex("COUNT(*)"));

                        if (count == 0) {
                            loadProducts(tpHelper);
                        } else {
                            Toast.makeText(context, "Product table not empty. Contains " + count + " products",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
                cursor.close();
            } catch (Exception e) {
                throw e;
            }

        }

        private void loadProducts(TPDbAdapter tpHelper) {
            ProductModel pm;
            pm = new ProductModel("5700384289095", "Irma Tusindfryd Toiletpapir",
                    3, 8, 233, 97, 125, 0, (float) 29.1,
                    0, 41, (float) 5.125, 1, 48,
                    0, (float) 31.64, 0, (float) 0.1761, 1, (float) 0.022, 1,
                    "Kvickly Helsinge", "Produceret i Sverige");
            tpHelper.insertData(pm);

            pm = new ProductModel("7311041080306", "First Price Toiletpapir 2-lags",
                    2, 8, 220, 96, 125, 1, (float) 27.5,
                    0, (float) 15.95, (float) 1.99, 1, 36,
                    0, 0, 0, (float) 0.0725, 1, (float) 0.009, 1,
                    "Spar Vejby Strand", "Produceret i Litauen");
            tpHelper.insertData(pm);

            pm = new ProductModel("5705830002242", "REMA 1000 Toiletpapir",
                    2, 8, 282, 97, 125, 0, (float) 35.25,
                    0, (float) 9.75, (float) 1.21875, 1, (float) 32.6,
                    0, (float) 10.93, 0, (float) 0.0346, 1, (float) 0.004322, 1,
                    "Rema Vejby", "Produceret i Sverige");
            tpHelper.insertData(pm);

            pm = new ProductModel("170190", "Lambi Classic Toilet Paper",
                    3, 9, 255, 0, 125, 1, (float) 31.9,
                    0, (float) 34.95, (float) 3.88, 1, (float) 0,
                    0, (float) 41.26, 0, (float) 0.1217, 1, (float) 0.01523, 1,
                    "Rema Vejby", "Produceret i Sverige");
            tpHelper.insertData(pm);

            pm = new ProductModel("WW-166808", "Staples 29 m",
                    2, 8, 250, 96, 115, 0, (float) 28.75,
                    0, (float) 24.94, (float) 3.12, 1,
                    (float) 16.50, 0, (float) 188.94, 0, (float) 0.10843, 1, (float) 0.1247, 1,
                    "Staples", "Online");
            tpHelper.insertData(pm);

            pm = new ProductModel("WW-114649", "Tork Advanced Extra Soft T4",
                    3, 42, 248, 94, 140, 1, (float) 34.70, 0,
                    (float) 386.85, (float) 9.21, 1,
                    0, 0, 0, 0, (float) 0.26544, 1, (float) 0.03714, 1,
                    "Staples", "Online");
            tpHelper.insertData(pm);

            pm = new ProductModel("WW-101012", "Scott® Performance 350",
                    3, 36, 350, 95, 125, 0, (float) 43.75, 0,
                    (float) 589, (float) 9.21, 1,
                    0, 0, 0, 0, (float) 0.46746, 1, (float) 0.48433, 1,
                    "Staples", "Online");
            tpHelper.insertData(pm);
        }

        private void loadSuppliers(TPDbAdapter tpHelper) {
            SupplierModel sm;
            sm = new SupplierModel("Bilka Hillerød", "Salling");
            tpHelper.insertData(sm);
            sm = new SupplierModel("Føtex Hillerød", "Salling");
            tpHelper.insertData(sm);
            sm = new SupplierModel("Kvickly Helsinge", "Coop");
            tpHelper.insertData(sm);
            sm = new SupplierModel("Netto Vejby", "Salling");
            tpHelper.insertData(sm);
            sm = new SupplierModel("Rema Vejby", "REMA 1000");
            tpHelper.insertData(sm);
            sm = new SupplierModel("Staples", "Staples");
            tpHelper.insertData(sm);
            sm = new SupplierModel("Spar Karsemose", "Dagrofa");
            tpHelper.insertData(sm);
            sm = new SupplierModel("Spar Vejby Strand", "Dagrofa");
            tpHelper.insertData(sm);
            sm = new SupplierModel("SuperBest Allerød", "SuperBest");
            tpHelper.insertData(sm);
            sm = new SupplierModel("Superbrugsen Gilleleje", "Coop");
            tpHelper.insertData(sm);
        }
    }
}
