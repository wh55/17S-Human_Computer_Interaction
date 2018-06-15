package com.example.wanhao.contactmanager;

import android.app.Activity;
import android.widget.EditText;

/*
 * Created by Hao WAN on 3/24/2017.
 * Contact Manager Project for 17s CS6326 HCI.
 *
 * Hao WAN (hxw161730)
 * Yanjiao JIA (yxj160130)
*/

public class DataSave {
    private Activity activity;

    // author: Yanjiao JIA
    public DataSave(Activity activity) {
        this.activity = activity;
    }

    // author: Yanjiao JIA
    public Edit.Contact generate() {
        EditText firstNameIn = (EditText) activity.findViewById(R.id.firstName);
        String firstName = firstNameIn.getText().toString();

        EditText lastNameIn = (EditText) activity.findViewById(R.id.lastName);
        String lastName = lastNameIn.getText().toString();

        EditText phoneIn = (EditText) activity.findViewById(R.id.phone);
        String phone = phoneIn.getText().toString();

        EditText emailIn = (EditText) activity.findViewById(R.id.email);
        String email = emailIn.getText().toString();

        EditText birthDateIn = (EditText) activity.findViewById(R.id.birthDate);
        String birthDate = birthDateIn.getText().toString();

        return new Edit.Contact(firstName, lastName, phone, email, birthDate);
    }

}
