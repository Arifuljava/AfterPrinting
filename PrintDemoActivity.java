package com.example.printer_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zj.btsdk.BluetoothService;
import com.zj.btsdk.PrintPic;

public class PrintDemoActivity extends AppCompatActivity {
    static final String TAG = "PrintDemoActivity";
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    Button btnSearch;
    Button btnSendDraw;
    Button btnSend;
    Button btnClose;
    EditText edtContext;
    BluetoothService mService = null;
    BluetoothDevice con_dev = null;
    Context context;

    String msg = "";
    String DIVIDER = "--------------------------------";
    String DIVIDER_DOUBLE = "================================";
    String BREAK = "\n";
    String SPACE5 = "     ";
    String SPACE4 = "   ";
    SharedPreferences sharedPreferences;
    String header = "";

    private TextView textView;


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            Toast.makeText(context, "Connect successful",
                                    Toast.LENGTH_SHORT).show();
                            btnClose.setEnabled(true);
                            btnSend.setEnabled(true);
                            btnSendDraw.setEnabled(true);
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            Log.d(TAG, "Bluetooth is connecting");
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            Log.d(TAG, "Bluetooth state listen or none");
                            break;
                    }
                    break;
                case BluetoothService.MESSAGE_CONNECTION_LOST:
                    Toast.makeText(context, "Device connection was lost",
                            Toast.LENGTH_SHORT).show();
                    btnClose.setEnabled(false);
                    btnSend.setEnabled(false);
                    btnSendDraw.setEnabled(false);
                    break;
                case BluetoothService.MESSAGE_UNABLE_CONNECT:
                    Toast.makeText(context, "Unable to connect device",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_demo);
        context = this;
        mService = new BluetoothService(this, mHandler);

        if (!mService.isAvailable()) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!mService.isBTopen()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            }
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        btnSendDraw = (Button) this.findViewById(R.id.btn_test);
        btnSendDraw.setOnClickListener(new ClickEvent());
        btnSearch = (Button) this.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new ClickEvent());
        btnSend = (Button) this.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new ClickEvent());
        btnClose = (Button) this.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new ClickEvent());
        edtContext = (EditText) findViewById(R.id.txt_content);
        textView = findViewById(R.id.text_view);
        btnClose.setEnabled(false);
        btnSend.setEnabled(false);
        btnSendDraw.setEnabled(false);

        Intent intent = getIntent();
        try {
            String text=getIntent().getStringExtra("text");
            Toast.makeText(PrintDemoActivity.this, ""+text, Toast.LENGTH_SHORT).show();
            edtContext.setText(""+text);
        }catch (Exception e) {
            Toast.makeText(PrintDemoActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mService != null)
            mService.stop();
        mService = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Bluetooth open successful", Toast.LENGTH_LONG).show();
                } else {
                    finish();
                }
                break;
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    con_dev = mService.getDevByMac(address);

                    mService.connect(con_dev);
                }
                break;
        }
    }



    @SuppressLint("SdCardPath")
    private void printImage() {
        byte[] sendData = null;
        PrintPic pg = new PrintPic();
        pg.initCanvas(800);
        pg.initPaint();
        pg.drawImage(0, 0, "/mnt/sdcard/print_test.png");
        sendData = pg.printDraw();
        mService.write(sendData);
    }

    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {
            if (v == btnSearch) {
                Intent serverIntent = new Intent(context, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
            } else if (v == btnSend) {
                header += ""+edtContext.getText().toString() + BREAK;
                header += "Default Page" + BREAK;
                msg += DIVIDER_DOUBLE + BREAK;
                msg += "System Time :  " + System.currentTimeMillis() + BREAK;
                msg += "Date: " + "23/03/2017 11.30" + BREAK;
                msg += "Cashier: "+edtContext.getText().toString() + BREAK;
                msg += BREAK;


                msg += DIVIDER + BREAK + BREAK;

                byte[] cmd = new byte[3];
                cmd[0] = 0x1b;
                cmd[1] = 0x21;
                cmd[2] |= 0x10;
                mService.write(cmd);
                mService.sendMessage(header, "GBK");
                cmd[2] &= 0xEF;
                mService.write(cmd);
                mService.sendMessage(msg, "GBK");

            } else if (v == btnClose) {
                mService.stop();
            } else if (v == btnSendDraw) {
//                String msg;
                printImage();

//                byte[] cmd = new byte[3];
//                cmd[0] = 0x1b;
//                cmd[1] = 0x21;
//                cmd[2] |= 0x10;
//                mService.write(cmd);
//                mService.sendMessage("Congratulations!\n", "GBK");
//                cmd[2] &= 0xEF;
//                mService.write(cmd);
//                msg = "  You have sucessfully created communications between your device and our bluetooth printer.\n\n"
//                        + "  the company is a high-tech enterprise which specializes" +
//                        " in R&D,manufacturing,marketing of thermal printers and barcode scanners.\n\n";
//
//
//                mService.sendMessage(msg, "GBK");
            }
        }
    }
}