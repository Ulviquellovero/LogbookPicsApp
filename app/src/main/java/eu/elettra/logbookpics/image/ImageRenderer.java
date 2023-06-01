package eu.elettra.logbookpics.image;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import eu.elettra.logbookpics.activities.CameraActivity;
import eu.elettra.logbookpics.json.JSONTask;
import eu.elettra.logbookpics.singleton.QrCodeInfo;

public class ImageRenderer {

    private JSONObject root;
    private String url;
    private String postUrl;
    private ImageView imageRef;
    private Activity activity;
    private Context ctx;

    public ImageRenderer(Activity activity, Context ctx, String url, int id, JSONTask.JSONCallback jsonCallback) {
        imageRef = (ImageView) activity.findViewById(id);
        this.activity=activity;
        this.ctx=ctx;

        if(QrCodeInfo.jsonTask==null)
            QrCodeInfo.jsonTask = new JSONTask(ctx);

        QrCodeInfo.jsonTask.loadJSON(url, new JSONTask.JSONCallback() {
            @Override
            public void onCallbackSuccessful() {
                Log.d(this.getClass().getSimpleName(),"Loaded JSON");
                root = QrCodeInfo.jsonTask.getRootJSON();
                renderImage(jsonCallback);
            }

            @Override
            public void onCallbackFailed() {
                Log.e(this.getClass().getSimpleName().toString(), "JSON Error");
                jsonCallback.onCallbackFailed();
            }
        });
    }


    // TODO: this can be reworked to work better
    private void renderImage(JSONTask.JSONCallback jsonCallback){
        String image;
        if(QrCodeInfo.jsonTask.getRootJSON() == null){
            return;
        }


        QrCodeInfo.root = QrCodeInfo.jsonTask.getRootJSON();


        image = QrCodeInfo.jsonTask.getRootJSON().optString("inputimage");

        // special cases
        if(image.equals("camera")){
            if(QrCodeInfo.imageBitmap==null)
                activity.startActivity(new Intent(activity, CameraActivity.class));
            jsonCallback.onCallbackSuccessful();
            return;
        } else if (image.equals("file")) {
            return;
        }

        QrCodeInfo.uploadUrl = QrCodeInfo.jsonTask.getRootJSON().optString("uploadJSONdestination");

        Log.d("LogBookDebug",QrCodeInfo.uploadUrl);

        String temp = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            byte[] decode = Base64.getDecoder().decode(image);
            temp = new String(decode);


            Bitmap imgSrc = BitmapFactory.decodeByteArray(decode, 0, decode.length);

            QrCodeInfo.imageBitmap = imgSrc;
            this.activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imageRef.setImageBitmap(imgSrc);
                }
            });


        }

        jsonCallback.onCallbackSuccessful();
    }

    public static void uploadImageToJson(){
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;

        int quality = 100;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        QrCodeInfo.imageBitmap.compress(format, quality, outputStream);

        byte[] byteArray = outputStream.toByteArray();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String encodedBitmap = Base64.getEncoder().encodeToString(byteArray);

            try {
                QrCodeInfo.postJSON.put("inputimage", encodedBitmap);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
