package com.example.ranacom.phonebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class ContactListAdapter extends ArrayAdapter<ContactList> {

    LayoutInflater inflater;

    public ContactListAdapter(Context context, List<ContactList> objects) {
        super(context,0, objects);
        inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contact_list_view, null);

            holder = new ViewHolder();
          //  holder.IVContactImage=(ImageView)convertView.findViewById(R.id.IVContactImage);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvPhoneNumber = (TextView) convertView.findViewById(R.id.tvPhoneNumber);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

      ContactList contactList = getItem(position);
       holder.tvName.setText(contactList.getContactName());
        holder.tvPhoneNumber.setText(contactList.getPhoneNumber());


//        Animation animationY=new TranslateAnimation(0,convertView.getWidth()/2, convertView.getHeight()/2,0);
//        animationY.setDuration(10000);
//        convertView.startAnimation(animationY);
//        animationY=null;

        return convertView;
    }

    static class ViewHolder {
     //   public ImageView IVContactImage;
        public TextView tvName;
        public TextView tvPhoneNumber;
    }
}
