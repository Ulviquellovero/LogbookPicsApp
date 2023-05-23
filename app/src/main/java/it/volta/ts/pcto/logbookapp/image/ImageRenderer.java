package it.volta.ts.pcto.logbookapp.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;

import it.volta.ts.pcto.logbookapp.json.JSONTask;

public class ImageRenderer {

    private JSONTask json;
    private String url;
    private ImageView imageRef;
    private Activity activity;

    public ImageRenderer(Activity activity, Context ctx, String url, int id) {
        imageRef = (ImageView) activity.findViewById(id);
        this.activity=activity;
        json = new JSONTask(ctx);
        json.loadJSON(url, new JSONTask.JSONCallback() {
            @Override
            public void onCallbackSuccessful() {
                Log.d(this.getClass().getSimpleName(),"Loaded JSON");
                renderImage();
            }

            @Override
            public void onCallbackFailed() {
                Log.e(this.getClass().getSimpleName().toString(), "JSON Error");
                // print it to the screen
            }
        });
    }

    // TODO: this can be reworked to work better
    private void renderImage(){
        JSONObject root = json.getRootJSON();
        String image;
        if(root == null){
            return;
        }

        image = root.optString("inputimage");

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


    }
}
