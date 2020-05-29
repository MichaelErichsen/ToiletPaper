package net.myerichsen.toiletpaper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.opencsv.CSVReader;

import net.myerichsen.toiletpaper.ui.products.ProductModel;
import net.myerichsen.toiletpaper.ui.suppliers.SupplierModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

/**
 * Database helper for product and supplier tables
 */
public class TPDbAdapter {
    private final TpDbHelper tpDbHelper;
    private final String[] pdColumns = {TpDbHelper.UID, TpDbHelper.LAYERS, TpDbHelper.PACKAGE_ROLLS,
            TpDbHelper.ROLL_SHEETS, TpDbHelper.SHEET_WIDTH, TpDbHelper.SHEET_LENGTH,
            TpDbHelper.SHEET_LENGTH_C, TpDbHelper.ROLL_LENGTH, TpDbHelper.ROLL_LENGTH_C,
            TpDbHelper.PACKAGE_PRICE, TpDbHelper.ROLL_PRICE, TpDbHelper.ROLL_PRICE_C,
            TpDbHelper.PAPER_WEIGHT, TpDbHelper.PAPER_WEIGHT_C,
            TpDbHelper.PACKAGE_WEIGHT, TpDbHelper.PACKAGE_WEIGHT_C,
            TpDbHelper.ROLL_WEIGHT, TpDbHelper.ROLL_WEIGHT_C,
            TpDbHelper.KILO_PRICE,
            TpDbHelper.KILO_PRICE_C, TpDbHelper.METER_PRICE, TpDbHelper.METER_PRICE_C,
            TpDbHelper.SHEET_PRICE, TpDbHelper.SHEET_PRICE_C, TpDbHelper.SUPPLIER,
            TpDbHelper.COMMENTS, TpDbHelper.ITEM_NO, TpDbHelper.BRAND,
            TpDbHelper.TIME_STAMP};
    private final String[] sdColumns = {TpDbHelper.SUPPLIER,
            TpDbHelper.CHAIN, TpDbHelper.TIME_STAMP};
    private final String[] countColumn = {"COUNT(*)"};

    public TPDbAdapter(Context context) {
        tpDbHelper = new TpDbHelper(context);
    }

