package com.example.ranacom.phonebook;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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


public class Contact extends ActionBarActivity {
    Bitmap bitmap;
    ListView lv;
    List<ContactList> namelist;
    DBHandler db;
    List<ContactList> contactses;
    ContactList contacts;
    EditText etPersonName,etPhoneNumber;
    Button btnShow,btnAdd;
    GridView gv;
    String img;
 //   ImageView profile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        init();
      //  profile  = (ImageView)findViewById(R.id.IVContactImage);
      //  test();



    }

    private void test() {
        List<ContactList> contactLists = new ArrayList<ContactList>();
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
                null,null
                        + " COLLATE LOCALIZED ASC");

        // Looping through the contacts and adding them to arrayList
        while (cursor.moveToNext()) {
            ContactList contactList = new ContactList();
            String name = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            String image_uri = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

            contactList.setPhoneNumber(phoneNumber);
            contactList.setContactName(name);
            contactList.setIVContactImage(image_uri);
/*
            if (image_uri!=null){ try {
                bitmap = MediaStore.Images.Media .getBitmap(getApplicationContext().getContentResolver(), Uri.parse(image_uri));

                //  profile.setImageBitmap(bitmap) ;
            } catch (IOException e) {

            }}else{
                //   profile.setImageResource(R.drawable.spalish);

            }*/


            contactLists.add(contactList);
           // ContactList contactList1 = new ContactList(name,phoneNumber);
            lv.setAdapter(new ContactListAdapter(getApplicationContext(), contactLists));
    }

    }


    private void init() {
        db = new DBHandler(this);

        etPersonName = (EditText) findViewById(R.id.etPersonName);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
       lv = (ListView) findViewById(R.id.lv);
       // gv = (GridView) findViewById(R.id.gv);

       /* Uri my_contact_Uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(3));
        InputStream photo_stream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(),my_contact_Uri);
        BufferedInputStream buf =new BufferedInputStream(photo_stream);*/

       // Bitmap my_btmp = BitmapFactory.decodeStream(buf);
      //  profile.setImageBitmap(my_btmp);

        btnShow= (Button) findViewById(R.id.btnShow);
        btnAdd= (Button) findViewById(R.id.btnAdd);

       btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ContactList contactList = new ContactList(etPersonName.getText().toString(),etPhoneNumber.getText().toString());
                db.addContact(contactList);*/
//ok

                Cursor cursor = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
                        null,null
                                + " COLLATE LOCALIZED ASC");

                // Looping through the contacts and adding them to arrayList
                while (cursor.moveToNext()) {
                    String name = cursor
                            .getString(cursor
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = cursor
                            .getString(cursor
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    String image_uri = cursor
                            .getString(cursor
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));





                    ContactList contactList = new ContactList(name,phoneNumber,image_uri);
                   // lv.setAdapter(new ContactListAdapter(getApplicationContext(), (List<ContactList>) contactList));
                   db.addContact(contactList);
                 //   Toast.makeText(getApplicationContext(),image_uri,Toast.LENGTH_LONG).show();

                 //   System.out.println(image_uri);




                }

                cursor.close();

                Toast.makeText(getApplicationContext(), "Imported", Toast.LENGTH_LONG).show();

            }
        });
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //   lv.setAdapter(new ContactListAdapter(getApplicationContext(),contactList));
                lv.setAdapter(new ContactListAdapter(getApplicationContext(),db.getAllContact()));
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
