package com.example.wanhao.contactmanager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

/*
 * Created by Hao WAN on 3/24/2017.
 * Contact Manager Project for 17s CS6326 HCI.
 *
 * Hao WAN (hxw161730)
 * Yanjiao JIA (yxj160130)
*/

public class ShowDetail extends ActionBarActivity {

    // author: Hao WAN
    private static long contactIndex;
    private static Context context;

    // author: Hao WAN
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        final long contactId = getIntent().getExtras().getLong("contactId");
        Data data = new Data();
        context = this;
        Edit.Contact contact = data.findContactById(contactId, this);
        contactIndex = contactId;

        if (contact != null) {
            updateContactDetail(contact);
        }
    }

    // author: Hao WAN
    private void updateContactDetail(Edit.Contact contact) {

        TextView firstName = (TextView) findViewById(R.id.firstNameValue);
        firstName.setText(contact.getFirstName());
        TextView lastName = (TextView) findViewById(R.id.lastNameValue);
        lastName.setText(contact.getLastName());
        final TextView phone = (TextView) findViewById(R.id.phoneValue);
        phone.setText(contact.getPhoneNumber());
        TextView email = (TextView) findViewById(R.id.emailValue);
        email.setText(contact.getEmail());
        TextView birthDate = (TextView) findViewById(R.id.birthDateValue);
        birthDate.setText(contact.getBirthDate());
        
        Button callNow = (Button) findViewById(R.id.btnCall);
        Button textNow = (Button) findViewById(R.id.btnText);

        final String getNumber = phone.getText().toString();

        callNow.setOnClickListener(new View.OnClickListener() {

            public void onClick(View viewBtnCall) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + getNumber));
                startActivity(callIntent);
                finish();
            }
        });


        textNow.setOnClickListener(new View.OnClickListener() {

            public void onClick(View viewBtnText) {
                Intent textIntent = new Intent(Intent.ACTION_VIEW);
                textIntent.setData(Uri.parse("sms:" + getNumber));
                startActivity(textIntent);
                finish();
            }
        });
        
    }

    // author: Hao WAN
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menudetail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // author: Hao WAN
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if (id == R.id.edit){
            Intent intent = new Intent();
            intent.setClassName("com.example.wanhao.contactmanager", "com.example.wanhao.contactmanager.Edit");

            intent.putExtra("contactIndex",contactIndex);
            startActivity(intent);
        }
        
        if (id == R.id.cancel){
            Intent i = new Intent();
            i.setClassName("com.example.wanhao.contactmanager", "com.example.wanhao.contactmanager.ShowList");
            startActivity(i);
            finish();
        }
               
        if (id == R.id.delete){  //show delete conforming dialog here
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.delete);

            Button dialogButtonY = (Button) dialog.findViewById(R.id.dialogButtonYes);

            dialogButtonY.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Data data = new Data();
                    data.deleteContact(contactIndex,context);
                    Toast.makeText(ShowDetail.this, "Contact deleted!", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    Intent i = new Intent();
                    i.setClassName("com.example.wanhao.contactmanager", "com.example.wanhao.contactmanager.ShowList");
                    startActivity(i);
                    finish();
                }
            });

            Button dialogButtonN = (Button) dialog.findViewById(R.id.dialogButtonNo);

            dialogButtonN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }

            });

            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
