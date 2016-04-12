package com.watrelos.victor.ft_hangout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by victor on 4/10/16.
 */
public class SmsDB {
    private static final String TAG = "SmsDB";



    public static abstract class SmsEntry implements BaseColumns {
        public static final String TABLE_NAME = "sms";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_SRC = "src";
        public static final String COLUMN_NAME_DEST = "dst";
        public static final String COLUMN_NAME_DATE = "date";

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + SmsEntry.TABLE_NAME + " (" +
                        SmsEntry._ID + " INTEGER PRIMARY KEY," +
                        SmsEntry.COLUMN_NAME_CONTENT + TEXT_TYPE + COMMA_SEP +
                        SmsEntry.COLUMN_NAME_SRC + TEXT_TYPE + COMMA_SEP +
                        SmsEntry.COLUMN_NAME_DEST + TEXT_TYPE + COMMA_SEP +
                        SmsEntry.COLUMN_NAME_DATE + TEXT_TYPE + 
                        " )";
        public static final String SQL_UPGRADE_ENTRIES =
                "";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + SmsEntry.TABLE_NAME;
    }

    public class SmsDBHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Sms.db";

        public SmsDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SmsEntry.SQL_CREATE_ENTRIES);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SmsEntry.SQL_UPGRADE_ENTRIES);
        }
    }

    public void deleteEntry(long id, Context context) {
        SmsDBHelper helper = new SmsDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete(SmsEntry.TABLE_NAME, SmsEntry._ID + "=?", new String[]{(String.valueOf(id))});
    }

    public void update(long id, String content, String src, String dst, String date, Context context) {
        SmsDBHelper helper = new SmsDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put(SmsEntry.COLUMN_NAME_CONTENT, content);
        newValues.put(SmsEntry.COLUMN_NAME_SRC, src);
        newValues.put(SmsEntry.COLUMN_NAME_DEST, dst);
        newValues.put(SmsEntry.COLUMN_NAME_DATE, date);
        Integer i = db.update(SmsEntry.TABLE_NAME, newValues, SmsEntry._ID + "=?", new String[]{String.valueOf(id)});
    }

    public boolean insertData(String content, String src, String dst, String date, Context context) {
        SmsDBHelper helper = new SmsDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SmsEntry.COLUMN_NAME_CONTENT, content);
        values.put(SmsEntry.COLUMN_NAME_SRC, src);
        values.put(SmsEntry.COLUMN_NAME_DATE, date);
        values.put(SmsEntry.COLUMN_NAME_DEST, dst);


        long newRowId;
        newRowId = db.insert(
                SmsEntry.TABLE_NAME,
                "null",
                values);
        return newRowId > -1;

    }


    public Cursor getSmsCursor(Context context, String phoneNumberSrc, String phoneNumberDst) {
        SmsDBHelper helper = new SmsDBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                SmsEntry._ID,
                SmsEntry.COLUMN_NAME_CONTENT,
                SmsEntry.COLUMN_NAME_SRC,
                SmsEntry.COLUMN_NAME_DATE,
                SmsEntry.COLUMN_NAME_DEST

        };
        String sortOrder = null;
        Cursor c = db.query(
                SmsEntry.TABLE_NAME,
                projection,
                "`" + SmsEntry.COLUMN_NAME_SRC + "` = '" + phoneNumberSrc + "' OR `" + SmsEntry.COLUMN_NAME_DEST + "` = '" + phoneNumberDst + "'",
                null,
                null,
                null,
                sortOrder
        );
        return c;
    }
}
