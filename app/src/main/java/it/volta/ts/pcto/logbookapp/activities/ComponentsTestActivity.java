package it.volta.ts.pcto.logbookapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;

import it.volta.ts.pcto.logbookapp.R;
import it.volta.ts.pcto.logbookapp.component_system.ComponentComposer;

public class ComponentsTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_components);


        ComponentComposer componentComposer = new ComponentComposer();

        try {
            componentComposer.readComponentsFromJson(ComponentsTestActivity.this);
        } catch (JSONException e) {
            Log.e("LogBookError",e.getMessage());
            throw new RuntimeException(e);
        }

        componentComposer.addComponentsToExistingScrollview(findViewById(R.id.components_view));
    }
}