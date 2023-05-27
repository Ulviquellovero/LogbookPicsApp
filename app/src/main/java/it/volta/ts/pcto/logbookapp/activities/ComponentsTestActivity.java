package it.volta.ts.pcto.logbookapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.volta.ts.pcto.logbookapp.MainActivity;
import it.volta.ts.pcto.logbookapp.R;
import it.volta.ts.pcto.logbookapp.component_system.ComponentComposer;
import it.volta.ts.pcto.logbookapp.component_system.components.ComponentBase;

import it.volta.ts.pcto.logbookapp.singleton.QrCodeInfo;
import it.volta.ts.pcto.logbookapp.utils.ViewUtils;

public class ComponentsTestActivity extends Activity {
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_components);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{"TextLabel", "Checkbox", "Radio","Bulleted List","Dropdown"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        type="";

        ((EditText) findViewById(R.id.new_title)).setText(QrCodeInfo.jsonTask.getRootJSON().optString("title"));
        ((EditText) findViewById(R.id.new_info)).setText(QrCodeInfo.jsonTask.getRootJSON().optString("info"));

        ComponentComposer componentComposer = new ComponentComposer(this);

        try {
            componentComposer.readComponentsFromJson(ComponentsTestActivity.this);
            componentComposer.addViewsToComponents(ComponentsTestActivity.this);
        } catch (JSONException e) {
            Log.e("LogBookError",e.getMessage());
            throw new RuntimeException(e);
        }

        componentComposer.addComponentsToExistingScrollview(findViewById(R.id.components_view), ComponentsTestActivity.this);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:{
                        Log.d("LogBookDebug", "TextLabel");
                        type="text";
                        break;
                    }
                    case 1:{
                        Log.d("LogBookDebug", "Checkbox");
                        type="checkbox";
                        /*
                        componentComposer.addView(ComponentsTestActivity.this, "checkbox");
                        componentComposer.removeAllComponentToExistingScrollView(findViewById(R.id.components_view));
                        componentComposer.addComponentsToExistingScrollview(findViewById(R.id.components_view));
                        */
                        break;
                    }
                    case 2:{
                        Log.d("LogBookDebug", "Radio");
                        type="radio";
                        break;
                    }
                    case 3:{
                        Log.d("LogBookDebug", "Bulleted List");
                        type="list";
                        break;
                    }
                    case 4:{
                        Log.d("LogBookDebug", "Dropdown");
                        type="dropdown";
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        ((Button)findViewById(R.id.proceed)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ComponentsTestActivity.this, PostActivity.class));
            }
        });

        ((Button) findViewById(R.id.add_component)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                componentComposer.addView(ComponentsTestActivity.this, type);
                Log.d("LogBookDebug", componentComposer.getList().toString());
                componentComposer.removeAllComponentToExistingScrollView(findViewById(R.id.components_view));
                componentComposer.addComponentsToExistingScrollview(findViewById(R.id.components_view),ComponentsTestActivity.this);

                // add new buttons
                scanNewControlButtons(componentComposer);
            }
        });
        // ---------

        scanNewControlButtons(componentComposer);

    }

    private void scanNewControlButtons(ComponentComposer componentComposer){
        ArrayList<View> upbuttonsList = ViewUtils.getViewsByTag(findViewById(R.id.components_view), "upbutton");
        ArrayList<View> downbuttonsList = ViewUtils.getViewsByTag(findViewById(R.id.components_view), "downbutton");
        ArrayList<View> deletebuttonsList = ViewUtils.getViewsByTag(findViewById(R.id.components_view), "deletebutton");


        View.OnClickListener upclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LogBookDebug", "onMoveUp");
                LinearLayout parent = (LinearLayout) view.getParent().getParent();
                componentComposer.moveElement(-1, findViewById(R.id.components_view), parent, ComponentsTestActivity.this);
            }
        };

        View.OnClickListener downclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LogBookDebug", "onMoveDown");
                LinearLayout parent = (LinearLayout) view.getParent().getParent();
                componentComposer.moveElement(1 ,findViewById(R.id.components_view), parent, ComponentsTestActivity.this);
            }
        };

        View.OnClickListener deleteclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LogBookDebug", "onDelete");
                LinearLayout parent = (LinearLayout) view.getParent().getParent();
                componentComposer.removeElementByView(findViewById(R.id.components_view), parent, ComponentsTestActivity.this);
            }
        };

        for(View b : upbuttonsList)
            b.setOnClickListener(upclickListener);
        for (View b : downbuttonsList)
            b.setOnClickListener(downclickListener);
        for (View b : deletebuttonsList)
            b.setOnClickListener(deleteclickListener);
    }

}