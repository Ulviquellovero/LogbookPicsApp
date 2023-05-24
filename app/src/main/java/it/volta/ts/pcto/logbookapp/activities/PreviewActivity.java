package it.volta.ts.pcto.logbookapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

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

        Log.d("LogBookDebug", QrCodeInfo.url);

        // getting post url

        // on the press of a button, post to the server
        Activity act = this;
        ((TextView)findViewById(R.id.post)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // do post
                postHandler = new PostHandler(act,PreviewActivity.this, QrCodeInfo.uploadUrl);
                view.setEnabled(false);
            }
        });
    }
}