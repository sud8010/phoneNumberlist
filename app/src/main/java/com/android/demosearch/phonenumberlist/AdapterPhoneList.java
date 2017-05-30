package com.android.demosearch.phonenumberlist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by daw on 26/5/17.
 */

public class AdapterPhoneList extends ArrayAdapter<PhoneModel>{

    Context context;
    int layoutResourceId;
    ArrayList<PhoneModel> data = null;

    public AdapterPhoneList(Context context, int layoutResourceId, ArrayList<PhoneModel> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new WeatherHolder();

            holder.txtName = (TextView)row.findViewById(R.id.txtName);
            holder.txtPhoneNumber = (TextView)row.findViewById(R.id.txtPhoneNumber);

            row.setTag(holder);
        }
        else
        {
            holder = (WeatherHolder)row.getTag();
        }

        String name = data.get(position).name;
        String number = data.get(position).number;
        holder.txtName.setText(name);
        holder.txtPhoneNumber.setText(number);

        return row;
    }

    static class WeatherHolder
    {

        TextView txtName;
        TextView txtPhoneNumber;
    }
}