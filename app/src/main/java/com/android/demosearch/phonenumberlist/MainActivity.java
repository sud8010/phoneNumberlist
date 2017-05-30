package com.android.demosearch.phonenumberlist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ListView listview;
    Context context;
    String value = null;
    String keyValue = null;
    private ArrayList<PhoneModel> list;
    HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        init();
        setlistener();
    }

    private void init() {
        setView();
        list = new ArrayList<PhoneModel>();
        map = new HashMap<String, String>();
        list = getPhoneNumberList();
//        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
//        while (phones.moveToNext()) {
//            String phoneName = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//            PhoneModel mdl = new PhoneModel();
//            mdl.name = phoneName;
//            mdl.number = phoneNumber;
//            list.add(mdl);
//
//        }
//        phones.close();
//
//        for (int k = 0; k < list.size(); k++) {
//            if (!map.containsKey(list.get(k).name)) {
//                map.put(list.get(k).name, list.get(k).number);
//            }
//        }
//
//        list.clear();
//        Set<String> keys = map.keySet();
//        for (String key : keys) {
//            PhoneModel mdl = new PhoneModel();
//            mdl.name = key;
//            keyValue = map.get(key);
//            char ch = keyValue.charAt(0);
//            if (ch == '0') {
//                keyValue = keyValue.substring(1);
//            }
//            if (!keyValue.contains("+91")) {
//                value = "+91" + keyValue;
//            } else {
//                value = keyValue;
//            }
//
//            mdl.number = value;
//            list.add(mdl);
//        }


        AdapterPhoneList adapter = new AdapterPhoneList(context, R.layout.phone_data, list);
        listview.setAdapter(adapter);
    }

    private ArrayList<PhoneModel> getPhoneNumberList(){
        ArrayList<PhoneModel> phoneList = new ArrayList<PhoneModel>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String phoneName = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            PhoneModel mdl = new PhoneModel();
            mdl.name = phoneName;
            mdl.number = phoneNumber;
            phoneList.add(mdl);

        }
        phones.close();

        Collections.sort(phoneList, new Comparator<PhoneModel>() {
            public int compare(PhoneModel lhs, PhoneModel rhs) {
                return lhs.name.compareTo(rhs.name);
            }
        });

        for (int k = 0; k < phoneList.size(); k++) {
            if (!map.containsKey(phoneList.get(k).name)) {
                map.put(phoneList.get(k).name, phoneList.get(k).number);
            }
        }

        phoneList.clear();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            PhoneModel mdl = new PhoneModel();
            mdl.name = key;
            keyValue = map.get(key);
            char ch = keyValue.charAt(0);
            if (ch == '0') {
                keyValue = keyValue.substring(1);
            }
            if (!keyValue.contains("+91")) {
                value = "+91" + keyValue;
            } else {
                value = keyValue;
            }

            mdl.number = value;
            phoneList.add(mdl);
        }
        return phoneList;
    }

    private void setView() {
        listview = (ListView) findViewById(R.id.phonelist);
    }

    private void setlistener() {

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = list.get(position).number;
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + str));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
           }
       });
    }

}
