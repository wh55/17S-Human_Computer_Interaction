package com.example.wanhao.contactmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/*
 * Created by Hao WAN on 3/24/2017.
 * Contact Manager Project for 17s CS6326.001 HCI.
 *
 * Hao WAN (hxw161730)
 * Yanjiao JIA (yxj160130)
*/

public class Add extends ActionBarActivity {
    private Data data;
    private DataSave dataSave;

    // author: Hao WAN
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new Data();
        dataSave = new DataSave(this);
        setContentView(R.layout.add);
    }

    // author: Hao WAN
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuadd, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // author: Hao WAN
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.save) {
            Edit.Contact contactFromView = dataSave.generate();

            if(contactFromView.getFirstName().trim().equals("")) //user must at least enter the first name
            {
                Toast.makeText(this, "Pleast Enter First Name!", Toast.LENGTH_LONG).show();
            }
            else
            {
                contactFromView.setId(System.currentTimeMillis());
                data.addContact(contactFromView, this);
                Toast.makeText(this, "Contact added!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClassName("com.example.wanhao.contactmanager", "com.example.wanhao.contactmanager.ShowList");
                startActivity(intent);
                finish();
            }
        }

        if (item.getItemId() == R.id.cancel){ //back to the list
            Intent intent = new Intent();
            intent.setClassName("com.example.wanhao.contactmanager", "com.example.wanhao.contactmanager.ShowList");
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
