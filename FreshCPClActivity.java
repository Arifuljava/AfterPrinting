package com.example.printer_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import static android.content.ContentValues.TAG;

import es.dmoral.toasty.Toasty;

public class FreshCPClActivity extends AppCompatActivity {
    Uri imageuri;
    int flag = 0;
    BluetoothSocket m5ocket;
    BluetoothManager mBluetoothManager;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice device;
    ImageView imageposit;

    Button printimageA;
    Bitmap bitmapdataMe;
    ImageView bitmapcalling;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresh_cpcl);
        ///imageprinting
        imageposit = findViewById(R.id.imageposit);
        printimageA = findViewById(R.id.printimageA);
        bitmapcalling=findViewById(R.id.bitmapcalling);
        byte[] bitmapData = getIntent().getByteArrayExtra("bitmapData");
        if (bitmapData != null) {
            bitmapdataMe = BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);
            // Use the bitmap as needed
          bitmapcalling.setImageBitmap(bitmapdataMe);

            flag = 2;
        }

        /*byte[] imageData = getIntent().getByteArrayExtra("imageData");
        // Process the image data here
        if (imageData != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            // Do further processing with the bitmap
            bitmapcalling.setImageBitmap(bitmap);
        }*/


        printimageA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String BlueMac = "FB:7F:9B:F2:20:B7";
                mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
                mBluetoothAdapter = mBluetoothManager.getAdapter();
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(BlueMac);
                ///Toasty.info(getApplicationContext(),"Please active bluetooth"+mBluetoothAdapter.isEnabled(),Toasty.LENGTH_SHORT,true).show();
                if (!mBluetoothAdapter.isEnabled()) {
                    Toasty.info(getApplicationContext(), "Please active bluetooth", Toasty.LENGTH_SHORT, true).show();
                    android.app.AlertDialog.Builder mybuilder = new android.app.AlertDialog.Builder(FreshCPClActivity.this);
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
                            if (ActivityCompat.checkSelfPermission(FreshCPClActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                                mBluetoothAdapter.enable();
                                Toasty.info(getApplicationContext(), "Bluetooth is active now.", Toasty.LENGTH_SHORT, true).show();
                            } else {
                                mBluetoothAdapter.enable();
                                Toasty.info(getApplicationContext(), "Bluetooth is active now.", Toasty.LENGTH_SHORT, true).show();
                            }

                        }
                    }).create().show();

                    return;
                } else {
                    printImage1();
                }


            }
        });

    }

    /*========================================================================================================
        ==================================PRINT SECTION===========================================================
         */
    int bitmapHeight = 1080;
    OutputStream os = null;

    private void printImage() {
    final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.testing);
   final int gettingW=384;
   final  int gettingsHeight=384;
   Bitmap resizeBitmap=Bitmap.createScaledBitmap(bitmap,gettingW,gettingsHeight,true);

    //final Bitmap bitmap = bitmapdataMe;
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
                    if (ActivityCompat.checkSelfPermission(FreshCPClActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                        m5ocket.connect();

                        os = m5ocket.getOutputStream();

                        if(resizeBitmap.getHeight()>bitmapHeight)
                        {
                            bitmapHeight=1080;
                        }
                        else
                        {
                            bitmapHeight=bitmap.getHeight();
                        }
                        Log.e("Ariful1",""+bitmap.getWidth());
                        Log.e("Ariful2",""+bitmap.getHeight());
                        Random random=new Random();
                        int sendingnumber=random.nextInt(10);
                        int mimisecond=sendingnumber*1000;


                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                // write your code here
                                countDownTimer =new CountDownTimer(10000,1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                        double seconddd=millisUntilFinished/1000;
                                        printimageA.setText("Sending Data : "+seconddd+" S");



                                    }

                                    @Override
                                    public void onFinish() {
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
                                        }catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        countDownTimer1=new CountDownTimer(2000,1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                                long second=  (millisUntilFinished/1000);
                                                int mysecond=Integer.parseInt(String.valueOf(second));



                                            }

                                            @Override
                                            public void onFinish() {

                                                printimageA.setText("Print Image");
                                                try {
                                                    os.flush();
                                                    os.flush();
                                                    m5ocket.close();
                                                }catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();
                                                return;
                                            }
                                        }.start();
                                        countDownTimer1.start();


                                    }
                                };
                                countDownTimer.start();
                            }
                        });
                    }
                    else {
                        m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                        m5ocket.connect();

                        os = m5ocket.getOutputStream();

                        if(bitmap.getHeight()>bitmapHeight)
                        {
                            bitmapHeight=1080;
                        }
                        else
                        {
                            bitmapHeight=bitmap.getHeight();
                        }
                        Log.e("Ariful1",""+bitmap.getWidth());
                        Random random=new Random();
                        int sendingnumber=random.nextInt(10);
                        int mimisecond=sendingnumber*1000;


                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                // write your code here
                                countDownTimer =new CountDownTimer(10000,1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                        double seconddd=millisUntilFinished/1000;
                                        printimageA.setText("Sending Data : "+seconddd+" S");



                                    }

                                    @Override
                                    public void onFinish() {
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
                                        }catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        countDownTimer1=new CountDownTimer(2000,1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                                long second=  (millisUntilFinished/1000);
                                                int mysecond=Integer.parseInt(String.valueOf(second));



                                            }

                                            @Override
                                            public void onFinish() {

                                                printimageA.setText("Print Image");
                                                try {
                                                    os.flush();
                                                    os.flush();
                                                    m5ocket.close();
                                                }catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();
                                                return;
                                            }
                                        }.start();
                                        countDownTimer1.start();


                                    }
                                };
                                countDownTimer.start();
                            }
                        });
                    }




                } catch (IOException e) {
                    Log.e("Error", ""+e.getMessage());

                }
            }
        });
        thread.start();
    }
    CountDownTimer countDownTimer,countDownTimer1;
    private  byte[]  BitmapToRGBbyte(Bitmap bitmapOrg) {
        ArrayList<Byte> Gray_ArrayList;
        Gray_ArrayList =new ArrayList<Byte>();
        int height = 1080;
        if(bitmapOrg.getHeight()>height)
        {
            height=1080;
        }
        else
        {
            height=bitmapOrg.getHeight();
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
                k=0;
                for (y = 0; y < width; y++) {
                    pixles = bitmapOrg.getPixel(x, y);
                    R = Color.red(pixles);
                    G = Color.green(pixles);
                    B = Color.blue(pixles);
                    //setcolor
                    R = G = B = (int) ((0.299 * R) + (0.587 * G) + (0.114 * B));

                    if (R >128) {
                        //bitSet.set(k);
                        x_GetR = 0;
                    } else {
                        x_GetR = 1;

                    }
                    ///texting cde
                    k++;
                    if (k == 1) {
                        Send_i=0;
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

            byte[] sss=new byte[Gray_ArrayList.size()];
            Gray_Send=new Byte[Gray_ArrayList.size()];
            Gray_ArrayList.toArray(Gray_Send);
            for(int xx=0;xx<Gray_Send.length;xx++){
                sss[xx]=Gray_Send[xx];
            }
            return  sss;
        } catch (Exception e) {

        }
        return null;
    }

    private  byte[]  BitmapToRGBbyteA(Bitmap bitmapOrg) {
        ArrayList<Byte> Gray_ArrayList;
        Gray_ArrayList =new ArrayList<Byte>();
        int height = 1080;
        if(bitmapOrg.getHeight()>height)
        {
            height=1080;
        }
        else
        {
            height=bitmapOrg.getHeight();
        }
        int width = 384;
        int R = 0, B = 0, G = 0;
        //int pixles;
        int []pixels = new int[width * height];
        int x = 0, y = 0;
        Byte[] Gray_Send;
        //bitSet = new BitSet();
        try {

            bitmapOrg.getPixels(pixels, 0, width, 0, 0, width, height);
            int alpha = 0xFF << 24;
            //int []i_G=new int[7];
            int []i_G=new int[13];
            int Send_Gray=0x00;
            int StartInt=0;
            char  StartWords=' ';

            int k=0;
            int Send_i=0;
            int mathFlag=0;
            for(int i = 0; i < height; i++)
            {

                k=0;
                Send_i=0;
                for (int j = 0; j <width; j++)
                {
                    int grey = pixels[width * i + j];
                    int red = ((grey & 0x00FF0000) >> 16);
                    int green = ((grey & 0x0000FF00) >> 8);
                    int blue = (grey & 0x000000FF);
                    grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);


//==================================
                    if(grey>128)
                    {
                        //bufImage[j]=0x00;
                        mathFlag=0;

                    }
                    else
                    {
                        //bufImage[j]=0x01;
                        mathFlag=1;
                    }
                    k++;
                    if(k==1)
                    {
                        Send_i=0;
                        Send_i=Send_i+128*mathFlag;//mathFlag|0x80
                    }
                    else if(k==2)
                    {
                        Send_i=Send_i+64*mathFlag;//mathFlag|0x40
                    }
                    else if(k==3)
                    {
                        Send_i=Send_i+32*mathFlag;//mathFlag|0x20
                    }
                    else if(k==4)
                    {
                        Send_i=Send_i+16*mathFlag;//mathFlag|0x10
                    }
                    else if(k==5)
                    {
                        Send_i=Send_i+8*mathFlag;//mathFlag|0x08
                    }
                    else if(k==6)
                    {
                        Send_i=Send_i+4*mathFlag;//mathFlag|0x04
                    }
                    else if(k==7)
                    {
                        Send_i=Send_i+2*mathFlag;//mathFlag|0x02
                    }
                    else if(k==8)
                    {
                        Send_i=Send_i+1*mathFlag;//mathFlag|0x01
                        Gray_ArrayList.add((byte)Send_i);

                        Send_i=0;
                        k=0;
                    }
// =================================





                   // grey = alpha | (grey << 16) | (grey << 8) | grey;
                   // pixels[width * i + j] = grey;
                }
                int aBc=0;

            }






//            int k = 0;
//            int Send_i = 0;
//            int x_GetR;
//            for (x = 0; x < height; x++) {
//                k=0;
//                for (y = 0; y < width; y++) {
//                    pixles = bitmapOrg.getPixel(x, y);
//                    R = Color.red(pixles);
//                    G = Color.green(pixles);
//                    B = Color.blue(pixles);
//                    //setcolor
//                    R = G = B = (int) ((0.299 * R) + (0.587 * G) + (0.114 * B));
//
//                    if (R >128) {
//                        //bitSet.set(k);
//                        x_GetR = 0;
//                    } else {
//                        x_GetR = 1;
//
//                    }
//                    ///texting cde
//                    k++;
//                    if (k == 1) {
//                        Send_i=0;
//                        Send_i = Send_i + x_GetR | 0x80;
//
//                    } else if (k == 2) {
//
//                        Send_i = Send_i + x_GetR | 0x40;
//
//                    } else if (k == 3) {
//
//                        Send_i = Send_i + x_GetR | 0x20;
//
//                    } else if (k == 4) {
//
//                        Send_i = Send_i + x_GetR | 0x10;
//
//                    } else if (k == 5) {
//
//                        Send_i = Send_i + x_GetR | 0x08;
//
//                    } else if (k == 6) {
//
//                        Send_i = Send_i + x_GetR | 0x04;
//
//                    } else if (k == 7) {
//
//                        Send_i = Send_i + x_GetR | 0x02;
//
//                    } else if (k == 8) {
//
//                        Send_i = Send_i + x_GetR | 0x01;
//
//                        Gray_ArrayList.add((byte) Send_i);
//                        Send_i = 0;
//                        k = 0;
//                        // byte b = (byte) Send_i; // replace with your byte value
//
//                        //i control it
//
//
//                    }
//
//
//                    /////////////////////=====================================
//
//
//                }
//            }

            byte[] sss=new byte[Gray_ArrayList.size()];
            Gray_Send=new Byte[Gray_ArrayList.size()];
            Gray_ArrayList.toArray(Gray_Send);
            for(int xx=0;xx<Gray_Send.length;xx++){
                sss[xx]=Gray_Send[xx];
            }
            return  sss;
        } catch (Exception e) {

        }
        return null;
    }
    public void pickimage(View view) {




         Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 101);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();


// Assume you have an ImageView with the id "imageView" in your layout XML file

// Get the reference to your ImageView


// Get the Drawable from the ImageView
        /*
        Drawable drawable = imageView.getDrawable();

// Convert the Drawable to a Bitmap
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            // If the Drawable is not a BitmapDrawable, create a new Bitmap and draw the Drawable onto it
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }

// Save the Bitmap to a file
        File file = new File(getExternalFilesDir(null), "image.jpg"); // Change the file name and path as needed
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            // File saved successfully
        } catch (IOException e) {
            e.printStackTrace();
            // Error occurred while saving the file
        }
         */


    }
    Uri bitmapUri;
    Bitmap mainimageBitmap;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK) {
            try {
                File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                bitmapUri = data.getData();



                try {
                    Picasso.get().load(bitmapUri).into(imageposit);
                }catch (Exception e) {
                    Picasso.get().load(bitmapUri).into(imageposit);
                }
            }catch (Exception e2) {
                e2.printStackTrace();
            }

        }

    }
    int PICK=12;
    boolean request=false;

    private void printImage1() {
        final Bitmap bitmap = bitmapdataMe;
        float scax=384f /bitmap.getWidth();
        float scaly= 384f / bitmap.getHeight();
        Log.e("dolon",""+bitmap);
        Log.e("zzz",""+bitmap.getWidth());
                Log.e("zzz",""+bitmap.getHeight());
        Matrix matrix=new Matrix();
        matrix.postScale(scax,scaly);
        Bitmap resize= Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
       // Bitmap aftercolor= Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
       // Canvas canvas=new Canvas(aftercolor);
       // canvas.drawColor(Color.WHITE);

        Log.e("Ariful9",""+scax);
        //final Bitmap bitmap = bitmapdataMe;

        final byte[] bitmapGetByte = BitmapToRGBbyteA(resize);//convertBitmapToRGBBytes (resize);
        Log.e("Ariful4",""+bitmapGetByte);
        String BlueMac = "FB:7F:9B:F2:20:B7";
        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(BlueMac);
        ///

        ////
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /// Toast.makeText(AssenTaskDounwActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    if (ActivityCompat.checkSelfPermission(FreshCPClActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                        m5ocket.connect();

                        os = m5ocket.getOutputStream();

                        if(resize.getHeight()>bitmapHeight)
                        {
                            bitmapHeight=1080;
                        }
                        else
                        {
                            bitmapHeight=resize.getHeight();
                        }
                        Log.e("Ariful1",""+resize.getWidth());
                        Log.e("Ariful2",""+resize.getHeight());
                        Log.e("Ariful3",""+bitmap);
                        Random random=new Random();
                        int sendingnumber=random.nextInt(10);
                        int mimisecond=sendingnumber*1000;


                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                // write your code here
                                countDownTimer =new CountDownTimer(10000,1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                        double seconddd=millisUntilFinished/1000;
                                        printimageA.setText("Sending Data : "+seconddd+" S");



                                    }

                                    @Override
                                    public void onFinish() {
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

                                            os.write(bitmapGetByte);
                                            os .write(t_line9.getBytes());
                                            os .write(t_line6.getBytes());
                                            os.write(t_line7.getBytes());
                                            os.write(t_line8.getBytes());
                                            Log.e("Ariful5","PrintCommand");
                                        }catch (Exception e) {
                                            e.printStackTrace();
                                            Log.e("Ariful6",""+e.getMessage());
                                        }
                                        countDownTimer1=new CountDownTimer(2000,1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                                long second=  (millisUntilFinished/1000);
                                                int mysecond=Integer.parseInt(String.valueOf(second));



                                            }

                                            @Override
                                            public void onFinish() {

                                                printimageA.setText("Print Image");
                                                try {
                                                    os.flush();
                                                    os.flush();
                                                    m5ocket.close();
                                                    Log.e("Ariful7","Go to print");
                                                }catch (Exception e) {
                                                    e.printStackTrace();
                                                    Log.e("Ariful8",""+e.getMessage());
                                                }

                                                Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();
                                                return;
                                            }
                                        }.start();
                                        countDownTimer1.start();


                                    }
                                };
                                countDownTimer.start();
                            }
                        });
                    }
                    else {
                        m5ocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                        m5ocket.connect();

                        os = m5ocket.getOutputStream();

                        if(bitmap.getHeight()>bitmapHeight)
                        {
                            bitmapHeight=1080;
                        }
                        else
                        {
                            bitmapHeight=bitmap.getHeight();
                        }
                        Log.e("Ariful1",""+bitmap.getWidth());
                        Random random=new Random();
                        int sendingnumber=random.nextInt(10);
                        int mimisecond=sendingnumber*1000;


                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                // write your code here
                                countDownTimer =new CountDownTimer(10000,1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                        double seconddd=millisUntilFinished/1000;
                                        printimageA.setText("Sending Data : "+seconddd+" S");



                                    }

                                    @Override
                                    public void onFinish() {
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

                                            os.write(bitmapGetByte);
                                            os .write(t_line9.getBytes());
                                            os .write(t_line6.getBytes());
                                            os.write(t_line7.getBytes());
                                            os.write(t_line8.getBytes());
                                        }catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        countDownTimer1=new CountDownTimer(2000,1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                                long second=  (millisUntilFinished/1000);
                                                int mysecond=Integer.parseInt(String.valueOf(second));



                                            }

                                            @Override
                                            public void onFinish() {

                                                printimageA.setText("Print Image");
                                                try {
                                                    os.flush();
                                                    os.flush();
                                                    m5ocket.close();
                                                }catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                Toasty.success(getApplicationContext(),"Data Sending Complete",Toasty.LENGTH_SHORT,true).show();
                                                return;
                                            }
                                        }.start();
                                        countDownTimer1.start();


                                    }
                                };
                                countDownTimer.start();
                            }
                        });
                    }




                } catch (IOException e) {
                    Log.e("Error", ""+e.getMessage());

                }
            }
        });
        thread.start();
    }
    public byte[] convertBitmapToRGBBytes(Bitmap bitmap) {
        int width = 384;
        int height = bitmap.getHeight();
        int pixelCount = width * height;

        int[] pixels = new int[pixelCount];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        byte[] rgbBytes = new byte[pixelCount * 3];

        for (int i = 0; i < pixelCount; i++) {
            int pixel = pixels[i];
            rgbBytes[i * 3] = (byte) ((pixel >> 16) & 0xFF); // Red component
            rgbBytes[i * 3 + 1] = (byte) ((pixel >> 8) & 0xFF); // Green component
            rgbBytes[i * 3 + 2] = (byte) (pixel & 0xFF); // Blue component
        }

        return rgbBytes;
    }
    public  Bitmap Change(Bitmap bitmap, int backgroundColor){
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        for (int x = 0; x < mutableBitmap.getWidth(); x++) {
            for (int y = 0; y < mutableBitmap.getHeight(); y++) {
                int pixel = mutableBitmap.getPixel(x, y);
                if (pixel == backgroundColor) {
                    mutableBitmap.setPixel(x, y, Color.TRANSPARENT);
                }
            }
        }

        return mutableBitmap;
    }
}