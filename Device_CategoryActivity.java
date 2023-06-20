package com.example.printer_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Device_CategoryActivity extends AppCompatActivity {
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;
    TextView sign_in_title;
    Button button_bluetooth, button_wifi, button_cloud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_category);
        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        sign_in_title = findViewById(R.id.sign_in_title);
        button_bluetooth = findViewById(R.id.button_bluetooth);
        button_wifi = findViewById(R.id.button_wifi);
        button_cloud = findViewById(R.id.button_cloud);

        String defaultlanguage = Locale.getDefault().getDisplayLanguage();
        if (defaultlanguage.toLowerCase().toString().equals("english")) {
            toolbar.setTitle(R.string.device_title);
            sign_in_title.setText(R.string.catedes);
            button_bluetooth.setText(R.string.bluetooth);
            button_wifi.setText(R.string.wifi);
            button_cloud.setText(R.string.cloud_print);
        } else {
            toolbar.setTitle(R.string.device_title_chai);
            sign_in_title.setText(R.string.catedes_chai);
            button_bluetooth.setText(R.string.blue_chai);
            button_wifi.setText(R.string.wifi_chai);
            button_cloud.setText(R.string.cloud_printing_chai);
        }

        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar();

        try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        } catch (Exception e) {
            e.printStackTrace();
        }
        //////textview
    }

    public void bluetooth(View view) {
        try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (!bluetoothAdapter.isEnabled()) {
                String defaultlanguage = Locale.getDefault().getDisplayLanguage();
                if (defaultlanguage.toLowerCase().toString().equals("english")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Device_CategoryActivity.this);
                    builder.setTitle("Bluetooth")
                            .setMessage("Your bluetooth is disable now.\nDo you want to enable it?")
                            .setPositiveButton("NOT NOW", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            }).setNegativeButton("ENABLE NOW", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    if (ActivityCompat.checkSelfPermission(Device_CategoryActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.
                                        return;
                                    }
                                    bluetoothAdapter.enable();
                                    bluetoothAdapter.enable();


                                }
                            }).create();
                    builder.show();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Device_CategoryActivity.this);
                    builder.setTitle(R.string.blue_chai)
                            .setMessage("您的蓝牙现在已禁用。你想启用它吗？")
                            .setPositiveButton("现在不要", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            }).setNegativeButton("立即启用", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    if (ActivityCompat.checkSelfPermission(Device_CategoryActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.
                                        return;
                                    }
                                    bluetoothAdapter.enable();
                                    bluetoothAdapter.enable();


                                }
                            }).create();
                    builder.show();
                }

            }
            else {
             startActivity(new Intent(getApplicationContext(),BluetoothImagePrinting.class));
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void wifi(View view) {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifiInfo.isConnected()) {
            // WiFi is connected
            // startActivity(new Intent(getApplicationContext(), WifiListActivity.class));
        } else {
            // WiFi is not connected
            String  defaultlanguage= Locale.getDefault().getDisplayLanguage();
            if (defaultlanguage.toLowerCase().toString().equals("english")) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Device_CategoryActivity.this);
                builder.setTitle("Wifi")
                        .setMessage("Your wifi is disable now.\nDo you want to enable it?")
                        .setPositiveButton("NOT NOW", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).setNegativeButton("See Wifi List", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                                if (wifiInfo.isConnected()) {
                                    // WiFi is connected
                                    // startActivity(new Intent(getApplicationContext(), WifiListActivity.class));
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Your wifi is disable now. Do you want to enable it?",Toast.LENGTH_SHORT).show();
                                    return;
                                }




                            }
                        }).create();
                builder.show();
            }
            else {
                AlertDialog.Builder builder=new AlertDialog.Builder(Device_CategoryActivity.this);
                builder.setTitle("无线上网")
                        .setMessage("您的 wifi 现在已禁用。您要启用它吗？")
                        .setPositiveButton("现在不要", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).setNegativeButton("查看 Wifi 列表", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                                if (wifiInfo.isConnected()) {
                                    // WiFi is connected
                                    ///   startActivity(new Intent(getApplicationContext(), WifiListActivity.class));
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"您的 wifi 现在已禁用。你想启用它吗？",Toast.LENGTH_SHORT).show();
                                    return;
                                }




                            }
                        }).create();
                builder.show();
            }

        }

/*

        ConnectivityManager cm = (ConnectivityManager) Device_CategoryActivity.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnectedOrConnecting()) {
            WifiManager wifiManager = (WifiManager) Device_CategoryActivity.this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(true);
            wifiManager.setWifiEnabled(true);
            startActivity(new Intent(getApplicationContext(), WifiListActivity.class));
        }
        else {
            AlertDialog.Builder builder=new AlertDialog.Builder(Device_CategoryActivity.this);
            builder.setTitle("Wifi")
                    .setMessage("Your wifi is disable now.\nDo you want to enable it?")
                    .setPositiveButton("NOT NOW", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    }).setNegativeButton("ENABLE NOW", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    WifiManager wifiManager = (WifiManager) Device_CategoryActivity.this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    wifiManager.setWifiEnabled(true);
                    wifiManager.setWifiEnabled(true);
                    startActivity(new Intent(getApplicationContext(), WifiListActivity.class));


                }
            }).create();
            builder.show();
        }
 */
    }

    public void cloud(View view) {
     //   startActivity(new Intent(getApplicationContext(), BitMapConvertActivity.class));
    }
}