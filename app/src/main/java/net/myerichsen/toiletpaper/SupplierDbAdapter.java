package net.myerichsen.toiletpaper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import net.myerichsen.toiletpaper.ui.suppliers.SupplierData;

import java.util.ArrayList;
import java.util.List;

public class SupplierDbAdapter {
    final SupplierHelper supplierHelper;
    final String[] columns = {SupplierHelper.UID, SupplierHelper.SUPPLIER, SupplierHelper.URI,
            SupplierHelper.TIME_STAMP};

    public SupplierDbAdapter(Context context) {
        supplierHelper = new SupplierHelper(context);
    }


    public SupplierData getData(String supplier) {
        return null;
    }

    /**
     * Insert a row
     */
    public long insertData(SupplierData sd) {
        SQLiteDatabase dbb = supplierHelper.getWritableDatabase();
        ContentValues contentValues = extractSupplierData(sd);
        return dbb.insert(SupplierHelper.TABLE_NAME, null, contentValues);
    }


    public List<SupplierData> getAllData(Context context) {
        List<SupplierData> lsd = new ArrayList<>();
        SQLiteDatabase db = supplierHelper.getWritableDatabase();

        Cursor cursor = db.query(SupplierHelper.TABLE_NAME, columns, null, null, null, null, null);

        SupplierData sd;
        while (cursor.moveToNext()) {
            lsd.add(populateSupplierData(cursor));
        }
        return lsd;

    }

    private ContentValues extractSupplierData(SupplierData pd) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SupplierHelper.UID, SupplierData.getUid());
        contentValues.put(SupplierHelper.SUPPLIER, SupplierData.getSupplier());
        contentValues.put(SupplierHelper.URI, SupplierData.getUri());
        contentValues.put(SupplierHelper.TIME_STAMP, SupplierData.getTimestamp());
        return contentValues;
    }

    private SupplierData populateSupplierData(Cursor cursor) {
        SupplierData sd = new SupplierData();
        SupplierData.setUid(cursor.getInt(cursor.getColumnIndex(SupplierHelper.UID)));
        SupplierData.setSupplier(cursor.getString(cursor.getColumnIndex(SupplierHelper.SUPPLIER)));
        SupplierData.setUri(cursor.getString(cursor.getColumnIndex(SupplierHelper.URI)));
        SupplierData.setUri(cursor.getString(cursor.getColumnIndex(SupplierHelper.URI)));
        SupplierData.setTimestamp(cursor.getString(cursor.getColumnIndex(SupplierHelper.TIME_STAMP)));
        return sd;
    }

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

        private static final String DATABASE_NAME = "TOILET_PAPER_DATABASE";
        private static final String TABLE_NAME = "TABLE_SUPPLIER";
        private static final String UID = "UID";
        private static final String SUPPLIER = "SUPPLIER";
        private static final String URI = "URI";
        private static final String TIME_STAMP = "TIME_STAMP";

        private static final int DATABASE_Version = 1;    // Database Version
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SUPPLIER + " TEXT, " +
                URI + " TEXT, " +
                TIME_STAMP + " TEXT);";

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private final Context context;

        public SupplierHelper(Context context) {
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
