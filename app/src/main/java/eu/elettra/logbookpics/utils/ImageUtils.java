package eu.elettra.logbookpics.utils;

import android.graphics.Bitmap;

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
}
