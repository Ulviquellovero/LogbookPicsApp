package it.volta.ts.pcto.logbookapp;

import android.app.Activity;
import android.os.Bundle;

import it.volta.ts.pcto.logbookapp.image.ImageRenderer;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);
        setContentView(R.layout.image_prev);

        // TESTING
        // TODO: replace this to test other feature
        ImageRenderer imageRenderer = new ImageRenderer(this, MainActivity.this, "https://users3.elettra.eu/lpa_app/lpa_jsonexample", R.id.img);

    }
}