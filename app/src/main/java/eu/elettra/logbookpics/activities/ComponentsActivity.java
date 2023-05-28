package eu.elettra.logbookpics.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.json.JSONException;

import java.util.ArrayList;

import eu.elettra.logbookpics.R;
import eu.elettra.logbookpics.component_system.ComponentComposer;

import eu.elettra.logbookpics.singleton.QrCodeInfo;
import eu.elettra.logbookpics.utils.ViewUtils;

public class ComponentsActivity extends Activity {
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
            componentComposer.readComponentsFromJson(ComponentsActivity.this);
            componentComposer.addViewsToComponents(ComponentsActivity.this);
        } catch (JSONException e) {
            Log.e("LogBookError",e.getMessage());
            throw new RuntimeException(e);
        }

        componentComposer.addComponentsToExistingScrollview(findViewById(R.id.components_view), ComponentsActivity.this);

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
                startActivity(new Intent(ComponentsActivity.this, PostActivity.class));
            }
        });

        ((Button) findViewById(R.id.undo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: implement function
                componentComposer.resetAllChanges();
            }
        });

        ((Button) findViewById(R.id.add_component)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                componentComposer.addView(ComponentsActivity.this, type);
                Log.d("LogBookDebug", componentComposer.getList().toString());
                componentComposer.removeAllComponentToExistingScrollView(findViewById(R.id.components_view));
                componentComposer.addComponentsToExistingScrollview(findViewById(R.id.components_view), ComponentsActivity.this);

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
                componentComposer.moveElement(-1, findViewById(R.id.components_view), parent, ComponentsActivity.this);

                // for some reasons this has to be done, otherwise the buttons stop working
                scanNewControlButtons(componentComposer);
            }
        };

        View.OnClickListener downclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LogBookDebug", "onMoveDown");
                LinearLayout parent = (LinearLayout) view.getParent().getParent();
                componentComposer.moveElement(1 ,findViewById(R.id.components_view), parent, ComponentsActivity.this);

                // for some reasons this has to be done, otherwise the buttons stop working
                scanNewControlButtons(componentComposer);
            }
        };

        View.OnClickListener deleteclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LogBookDebug", "onDelete");
                LinearLayout parent = (LinearLayout) view.getParent().getParent();
                componentComposer.removeElementByView(findViewById(R.id.components_view), parent, ComponentsActivity.this);

                // for some reasons this has to be done, otherwise the buttons stop working
                scanNewControlButtons(componentComposer);
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