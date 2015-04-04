package com.example.ranacom.phonebook;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ContactDetails extends ActionBarActivity {
ImageView contactImage;
    TextView contactName,phoneNumber,emailAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        String contactId1 = getIntent().getStringExtra("contactId");
        String contactName1 = getIntent().getStringExtra("contactName");
        final String phoneNumber1 = getIntent().getStringExtra("phoneNumber");
        String emailAddress1 = getIntent().getStringExtra("emailAddress");
        String contactImage1 = getIntent().getStringExtra("contactImage");
        final String call="tel:"+phoneNumber1;
        String phoneId1 = getIntent().getStringExtra("phoneId");
        contactName= (TextView) findViewById(R.id.contactName);
        contactName.setText(contactName1);
        contactImage= (ImageView) findViewById(R.id.contactImage);
        if (contactImage1 != null) {
            // Toast.makeText(getContext(),image_uri,Toast.LENGTH_LONG).show();
            contactImage.setImageURI(Uri.parse(contactImage1));

        }else{
            contactImage.setImageResource(R.drawable.spalish);
        }

        phoneNumber= (TextView) findViewById(R.id.phoneNumber);
        phoneNumber.setText(phoneNumber1);
        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse(call));

                try {
                    startActivity(phoneIntent);
                  //  finish();

                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(),
                            "Call faild, please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        emailAddress= (TextView) findViewById(R.id.emailAddress);
        emailAddress.setText(emailAddress1);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_details, menu);
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
