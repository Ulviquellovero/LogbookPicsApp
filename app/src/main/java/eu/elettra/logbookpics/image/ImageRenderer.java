package eu.elettra.logbookpics.image;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import eu.elettra.logbookpics.activities.CameraActivity;
import eu.elettra.logbookpics.json.JSONTask;
import eu.elettra.logbookpics.singleton.QrCodeInfo;
import eu.elettra.logbookpics.utils.ImageUtils;

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
        QrCodeInfo.uploadUrl = QrCodeInfo.jsonTask.getRootJSON().optString("uploadJSONdestination");
        // special cases
        if(image.equals("camera")){
            if(QrCodeInfo.imageBitmap==null)
                activity.startActivity(new Intent(activity, CameraActivity.class));
            jsonCallback.onCallbackSuccessful();
            return;
        } else if (image.equals("file")) {
            imageChooser();
            jsonCallback.onCallbackSuccessful();
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


    // this function is triggered when
    // the Select Image Button is clicked
    private final int SELECT_PICTURE = 200;
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        activity.startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // TODO: set image bitmap from here
                // Get the url of the image from data
                Uri imageUri = data.getData();
                Bitmap bitmap;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), imageUri);

                    // TODO: 1000 is temporary
                    QrCodeInfo.imageBitmap = ImageUtils.getResizedBitmap(bitmap, 1000);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
}

