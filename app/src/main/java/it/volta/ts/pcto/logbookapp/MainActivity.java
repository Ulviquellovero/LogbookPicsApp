package it.volta.ts.pcto.logbookapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import it.volta.ts.pcto.logbookapp.utils.JSONTask;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // TESTING
        // TODO: replace this to test other feature
        JSONTask myJSONTask = new JSONTask();
        myJSONTask.loadJSON(MainActivity.this,"https://users3.elettra.eu/lpa_app/lpa_jsonexample");


    }
}