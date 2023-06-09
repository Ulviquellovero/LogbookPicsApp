package eu.elettra.logbookpics.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import eu.elettra.logbookpics.singleton.QrCodeInfo;

public class ImageUtils {

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = 50;
        int height = 50;  // image.getHeight()

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static void saveBitmapToGallery(Context ctx) {
        Bitmap bitmap = QrCodeInfo.imageBitmap;

        String filename = Long.toString(System.currentTimeMillis())+".jpg"; // Name of the file to be saved
        File directory = ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File file = new File(directory, filename);

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();

            //scanFile(file, ctx);
            addImageToGallery(file, ctx);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
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


    private static void addImageToGallery(File file, Context ctx){
            if(!file.exists()) return;
            ContentResolver contentResolver = ctx.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, file.getName());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());

            Uri uri = contentResolver.insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
    }

}
