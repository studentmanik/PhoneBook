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
    private static final int DATABASE_VERSION = 7;

    // Database Name
    private static final String DATABASE_NAME = "contact_db";

    // Contacts table name
    private static final String TABLE_CONTACTS = "tbl_contact";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CONTACT_ID = "contactId";
    private static final String KEY_CONTACT_NAME = "contact_name";
    private static final String KEY_CONTACT_NUMBER = "contact_number";
    private static final String KEY_CONTACT_IMG = "contact_img";
    private static final String KEY_CONTACT_EMAIL = "contact_email";



    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CONTACT_ID + " TEXT,"+ KEY_CONTACT_NAME + " TEXT,"+ KEY_CONTACT_NUMBER + " TEXT," + KEY_CONTACT_IMG + " TEXT,"+ KEY_CONTACT_EMAIL + " TEXT" + ")";
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

     //   String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS +" WHARE "+  ;
        ContentValues values = new ContentValues();

        values.put(KEY_CONTACT_ID, list.getContactId());
        values.put(KEY_CONTACT_NAME, list.getContactName()); // Contact Name
        values.put(KEY_CONTACT_NUMBER, list.getPhoneNumber()); // Contact Phone
        values.put(KEY_CONTACT_IMG, list.getIVContactImage());
        values.put(KEY_CONTACT_EMAIL, list.getEmailAddress());




        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }
    public  void DeleteContact(String id){

        SQLiteDatabase db = super.getWritableDatabase();
        String DELETE_ID= "DELETE FROM " + TABLE_CONTACTS +" WHERE"+" "+ KEY_ID +"="+id;
        db.execSQL(DELETE_ID);

        db.close();
    }
    public List<ContactList> getAllContact() {
        List<ContactList> contactLists = new ArrayList<ContactList>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
       // db.rawQuery("DELETE FROM " + TABLE_CONTACTS + " WHERE " + KEY_CONTACT_NUMBER + " = " + KEY_CONTACT_NUMBER + ");", null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ContactList contactList = new ContactList();
                contactList.setPhoneId(Integer.parseInt(cursor.getString(0)));

                contactList.setContactId(cursor.getString(1));
                contactList.setContactName(cursor.getString(2));
                contactList.setPhoneNumber(cursor.getString(3));
                contactList.setIVContactImage(cursor.getString(4));
                contactList.setEmailAddress(cursor.getString(5));



              //  contactList.setIVContactImage(Integer.parseInt(cursor.getString(2)));

                contactLists.add(contactList);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactLists;
    }
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.delete(TABLE_CONTACTS,null,null);
       // db.execSQL("delete * from"+ TABLE_CONTACTS);
      // db.delete(TABLE_CONTACTS, null, null);
       // db.execSQL("TRUNCATE table" + TABLE_CONTACTS);
        /*String selectQuery = "DELETE FROM table_name ";
        Cursor cursor = db.getReadableDatabase().rawQuery(selectQuery, null);*/
        db.execSQL("DELETE FROM tbl_contact");
       // db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.close();
    }

}
