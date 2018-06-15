package com.example.wanhao.contactmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Created by Hao WAN on 3/24/2017.
 * Contact Manager Project for 17s CS6326 HCI.
 *
 * Hao WAN (hxw161730)
 * Yanjiao JIA (yxj160130)
*/

public class Edit extends ActionBarActivity {

    private Data data;
    private DataSave dataSave;
    private static Contact oldContact;
    public static Context context;

    // author: Hao WAN
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        final long contactIndex = getIntent().getExtras().getLong("contactIndex");
        data = new Data();
        dataSave = new DataSave(this);
        oldContact = data.findContactById(contactIndex,this);
        context = this;

        if (oldContact != null) {
            populateForm(oldContact);
        }

    }

    // author: Hao WAN
    public void populateForm(Contact contact){

        TextView firstName = (TextView) findViewById(R.id.firstName);
        firstName.setText(contact.getFirstName());
        TextView lastName = (TextView) findViewById(R.id.lastName);
        lastName.setText(contact.getLastName());
        TextView phone = (TextView) findViewById(R.id.phone);
        phone.setText(contact.getPhoneNumber());
        TextView email = (TextView) findViewById(R.id.email);
        email.setText(contact.getEmail());
        TextView birthDate = (TextView) findViewById(R.id.birthDate);
        birthDate.setText(contact.getBirthDate());
    }

    // author: Hao WAN
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuedit, menu);
        return true;
    }

    // author: Hao WAN
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save) {
            Contact contactFromView = dataSave.generate();

            if(contactFromView.getFirstName().trim().equals(""))
            {
                Toast.makeText(this, "Pleast Enter First Name!", Toast.LENGTH_LONG).show();
            }
            else
            {
                contactFromView.setId(System.currentTimeMillis());
                long oldId = oldContact.getId();

                data.editContact(contactFromView, context, oldId);
                Toast.makeText(this, "Contact updated!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClassName("com.example.wanhao.contactmanager", "com.example.wanhao.contactmanager.ShowList");
                startActivity(intent);
                finish();
            }
        }

        if (id == R.id.cancel){
            Intent intent = new Intent();
            intent.setClassName("com.example.wanhao.contactmanager", "com.example.wanhao.contactmanager.ShowList");
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    // author: Hao WAN
    public static class Contact {
        private long id;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String email;
        private String birthDate;

        // author: Hao WAN
        public Contact(long id) {
            this.id = id;
        }

        // author: Hao WAN
        public Contact(String firstName, String lastName, String phoneNumber, String email, String birthDate) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.birthDate = birthDate;
        }

        // author: Hao WAN
        public long getId() {
            return id;
        }
        public void setId(long id) {
            this.id = id;
        }

        // author: Hao WAN
        public String getFirstName() {
            return firstName;
        }
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        // author: Hao WAN
        public String getLastName() {
            return lastName;
        }
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        // author: Hao WAN
        public String getPhoneNumber() {
            return phoneNumber;
        }
        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        // author: Hao WAN
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        // author: Hao WAN
        public String getBirthDate() {
            return birthDate;
        }
        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

    }
}
