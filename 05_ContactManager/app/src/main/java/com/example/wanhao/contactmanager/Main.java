package com.example.wanhao.contactmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/*
 * Created by Hao WAN on 3/24/2017.
 * Contact Manager Project for 17s CS6326 HCI.
 *
 * Hao WAN (hxw161730)
 * Yanjiao JIA (yxj160130)
*/

public class Main extends ActionBarActivity {

    // author: Hao WAN
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent();
        intent.setClassName("com.example.wanhao.contactmanager", "com.example.wanhao.contactmanager.ShowList");
        startActivity(intent);
        finish();
    }
}
