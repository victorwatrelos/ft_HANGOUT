package com.watrelos.victor.ft_hangout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by victor on 3/29/16.
 */
public class ContactDB {

    private static final String TAG = "ContactDB";



    public static abstract class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "contact";
        public static final String COLUMN_NAME_ENTRY_ID = "entry";
        public static final String COLUMN_NAME_FIRSTNAME = "firstname";
        public static final String COLUMN_NAME_LASTNAME = "lastname";
        public static final String COLUMN_NAME_PHONE = "phone_number";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_CODE = "code";

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + ContactEntry.TABLE_NAME + " (" +
                        ContactEntry._ID + " INTEGER PRIMARY KEY," +
                        ContactEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                        ContactEntry.COLUMN_NAME_FIRSTNAME + TEXT_TYPE + COMMA_SEP +
                        ContactEntry.COLUMN_NAME_LASTNAME + TEXT_TYPE + COMMA_SEP +
                        ContactEntry.COLUMN_NAME_ADDRESS + TEXT_TYPE + COMMA_SEP +
                        ContactEntry.COLUMN_NAME_CODE + TEXT_TYPE + COMMA_SEP +
                        ContactEntry.COLUMN_NAME_PHONE + TEXT_TYPE +
                        " )";
        public static final String SQL_UPGRADE_ENTRIES =
                "";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + ContactEntry.TABLE_NAME;
    }

    public class ContactDBHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Contact.db";

        public ContactDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(ContactEntry.SQL_CREATE_ENTRIES);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(ContactEntry.SQL_UPGRADE_ENTRIES);
        }
    }

    public void deleteEntry(long id, Context context) {
        ContactDBHelper helper = new ContactDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete(ContactEntry.TABLE_NAME, ContactEntry._ID + "=?", new String[]{(String.valueOf(id))});
    }

    public void update(long id, String firstname, String lastname, String phone, String address, String code, Context context) {
        ContactDBHelper helper = new ContactDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        phone = this.changePhoneNumber(phone);
        ContentValues newValues = new ContentValues();
        newValues.put(ContactEntry.COLUMN_NAME_FIRSTNAME, firstname);
        newValues.put(ContactEntry.COLUMN_NAME_LASTNAME, lastname);
        newValues.put(ContactEntry.COLUMN_NAME_PHONE, phone);
        newValues.put(ContactEntry.COLUMN_NAME_ADDRESS, address);
        newValues.put(ContactEntry.COLUMN_NAME_CODE, code);
        Integer i = db.update(ContactEntry.TABLE_NAME, newValues, ContactEntry._ID + "=?", new String[]{String.valueOf(id)});
    }

    private String  changePhoneNumber(String phone) {
        if (phone.length() == 10 && phone.matches("\\d+(?:\\.\\d+)?") && phone.charAt(0) == '0')
        {
            return "+33" + phone.substring(1);
        }
        return phone;
    }

    public void insertData(String firstname, String lastname, String phone, String address, String code, Context context) {
        ContactDBHelper helper = new ContactDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        phone = this.changePhoneNumber(phone);
        values.put(ContactEntry.COLUMN_NAME_ENTRY_ID, "2");
        values.put(ContactEntry.COLUMN_NAME_FIRSTNAME, firstname);
        values.put(ContactEntry.COLUMN_NAME_LASTNAME, lastname);
        values.put(ContactEntry.COLUMN_NAME_PHONE, phone);
        values.put(ContactEntry.COLUMN_NAME_ADDRESS, address);
        values.put(ContactEntry.COLUMN_NAME_CODE, code);

        long newRowId;
        newRowId = db.insert(
                ContactEntry.TABLE_NAME,
                "null",
                values);

    }

    public Map<String, String> getContact(long id, Context context) {
        ContactDBHelper helper = new ContactDBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Map map = new HashMap<String, String>();
        String[] projection = {
                ContactEntry._ID,
                ContactEntry.COLUMN_NAME_FIRSTNAME,
                ContactEntry.COLUMN_NAME_LASTNAME,
                ContactEntry.COLUMN_NAME_PHONE,
                ContactEntry.COLUMN_NAME_ADDRESS,
                ContactEntry.COLUMN_NAME_CODE
        };
        Cursor c = db.query(
                ContactEntry.TABLE_NAME,
                projection,
                "`" + ContactEntry._ID + "` = " + id,
                null,
                null,
                null,
                null
        );
        c.moveToFirst();
        if (c.isAfterLast())
            return null;
        map.put("firstname", c.getString(1));
        map.put("lastname", c.getString(2));
        map.put("phone", c.getString(3));
        map.put("address", c.getString(4));
        map.put("code", c.getString(5));
        return map;
    }


    public Cursor getContactsCursor(Context context) {
        ContactDBHelper helper = new ContactDBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                ContactEntry._ID,
                ContactEntry.COLUMN_NAME_FIRSTNAME,
                ContactEntry.COLUMN_NAME_LASTNAME,
                ContactEntry.COLUMN_NAME_PHONE
        };
        String sortOrder = ContactEntry.COLUMN_NAME_FIRSTNAME + " DESC";
        Cursor c = db.query(
                ContactEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        return c;
    }
}
