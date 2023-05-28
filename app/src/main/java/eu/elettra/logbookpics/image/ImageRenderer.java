package eu.elettra.logbookpics.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONObject;

import java.util.Base64;

import eu.elettra.logbookpics.json.JSONTask;
import eu.elettra.logbookpics.singleton.QrCodeInfo;

public class ImageRenderer {

    private JSONObject root;
    private String url;
    private String postUrl;
    private ImageView imageRef;
    private Activity activity;

    public ImageRenderer(Activity activity, Context ctx, String url, int id, JSONTask.JSONCallback jsonCallback) {
        imageRef = (ImageView) activity.findViewById(id);
        this.activity=activity;

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

        if(QrCodeInfo.root!= null)  Log.d("ImageRenderer", "if it aint null here");
        image = QrCodeInfo.jsonTask.getRootJSON().optString("inputimage");
        QrCodeInfo.uploadUrl = QrCodeInfo.jsonTask.getRootJSON().optString("uploadJSONdestination");

        Log.d("LogBookDebug",QrCodeInfo.uploadUrl);

        String temp = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            byte[] decode = Base64.getDecoder().decode(image);
            temp = new String(decode);


            Bitmap imgSrc = BitmapFactory.decodeByteArray(decode, 0, decode.length);

            this.activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imageRef.setImageBitmap(imgSrc);
                }
            });


        }

        jsonCallback.onCallbackSuccessful();
    }

}
