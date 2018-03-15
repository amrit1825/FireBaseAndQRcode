package com.example.amrthaku.inventorymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SaveActivity extends AppCompatActivity {

    //view objects
    private EditText editTextOwner,editTextManufacturer,editTextImei;
    private Button buttonAddDevice,buttonMenu;
    //ListView listViewArtists;

    //a list to store all the artist from firebase database
    //List<Artist> artists;

    //our database reference object
    private DatabaseReference databaseDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        //getting the reference of artists node
        databaseDevices = FirebaseDatabase.getInstance().getReference("Devices");

        //getting views
        editTextOwner = (EditText) findViewById(R.id.editTextOwner);
        editTextManufacturer = (EditText) findViewById(R.id.editTextManufacturer);
        editTextImei = (EditText) findViewById(R.id.editTextImei);

        buttonAddDevice = (Button) findViewById(R.id.buttonAddDevice);
        buttonMenu = (Button) findViewById(R.id.buttonMenu);
        //list to store artists
        //artists = new ArrayList<>();


        //adding an onclicklistener to button
        buttonAddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                addDevice();
            }
        });

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                finish();
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
    }

    /*
    * This method is saving a new artist to the
    * Firebase Realtime Database
    * */
    private void addDevice() {
        //getting the values to save
        String owner = editTextOwner.getText().toString().trim();
        String manufacturer = editTextManufacturer.getText().toString().trim();
        String imei = editTextImei.getText().toString().trim();

        //checking if the value is provided
        if (!TextUtils.isEmpty(owner) && !TextUtils.isEmpty(manufacturer) && !TextUtils.isEmpty(imei)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseDevices.push().getKey();

            //creating an Artist Object
            Device device = new Device();
            device.setId(id);
            device.setOwner(owner);
            device.setManufacturer(manufacturer);
            device.setImei(imei);

            Log.i("Information",device.getOwner());
            Log.i("Information",device.getManufacturer());
            Log.i("Information",device.getImei());
            //Saving the Artist
            databaseDevices.child(id).setValue(device);

            //setting edittext to blank again
            editTextOwner.setText("");
            editTextManufacturer.setText("");
            editTextImei.setText("");

            //displaying a success toast
            Toast.makeText(this, "Device added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter all the required fields", Toast.LENGTH_LONG).show();
        }
    }
}
