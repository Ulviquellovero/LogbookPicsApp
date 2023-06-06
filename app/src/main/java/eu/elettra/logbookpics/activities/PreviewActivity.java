package eu.elettra.logbookpics.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCallerKt;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.IOException;

import eu.elettra.logbookpics.MainActivity;
import eu.elettra.logbookpics.R;
import eu.elettra.logbookpics.image.ImageRenderer;
import eu.elettra.logbookpics.image.PostHandler;
import eu.elettra.logbookpics.json.JSONTask;
import eu.elettra.logbookpics.singleton.QrCodeInfo;
import eu.elettra.logbookpics.utils.ImageUtils;

public class PreviewActivity extends AppCompatActivity {

    ImageRenderer imageRenderer;
    PostHandler postHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_prev);
        Activity act = this;
        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        imageRenderer = new ImageRenderer(this, PreviewActivity.this, QrCodeInfo.url, R.id.img, new JSONTask.JSONCallback() {
            @Override
            public void onCallbackSuccessful() {

                // startActivity(new Intent(PreviewActivity.this, ImageEditingActivity.class));

                setContentView(R.layout.activity_new_image_prev);


                ((ImageView) findViewById(R.id.imagePreview)).setImageBitmap(QrCodeInfo.imageBitmap);

                // listeners
                // next
                ((ImageButton) findViewById(R.id.imageButton2)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(PreviewActivity.this, ImageEditingActivity.class));
                    }
                });

                // scan new
                ((ImageButton) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(PreviewActivity.this, MainActivity.class));
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