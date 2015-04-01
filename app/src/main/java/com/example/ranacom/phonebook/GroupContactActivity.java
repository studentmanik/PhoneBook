package com.example.ranacom.phonebook;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class GroupContactActivity extends Fragment {
    EditText mContactNameEditText,mContactPhoneEditText,mContactEmailEditText,mCompanyName,mJobTitle;
    Button btnAdd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.add_contact, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        btnAdd= (Button) view.findViewById(R.id.btnAdd);
        mContactNameEditText= (EditText) view.findViewById(R.id.mContactNameEditText);
        mContactPhoneEditText= (EditText) view.findViewById(R.id.mContactPhoneEditText);
        mContactEmailEditText= (EditText) view.findViewById(R.id.mContactEmailEditText);
        mCompanyName= (EditText) view.findViewById(R.id.mCompanyName);
        mJobTitle= (EditText) view.findViewById(R.id.mJobTitle);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = mContactNameEditText.getText().toString();
                String phone = mContactPhoneEditText.getText().toString();
                String email = mContactEmailEditText.getText().toString();

                String company = mCompanyName.getText().toString();
                String jobtitle = mJobTitle.getText().toString();

                Intent insertIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
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
                startActivity(insertIntent);


            }
        });
           }


}
