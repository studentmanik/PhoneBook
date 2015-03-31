package com.example.ranacom.phonebook;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Contact extends ActionBarActivity /*implements AbsListView.OnScrollListener*/ {
    Bitmap bitmap;
    ListView lv;
   // List<ContactList> namelist;
    DBHandler db;
   // List<ContactList> contactses;
  //  ContactList contacts;
    EditText etPersonName,etPhoneNumber;
    Button btnShow,btnAdd;
    ContactListAdapter adapter;

    GridView gv;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        init();



    }




    private void init() {
        db = new DBHandler(this);

        etPersonName = (EditText) findViewById(R.id.etPersonName);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);

        gv = (GridView) findViewById(R.id.gv);



        btnShow= (Button) findViewById(R.id.btnShow);
        btnAdd= (Button) findViewById(R.id.btnAdd);


        ContentResolver cr = getBaseContext().getContentResolver();
        Cursor cur = cr .query(ContactsContract.Contacts.CONTENT_URI, null, null, null,  null+ " COLLATE LOCALIZED ASC");

        if (cur.getCount() > 0) {

        //    Log.i("Content provider", "Reading contact  emails");

            while (cur .moveToNext()) {

                String phoneNumber = null;
                String name = null;
                String image_uri = null;
                String emailAddress = null;

                String contactId = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));



                Cursor emails = cr.query( ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);




                while (emails.moveToNext()) {


                    emailAddress = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));



                }
                Cursor data = cr.query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                while (data.moveToNext()) {
                      phoneNumber = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                      name = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                      image_uri = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                }


               ContactList contactList = new ContactList(name,phoneNumber,image_uri,emailAddress,contactId);

                db.addContact(contactList);
                data.close();
                emails.close();
            }


        }
        cur.close();





        Toast.makeText(getApplicationContext(), "Imported", Toast.LENGTH_LONG).show();

        adapter= new ContactListAdapter(getApplicationContext(),db.getAllContact());
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getApplicationContext(),ContactDetails.class);
                startActivity(i);
            }
        });

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
