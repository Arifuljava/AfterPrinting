package com.example.printer_ex;

/*
public class MainActivity extends FlutterActivity {
    private static final String CHANNEL_NAME = "com.github.Arifuljava:GrozziieBlutoothSDk:v1.0.1";

    @Override
    public void configureFlutterEngine(FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);

        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL_NAME).setMethodCallHandler(
                (call, result) -> {
                    if (call.method.equals("receiveBitmap")) {
                        byte[] bitmapData = call.argument("bitmapData");
                        double desiredWidth = call.argument("desiredWidth");
                        double desiredHeight = call.argument("desiredHeight");
                        if (bitmapData != null) {
                            Bitmap bitmap = getBitmapFromByteArray(bitmapData);
                            handleReceivedBitmap(bitmap, (int) desiredWidth, (int) desiredHeight);
                        }
                    }
                }
        );
    }

    private Bitmap getBitmapFromByteArray(byte[] bitmapData) {
        ByteBuffer buffer = ByteBuffer.wrap(bitmapData);
        return BitmapFactory.decodeByteArray(bitmapData, 0, buffer.limit());
    }

    private void handleReceivedBitmap(Bitmap bitmap, int desiredWidth, int desiredHeight) {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, desiredWidth, desiredHeight, false);

        Intent intent = new Intent(MainActivity.this, FreshCPClActivity.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        intent.putExtra("bitmapData", byteArray);
        startActivity(intent);
        // Process the bitmap data here
        Toast.makeText(MainActivity.this, "Image Load Done", Toast.LENGTH_SHORT).show();
    }
}*/

/*public class MainActivity extends FlutterActivity {
    private static final String CHANNEL_NAME = "com.github.Arifuljava:GrozziieBlutoothSDk:v1.0.1";
    private PrintDemoActivity printDemoActivity;

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);

        printDemoActivity = new PrintDemoActivity();

        new MethodChannel(flutterEngine.getDartExecutor(), CHANNEL_NAME).setMethodCallHandler(
                new MethodChannel.MethodCallHandler() {
                    @Override
                    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
                        if (Objects.equals(call.method, "printNow")) {
                            showToast(call, result);
                        }
                    }
                }
        );
    }

    private void showToast(MethodCall call, MethodChannel.Result result) {
        String message = call.argument("message");
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

        // Start the InitializeSDKActivity
        Intent intent = new Intent(MainActivity.this, PrintDemoActivity.class);
        startActivity(intent);

        result.success(null);
    }

}*/

/*private void launchPrintDemoActivity(String text) {
        Intent intent = new Intent(MainActivity.this, PrintDemoActivity.class);
        intent.putExtra("text", text);
        startActivity(intent);
    }*/

/*if (call.method.equals("passTextToJava")) {
          String text = call.argument("text");
          //launchPrintDemoActivity(text);
          result.success(null);
          } else*/


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Objects;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;


public class MainActivity extends FlutterActivity {
    private static final String CHANNEL_NAME = "com.github.Arifuljava:GrozziieBlutoothSDk:v1.0.1";
    private PrintDemoActivity printDemoActivity;

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);

        printDemoActivity = new PrintDemoActivity();

        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL_NAME).setMethodCallHandler(
                (call, result) -> {
                    if (call.method.equals("receiveBitmap")) {
                        byte[] bitmapData = call.argument("bitmapData");
                        if (bitmapData != null) {
                            Bitmap bitmap = getBitmapFromByteArray(bitmapData);
                            handleReceivedBitmap(bitmap);
                        }
                    }
                }
        );
    }

    private Bitmap getBitmapFromByteArray(byte[] bitmapData) {
        ByteBuffer buffer = ByteBuffer.wrap(bitmapData);
        return BitmapFactory.decodeByteArray(bitmapData, 0, buffer.limit());
    }

    private void handleReceivedBitmap(Bitmap bitmap) {
        Intent intent = new Intent(MainActivity.this, FreshCPClActivity.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        intent.putExtra("bitmapData", byteArray);
        startActivity(intent);
        // Process the bitmap data here
        Toast.makeText(MainActivity.this, "Image Load Done", Toast.LENGTH_SHORT).show();
    }
}

/*public class MainActivity extends FlutterActivity {
    private static final String CHANNEL_NAME = "com.github.Arifuljava:GrozziieBlutoothSDk:v1.0.1";
    private PrintDemoActivity printDemoActivity;

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);

        printDemoActivity = new PrintDemoActivity();

        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL_NAME).setMethodCallHandler(
                (call, result) -> {
                    if (call.method.equals("receiveImage")) {
                        byte[] imageData = call.argument("imageData");
                        if (imageData != null) {
                            handleReceivedImage(imageData);
                        }
                    }
                }
        );
    }

    private void handleReceivedImage(byte[] imageData) {
        Intent intent = new Intent(MainActivity.this, FreshCPClActivity.class);
        intent.putExtra("imageData", imageData);
        startActivity(intent);
        Toast.makeText(MainActivity.this, "Image Load Done", Toast.LENGTH_SHORT).show();
    }
}*/






