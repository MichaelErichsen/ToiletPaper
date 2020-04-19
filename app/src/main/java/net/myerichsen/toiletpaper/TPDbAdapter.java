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

import net.myerichsen.toiletpaper.ui.products.ProductData;
import net.myerichsen.toiletpaper.ui.suppliers.SupplierData;

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

    /**
     * Constructor
     *
     * @param context
     */
    public TPDbAdapter(Context context) {
        tpDbHelper = new TpDbHelper(context);
    }

    /**
     * Get data by brand
     *
     * @return List of columns in record
     */
    public ProductData getProductDataByBrand(String brand) {
        ProductData pd = null;

        SQLiteDatabase db = tpDbHelper.getWritableDatabase();

        String[] args = {brand};
        Cursor cursor = db.query(TpDbHelper.TABLE_PRODUCT, pdColumns, "BRAND=?", args, null, null, null);

        if (cursor.getCount() > 0) {
            pd = populateProductData(cursor);
        }

        return pd;
    }


    /**
     * Insert a product row
     */
    public void insertData(ProductData pd) {
        SQLiteDatabase dbb = tpDbHelper.getWritableDatabase();
        ContentValues contentValues = extractData(pd);
        dbb.insert(TpDbHelper.TABLE_PRODUCT, null, contentValues);
    }

    /**
     * Insert a supplier row
     */
    private void insertData(SupplierData sd) {
        SQLiteDatabase dbb = tpDbHelper.getWritableDatabase();
        ContentValues contentValues = extractData(sd);
        dbb.insert(TpDbHelper.TABLE_SUPPLIER, null, contentValues);
    }

    /**
     * Get data by item number
     *
     * @return List of columns in record
     */
    public ProductData getProductDataByItemNo(String itemNo) {
        ProductData pd = null;

        SQLiteDatabase db = tpDbHelper.getWritableDatabase();

        String[] args = {itemNo};
        Cursor cursor = db.query(TpDbHelper.TABLE_PRODUCT, pdColumns, "ITEM_NO=?", args, null, null, null);

        if (cursor.getCount() > 0) {
            pd = populateProductData(cursor);
        }

        return pd;
    }

    /**
     * Get all data from product table
     *
     * @param context Application context
     * @return List of columns in record
     */
    public List<ProductData> getAllProductData(Context context) {
        List<ProductData> lpd = new ArrayList<>();

        SQLiteDatabase db = tpDbHelper.getWritableDatabase();

        Cursor cursor = db.query(TpDbHelper.TABLE_PRODUCT, pdColumns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            lpd.add(populateProductData(cursor));
        }
        return lpd;
    }

    /**
     * Do an inital load
     */
    public void doInitialLoad() {
        tpDbHelper.loadInitialData();
    }
    /**
     * Get all data from supplier table
     *
     * @param context Application context
     * @return List of supplier data
     */
    public List<SupplierData> getAllSupplierData(Context context) {
        List<SupplierData> lsd = new ArrayList<>();
        try {
            SQLiteDatabase db = tpDbHelper.getWritableDatabase();

            Cursor cursor = db.query(TpDbHelper.TABLE_SUPPLIER, sdColumns, null, null, null, null, null);

            while (cursor.moveToNext()) {
                lsd.add(populateSupplierData(cursor));
            }

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return lsd;
    }

    /**
     * Extract supplier data into content value object
     *
     * @param sd Supplier data
     * @return ContentValues
     */
    private ContentValues extractData(SupplierData sd) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TpDbHelper.SUPPLIER, sd.getSupplier());
        contentValues.put(TpDbHelper.CHAIN, sd.getChain());
        contentValues.put(TpDbHelper.TIME_STAMP, sd.getTimestamp());
        return contentValues;
    }

    /**
     * Extract product data into Content Value object
     *
     * @param pd Product data
     * @return ContentValues
     */
    private ContentValues extractData(ProductData pd) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TpDbHelper.LAYERS, pd.getLayers());
        contentValues.put(TpDbHelper.PACKAGE_ROLLS, pd.getPackageRolls());
        contentValues.put(TpDbHelper.ROLL_SHEETS, pd.getRollSheets());
        contentValues.put(TpDbHelper.SHEET_WIDTH, pd.getSheetWidth());
        contentValues.put(TpDbHelper.SHEET_LENGTH, pd.getSheetLength());
        contentValues.put(TpDbHelper.SHEET_LENGTH_C, pd.getSheetLength_c());
        contentValues.put(TpDbHelper.ROLL_LENGTH, pd.getRollLength());
        contentValues.put(TpDbHelper.ROLL_LENGTH_C, pd.getRollLength_c());
        contentValues.put(TpDbHelper.PACKAGE_PRICE, pd.getPackagePrice());
        contentValues.put(TpDbHelper.ROLL_PRICE, pd.getRollPrice());
        contentValues.put(TpDbHelper.ROLL_PRICE_C, pd.getRollPrice_c());
        contentValues.put(TpDbHelper.PAPER_WEIGHT, pd.getPaperWeight());
        contentValues.put(TpDbHelper.PAPER_WEIGHT_C, pd.getPaperWeight_c());
        contentValues.put(TpDbHelper.KILO_PRICE, pd.getKiloPrice());
        contentValues.put(TpDbHelper.KILO_PRICE_C, pd.getKiloPrice_c());
        contentValues.put(TpDbHelper.METER_PRICE, pd.getMeterPrice());
        contentValues.put(TpDbHelper.METER_PRICE_C, pd.getMeterPrice_c());
        contentValues.put(TpDbHelper.SHEET_PRICE, pd.getSheetPrice());
        contentValues.put(TpDbHelper.SHEET_PRICE_C, pd.getSheetPrice_c());
        contentValues.put(TpDbHelper.SUPPLIER, pd.getSupplier());
        contentValues.put(TpDbHelper.COMMENTS, pd.getComments());
        contentValues.put(TpDbHelper.ITEM_NO, pd.getItemNo());
        contentValues.put(TpDbHelper.BRAND, pd.getBrand());
        contentValues.put(TpDbHelper.TIME_STAMP, pd.getTimestamp());
        return contentValues;
    }

    /**
     * Populate supplier data from database table
     *
     * @param cursor Database cursor
     * @return Supplier data
     */
    private SupplierData populateSupplierData(Cursor cursor) {
        SupplierData sd = new SupplierData();
        sd.setSupplier(cursor.getString(cursor.getColumnIndex(TpDbHelper.SUPPLIER)));
        sd.setChain(cursor.getString(cursor.getColumnIndex(TpDbHelper.CHAIN)));
        sd.setTimestamp(cursor.getString(cursor.getColumnIndex(TpDbHelper.TIME_STAMP)));
        return sd;
    }

    /**
     * Populate product data from database table
     *
     * @param cursor Database cursor
     * @return product data
     */
    private ProductData populateProductData(Cursor cursor) {
        ProductData pd = new ProductData();
        pd.setUid(cursor.getInt(cursor.getColumnIndex(TpDbHelper.UID)));
        pd.setLayers(cursor.getInt(cursor.getColumnIndex(TpDbHelper.LAYERS)));
        pd.setPackageRolls(cursor.getInt(cursor.getColumnIndex(TpDbHelper.PACKAGE_ROLLS)));
        pd.setRollSheets(cursor.getInt(cursor.getColumnIndex(TpDbHelper.ROLL_SHEETS)));
        pd.setSheetWidth(cursor.getInt(cursor.getColumnIndex(TpDbHelper.SHEET_WIDTH)));
        pd.setSheetLength(cursor.getInt(cursor.getColumnIndex(TpDbHelper.SHEET_LENGTH)));
        pd.setSheetLength_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.ROLL_LENGTH_C)));
        pd.setRollLength(cursor.getInt(cursor.getColumnIndex(TpDbHelper.ROLL_LENGTH)));
        pd.setRollLength_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.SHEET_LENGTH_C)));
        pd.setPackagePrice(cursor.getFloat(cursor.getColumnIndex(TpDbHelper.PACKAGE_PRICE)));
        pd.setRollPrice(cursor.getFloat(cursor.getColumnIndex(TpDbHelper.ROLL_PRICE)));
        pd.setRollPrice_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.ROLL_PRICE_C)));
        pd.setPaperWeight(cursor.getFloat(cursor.getColumnIndex(TpDbHelper.PAPER_WEIGHT)));
        pd.setPaperWeight_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.PAPER_WEIGHT_C)));
        pd.setKiloPrice(cursor.getFloat(cursor.getColumnIndex(TpDbHelper.KILO_PRICE)));
        pd.setKiloPrice_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.KILO_PRICE_C)));
        pd.setMeterPrice(cursor.getFloat(cursor.getColumnIndex(TpDbHelper.METER_PRICE)));
        pd.setMeterPrice_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.METER_PRICE_C)));
        pd.setSheetPrice(cursor.getFloat(cursor.getColumnIndex(TpDbHelper.SHEET_PRICE)));
        pd.setSheetPrice_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.SHEET_PRICE_C)));
        pd.setSupplier(cursor.getString(cursor.getColumnIndex(TpDbHelper.SUPPLIER)));
        pd.setComments(cursor.getString(cursor.getColumnIndex(TpDbHelper.COMMENTS)));
        pd.setItemNo(cursor.getString(cursor.getColumnIndex(TpDbHelper.ITEM_NO)));
        pd.setBrand(cursor.getString(cursor.getColumnIndex(TpDbHelper.BRAND)));
        pd.setTimestamp(cursor.getString(cursor.getColumnIndex(TpDbHelper.TIME_STAMP)));
        return pd;
    }

    // TODO Update

    /**
     * Delete row from product table
     *
     * @param uid Unique identifier
     * @return
     */
    public int deleteProduct(int uid) {
        SQLiteDatabase db = tpDbHelper.getWritableDatabase();
        String s = uid + "";
        String[] whereArgs = {s};

        return db.delete(TpDbHelper.TABLE_PRODUCT, TpDbHelper.UID + " = ?", whereArgs);
    }

    /**
     * Delete row from suplier table
     *
     * @param supplier
     * @return
     */
    // TODO Update
    public int deleteSupplier(String supplier) {
        SQLiteDatabase db = tpDbHelper.getWritableDatabase();
        String[] whereArgs = {supplier};

        return db.delete(TpDbHelper.TABLE_SUPPLIER, TpDbHelper.SUPPLIER + " = ?", whereArgs);
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
        private static final String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT +
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

        private static final String CREATE_SUPPLIER_TABLE = "CREATE TABLE " + TABLE_SUPPLIER +
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
                loadInitialData();
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
                onCreate(db);
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        /**
         * Initial data load
         */
        private void loadInitialData() {
            ProductData pd;
            SupplierData sd;

            try {
                TPDbAdapter tpHelper = new TPDbAdapter(context);

                sd = new SupplierData("Bilka Hillerød", "Salling");
                tpHelper.insertData(sd);
                sd = new SupplierData("Føtex Hillerød", "Salling");
                tpHelper.insertData(sd);
                sd = new SupplierData("Kvickly Helsinge", "Coop");
                tpHelper.insertData(sd);
                sd = new SupplierData("Netto Vejby", "Salling");
                tpHelper.insertData(sd);
                sd = new SupplierData("Rema Vejby", "REMA 1000");
                tpHelper.insertData(sd);
                sd = new SupplierData("Spar Karsemose", "Dagrofa");
                tpHelper.insertData(sd);
                sd = new SupplierData("Spar Vejby Strand", "Dagrofa");
                tpHelper.insertData(sd);
                sd = new SupplierData("SuperBest Allerød", "SuperBest");
                tpHelper.insertData(sd);
                sd = new SupplierData("Superbrugsen Gilleleje", "Coop");
                tpHelper.insertData(sd);

                pd = new ProductData("5700384289095", "Irma Tusindfryd Toiletpapir",
                        3, 8, 233, 97, 125, 0, (float) 29.1,
                        0, 41, 0, (float) 5.125, 1, 48,
                        0, (float) 31.64, 0, (float) 0.1761, 1, (float) 0.022, 1,
                        "Kvickly", "Produceret i Sverige");
                tpHelper.insertData(pd);

                pd = new ProductData("7311041080306", "First Price Toiletpapir 2-lags",
                        2, 8, 220, 96, 125, 1, (float) 27.5,
                        0, (float) 15.95, 0, (float) 1.99, 1, 36,
                        0, 0, 0, (float) 0.0725, 1, (float) 0.009, 1,
                        "Spar Vejby Strand", "Produceret i Litauen");
                tpHelper.insertData(pd);

                pd = new ProductData("5705830002242", "REMA 100 Toiletpapir",
                        2, 8, 282, 97, 125, 0, (float) 35.25,
                        0, (float) 9.75, 0, (float) 1.21875, 1, (float) 32.6,
                        0, (float) 10.93, 0, (float) 0.0346, 1, (float) 0.004322, 1,
                        "Rema Vejby", "Produceret i Sverige");
                tpHelper.insertData(pd);

            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }
}
