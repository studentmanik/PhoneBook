package com.example.ranacom.phonebook;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
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

    ViewPager pager;

    DBHandler db;

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

    private ActionBar.TabListener tabListener = new ActionBar.TabListener() {
        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            pager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }
    };


    private void init() {
        db = new DBHandler(this);
        ContentResolver cr = getBaseContext().getContentResolver();
        Cursor cur = cr .query(ContactsContract.Contacts.CONTENT_URI, null, null, null,  null+ " COLLATE LOCALIZED ASC");

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
        cur.close();





        Toast.makeText(getApplicationContext(), "Imported", Toast.LENGTH_LONG).show();





        pager=(ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new SwipeAdapter(getSupportFragmentManager()));

        pager.setOnPageChangeListener(onPageChangeListener);


        ActionBar actionBar= getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(R.style.ColorActionbar));


        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setIcon(R.drawable.favorite).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setIcon(R.drawable.single).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setIcon(R.drawable.group).setTabListener(tabListener));
    }



    private ViewPager.OnPageChangeListener onPageChangeListener= new ViewPager.SimpleOnPageChangeListener(){
        @Override
        public void onPageSelected(int position) {
            getSupportActionBar().setSelectedNavigationItem(position);
            super.onPageSelected(position);
        }
    };

    private class SwipeAdapter extends FragmentPagerAdapter {
        public SwipeAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            Fragment fragment=null;

            switch (i){

                case 0:
                   fragment=new FavoriteContactActivity();
                    break;
                case 1:
                  fragment= new SingleContactActivity();
                    break;
                case 2:
                fragment= new GroupContactActivity();
                    break;

            }


            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

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
