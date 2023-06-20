package com.example.printer_ex;

import android.Manifest;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class DeviceList extends ListActivity {
    private static String TAG = "---DeviceList";
    public static final int REQUEST_COARSE_LOCATION = 200;

    static public final int REQUEST_CONNECT_BT = 0 * 2300;
    static private final int REQUEST_ENABLE_BT = 0 * 1000;
    static private BluetoothAdapter mBluetoothAdapter = null;
    static private ArrayAdapter<String> mArrayAdapter = null;

    static private ArrayAdapter<BluetoothDevice> btDevices = null;

    private static final UUID SPP_UUID = UUID
            .fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    // UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    static private BluetoothSocket mbtSocket = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Bluetooth Devices");

        try {
            if (initDevicesList() != 0) {
                finish();
            }

        } catch (Exception ex) {
            finish();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_COARSE_LOCATION);
        } else {
            proceedDiscovery();
        }

        if (ContextCompat.checkSelfPermission(DeviceList.this, Manifest.permission.BLUETOOTH_CONNECT)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted
            // Proceed with your Bluetooth operation
            Toast.makeText(this, "hhh", Toast.LENGTH_SHORT).show();
        } else {
            // Permission is not granted
            // Request the permission
            ActivityCompat.requestPermissions(DeviceList.this,
                    new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                    requestCode11);
        }
    }

    int requestCode11 = 1;

    protected void proceedDiscovery() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_NAME_CHANGED);
        registerReceiver(mBTReceiver, filter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            mBluetoothAdapter.startDiscovery();

        }
        mBluetoothAdapter.startDiscovery();
    }

    public static BluetoothSocket getSocket() {
        return mbtSocket;
    }

    private void flushData() {
        try {
            if (mbtSocket != null) {
                mbtSocket.close();
                mbtSocket = null;
            }

            if (mBluetoothAdapter != null) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {

                    mBluetoothAdapter.cancelDiscovery();
                }
                mBluetoothAdapter.cancelDiscovery();
            }

            if (btDevices != null) {
                btDevices.clear();
                btDevices = null;
            }

            if (mArrayAdapter != null) {
                mArrayAdapter.clear();
                mArrayAdapter.notifyDataSetChanged();
                mArrayAdapter.notifyDataSetInvalidated();
                mArrayAdapter = null;
            }

            //finalize();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }

    }

    private int initDevicesList() {
        flushData();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),
                    "Bluetooth not supported!!", Toast.LENGTH_LONG).show();
            return -1;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {

            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }

            mArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.layout_list);

            setListAdapter(mArrayAdapter);

            Intent enableBtIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            try {
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } catch (Exception ex) {

                return -2;
            }

            Toast.makeText(getApplicationContext(),
                            "Getting all available Bluetooth Devices", Toast.LENGTH_SHORT)
                    .show();

            return 0;

        }
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        mArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.layout_list);

        setListAdapter(mArrayAdapter);

        Intent enableBtIntent = new Intent(
                BluetoothAdapter.ACTION_REQUEST_ENABLE);
        try {
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } catch (Exception ex) {

            return -2;
        }

        Toast.makeText(getApplicationContext(),
                        "Getting all available Bluetooth Devices", Toast.LENGTH_SHORT)
                .show();

        return 0;

    }

    private void handleGalleryIntent(Intent data) {
        // Get the URI of the selected image
        Uri selectedImage = data.getData();
        // Get a Bitmap object from the selected image
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Create a new Drawable object from the Bitmap
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        // Get the resources object
        Resources resources = getResources();
        // Get the name of the new drawable resource
        String resourceName = "new_image";
        // Get the identifier of the drawable resource
        int resourceId = resources.getIdentifier(resourceName, "drawable", getPackageName());
        // Save the drawable to a file in the drawable directory
        try {
            FileOutputStream outputStream = openFileOutput(resourceName + ".png", Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Add the new drawable resource to the drawable directory
        File newDrawableFile = new File(getFilesDir(), resourceName + ".png");
        Uri newDrawableUri = Uri.fromFile(newDrawableFile);
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(newDrawableUri);
        sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent intent) {
        super.onActivityResult(reqCode, resultCode, intent);

        switch (reqCode) {
            case REQUEST_ENABLE_BT:

                if (resultCode == RESULT_OK) {
                    if (ActivityCompat.checkSelfPermission(DeviceList.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                        Set<BluetoothDevice> btDeviceList = mBluetoothAdapter
                                .getBondedDevices();
                        try {
                            if (btDeviceList.size() > 0) {

                                for (BluetoothDevice device : btDeviceList) {
                                    if (btDeviceList.contains(device) == false) {

                                        btDevices.add(device);

                                        mArrayAdapter.add(device.getName() + "\n"
                                                + device.getAddress());
                                        mArrayAdapter.notifyDataSetInvalidated();
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            Log.e(TAG, ex.getMessage());
                        }
                    }
                    Set<BluetoothDevice> btDeviceList = mBluetoothAdapter
                            .getBondedDevices();
                    try {
                        if (btDeviceList.size() > 0) {

                            for (BluetoothDevice device : btDeviceList) {
                                if (btDeviceList.contains(device) == false) {

                                    btDevices.add(device);

                                    mArrayAdapter.add(device.getName() + "\n"
                                            + device.getAddress());
                                    mArrayAdapter.notifyDataSetInvalidated();
                                }
                            }
                        }
                    } catch (Exception ex) {
                        Log.e(TAG, ex.getMessage());
                    }
                }

                break;
        }
        mBluetoothAdapter.startDiscovery();

    }

    private final BroadcastReceiver mBTReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                try {
                    if (btDevices == null) {
                        btDevices = new ArrayAdapter<BluetoothDevice>(
                                getApplicationContext(), R.layout.layout_list);
                    }

                    if (btDevices.getPosition(device) < 0) {
                        btDevices.add(device);
                        if (ActivityCompat.checkSelfPermission(DeviceList.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                            mArrayAdapter.add(device.getName() + "\n"
                                    + device.getAddress() + "\n");
                            mArrayAdapter.notifyDataSetInvalidated();
                        }
                        mArrayAdapter.add(device.getName() + "\n"
                                + device.getAddress() + "\n");
                        mArrayAdapter.notifyDataSetInvalidated();
                    }
                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }
            }
        }
    };

    @Override
    protected void onListItemClick(ListView l, View v, final int position,
                                   long id) {
        super.onListItemClick(l, v, position, id);
        if (mBluetoothAdapter == null) {
            return;
        }

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                boolean gotuuid = btDevices.getItem(position)
                        .fetchUuidsWithSdp();
                UUID uuid = btDevices.getItem(position).getUuids()[0]
                        .getUuid();
                Toast.makeText(this, ""+uuid, Toast.LENGTH_SHORT).show();
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                BluetoothDevice device = bluetoothAdapter.getRemoteDevice(btDevices.getItem(position).getAddress());
                device.createBond();
                Toast.makeText(this, ""+device, Toast.LENGTH_SHORT).show();
                BluetoothSocket socket = null;

                try {
                    socket = device.createRfcommSocketToServiceRecord(uuid);
                    socket.connect();
                    Log.d("get","get");
                    // Connection successful
                } catch (IOException e) {
                    // Handle connection error
                } finally {
                    try {
                        if (socket != null) {
                            socket.close();
                        }
                    } catch (IOException e) {
                        // Handle socket close error
                    }
                }
            }
            else {
                boolean gotuuid = btDevices.getItem(position)
                        .fetchUuidsWithSdp();
                UUID uuid = btDevices.getItem(position).getUuids()[0]
                        .getUuid();
                Toast.makeText(this, ""+uuid, Toast.LENGTH_SHORT).show();
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                BluetoothDevice device = bluetoothAdapter.getRemoteDevice(btDevices.getItem(position).getAddress());
                device.createBond();
                Toast.makeText(this, ""+device, Toast.LENGTH_SHORT).show();
                BluetoothSocket socket = null;

                try {
                    socket = device.createRfcommSocketToServiceRecord(uuid);
                    socket.connect();
                    Log.d("get","get");
                    // Connection successful
                } catch (IOException e) {
                    // Handle connection error
                } finally {
                    try {
                        if (socket != null) {
                            socket.close();
                        }
                    } catch (IOException e) {
                        // Handle socket close error
                    }
                }
            }

        }catch (Exception w)
        {
            Toast.makeText(this, ""+w.getMessage(), Toast.LENGTH_SHORT).show();
        }
       /*
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                        getApplicationContext(),
                        "Connecting to " + btDevices.getItem(position).getName() + ","
                                + btDevices.getItem(position).getAddress(),
                        Toast.LENGTH_SHORT).show();
                if (mBluetoothAdapter == null) {
                    return;
                }

                if (ActivityCompat.checkSelfPermission(DeviceList.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {

                    if (mBluetoothAdapter.isDiscovering()) {
                        mBluetoothAdapter.cancelDiscovery();
                    }

                    Toast.makeText(
                            getApplicationContext(),
                            "Connecting to " + btDevices.getItem(position).getName() + ","
                                    + btDevices.getItem(position).getAddress(),
                            Toast.LENGTH_SHORT).show();



                }
                if (mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.cancelDiscovery();
                }

                Toast.makeText(
                        getApplicationContext(),
                        "Connecting to " + btDevices.getItem(position).getName() + ","
                                + btDevices.getItem(position).getAddress(),
                        Toast.LENGTH_SHORT).show();

                Thread connectThread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            if (ActivityCompat.checkSelfPermission(DeviceList.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                                boolean gotuuid =true;
                                UUID uuid = btDevices.getItem(position).getUuids()[0]
                                        .getUuid();
                                mbtSocket = btDevices.getItem(position)
                                        .createRfcommSocketToServiceRecord(uuid);

                                mbtSocket.connect();
                            }
                            boolean gotuuid = btDevices.getItem(position)
                                    .fetchUuidsWithSdp();
                            UUID uuid = btDevices.getItem(position).getUuids()[0]
                                    .getUuid();
                            mbtSocket = btDevices.getItem(position)
                                    .createRfcommSocketToServiceRecord(uuid);

                            mbtSocket.connect();
                        } catch (IOException ex) {
                            runOnUiThread(socketErrorRunnable);
                            try {
                                mbtSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            mbtSocket = null;
                        } finally {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    finish();

                                }
                            });
                        }
                    }
                });

                connectThread.start();

               // Toast.makeText(this, "hjjjj", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(
                        getApplicationContext(),
                        "Connecting to " + btDevices.getItem(position).getName() + ","
                                + btDevices.getItem(position).getAddress(),
                        Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        */
      /*
        if (mBluetoothAdapter == null) {
            return;
        }

        if (ActivityCompat.checkSelfPermission(DeviceList.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {

            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }

            Toast.makeText(
                    getApplicationContext(),
                    "Connecting to " + btDevices.getItem(position).getName() + ","
                            + btDevices.getItem(position).getAddress(),
                    Toast.LENGTH_SHORT).show();



        }
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        Toast.makeText(
                getApplicationContext(),
                "Connecting to " + btDevices.getItem(position).getName() + ","
                        + btDevices.getItem(position).getAddress(),
                Toast.LENGTH_SHORT).show();

        Thread connectThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    if (ActivityCompat.checkSelfPermission(DeviceList.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                        boolean gotuuid = btDevices.getItem(position)
                                .fetchUuidsWithSdp();
                        UUID uuid = btDevices.getItem(position).getUuids()[0]
                                .getUuid();
                        mbtSocket = btDevices.getItem(position)
                                .createRfcommSocketToServiceRecord(uuid);

                        mbtSocket.connect();
                    }
                    boolean gotuuid = btDevices.getItem(position)
                            .fetchUuidsWithSdp();
                    UUID uuid = btDevices.getItem(position).getUuids()[0]
                            .getUuid();
                    mbtSocket = btDevices.getItem(position)
                            .createRfcommSocketToServiceRecord(uuid);

                    mbtSocket.connect();
                } catch (IOException ex) {
                    runOnUiThread(socketErrorRunnable);
                    try {
                        mbtSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mbtSocket = null;
                } finally {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            finish();

                        }
                    });
                }
            }
        });

        connectThread.start();
       */
    }

    private Runnable socketErrorRunnable = new Runnable() {

        @Override
        public void run() {
            Toast.makeText(getApplicationContext(),
                    "Cannot establish connection", Toast.LENGTH_SHORT).show();
            if (ActivityCompat.checkSelfPermission(DeviceList.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {

                mBluetoothAdapter.startDiscovery();
            }
            mBluetoothAdapter.startDiscovery();

        }
    };
    int flaggg=2;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_COARSE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    proceedDiscovery();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Permission is not granted!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            }

        }
        if (requestCode11 == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                // Proceed with your Bluetooth operation
                flaggg=3;
               // Toast.makeText(this, "0", Toast.LENGTH_SHORT).show();

            } else {
                // Permission denied
                // Handle the case when the user denies the permission
               // Toast.makeText(this, "11", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, Menu.FIRST, Menu.NONE, "Refresh Scanning");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case Menu.FIRST:
                initDevicesList();
                break;
        }

        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            unregisterReceiver(mBTReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}