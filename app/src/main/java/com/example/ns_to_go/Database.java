package com.example.ns_to_go;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ns_to_go.Data.Station;
import com.example.ns_to_go.Data.StationType;
import com.google.android.gms.maps.model.LatLng;

import java.sql.Blob;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    private static final String TAG = "MetingManager";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DATABASE";
    private static final String TABLE_NAME = "stations";
    private static final String COLUMN_UICCODE = "uiccode";
    private static final String COLUMN_EVACODE = "evacode";
    private static final String COLUMN_SHORT_NAME = "short_name";
    private static final String COLUMN_MIDDLE_NAME = "middle_name";
    private static final String COLUMN_LONG_NAME = "long_name";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_LAND = "land";
    private static final String COLUMN_STATIONTYPE = "stationtype";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_UICCODE + " TEXT PRIMARY KEY," +
                    COLUMN_EVACODE + " TEXT," +
                    COLUMN_SHORT_NAME + " TEXT," +
                    COLUMN_MIDDLE_NAME + " TEXT," +
                    COLUMN_LONG_NAME + " TEXT," +
                    COLUMN_CODE + " TEXT," +
                    COLUMN_LATITUDE + " REAL," +
                    COLUMN_LONGITUDE + " REAL," +
                    COLUMN_LAND + " TEXT," +
                    COLUMN_STATIONTYPE + " TEXT" +
                    ");";


    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String CHECK_TABLE = "SELECT count(*) FROM " + TABLE_NAME;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.i(TAG, "onCreate() called, creating table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        Log.i(TAG, "onUpgrade() called, dropping table");
        onCreate(db);
    }

    public void insertValue(Station station) {
        getWritableDatabase().delete(TABLE_NAME, "sequenceID=?", new String[]{station.getUICCODE() + ""});

        ContentValues values = new ContentValues();

        values.put(COLUMN_UICCODE, station.getUICCODE());
        values.put(COLUMN_EVACODE, station.getEVACode());
        values.put(COLUMN_SHORT_NAME, station.getNames()[0]);
        values.put(COLUMN_MIDDLE_NAME, station.getNames()[1]);
        values.put(COLUMN_LONG_NAME, station.getNames()[2]);
        values.put(COLUMN_CODE, station.getCode());
        values.put(COLUMN_LATITUDE, station.getCode());
        values.put(COLUMN_LONGITUDE, station.getCode());
        values.put(COLUMN_LAND, station.getLand());
        values.put(COLUMN_STATIONTYPE, station.getStationType().toString());

        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_NAME, null, values);
        Log.i(TAG, "insertValue() called, inserted into " + TABLE_NAME);
    }

    public boolean isTableFilled() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(CHECK_TABLE, null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        return icount != 0;
    }

    public void resetTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(DROP_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    public ArrayList<Station> readValues() {
        String query = "SELECT * FROM " + TABLE_NAME + ";";
        String[] args = new String[10];
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Station> stations = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String uicCode = cursor.getString(cursor.getColumnIndex(COLUMN_UICCODE));
                String evaCode = cursor.getString(cursor.getColumnIndex(COLUMN_EVACODE));
                String shortName = cursor.getString(cursor.getColumnIndex(COLUMN_SHORT_NAME));
                String middleName = cursor.getString(cursor.getColumnIndex(COLUMN_MIDDLE_NAME));
                String longName = cursor.getString(cursor.getColumnIndex(COLUMN_LONG_NAME));
                String code = cursor.getString(cursor.getColumnIndex(COLUMN_CODE));
                double latitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE));
                String land = cursor.getString(cursor.getColumnIndex(COLUMN_LAND));
                String stationType = cursor.getString(cursor.getColumnIndex(COLUMN_STATIONTYPE));

                String[] names = new String[3];
                names[0] = shortName;
                names[1] = middleName;
                names[2] = longName;

                stations.add(new Station(StationType.valueOf(stationType), land, latitude, longitude, names, code, evaCode, uicCode));
            } while (cursor.moveToNext());
        }
        return stations;
    }


}
