package eu.elettra.logbookpics.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        imageRenderer = new ImageRenderer(this, PreviewActivity.this, QrCodeInfo.url, R.id.img, new JSONTask.JSONCallback() {
            @SuppressLint("ResourceType")
            @Override
            public void onCallbackSuccessful() {

                //TextView testComponents = (TextView)findViewById(R.id.test_components);
                Button proceed = new Button(PreviewActivity.this);
                proceed.setText("Proceed");

                proceed.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                proceed.setEnabled(true);

                ((LinearLayout) findViewById(R.id.image_prev)).addView(proceed);

                proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //startActivity(new Intent(PreviewActivity.this, ComponentsActivity.class));
                        startActivity(new Intent(PreviewActivity.this, ImageEditingActivity.class));
                    }
                });
            }

            @Override
            public void onCallbackFailed() {
                TextView textView = new TextView(PreviewActivity.this);
                textView.setText("an error has occured while parsing the provided QR Code");
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setEnabled(true);

                ((LinearLayout) findViewById(R.id.image_prev)).addView(textView);
            }
        });

    }
}