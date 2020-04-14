package net.myerichsen.toiletpaper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class myDbAdapter {
    myDbHelper myhelper;
    String[] columns = {myDbHelper.UID, myDbHelper.LAYERS, myDbHelper.PACKAGE_ROLLS,
            myDbHelper.ROLL_SHEETS, myDbHelper.SHEET_WIDTH, myDbHelper.SHEET_LENGTH,
            myDbHelper.SHEET_LENGTH_C, myDbHelper.PACKAGE_PRICE, myDbHelper.PACKAGE_PRICE_C,
            myDbHelper.ROLL_PRICE, myDbHelper.ROLL_PRICE_C, myDbHelper.PAPER_WEIGHT,
            myDbHelper.PAPER_WEIGHT_C, myDbHelper.KILO_PRICE, myDbHelper.KILO_PRICE_C,
            myDbHelper.METER_PRICE, myDbHelper.METER_PRICE_C, myDbHelper.SHEET_PRICE,
            myDbHelper.SHEET_PRICE_C, myDbHelper.SUPPLIER, myDbHelper.COMMENTS,
            myDbHelper.ITEM_NO, myDbHelper.BRAND, myDbHelper.TIME_STAMP};

    public myDbAdapter(Context context) {
        myhelper = new myDbHelper(context);
    }

    /**
     * Get data by brand
     *
     * @return List of columns in record
     */
    public ProductData getDataByBrand(String brand) {
        ProductData pd = null;

        SQLiteDatabase db = myhelper.getWritableDatabase();

        String[] args = {brand};
        Cursor cursor = db.query(myDbHelper.TABLE_NAME, columns, "BRAND=?", args, null, null, null);

        if (cursor.getCount() > 0) {
            pd = populateProductData(cursor);
        }

        return pd;
    }

    /**
     * Insert a row
     */
    public long insertData(ProductData pd) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = extractProductData(pd);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    /**
     * Get data by item number
     *
     * @return List of columns in record
     */
    public ProductData getDataByItemNo(String itemNo) {
        ProductData pd = null;

        SQLiteDatabase db = myhelper.getWritableDatabase();

        String[] args = {itemNo};
        Cursor cursor = db.query(myDbHelper.TABLE_NAME, columns, "ITEM_NO=?", args, null, null, null);

        if (cursor.getCount() > 0) {
            pd = populateProductData(cursor);
        }

        return pd;
    }

    /**
     * Get data by item number
     *
     * @return List of columns in record
     */
    public List<ProductData> getAllData(Context context) {
        List<ProductData> lpd = new ArrayList<ProductData>();

        SQLiteDatabase db = myhelper.getWritableDatabase();


        Cursor cursor = null;
        cursor = db.query(myDbHelper.TABLE_NAME, columns, null, null, null, null, null);

        ProductData pd;
        while (cursor.moveToNext()) {
            lpd.add(populateProductData(cursor));
        }
        return lpd;
    }

    private ContentValues extractProductData(ProductData pd) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.UID, ProductData.getUid());
        contentValues.put(myDbHelper.LAYERS, ProductData.getLayers());
        contentValues.put(myDbHelper.PACKAGE_ROLLS, ProductData.getPackageRolls());
        contentValues.put(myDbHelper.ROLL_SHEETS, ProductData.getRollSheets());
        contentValues.put(myDbHelper.SHEET_WIDTH, ProductData.getSheetWidth());
        contentValues.put(myDbHelper.SHEET_LENGTH, ProductData.getSheetLength());
        contentValues.put(myDbHelper.SHEET_LENGTH_C, ProductData.getSheetLength_c());
        contentValues.put(myDbHelper.PACKAGE_PRICE, ProductData.getPackagePrice());
        contentValues.put(myDbHelper.PACKAGE_PRICE_C, ProductData.getPackagePrice_c());
        contentValues.put(myDbHelper.ROLL_PRICE, ProductData.getRollPrice());
        contentValues.put(myDbHelper.ROLL_PRICE_C, ProductData.getRollPrice_c());
        contentValues.put(myDbHelper.PAPER_WEIGHT, ProductData.getPaperWeight());
        contentValues.put(myDbHelper.PAPER_WEIGHT_C, ProductData.getPaperWeight_c());
        contentValues.put(myDbHelper.KILO_PRICE, ProductData.getKiloPrice());
        contentValues.put(myDbHelper.KILO_PRICE_C, ProductData.getKiloPrice_c());
        contentValues.put(myDbHelper.METER_PRICE, ProductData.getMeterPrice());
        contentValues.put(myDbHelper.METER_PRICE_C, ProductData.getMeterPrice_c());
        contentValues.put(myDbHelper.SHEET_PRICE, ProductData.getSheetPrice());
        contentValues.put(myDbHelper.SHEET_PRICE_C, ProductData.getSheetPrice_c());
        contentValues.put(myDbHelper.SUPPLIER, ProductData.getSupplier());
        contentValues.put(myDbHelper.COMMENTS, ProductData.getComments());
        contentValues.put(myDbHelper.ITEM_NO, ProductData.getItemNo());
        contentValues.put(myDbHelper.BRAND, ProductData.getBrand());
        contentValues.put(myDbHelper.TIME_STAMP, ProductData.getTimestamp());
        return contentValues;
    }

    private ProductData populateProductData(Cursor cursor) {
        ProductData pd = new ProductData();
        ProductData.setUid(cursor.getInt(cursor.getColumnIndex(myDbHelper.UID)));
        ProductData.setLayers(cursor.getInt(cursor.getColumnIndex(myDbHelper.LAYERS)));
        ProductData.setPackageRolls(cursor.getInt(cursor.getColumnIndex(myDbHelper.PACKAGE_ROLLS)));
        ProductData.setRollSheets(cursor.getInt(cursor.getColumnIndex(myDbHelper.ROLL_SHEETS)));
        ProductData.setSheetWidth(cursor.getInt(cursor.getColumnIndex(myDbHelper.SHEET_WIDTH)));
        ProductData.setSheetLength(cursor.getInt(cursor.getColumnIndex(myDbHelper.SHEET_LENGTH)));
        ProductData.setSheetLength_c(cursor.getInt(cursor.getColumnIndex(myDbHelper.SHEET_LENGTH_C)));
        ProductData.setPackagePrice(cursor.getFloat(cursor.getColumnIndex(myDbHelper.PACKAGE_PRICE)));
        ProductData.setPackagePrice_c(cursor.getInt(cursor.getColumnIndex(myDbHelper.PACKAGE_PRICE_C)));
        ProductData.setRollPrice(cursor.getFloat(cursor.getColumnIndex(myDbHelper.ROLL_PRICE)));
        ProductData.setRollPrice_c(cursor.getInt(cursor.getColumnIndex(myDbHelper.ROLL_PRICE_C)));
        ProductData.setPaperWeight(cursor.getFloat(cursor.getColumnIndex(myDbHelper.PAPER_WEIGHT)));
        ProductData.setPaperWeight_c(cursor.getInt(cursor.getColumnIndex(myDbHelper.PAPER_WEIGHT_C)));
        ProductData.setKiloPrice(cursor.getFloat(cursor.getColumnIndex(myDbHelper.KILO_PRICE)));
        ProductData.setKiloPrice_c(cursor.getInt(cursor.getColumnIndex(myDbHelper.KILO_PRICE_C)));
        ProductData.setMeterPrice(cursor.getFloat(cursor.getColumnIndex(myDbHelper.METER_PRICE)));
        ProductData.setMeterPrice_c(cursor.getInt(cursor.getColumnIndex(myDbHelper.METER_PRICE_C)));
        ProductData.setSheetPrice(cursor.getFloat(cursor.getColumnIndex(myDbHelper.SHEET_PRICE)));
        ProductData.setSheetPrice_c(cursor.getInt(cursor.getColumnIndex(myDbHelper.SHEET_PRICE_C)));
        ProductData.setSupplier(cursor.getString(cursor.getColumnIndex(myDbHelper.SUPPLIER)));
        ProductData.setComments(cursor.getString(cursor.getColumnIndex(myDbHelper.COMMENTS)));
        ProductData.setItemNo(cursor.getString(cursor.getColumnIndex(myDbHelper.ITEM_NO)));
        ProductData.setBrand(cursor.getString(cursor.getColumnIndex(myDbHelper.BRAND)));
        ProductData.setTimestamp(cursor.getString(cursor.getColumnIndex(myDbHelper.TIME_STAMP)));
        return pd;
    }

    // TODO Update
    public int delete(String ITEM_NO) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs = {ITEM_NO};

        int count = db.delete(myDbHelper.TABLE_NAME, myDbHelper.ITEM_NO + " = ?", whereArgs);
        return count;
    }

    /**
     * Inner helper class
     */
    static class myDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "TOILET_PAPER_DATABASE";
        private static final String TABLE_NAME = "TABLE_PACKAGE";
        private static final String UID = "UID";
        private static final String LAYERS = "LAYERS";
        private static final String PACKAGE_ROLLS = "PACKAGE_ROLLS";
        private static final String ROLL_SHEETS = "ROLL_SHEETS";
        private static final String SHEET_WIDTH = "SHEET_WIDTH";
        private static final String SHEET_LENGTH = "SHEET_LENGTH";
        private static final String SHEET_LENGTH_C = "SHEET_LENGTH_C";
        private static final String PACKAGE_PRICE = "PACKAGE_PRICE";
        private static final String PACKAGE_PRICE_C = "PACKAGE_PRICE_C";
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

        // TODO ROLL_LENGTH & _C
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LAYERS + " INTEGER, " +
                PACKAGE_ROLLS + " INTEGER, " +
                ROLL_SHEETS + " INTEGER, " +
                SHEET_WIDTH + " INTEGER, " +
                SHEET_LENGTH + " INTEGER, " +
                SHEET_LENGTH_C + " INTEGER, " +
                PACKAGE_PRICE + " NUMERIC, " +
                PACKAGE_PRICE_C + " INTEGER, " +
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

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Toast.makeText(context, "OnUpgrade", Toast.LENGTH_LONG).show();
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
