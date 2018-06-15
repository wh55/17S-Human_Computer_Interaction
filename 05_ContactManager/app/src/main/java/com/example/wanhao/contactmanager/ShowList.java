package com.example.wanhao.contactmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

/*
 * Created by Hao WAN on 3/24/2017.
 * Contact Manager Project for 17s CS6326 HCI.
 *
 * Hao WAN (hxw161730)
 * Yanjiao JIA (yxj160130)
*/

public class ShowList extends ActionBarActivity {

    // author: Hao WAN
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        final Data data = new Data();

        List<Edit.Contact> contacts = data.getAllContacts(this);
        ListView listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent displayContactIntent = new Intent();
                displayContactIntent.setClassName("com.example.wanhao.contactmanager", "com.example.wanhao.contactmanager.ShowDetail");
                displayContactIntent.putExtra("contactId", id);
                startActivity(displayContactIntent);
            }

        });

        addContactsToList(contacts);
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // author: Hao WAN
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Edit.Contact> filteredContacts = data.findBySearchQuery(query, ShowList.this);
                addContactsToList(filteredContacts);
                return false;
            }

            // author: Hao WAN
            @Override
            public boolean onQueryTextChange(String newText) {
                List<Edit.Contact> filteredContacts = data.findBySearchQuery(newText, ShowList.this);
                addContactsToList(filteredContacts);
                return false;
            }
        });

    }

    // author: Hao WAN
    private void addContactsToList(List<Edit.Contact> contacts) {
        ListView listView = (ListView) findViewById(R.id.listView);
        DataGet dataGet = new DataGet(contacts, this);
        listView.setAdapter(dataGet);
    }

    // author: Hao WAN
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menulist, menu);
        return super.onCreateOptionsMenu(menu);

    }

    // author: Hao WAN
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent intent = new Intent();
            intent.setClassName("com.example.wanhao.contactmanager", "com.example.wanhao.contactmanager.Add");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
