package com.example.amrthaku.inventorymanagement;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RetrieveActivity extends AppCompatActivity {

    Button buttonMenuBack;

    ListView listViewDevice;
    List<Device> deviceList;
    private DatabaseReference databaseDevices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        deviceList = new ArrayList<>();

        databaseDevices = FirebaseDatabase.getInstance().getReference("Devices");
        listViewDevice = (ListView) findViewById(R.id.listViewDevice);

        buttonMenuBack = (Button) findViewById(R.id.buttonMenuBack);

        buttonMenuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                finish();
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        listViewDevice.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Device device = deviceList.get(i);

                showUpdateDialog(device.getId() ,device.getOwner(),device.getManufacturer(),device.getImei());
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseDevices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                deviceList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Device device = postSnapshot.getValue(Device.class);
                    //adding artist to the list
                    deviceList.add(device);
                }

                //creating adapter
                DeviceList deviceAdapter = new DeviceList(RetrieveActivity.this, deviceList);
                //attaching adapter to the listview
                listViewDevice.setAdapter(deviceAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private  void showUpdateDialog(final String deviceId, String deviceOwner, final String deviceManufacturer, final String deviceImei){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_device, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextNewOwner = (EditText) dialogView.findViewById(R.id.editTextNewOwner);
        Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle("Update / Delete device");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editTextNewOwner.getText().toString().trim();
                updateDevice(deviceId,name,deviceManufacturer,deviceImei);

                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDevice(deviceId);

                alertDialog.dismiss();
            }
        });



    }

    private void updateDevice(String id, String newOwner, String imei, String manufacturer){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Devices");

        Device device = new Device(id,manufacturer,imei,newOwner);

        databaseReference.child(id).setValue(device);


        Toast.makeText(this,"Updated Successfully",Toast.LENGTH_SHORT).show();



    }

    private void deleteDevice(String id){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Devices");

        databaseReference.child(id).removeValue();

        Toast.makeText(this,"Deleted Successfully",Toast.LENGTH_SHORT).show();

    }


}

