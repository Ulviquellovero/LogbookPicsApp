package it.volta.ts.pcto.logbookapp.singleton;

import android.graphics.Bitmap;

import org.json.JSONObject;

import it.volta.ts.pcto.logbookapp.json.JSONTask;

public class QrCodeInfo {
    public static String url;
    public static JSONTask jsonTask;
    public static String uploadUrl;

    public static JSONObject postJSON;
    // this to fields below should be ignored, because for the time being they are here only for testing purposes
    public static JSONObject root;
    public static Bitmap imageBitmap;
}
