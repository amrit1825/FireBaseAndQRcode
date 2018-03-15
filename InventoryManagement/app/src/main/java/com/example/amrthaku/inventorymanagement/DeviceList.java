package com.example.amrthaku.inventorymanagement;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class DeviceList extends ArrayAdapter<Device> {
    private Activity context;
    List<Device> deviceList;

    public DeviceList(Activity context, List<Device> deviceList) {
        super(context, R.layout.listview, deviceList);
        this.context = context;
        this.deviceList = deviceList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.listview, null, true);

        TextView textViewOwner = (TextView) listViewItem.findViewById(R.id.textViewOwner);
        TextView textViewManufacturer = (TextView) listViewItem.findViewById(R.id.textViewManufacturer);
        TextView textViewImei = (TextView) listViewItem.findViewById(R.id.textViewImei);

        Device device = deviceList.get(position);
        textViewOwner.setText(device.getOwner());
        textViewManufacturer.setText(device.getManufacturer());
        textViewImei.setText(device.getImei());

        return listViewItem;
    }
}