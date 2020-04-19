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
    private final String[] columns = {SupplierHelper.UID, SupplierHelper.SUPPLIER,
            SupplierHelper.URI, SupplierHelper.TIME_STAMP};

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
        dbb.insert(SupplierHelper.TABLE_NAME, null, contentValues);
    }


    /**
     * Get all data from table
     *
     * @param context Application context
     * @return List of supplier data
     */
    public List<SupplierData> getAllData(Context context) {
        List<SupplierData> lsd = new ArrayList<>();
        try {
            SQLiteDatabase db = supplierHelper.getWritableDatabase();

            // TODO Exception here: No such table TABLE_SUPPLIER
            Cursor cursor = db.query(SupplierHelper.TABLE_NAME, columns, null, null, null, null, null);

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
        contentValues.put(SupplierHelper.URI, sd.getUri());
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
        sd.setUid(cursor.getInt(cursor.getColumnIndex(SupplierHelper.UID)));
        sd.setSupplier(cursor.getString(cursor.getColumnIndex(SupplierHelper.SUPPLIER)));
        sd.setUri(cursor.getString(cursor.getColumnIndex(SupplierHelper.URI)));
        sd.setTimestamp(cursor.getString(cursor.getColumnIndex(SupplierHelper.TIME_STAMP)));
        return sd;
    }

    /**
     * Delete row from table
     *
     * @param ITEM_NO
     * @return
     */
    // TODO Update
    public int delete(String ITEM_NO) {
        SQLiteDatabase db = supplierHelper.getWritableDatabase();
        String[] whereArgs = {ITEM_NO};

        return db.delete(SupplierHelper.TABLE_NAME, SupplierHelper.SUPPLIER + " = ?", whereArgs);
    }

    /**
     * Inner helper class
     */
    static class SupplierHelper extends SQLiteOpenHelper {

        // Database TOILET_PAPER_DATABASE is located at
        // "C:/Users/michael/Documents/AndroidStudio/DeviceExplorer/Pixel_2_API_R [emulator-5554]/data/data/net.myerichsen.toiletpaper/databases/TOILET_PAPER_DATABASE"

        // TODO Drop URI and add chain instead
        private static final String DATABASE_NAME = "TOILET_PAPER_DATABASE";
        private static final String TABLE_NAME = "TABLE_SUPPLIER";
        private static final String UID = "UID";
        private static final String SUPPLIER = "SUPPLIER";
        private static final String URI = "URI";
        private static final String TIME_STAMP = "TIME_STAMP";

        private static final int DATABASE_Version = 2;    // Database Version
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SUPPLIER + " TEXT, " +
                URI + " TEXT, " +
                TIME_STAMP + " TEXT);";

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
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
                Toast.makeText(context, CREATE_TABLE, Toast.LENGTH_LONG).show();
                db.execSQL(CREATE_TABLE);
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
                db.execSQL(DROP_TABLE);
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

                sd = new SupplierData("Aldi", "www.aldi.dk");
                sHelper.insertData(sd);
                sd = new SupplierData("Coop (Kvickly/Brugsen/Fakta/Irma)", "www.coop.dk");
                sHelper.insertData(sd);
                sd = new SupplierData("Dagrofa (Meny/Min Købmand/Let-Køb/Spar", "www.dagrofa.dk");
                sHelper.insertData(sd);
                sd = new SupplierData("Lidl", "www.lidl.dk");
                sHelper.insertData(sd);
                sd = new SupplierData("REMA 1000", "www.rema1000.dk");
                sHelper.insertData(sd);
                sd = new SupplierData("Salling (Bilka/Føtex/Netto)", "www.sallinggroup.com");
                sHelper.insertData(sd);
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }
}
