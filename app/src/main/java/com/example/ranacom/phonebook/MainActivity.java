package com.example.ranacom.phonebook;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    int id = 1;
    SharedPreferences sharedpreferences;
    EditText etPersonName;
   Button btnShow;

    TextView tv;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPersonName= (EditText) findViewById(R.id.etPersonName);
        tv= (TextView) findViewById(R.id.tv);
        btnShow= (Button) findViewById(R.id.btnShow);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, getApplicationContext().MODE_PRIVATE);
        if (sharedpreferences.contains(Name))
        {
            etPersonName.setVisibility(View.INVISIBLE);
            btnShow.setVisibility(View.INVISIBLE);
            tv.setText(sharedpreferences.getString(Name, ""));

            Thread background = new Thread() {
                public void run() {

                    try {
                        // Thread will sleep for 5 seconds
                        sleep(1*10);

                        // After 5 seconds redirect to another intent
                        Intent i=new Intent(getBaseContext(),Contact.class);
                        startActivity(i);

                        //Remove activity
                        finish();

                    } catch (Exception e) {

                    }
                }
            };
            background.start();


        }
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = etPersonName.getText().toString();
                tv.setText(n);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Name, n);
                editor.commit();

                mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mBuilder = new NotificationCompat.Builder(MainActivity.this);
                mBuilder.setContentTitle("Download")
                        .setContentText("Download in progress")
                        .setSmallIcon(R.drawable.group);


               new Downloader().execute();


            }
        });




        // start thread



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private class Downloader extends AsyncTask<Void, Integer, Integer> {
        private ProgressDialog progress;

        @Override
        protected Integer doInBackground(Void... params) {
         /*   int i;
            for (i = 0; i <= 100; i += 5) {
                // Sets the progress indicator completion percentage
                publishProgress(Math.min(i, 100));
                try {
                    // Sleep for 5 seconds
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                 //   Log.d("TAG", "sleep failure");
                }
            }*/
            int i;
            DBHandler db;
            db = new DBHandler(getApplicationContext());
            ContentResolver cr = getBaseContext().getContentResolver();
            Cursor cur = cr .query(ContactsContract.Contacts.CONTENT_URI, null, null, null,  null+ " COLLATE LOCALIZED ASC");
            for (i = 0; i <= cur.getCount(); i += 1) {
                // Sets the progress indicator completion percentage
                publishProgress(Math.min(i, 100));
                try {
                    // Sleep for 5 seconds
                    Thread.sleep(2);




            if (cur.getCount() > 0) {


                while (cur .moveToNext()) {

                    String phoneNumber = null;
                    String name = null;
                    String image_uri = null;
                    String emailAddress = null;
                    String emailAddress1 = null;

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
                } catch (InterruptedException e) {
                    //   Log.d("TAG", "sleep failure");
                }
            }
            cur.close();
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            mBuilder.setContentText("Download complete");
            // Removes the progress bar
            mBuilder.setProgress(0, 0, false);
            mNotifyManager.notify(id, mBuilder.build());
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Displays the progress bar for the first time.
            mBuilder.setProgress(100, 0, false);
            mNotifyManager.notify(id, mBuilder.build());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Update progress
            mBuilder.setProgress(100, values[0], false);
            mNotifyManager.notify(id, mBuilder.build());
            super.onProgressUpdate(values);
        }
    }
}
