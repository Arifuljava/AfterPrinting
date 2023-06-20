package com.example.printer_ex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class SecondActivityCPCL extends AppCompatActivity {
ImageView imageview;
Button btnScan;

    Uri imageuri;
    int flag = 0;
    BluetoothSocket m5ocket;
    BluetoothManager mBluetoothManager;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice device;
    ImageView imageposit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_cpcl);
        imageview=(ImageView) findViewById(R.id.imageview);
        btnScan=findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String BlueMac = "FB:7F:9B:F2:20:B7";
                mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
                mBluetoothAdapter = mBluetoothManager.getAdapter();
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(BlueMac);
                ///Toasty.info(getApplicationContext(),"Please active bluetooth"+mBluetoothAdapter.isEnabled(),Toasty.LENGTH_SHORT,true).show();
                if (!mBluetoothAdapter.isEnabled()) {
                    Toast.makeText(SecondActivityCPCL.this, "Please active bluetooth", Toast.LENGTH_SHORT).show();
                    android.app.AlertDialog.Builder mybuilder = new android.app.AlertDialog.Builder(SecondActivityCPCL.this);
                    mybuilder.setTitle("Confirmation")
                            .setMessage("Do you want to active bluetooth");
                    mybuilder.setPositiveButton("Not Now", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setNegativeButton("Right Now", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (ActivityCompat.checkSelfPermission(SecondActivityCPCL.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                                mBluetoothAdapter.enable();
                            }
                            mBluetoothAdapter.enable();
                            Toast.makeText(SecondActivityCPCL.this, "Bluetooth is active now.", Toast.LENGTH_SHORT).show();
                        }
                    }).create().show();

                    return;
                } else {
                    Toast.makeText(SecondActivityCPCL.this, "ggg", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }
    int bitmapHeight = 1080;
    OutputStream os = null;

    private void printImage() {
        Toast.makeText(this, "hhh", Toast.LENGTH_SHORT).show();
        final Bitmap bitmap =  BitmapFactory.decodeResource(getResources(),R.drawable.testing);;
        final byte[] bitmapGetByte = BitmapToRGBbyte(bitmap);
        String BlueMac = "FB:7F:9B:F2:20:B7";
        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(BlueMac);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /// Toast.makeText(AssenTaskDounwActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    if (ActivityCompat.checkSelfPermission(SecondActivityCPCL.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                        m5ocket.connect();
                        Log.e("Ariful_1","Connected");

                        os = m5ocket.getOutputStream();


                        if(bitmap.getHeight()>bitmapHeight)
                        {
                            bitmapHeight=1080;
                        }
                        else
                        {
                            bitmapHeight=bitmap.getHeight();
                        }
                        Random random=new Random();
                        int sendingnumber=random.nextInt(10);
                        int mimisecond=sendingnumber*1000;

                        try {
                            Log.e("Ariful_5",""+bitmap.getWidth());
                            Log.e("Ariful_6",""+bitmap.getHeight());
                            String t_line1 = "! 0 200 200 "+bitmapHeight+" 1 \r\n";//bitmap.getHeight()
                            String t_line2 = "pw "+384+"\r\n";
                            String t_line3 = "DENSITY 12\r\n";
                            String t_line4 = "SPEED 3\r\n";
                            String t_line5 = "CG "+384/8+" "+bitmapHeight+" 0 0 ";
                            String t_line6 ="PR 0\r\n";
                            String t_line7= "FORM\r\n";
                            String t_line8 = "PRINT\r\n";
                            String t_line9 = "\r\n";
                            os.write(t_line1.getBytes());
                            os.write(t_line2.getBytes());
                            os.write(t_line3.getBytes());
                            os .write(t_line4.getBytes());
                            os .write(t_line5.getBytes());
                            os .write(t_line4.getBytes());
                            os.write(bitmapGetByte);
                            os .write(t_line9.getBytes());
                            os .write(t_line6.getBytes());
                            os.write(t_line7.getBytes());
                            os.write(t_line8.getBytes());
                            Log.e("Ariful_2","Connected"+bitmapGetByte);
                            Log.e("Ariful_7",""+m5ocket.isConnected());
                            try {
                                os.flush();
                                os.flush();
                                m5ocket.close();
                                Log.e("Ariful_3","uuuu");
                            }catch (Exception e) {
                                e.printStackTrace();
                                Log.e("Ariful_4",""+e.getMessage());
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    else{
                        m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                        m5ocket.connect();

                        os = m5ocket.getOutputStream();
                        Log.e("Dolon1","Connected");
                        if(bitmap.getHeight()>bitmapHeight)
                        {
                            bitmapHeight=1080;
                        }
                        else
                        {
                            bitmapHeight=bitmap.getHeight();
                        }
                        Random random=new Random();
                        int sendingnumber=random.nextInt(10);
                        int mimisecond=sendingnumber*1000;


                        try {
                            String t_line1 = "! 0 200 200 "+bitmapHeight+" 1 \r\n";//bitmap.getHeight()
                            String t_line2 = "pw "+384+"\r\n";
                            String t_line3 = "DENSITY 12\r\n";
                            String t_line4 = "SPEED 3\r\n";
                            String t_line5 = "CG "+384/8+" "+bitmapHeight+" 0 0 ";
                            String t_line6 ="PR 0\r\n";
                            String t_line7= "FORM\r\n";
                            String t_line8 = "PRINT\r\n";
                            String t_line9 = "\r\n";
                            os.write(t_line1.getBytes());
                            os.write(t_line2.getBytes());
                            os.write(t_line3.getBytes());
                            os .write(t_line4.getBytes());
                            os .write(t_line5.getBytes());
                            os .write(t_line4.getBytes());
                            os.write(bitmapGetByte);
                            os .write(t_line9.getBytes());
                            os .write(t_line6.getBytes());
                            os.write(t_line7.getBytes());
                            os.write(t_line8.getBytes());
                            Log.e("dOLO2","Connected");
                            try {
                                os.flush();
                                os.flush();
                                m5ocket.close();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                    }




                } catch (IOException e) {
                    Log.e("Error", ""+e.getMessage());

                }
            }
        });
        thread.start();
    }
    CountDownTimer countDownTimer, countDownTimer1;

    private byte[] BitmapToRGBbyte(Bitmap bitmapOrg) {
        ArrayList<Byte> Gray_ArrayList;
        Gray_ArrayList = new ArrayList<Byte>();
        int height = 1080;
        if (bitmapOrg.getHeight() > height) {
            height = 1080;
        } else {
            height = bitmapOrg.getHeight();
        }
        int width = 384;
        int R = 0, B = 0, G = 0;
        int pixles;
        int x = 0, y = 0;
        Byte[] Gray_Send;
        //bitSet = new BitSet();
        try {
            int k = 0;
            int Send_i = 0;
            int x_GetR;
            for (x = 0; x < height; x++) {
                k = 0;
                for (y = 0; y < width; y++) {
                    pixles = bitmapOrg.getPixel(x, y);
                    R = Color.red(pixles);
                    G = Color.green(pixles);
                    B = Color.blue(pixles);
                    //setcolor
                    R = G = B = (int) ((0.299 * R) + (0.587 * G) + (0.114 * B));

                    if (R < 120) {
                        //bitSet.set(k);
                        x_GetR = 0;
                    } else {
                        x_GetR = 1;

                    }
                    ///texting cde
                    k++;
                    if (k == 1) {
                        Send_i = 0;
                        Send_i = Send_i + x_GetR | 0x80;

                    } else if (k == 2) {

                        Send_i = Send_i + x_GetR | 0x40;

                    } else if (k == 3) {

                        Send_i = Send_i + x_GetR | 0x20;

                    } else if (k == 4) {

                        Send_i = Send_i + x_GetR | 0x10;

                    } else if (k == 5) {

                        Send_i = Send_i + x_GetR | 0x08;

                    } else if (k == 6) {

                        Send_i = Send_i + x_GetR | 0x04;

                    } else if (k == 7) {

                        Send_i = Send_i + x_GetR | 0x02;

                    } else if (k == 8) {

                        Send_i = Send_i + x_GetR | 0x01;

                        Gray_ArrayList.add((byte) Send_i);
                        Send_i = 0;
                        k = 0;
                        // byte b = (byte) Send_i; // replace with your byte value

                        //i control it


                    }


                    /////////////////////=====================================


                }
            }

            byte[] sss = new byte[Gray_ArrayList.size()];
            Gray_Send = new Byte[Gray_ArrayList.size()];
            Gray_ArrayList.toArray(Gray_Send);
            for (int xx = 0; xx < Gray_Send.length; xx++) {
                sss[xx] = Gray_Send[xx];
            }
            return sss;
        } catch (Exception e) {

        }
        return null;
    }
}