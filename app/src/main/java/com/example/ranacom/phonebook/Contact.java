package com.example.ranacom.phonebook;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class Contact extends ActionBarActivity {

    ListView lv;
    List<ContactList> namelist;
    DBHandler db;
    List<ContactList> contactses;
    ContactList contacts;
    EditText etPersonName,etPhoneNumber;
    Button btnShow,btnAdd;



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
        lv = (ListView) findViewById(R.id.lv);
        btnShow= (Button) findViewById(R.id.btnShow);
        btnAdd= (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ContactList contactList = new ContactList(etPersonName.getText().toString(),etPhoneNumber.getText().toString());
                db.addContact(contactList);*/


                Cursor cursor = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
                        null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                                + " COLLATE LOCALIZED ASC");

                // Looping through the contacts and adding them to arrayList
                while (cursor.moveToNext()) {
                    String name = cursor
                            .getString(cursor
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = cursor
                            .getString(cursor
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    Toast.makeText(getApplicationContext(),name+ "  " + phoneNumber, Toast.LENGTH_LONG).show();


                    ContactList contactList = new ContactList(name,phoneNumber);
                    db.addContact(contactList);





                }

                cursor.close();
                Toast.makeText(getApplicationContext(), "Imported", Toast.LENGTH_LONG).show();

            }
        });
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
