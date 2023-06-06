package eu.elettra.logbookpics.image;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import eu.elettra.logbookpics.activities.CameraActivity;
import eu.elettra.logbookpics.activities.PreviewActivity;
import eu.elettra.logbookpics.json.JSONTask;
import eu.elettra.logbookpics.singleton.QrCodeInfo;
import eu.elettra.logbookpics.utils.ImageUtils;
import kotlin.Unit;

public class ImageRenderer {

    private JSONObject root;
    private String url;
    private String postUrl;
    private ImageView imageRef;
    private Activity activity;
    private ActivityResultLauncher<Intent> galleryActivityResultLauncher;
    private Context ctx;

    public ImageRenderer(Activity activity, Context ctx, String url, int id, JSONTask.JSONCallback jsonCallback) {
        imageRef = (ImageView) activity.findViewById(id);
        this.activity=activity;
        this.ctx=ctx;
        
        //setupActivityResultLauncher();

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

        QrCodeInfo.uploadUrl = QrCodeInfo.jsonTask.getRootJSON().optString("uploadJSONdestination");
        // special cases
        if(image.equals("camera")){
            if(QrCodeInfo.imageBitmap==null)
                activity.startActivity(new Intent(activity, CameraActivity.class));
            // small control here
            if(QrCodeInfo.imageBitmap!=null) jsonCallback.onCallbackSuccessful(); else jsonCallback.onCallbackFailed();
            return;
        } else if (image.equals("file")) {

            // TODO: make this work somehow
            //launchGalleryActivity();

            // small control here
            if(QrCodeInfo.imageBitmap!=null) jsonCallback.onCallbackSuccessful(); else jsonCallback.onCallbackFailed();
            return;
        }

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


    /*
    private void setupActivityResultLauncher() {
        // this does not work for some reasons that only god knows
        galleryActivityResultLauncher =  activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Gestisci il risultato qui
                        int resultCode = result.getResultCode();
                        Intent data = result.getData();
                        // Esegui le operazioni necessarie in base al risultato
                    }
                });
    }

    private void launchGalleryActivity() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryActivityResultLauncher.launch(intent);
    }
     */



}

