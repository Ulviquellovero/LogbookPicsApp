package it.volta.ts.pcto.logbookapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import it.volta.ts.pcto.logbookapp.R;
import it.volta.ts.pcto.logbookapp.image.ImageRenderer;
import it.volta.ts.pcto.logbookapp.image.PostHandler;
import it.volta.ts.pcto.logbookapp.singleton.QrCodeInfo;

public class PreviewActivity extends Activity {

    ImageRenderer imageRenderer;
    PostHandler postHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_prev);
        imageRenderer = new ImageRenderer(this, PreviewActivity.this, QrCodeInfo.url, R.id.img);

        // getting post url
        //String postUrl = QrCodeInfo.jsonTask.getRootJSON().optString("uploadJSONdestination");
        Log.d("LogBookDebug", QrCodeInfo.jsonTask.getRootJSONString().toString());

        postHandler = new PostHandler(this,PreviewActivity.this, "https://users3.elettra.eu/lpa_app/post_json" /*QrCodeInfo.jsonTask.getRootJSON().optString("uploadJSONdestination")*/);
        // on the press of a button, post to the server
        ((TextView)findViewById(R.id.post)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // do post
                postHandler.doPost();
            }
        });
    }
}