    /**
     * Select from product table with selection arguments
     *
     * @param selection e.g. BRAND=?
     * @param column    Selection argument column
     * @return List of product models
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
     * Select from product table with selection arguments ordered
     *
     * @param selection   e.g. BRAND=?
     * @param argColumn   Selection argument column
     * @param orderColumn Column to order by
     * @return List of product models
     */
    public List<ProductModel> getProductModels(String selection, String argColumn, String orderColumn) {
        List<ProductModel> lpm = new ArrayList<>();

        SQLiteDatabase db = tpDbHelper.getReadableDatabase();

        String[] args = {argColumn};
        Cursor cursor = db.query(TpDbHelper.TABLE_PRODUCT, pdColumns, selection, args, null, null, orderColumn);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                lpm.add(populateProductModel(cursor));
            }
        }
        cursor.close();

        return lpm;
    }

    /**
     * Select everything from product table with selection arguments ordered
     *
     * @param orderColumn Column to order by
     * @return List of product models
     */
    public List<ProductModel> getProductModels(String orderColumn) {
        List<ProductModel> lpm = new ArrayList<>();

        SQLiteDatabase db = tpDbHelper.getReadableDatabase();

        Cursor cursor = db.query(TpDbHelper.TABLE_PRODUCT, pdColumns, null, null, null, null, orderColumn);

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
     * Select all data from product table
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
//        deleteSupplier("*");
//        deleteProduct();
        tpDbHelper.loadInitialData();
    }

    /**
     * Get all data from supplier table
     *
     * @return List of supplier data
     */
    public List<SupplierModel> getSupplierModels() {
        List<SupplierModel> lsm = new ArrayList<>();

        SQLiteDatabase db = tpDbHelper.getReadableDatabase();

        Cursor cursor = db.query(TpDbHelper.TABLE_SUPPLIER, sdColumns, null,
                null, null, null, TpDbHelper.SUPPLIER);

        while (cursor.moveToNext()) {
            lsm.add(populateSupplierModel(cursor));
        }

        cursor.close();
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
//        contentValues.put(TpDbHelper.TIME_STAMP, sm.getTimestamp());
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
        contentValues.put(TpDbHelper.PACKAGE_WEIGHT, pm.getPackageWeight());
        contentValues.put(TpDbHelper.PACKAGE_WEIGHT_C, pm.getPackageWeight_c());
        contentValues.put(TpDbHelper.ROLL_WEIGHT, pm.getRollWeight());
        contentValues.put(TpDbHelper.ROLL_WEIGHT_C, pm.getRollWeight_c());
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
        pm.setSheetLength_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.SHEET_LENGTH_C)));
        pm.setRollLength(cursor.getInt(cursor.getColumnIndex(TpDbHelper.ROLL_LENGTH)));
        pm.setRollLength_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.ROLL_LENGTH_C)));
        pm.setPackagePrice(cursor.getFloat(cursor.getColumnIndex(TpDbHelper.PACKAGE_PRICE)));
        pm.setRollPrice(cursor.getFloat(cursor.getColumnIndex(TpDbHelper.ROLL_PRICE)));
        pm.setRollPrice_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.ROLL_PRICE_C)));
        pm.setPaperWeight(cursor.getFloat(cursor.getColumnIndex(TpDbHelper.PAPER_WEIGHT)));
        pm.setPaperWeight_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.PAPER_WEIGHT_C)));
        pm.setPackageWeight(cursor.getFloat(cursor.getColumnIndex(TpDbHelper.PACKAGE_WEIGHT)));
        pm.setPackageWeight_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.PACKAGE_WEIGHT_C)));
        pm.setRollWeight(cursor.getFloat(cursor.getColumnIndex(TpDbHelper.ROLL_WEIGHT)));
        pm.setRollWeight_c(cursor.getInt(cursor.getColumnIndex(TpDbHelper.ROLL_WEIGHT_C)));
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

    public void deleteProduct(int uid) throws Exception {
        SQLiteDatabase db = tpDbHelper.getWritableDatabase();
        String s = uid + "";
        String[] whereArgs = {s};
        int rows = db.delete(TpDbHelper.TABLE_PRODUCT, TpDbHelper.UID + " = ?", whereArgs);
        if (rows == 0)
            throw new Exception("Ingen produkter slettet med løbenummer " + uid);
    }

    private void deleteProduct() {
        SQLiteDatabase db = tpDbHelper.getWritableDatabase();
        db.delete(TpDbHelper.TABLE_PRODUCT, null, null);
    }

    public void deleteSupplier(String supplier) {
        SQLiteDatabase db = tpDbHelper.getWritableDatabase();
        String[] whereArgs = {supplier};
        if (supplier.equals("*")) {
            db.delete(TpDbHelper.TABLE_SUPPLIER, null, null);
        } else {
            db.delete(TpDbHelper.TABLE_SUPPLIER, TpDbHelper.SUPPLIER + " = ?", whereArgs);
        }
    }

    public List<ProductModel> getProductModelsSorted(String sortKey, String sortFilter) {
        Cursor cursor;
        List<ProductModel> lpm = new ArrayList<>();
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

        return lpm;
    }

    public List<SupplierModel> getSupplierModels(String selection, String supplier) {
        List<SupplierModel> lsm = new ArrayList<>();

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
        private static final String PACKAGE_WEIGHT = "PACKAGE_WEIGHT";
        private static final String PACKAGE_WEIGHT_C = "PACKAGE_WEIGHT_C";
        private static final String ROLL_WEIGHT = "ROLL_WEIGHT";
        private static final String ROLL_WEIGHT_C = "ROLL_WEIGHT_C";
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
        private static final int DATABASE_Version = 5;    // Database Version
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
                PACKAGE_WEIGHT + " NUMERIC, " +
                PACKAGE_WEIGHT_C + " INTEGER, " +
                ROLL_WEIGHT + " NUMERIC, " +
                ROLL_WEIGHT_C + " INTEGER, " +
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
                TIME_STAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";

        private static final String DROP_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + TABLE_PRODUCT;
        private static final String CREATE_SUPPLIER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SUPPLIER +
                " (" + SUPPLIER + " TEXT PRIMARY KEY, " +
                CHAIN + " TEXT, " +
                TIME_STAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";

        private static final String DROP_SUPPLIER_TABLE = "DROP TABLE IF EXISTS " + TABLE_SUPPLIER;
        private final Context context;

        TpDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_SUPPLIER_TABLE);
            db.execSQL(CREATE_PRODUCT_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_PRODUCT_TABLE);
            db.execSQL(DROP_SUPPLIER_TABLE);
            onCreate(db);
        }

        private void loadInitialData() throws Exception {
            TPDbAdapter adapter = new TPDbAdapter(context);
            SQLiteDatabase db = getWritableDatabase();

            onCreate(db);

            Cursor cursor = db.query(TABLE_SUPPLIER, adapter.countColumn, null,
                    null, null, null, null, null);

            if (cursor.getCount() > 0) {
                if (cursor.moveToNext()) {
                    int count = cursor.getInt(cursor.getColumnIndex("COUNT(*)"));

                    if (count == 0) {
                        loadSuppliers(adapter);
                    } else {
                        throw new Exception("Butikstabel ikke indsat. Der er allerede " + count + " butikker");
                    }
                }
            }
            cursor.close();

            cursor = db.query(TABLE_PRODUCT, adapter.countColumn, null,
                    null, null, null, null, null);
            if (cursor.getCount() > 0) {
                if (cursor.moveToNext()) {
                    int count = cursor.getInt(cursor.getColumnIndex("COUNT(*)"));

                    if (count == 0) {
                        loadProducts(adapter);
                    } else {
                        throw new Exception("Produkttabel ikke indsat. Der er allerede " + count + " produkter");
                    }
                }
            }
            cursor.close();
        }

        /**
         * @param adapter The database adapter
         */
        private void loadProducts(TPDbAdapter adapter) {
            try {
                InputStream is = context.getAssets().open("products.csv");
                InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
                CSVReader csvreader = new CSVReader(reader);
                List<String[]> csv = csvreader.readAll();
                ProductModel pm;
                String[] data;

                for (int i = 1; i < csv.size(); i++) {
                    data = csv.get(i);
                    pm = new ProductModel(
                            data[0].trim(),
                            data[1].trim(),
                            Integer.parseInt(data[2].trim()),
                            Integer.parseInt(data[3].trim()),
                            Integer.parseInt(data[4].trim()),
                            Integer.parseInt(data[5].trim()),
                            Integer.parseInt(data[6].trim()),
                            Integer.parseInt(data[7].trim()),
                            Float.parseFloat(data[8].trim()),
                            Integer.parseInt(data[9].trim()),
                            Float.parseFloat(data[10].trim()),
                            Float.parseFloat(data[11].trim()),
                            Integer.parseInt(data[12].trim()),
                            Float.parseFloat(data[13].trim()),
                            Integer.parseInt(data[14].trim()),
                            Float.parseFloat(data[15].trim()),
                            Integer.parseInt(data[16].trim()),
                            Float.parseFloat(data[17].trim()),
                            Integer.parseInt(data[18].trim()),
                            Float.parseFloat(data[19].trim()),
                            Integer.parseInt(data[20].trim()),
                            Float.parseFloat(data[21].trim()),
                            Integer.parseInt(data[22].trim()),
                            Float.parseFloat(data[23].trim()),
                            Integer.parseInt(data[24].trim()),
                            data[25].trim(),
                            data[26]);
                    adapter.insertData(pm);
                    TimeUnit.SECONDS.sleep(1);
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void loadSuppliers(TPDbAdapter adapter) {
            try {
                InputStream is = context.getAssets().open("suppliers.csv");
                InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
                CSVReader csvreader = new CSVReader(reader);
                List<String[]> csv = csvreader.readAll();
                SupplierModel sm;
                String[] data;

                for (int i = 1; i < csv.size(); i++) {
                    data = csv.get(i);
                    sm = new SupplierModel(
                            data[0].trim(),
                            data[1].trim());
                    adapter.insertData(sm);
                    TimeUnit.SECONDS.sleep(1);
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
