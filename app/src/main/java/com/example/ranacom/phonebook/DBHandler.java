package com.example.ranacom.phonebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {

    // database VERSION
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contact_db";

    // Contacts table name
    private static final String TABLE_CONTACTS = "tbl_contact";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CONTACT_NAME = "contact_name";
    private static final String KEY_CONTACT_NUMBER = "contact_number";
    private static final String KEY_CONTACT_IMG = "contact_img";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CONTACT_NAME + " TEXT,"
                + KEY_CONTACT_NUMBER + " TEXT," + KEY_CONTACT_IMG + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }


    void addContact(ContactList list) {

        SQLiteDatabase db = super.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CONTACT_NAME, list.getContactName()); // Contact Name
        values.put(KEY_CONTACT_NUMBER, list.getPhoneNumber()); // Contact Phone
        values.put(KEY_CONTACT_IMG, 122);


        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }
    public List<ContactList> getAllContact() {
        List<ContactList> contactLists = new ArrayList<ContactList>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ContactList contactList = new ContactList();
                contactList.setPhoneId(Integer.parseInt(cursor.getString(0)));
                contactList.setContactName(cursor.getString(1));
                contactList.setPhoneNumber(cursor.getString(2));


              //  contactList.setIVContactImage(Integer.parseInt(cursor.getString(2)));

                contactLists.add(contactList);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactLists;
    }

}
