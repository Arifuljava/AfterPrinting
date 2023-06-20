package com.example.printer_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.NotificationManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class BluetoothImagePrinting extends AppCompatActivity {
    ImageView imageview;
    ImageView btImage;
    private String TAG = "Main Activity";
    EditText message;
    Button btnPrint, btnBill, btnDonate;


    byte FONT_TYPE;
    private static BluetoothSocket btsocket;
    private static OutputStream outputStream;

    ///for cpcl
    Uri imageuri;
    int flag = 0;
    BluetoothSocket m5ocket;
    BluetoothManager mBluetoothManager;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice device;
    ImageView imageposit;
    Bitmap bitmapdataMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_image_printing);

        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        String defaultlanguage = Locale.getDefault().getDisplayLanguage();
        imageview = findViewById(R.id.imageview);


        btImage = findViewById(R.id.btImageView);
        // Retrieve the image data from the intent
        byte[] bitmapData = getIntent().getByteArrayExtra("bitmapData");
        if (bitmapData != null) {
            bitmapdataMe = BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);
            // Use the bitmap as needed
            imageview.setImageBitmap(bitmapdataMe);
            flag = 2;
        }


        btnScan = findViewById(R.id.btnScan);
        if (defaultlanguage.toLowerCase().toString().equals("english")) {
            toolbar.setTitle("Pick Up Image");
        } else {
            toolbar.setTitle("拾取图像");
        }
        message = (EditText) findViewById(R.id.txtMessage);
        btnPrint = (Button) findViewById(R.id.btnPrint);
        btnBill = (Button) findViewById(R.id.btnBill);
        btnDonate = (Button) findViewById(R.id.btnDonate);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_myarrow);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_myarrow);
        getSupportActionBar().setElevation(10.0f);
        getSupportActionBar().setElevation(10.0f);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intent, 102);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                permissionDeniedResponse.getPermissionName();

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                if (permissionRequest.getName().equals("shout down")) {
                                    permissionToken.continuePermissionRequest();
                                } else if (permissionRequest.getName().equals("granted")) {
                                    permissionToken.continuePermissionRequest();
                                } else {
                                    permissionToken.continuePermissionRequest();
                                }
                                permissionToken.continuePermissionRequest();


                            }
                        }).check();

            }
        });
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1) {
                    Dexter.withContext(getApplicationContext())
                            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                    Intent intent = new Intent();
                                    intent.setType("image/*");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(intent, 102);
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                    permissionDeniedResponse.getPermissionName();

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                    if (permissionRequest.getName().equals("shout down")) {
                                        permissionToken.continuePermissionRequest();
                                    } else if (permissionRequest.getName().equals("granted")) {
                                        permissionToken.continuePermissionRequest();
                                    } else {
                                        permissionToken.continuePermissionRequest();
                                    }
                                    permissionToken.continuePermissionRequest();


                                }
                            }).check();
                } else {
                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(BluetoothImagePrinting.this);

                    bottomSheetDialog.setContentView(R.layout.simpllayout);
                    btnScwan = (Button) bottomSheetDialog.findViewById(R.id.btnScwan);
                    widthhh = (EditText) bottomSheetDialog.findViewById(R.id.widthhh);
                    heighjttt = (EditText) bottomSheetDialog.findViewById(R.id.heighjttt);
                    amount_page = (EditText) bottomSheetDialog.findViewById(R.id.amount_page);
                    widthhh.addTextChangedListener(widthwatcher);
                    heighjttt.addTextChangedListener(heightwatcher);
                    widthhh.setText("180");
                    heighjttt.setText("250");
                    amount_page.setText("1");
                    btnScwan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(widthhh.getText().toString()) || TextUtils.isEmpty(heighjttt.getText().toString())
                                    || TextUtils.isEmpty(amount_page.getText().toString())) {
                                Toast.makeText(BluetoothImagePrinting.this, "Enter all information", Toast.LENGTH_SHORT).show();
                            } else {
                                // Toast.makeText(BluetoothImagePrinting.this, "", Toast.LENGTH_SHORT).show();
                                printDemo22();
                            }

                        }
                    });


                    bottomSheetDialog.show();
                }
            }
        });


    }

    private void printDemo22() {
        String BlueMac = "FB:7F:9B:F2:20:B7";
        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(BlueMac);
        ///Toasty.info(getApplicationContext(),"Please active bluetooth"+mBluetoothAdapter.isEnabled(),Toasty.LENGTH_SHORT,true).show();
        if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "Please active bluetooth", Toast.LENGTH_SHORT).show();
            android.app.AlertDialog.Builder mybuilder = new android.app.AlertDialog.Builder(BluetoothImagePrinting.this);
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
                    if (ActivityCompat.checkSelfPermission(BluetoothImagePrinting.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        mBluetoothAdapter.enable();
                    }
                    mBluetoothAdapter.enable();
                    Toast.makeText(BluetoothImagePrinting.this, "Bluetooth is active now.", Toast.LENGTH_SHORT).show();
                }
            }).create().show();

            return;
        } else {
            Toast.makeText(this, "ggg", Toast.LENGTH_SHORT).show();
            printImage();
        }
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

    int bitmapHeight = 1080;
    OutputStream os = null;

    private void printImage() {
        Toast.makeText(this, "hhh", Toast.LENGTH_SHORT).show();
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.testing);;
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
                    if (ActivityCompat.checkSelfPermission(BluetoothImagePrinting.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
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

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                // write your code here
                                countDownTimer =new CountDownTimer(1000,1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                        double seconddd=millisUntilFinished/1000;




                                    }

                                    @Override
                                    public void onFinish() {

                                        countDownTimer1=new CountDownTimer(2000,1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                                long second=  (millisUntilFinished/1000);
                                                int mysecond=Integer.parseInt(String.valueOf(second));



                                            }

                                            @Override
                                            public void onFinish() {




                                                Toast.makeText(BluetoothImagePrinting.this, "Data Sending Complete", Toast.LENGTH_SHORT).show();
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


                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                // write your code here
                                countDownTimer =new CountDownTimer(1000,1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                        double seconddd=millisUntilFinished/1000;




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
                                        countDownTimer1=new CountDownTimer(2000,1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                                long second=  (millisUntilFinished/1000);
                                                int mysecond=Integer.parseInt(String.valueOf(second));



                                            }

                                            @Override
                                            public void onFinish() {


                                                try {
                                                    os.flush();
                                                    os.flush();
                                                    m5ocket.close();
                                                }catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                Toast.makeText(BluetoothImagePrinting.this, "Data Sending Complete", Toast.LENGTH_SHORT).show();
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
                    Log.e(TAG, ""+e.getMessage());

                }
            }
        });
        thread.start();
    }

    TextWatcher heightwatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            check=s.toString();
            if (TextUtils.isEmpty(check)) {
            }
            else {
                if (Integer.parseInt(check)>250) {
                    heighjttt.setError("Invalid Height Maxnimum is 250");
                    btnScwan.setVisibility(View.GONE);
                }
                else {
                    btnScwan.setVisibility(View.VISIBLE);
                }
            }
        }
    };
    Button btnScwan;
    String check;
    TextWatcher widthwatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            check=s.toString();
            if (TextUtils.isEmpty(check)) {
            }
            else {
                if (Integer.parseInt(check)>190) {
                    widthhh.setError("Invalid Width Maxnimum is 190");
                    btnScwan.setVisibility(View.GONE);
                }
                else {
                    btnScwan.setVisibility(View.VISIBLE);
                }
            }
        }
    };
    EditText widthhh,heighjttt,amount_page;
    int flag1=1;
    Button btnScan;
    Uri imageUri;
    Bitmap imageBitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode==102 && resultCode==RESULT_OK && data.getData()!=null )
        {
            try {
                imageUri=data.getData();
                Picasso.get().load(imageUri).into(imageview);
                Canvas canvas=new Canvas();
                flag=2;
                handleGalleryIntent(data);
                btnScan.setText("Go to print Page");
                ///Toast.makeText(this, ""+imageBitmap, Toast.LENGTH_SHORT).show();


            }catch (Exception e)
            {

            }
        }
        else {
            try {
                btsocket = DeviceList.getSocket();
                if(btsocket != null){
                    printText(message.getText().toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        String resourceName = "new_image_image";
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
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        // Add the new drawable resource to the drawable directory
        File newDrawableFile = new File(getFilesDir(), resourceName + ".png");

        newDrawableUri = Uri.fromFile(newDrawableFile);
        //Toast.makeText(this, ""+newDrawableUri, Toast.LENGTH_SHORT).show();
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(newDrawableUri);
        sendBroadcast(mediaScanIntent);
    }
    Uri newDrawableUri;
    protected void printDemo() {
        if(btsocket == null){
            Intent BTIntent = new Intent(getApplicationContext(), DeviceList.class);
            this.startActivityForResult(BTIntent, DeviceList.REQUEST_CONNECT_BT);
        }
        else{
            int n=Integer.parseInt(amount_page.getText().toString());
            for (int i=0;i<n;i++) {
                OutputStream opstream = null;
                try {
                    opstream = btsocket.getOutputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                outputStream = opstream;

                //print command
                try {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputStream = btsocket.getOutputStream();

                    byte[] printformat = { 0x1B, 0*21, FONT_TYPE };
                    //outputStream.write(printformat);

                    //print title
                    printUnicode();
                    //print normal text
                    printCustom(message.getText().toString(),0,0);
                    printPhoto(newDrawableUri);
                    printNewLine();
                    printText("     >>>>   Thank you  <<<<     "); // total 32 char in a single line
                    //resetPrint(); //reset printer
                    printUnicode();
                    printNewLine();
                    printNewLine();
                    printNewLine();
                    printNewLine();
                    printNewLine();
                    printNewLine();
                    printNewLine();



                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }




    //print custom
    private void printCustom(String msg, int size, int align) {
        //Print config "mode"
        byte[] cc = new byte[]{0x1B,0x21,0x03};  // 0- normal size text
        //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B,0x21,0x08};  // 1- only bold text
        byte[] bb2 = new byte[]{0x1B,0x21,0x20}; // 2- bold with medium text
        byte[] bb3 = new byte[]{0x1B,0x21,0x10}; // 3- bold with large text
        try {
            switch (size){
                case 0:
                    outputStream.write(cc);
                    break;
                case 1:
                    outputStream.write(bb);
                    break;
                case 2:
                    outputStream.write(bb2);
                    break;
                case 3:
                    outputStream.write(bb3);
                    break;
            }

            switch (align){
                case 0://left align
                    outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case 1:
                    //center align
                    outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case 2:
                    //right align
                    outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }
            outputStream.write(msg.getBytes());
            outputStream.write(PrinterCommands.LF);
            //outputStream.write(cc);
            //printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print photo
    public void printPhoto(Uri img) {
        try {
            String im=""+img;
            String imagePathString = img.getPath();
            File imageFile = new File(imagePathString);
            Bitmap bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

            /// Toast.makeText(this, ""+bmp, Toast.LENGTH_SHORT).show();
            int newHeight = Integer.parseInt(heighjttt.getText().toString());
            int newWidth = Integer.parseInt(widthhh.getText().toString());

// Create a new bitmap with the new size
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bmp, newWidth, newHeight, false);
            if(resizedBitmap!=null){
                byte[] command = Utils.decodeBitmap(resizedBitmap);
                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(command);
            }else{
                Log.e("Print Photo error", "the file isn't exists");
            }

            ///print noyification

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    //print unicode
    public void printUnicode(){
        try {
            outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            printText(Utils.UNICODE_TEXT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //print new line
    private void printNewLine() {
        try {
            outputStream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void resetPrint() {
        try{
            outputStream.write(PrinterCommands.ESC_FONT_COLOR_DEFAULT);
            outputStream.write(PrinterCommands.FS_FONT_ALIGN);
            outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
            outputStream.write(PrinterCommands.ESC_CANCEL_BOLD);
            outputStream.write(PrinterCommands.LF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print text
    private void printText(String msg) {
        try {
            // Print normal text
            outputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            outputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String leftRightAlign(String str1, String str2) {
        String ans = str1 +str2;
        if(ans.length() <31){
            int n = (31 - str1.length() + str2.length());
            ans = str1 + new String(new char[n]).replace("\0", " ") + str2;
        }
        return ans;
    }


    private String[] getDateTime() {
        final Calendar c = Calendar.getInstance();
        String dateTime [] = new String[2];
        dateTime[0] = c.get(Calendar.DAY_OF_MONTH) +"/"+ c.get(Calendar.MONTH) +"/"+ c.get(Calendar.YEAR);
        dateTime[1] = c.get(Calendar.HOUR_OF_DAY) +":"+ c.get(Calendar.MINUTE);
        return dateTime;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if(btsocket!= null){
                outputStream.close();
                btsocket.close();
                btsocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}