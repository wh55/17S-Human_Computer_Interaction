package com.example.wanhao.contactmanager;


/*
 * Created by Hao WAN on 3/24/2017.
 * Contact Manager Project for 17s CS6326 HCI.
 *
 * Hao WAN (hxw161730)
 * Yanjiao JIA (yxj160130)
*/

public class DataSplit {
    private static final String CONTACT_DELIMITER = "\t";
    public static final String BLANK = " ";

    // author: Yanjiao JIA
    public String transform(Edit.Contact contact) {
            return contact.getId() + CONTACT_DELIMITER + contact.getFirstName() + CONTACT_DELIMITER +  nullSafe(contact.getLastName())
                    + CONTACT_DELIMITER + nullSafe(contact.getPhoneNumber()) + CONTACT_DELIMITER + nullSafe(contact.getEmail()) + CONTACT_DELIMITER + nullSafe(contact.getBirthDate()) + "\n";
    }

    // author: Yanjiao JIA
    private String nullSafe(String value) { //the other fields except first name could be left blank
        if (value == null) {
            return BLANK;
        }
        return value;
    }

    // author: Yanjiao JIA
    public Edit.Contact reverse(String line) {
        String[] contactRecord = line.split(CONTACT_DELIMITER);
        Long id = Long.valueOf(contactRecord[0]);
        Edit.Contact contact = new Edit.Contact(id);
        contact.setFirstName(contactRecord[1]);
        contact.setLastName(contactRecord[2]);
        contact.setPhoneNumber(contactRecord[3]);
        contact.setEmail(contactRecord[4]);
        contact.setBirthDate(contactRecord[5].trim());
        return contact;
    }
}
