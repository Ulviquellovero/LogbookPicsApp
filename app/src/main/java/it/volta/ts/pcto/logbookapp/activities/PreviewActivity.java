package it.volta.ts.pcto.logbookapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import it.volta.ts.pcto.logbookapp.R;
import it.volta.ts.pcto.logbookapp.image.ImageRenderer;
import it.volta.ts.pcto.logbookapp.singleton.QrCodeInfo;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_prev);
        ImageRenderer imageRenderer = new ImageRenderer(this, PreviewActivity.this, QrCodeInfo.url, R.id.img);
    }
}