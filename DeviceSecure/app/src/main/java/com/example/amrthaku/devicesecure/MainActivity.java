package com.example.amrthaku.devicesecure;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_PHONE_STATE_READ = 100;
    private int checkedPermission = PackageManager.PERMISSION_DENIED;

    private Button button;
    private TextView textView;
    private EditText editText;
    private ImageView imageView;
    TelephonyManager tm;
    String imei;
    String fail = "Permission denied";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkedPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (Build.VERSION.SDK_INT >= 23 && checkedPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            checkedPermission = PackageManager.PERMISSION_GRANTED;
        }

        if (checkedPermission != PackageManager.PERMISSION_DENIED) {
            button = (Button) findViewById(R.id.button);
            textView = (TextView) findViewById(R.id.details_TextView);
            editText = (EditText) findViewById(R.id.owner_Name_EditTextView);
            imageView = (ImageView) findViewById(R.id.image);
            tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textView.setText("Owner: " + editText.getText() + "\n" +
                            "Manufacture: " + Build.MANUFACTURER + "\n" +
                            "SERIAL: " + Build.SERIAL + "\n" +
                            "MODEL: " + Build.MODEL + "\n" +
                            "IMEI: " + imei + "\n" +
                            "OS Version: " + Build.VERSION.SDK_INT);
                    String text = textView.getText().toString(); // Whatever you need to encode in the QR code
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        imageView.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                }
            });
        } else {
            textView.setText(fail);
        }
    }

    private void requestPermission() {
        Toast.makeText(MainActivity.this, "Requesting permission", Toast.LENGTH_LONG).show();
        this.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                REQUEST_CODE_PHONE_STATE_READ);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_PHONE_STATE_READ:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkedPermission = PackageManager.PERMISSION_GRANTED;
                }
                break;

        }

    }
}