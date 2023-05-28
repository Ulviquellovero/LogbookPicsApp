package eu.elettra.logbookpics.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import eu.elettra.logbookpics.R;
import eu.elettra.logbookpics.image.ImageRenderer;
import eu.elettra.logbookpics.image.PostHandler;
import eu.elettra.logbookpics.json.JSONTask;
import eu.elettra.logbookpics.singleton.QrCodeInfo;

public class PreviewActivity extends Activity {

    ImageRenderer imageRenderer;
    PostHandler postHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_prev);
        Activity act = this;
        imageRenderer = new ImageRenderer(this, PreviewActivity.this, QrCodeInfo.url, R.id.img, new JSONTask.JSONCallback() {
            @Override
            public void onCallbackSuccessful() {

                TextView postButton = (TextView)findViewById(R.id.post);
                TextView testComponents = (TextView)findViewById(R.id.test_components);

                postButton.setEnabled(true);
                testComponents.setEnabled(true);

                postButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // do post
                        postHandler = new PostHandler(act,PreviewActivity.this, QrCodeInfo.uploadUrl);
                        view.setEnabled(false);
                    }
                });

                testComponents.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(PreviewActivity.this, ComponentsActivity.class));
                    }
                });
            }

            @Override
            public void onCallbackFailed() {

            }
        });

    }
}