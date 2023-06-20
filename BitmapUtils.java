package com.example.printer_ex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {
    public static void saveBitmapAsJPG(Bitmap bitmap, String fileName, Context context) {
        // Create a file in the external storage directory
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = new File(storageDir, fileName);

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void copyJPGToDrawable(String jpgFilePath, int drawableResourceId, Context context) {
        Bitmap bitmap = BitmapFactory.decodeFile(jpgFilePath);

        if (bitmap == null) {
            // Error loading the JPG file
            return;
        }

        // Replace the existing drawable resource with the JPG file
        BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        ContextCompat.getDrawable(context, drawableResourceId).setCallback(null);
        ContextCompat.getDrawable(context, drawableResourceId).setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ContextCompat.getDrawable(context, drawableResourceId).draw(new Canvas());
    }
}