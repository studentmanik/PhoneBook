package com.example.ranacom.phonebook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SingleContactActivity extends Fragment {
    GridView gv;
    ContactList contactList;
    ContactListAdapter adapter;
    DBHandler db;
    EditText search;
    List<ContactList> contactLists;
    List<ContactList> searchLists=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.singlelayout, container, false);
        init(view);
        return view;


    }

    private void applySearch(String searchStr) {

        searchLists.clear();
        //contactLists=db.getAllContact();

        for (int i = 0; i < contactLists.size(); i++) {
          if (contactLists.get(i).getContactName().contains(searchStr)) {
                Toast.makeText(getActivity(), contactLists.get(i).getContactName().toLowerCase(), Toast.LENGTH_SHORT).show();

                searchLists.add(contactLists.get(i));


         }

        }

        adapter= new ContactListAdapter(getActivity(),searchLists);
        gv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void init(View view) {
        db=new DBHandler(getActivity());
         contactList=new ContactList();
        gv = (GridView) view.findViewById(R.id.gv);
        search= (EditText) view.findViewById(R.id.search_bar1);
        contactLists=db.getAllContact();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (search.getText().toString().trim().length() > 0) {
                search.setBackgroundColor(Color.BLUE);

                    applySearch(search.getText().toString().trim());
                } else {

                   adapter= new ContactListAdapter(getActivity(),contactLists);
                    gv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        adapter= new ContactListAdapter(getActivity(),contactLists);

        gv.setAdapter(adapter);


        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String contactId = db.getAllContact().get(position).getContactId();
                String contactName = db.getAllContact().get(position).getContactName();
              String phoneNumber = db.getAllContact().get(position).getPhoneNumber();
              String emailAddress = db.getAllContact().get(position).getEmailAddress();
                String contactImage = db.getAllContact().get(position).getIVContactImage();
                int phoneId = db.getAllContact().get(position).getPhoneId();


                Intent i=new Intent(getActivity(),ContactDetails.class);
                i.putExtra("contactId", contactId);
                i.putExtra("contactName", contactName);
                i.putExtra("phoneNumber", phoneNumber);
                i.putExtra("emailAddress", emailAddress);
                i.putExtra("contactImage", contactImage);
                i.putExtra("phoneId", phoneId);

                startActivity(i);
            }
        });
    }

}
