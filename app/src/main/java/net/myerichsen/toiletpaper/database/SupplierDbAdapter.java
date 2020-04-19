package net.myerichsen.toiletpaper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import net.myerichsen.toiletpaper.ui.suppliers.SupplierData;

import java.util.ArrayList;
import java.util.List;

/**
 * Database helper for supplier table
 */
public class SupplierDbAdapter {
    private final SupplierHelper supplierHelper;
    private final String[] sdColumns = {SupplierHelper.SUPPLIER,
            SupplierHelper.CHAIN, SupplierHelper.TIME_STAMP};

    /**
     * Constructor
     *
     * @param context Application context
     */
    public SupplierDbAdapter(Context context) {
        supplierHelper = new SupplierHelper(context);
    }

    /**
     * Insert a row
     */
    private void insertData(SupplierData sd) {
        SQLiteDatabase dbb = supplierHelper.getWritableDatabase();
        ContentValues contentValues = extractSupplierData(sd);
        dbb.insert(SupplierHelper.SUPPLIER_TABLE_NAME, null, contentValues);
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
            SQLiteDatabase db = supplierHelper.getWritableDatabase();

            // TODO Exception here: No such table TABLE_SUPPLIER
            Cursor cursor = db.query(SupplierHelper.SUPPLIER_TABLE_NAME, sdColumns, null, null, null, null, null);

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
    private ContentValues extractSupplierData(SupplierData sd) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SupplierHelper.SUPPLIER, sd.getSupplier());
        contentValues.put(SupplierHelper.CHAIN, sd.getChain());
        contentValues.put(SupplierHelper.TIME_STAMP, sd.getTimestamp());
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
        sd.setSupplier(cursor.getString(cursor.getColumnIndex(SupplierHelper.SUPPLIER)));
        sd.setChain(cursor.getString(cursor.getColumnIndex(SupplierHelper.CHAIN)));
        sd.setTimestamp(cursor.getString(cursor.getColumnIndex(SupplierHelper.TIME_STAMP)));
        return sd;
    }

    /**
     * Delete row from suplier table
     *
     * @param supplier
     * @return
     */
    // TODO Update
    public int deleteSupplier(String supplier) {
        SQLiteDatabase db = supplierHelper.getWritableDatabase();
        String[] whereArgs = {supplier};

        return db.delete(SupplierHelper.SUPPLIER_TABLE_NAME, SupplierHelper.SUPPLIER + " = ?", whereArgs);
    }

    /**
     * Inner helper class
     */
    static class SupplierHelper extends SQLiteOpenHelper {

        // Database TOILET_PAPER_DATABASE is located at
        // "C:/Users/michael/Documents/AndroidStudio/DeviceExplorer/Pixel_2_API_R [emulator-5554]/data/data/net.myerichsen.toiletpaper/databases/TOILET_PAPER_DATABASE"

        private static final String DATABASE_NAME = "TOILET_PAPER_DATABASE";
        private static final String SUPPLIER_TABLE_NAME = "TABLE_SUPPLIER";
        private static final String SUPPLIER = "SUPPLIER";
        private static final String CHAIN = "CHAIN";
        private static final String TIME_STAMP = "TIME_STAMP";

        private static final int DATABASE_Version = 2;    // Database Version
        private static final String CREATE_SUPPLIER_TABLE = "CREATE TABLE " + SUPPLIER_TABLE_NAME +
                " (" + SUPPLIER + " TEXT PRIMARY KEY, " +
                CHAIN + " TEXT, " +
                TIME_STAMP + " TEXT);";

        private static final String DROP_SUPPLIER_TABLE = "DROP TABLE IF EXISTS " + SUPPLIER_TABLE_NAME;
        private final Context context;

        /**
         * Constructor
         *
         * @param context
         */
        SupplierHelper(Context context) {
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
                Toast.makeText(context, CREATE_SUPPLIER_TABLE, Toast.LENGTH_LONG).show();
                db.execSQL(CREATE_SUPPLIER_TABLE);
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
            SupplierData sd;
            try {
                SupplierDbAdapter sHelper = new SupplierDbAdapter(context);

                sd = new SupplierData("Bilka Hillerød", "Salling");
                sHelper.insertData(sd);
                sd = new SupplierData("Føtex Hillerød", "Salling");
                sHelper.insertData(sd);
                sd = new SupplierData("Kvickly Helsinge", "Coop");
                sHelper.insertData(sd);
                sd = new SupplierData("Netto Vejby", "Salling");
                sHelper.insertData(sd);
                sd = new SupplierData("Rema Vejby", "REMA 1000");
                sHelper.insertData(sd);
                sd = new SupplierData("Spar Karsemose", "Dagrofa");
                sHelper.insertData(sd);
                sd = new SupplierData("Spar Vejby Strand", "Dagrofa");
                sHelper.insertData(sd);
                sd = new SupplierData("SuperBest Allerød", "SuperBest");
                sHelper.insertData(sd);
                sd = new SupplierData("Superbrugsen Gilleleje", "Coop");
                sHelper.insertData(sd);
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }
}
