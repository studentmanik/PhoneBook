package com.example.ranacom.phonebook;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AddContact extends ActionBarActivity {
EditText mContactNameEditText,mContactPhoneEditText,mContactEmailEditText,takeImage,mJobTitle;
    Button btnAdd;
    Uri outputFileUri;
    DBHandler db;
    int TAKE_PHOTO_CODE = 0;
    public static int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);


        btnAdd= (Button) findViewById(R.id.btnAdd);
        mContactNameEditText= (EditText) findViewById(R.id.mContactNameEditText);
        mContactPhoneEditText= (EditText) findViewById(R.id.mContactPhoneEditText);
        mContactEmailEditText= (EditText) findViewById(R.id.mContactEmailEditText);
        takeImage= (EditText) findViewById(R.id.takeImage);
        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
                File newdir = new File(dir);
                newdir.mkdirs();
                String file = dir+count+".jpg";
                File newfile = new File(file);
                try {
                    newfile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                outputFileUri = Uri.fromFile(newfile);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
                File imgFile = new  File(file);
                count++;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = mContactNameEditText.getText().toString();
                String phone = mContactPhoneEditText.getText().toString();
                String email = mContactEmailEditText.getText().toString();
              //  String image_uri= outputFileUri;
                String contactId= null;

               // ContactList contactList = new ContactList(name,phone,outputFileUri,email,contactId);
              //  db.addContact(contactList);

               /* Intent insertIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
                insertIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

// Sets the new contact name
                insertIntent.putExtra(ContactsContract.Intents.Insert.NAME, name);

// Sets the new company and job title
                insertIntent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                insertIntent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobtitle);
                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                ContentValues rawContactRow = new ContentValues();

                rawContactRow.put(ContactsContract.RawContacts.ACCOUNT_TYPE,"Account type");
                rawContactRow.put(ContactsContract.RawContacts.ACCOUNT_NAME, "Account name");
                contactData.add(rawContactRow);
                ContentValues phoneRow = new ContentValues();


                phoneRow.put(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                );


                phoneRow.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);


                contactData.add(phoneRow);

                ContentValues emailRow = new ContentValues();


                emailRow.put(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                );


                emailRow.put(ContactsContract.CommonDataKinds.Email.ADDRESS, email);


                contactData.add(emailRow);

                insertIntent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);

// Send out the intent to start the device's contacts app in its add contact activity.
                startActivity(insertIntent);*/


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);

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
