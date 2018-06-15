package com.example.wanhao.contactmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/*
 * Created by Hao WAN on 3/24/2017.
 * Contact Manager Project for 17s CS6326 HCI.
 *
 * Hao WAN (hxw161730)
 * Yanjiao JIA (yxj160130)
*/

public class DataGet extends BaseAdapter {
    private List<Edit.Contact> contacts;
    private LayoutInflater layoutInflater;

    // author: Yanjiao JIA
    public DataGet(List<Edit.Contact> contacts, Context context) {
        this.contacts = contacts;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // author: Yanjiao JIA
    @Override
    public int getCount() {
        return contacts.size();
    }

    // author: Yanjiao JIA
    @Override
    public Edit.Contact getItem(int m) {
        return contacts.get(m);
    }

    // author: Yanjiao JIA
    @Override
    public long getItemId(int m) {
        return contacts.get(m).getId();
    }

    // author: Yanjiao JIA
    @Override
    public View getView(int m, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = layoutInflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        } //use built-in XML layout

        Edit.Contact contact = getItem(m);
        TextView contactView = (TextView) view.findViewById(android.R.id.text1);
        contactView.setText(contact.getFirstName() + " " + contact.getLastName() + "\n" + contact.getPhoneNumber());

        return view;
    }
}
