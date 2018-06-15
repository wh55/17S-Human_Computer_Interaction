package com.example.wanhao.contactmanager;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * Created by Hao WAN on 3/24/2017.
 * Contact Manager Project for 17s CS6326 HCI.
 *
 * Hao WAN (hxw161730)
 * Yanjiao JIA (yxj160130)
*/


public class Data {
    private static final String FILE_PATH = "data.txt";
    private DataSplit dataSplit = new DataSplit();

    // author: Yanjiao JIA
    public List<Edit.Contact> findBySearchQuery(String query, Context context) {

        List<String> lines = readLines(context);
        List<Edit.Contact> contacts = new ArrayList<Edit.Contact>();

        for (String line : lines) {

            if (query.isEmpty() || line.toLowerCase().contains(query.toLowerCase())) {
                Edit.Contact contact = dataSplit.reverse(line);
                contacts.add(contact);
            }
        }

        return contacts;
    }

    // author: Yanjiao JIA
    public Edit.Contact findContactById(Long id, Context context) {

        List<String> lines = readLines(context);

        for (String line : lines) {

            Edit.Contact contact = dataSplit.reverse(line);
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }

    // author: Yanjiao JIA
    public List<Edit.Contact> getAllContacts(Context context) {

        List<String> lines = readLines(context);
        List<Edit.Contact> contacts = new ArrayList<Edit.Contact>();

        for (String line : lines) {
            Edit.Contact contact = dataSplit.reverse(line);
            contacts.add(contact);
        }

        sortContacts(contacts);
        return contacts;
    }

    // author: Yanjiao JIA
    private List<String> readLines(Context context) {

        List<String> lines = new ArrayList<String>();
        String line;

        try {
            InputStream inputStream = context.openFileInput(FILE_PATH);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                while ((line = bufferedReader.readLine()) != null) {
                    lines.add(line+"\n");
                }

                inputStream.close();
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }


    // author: Yanjiao JIA
    public void addContact(Edit.Contact contactToAdd, Context context) {

        File file = new File(context.getFilesDir(), FILE_PATH);
        String contactRecord = dataSplit.transform(contactToAdd);

        try {
            FileOutputStream fileOutputStream;

            if (file.exists()) {
                fileOutputStream = context.openFileOutput(FILE_PATH, Context.MODE_APPEND);
            }
            else {
                fileOutputStream = context.openFileOutput(FILE_PATH, Context.MODE_PRIVATE);
            }

            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutputStream);
            outputWriter.write(contactRecord);
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // author: Yanjiao JIA
    public void editContact(Edit.Contact contactToEdit, Context context, long contactIndex){

        List<String> lines = getStringList(context);
        contactToEdit.setId(System.currentTimeMillis());
        String newRecord = dataSplit.transform(contactToEdit);
        String idx = String.valueOf(contactIndex);

        for (String record: lines){
            if (record.contains(idx))
                lines.set(lines.indexOf(record),newRecord);
        }

        try {
            FileOutputStream fileOutputStream;
            fileOutputStream = context.openFileOutput(FILE_PATH, Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutputStream);

            for (String record: lines){
                outputWriter.write(record);
            }

            outputWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // author: Yanjiao JIA
    public List<String> getStringList(Context context){

        List<String> lines = new ArrayList<String>();
        String line;

        try {
            InputStream inputStream = context.openFileInput(FILE_PATH);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                while ((line = bufferedReader.readLine()) != null) {
                      lines.add(line+"\n");
                }

                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    // author: Yanjiao JIA
    public void deleteContact(long contactIndex, Context context){
        List<String> lines = getStringList(context);
        String myindex = String.valueOf(contactIndex);
        int indexTodelete = -1;

        for (String record: lines){
            Edit.Contact c = dataSplit.reverse(record);
            String index = String.valueOf(c.getId());
            if (index.equals(myindex))
                indexTodelete = lines.indexOf(record);
        }

        lines.remove(indexTodelete);

        try {
            FileOutputStream fileOutputStream;
            fileOutputStream = context.openFileOutput(FILE_PATH, Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutputStream);
            for (String record: lines){
                outputWriter.write(record);
            }
            outputWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // author: Yanjiao JIA
    public void sortContacts(List<Edit.Contact> contacts){
        Collections.sort(contacts, new Comparator<Edit.Contact>() {
            @Override
            public int compare(Edit.Contact first, Edit.Contact second) {
                return first.getFirstName().compareToIgnoreCase(second.getFirstName());
            }
        });

    }
}
