package com.example.ranacom.phonebook;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


public class SingleContactActivity extends Fragment {
    GridView gv;

    ContactListAdapter adapter;
    DBHandler db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.singlelayout, container, false);
        init(view);
        return view;


    }
    private void init(View view) {
        db=new DBHandler(getActivity());

        gv = (GridView) view.findViewById(R.id.gv);


        adapter= new ContactListAdapter(getActivity(),db.getAllContact());

        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getActivity(),ContactDetails.class);
                startActivity(i);
            }
        });
    }

}
