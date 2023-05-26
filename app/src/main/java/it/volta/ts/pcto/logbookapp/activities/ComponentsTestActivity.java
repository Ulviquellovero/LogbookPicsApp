package it.volta.ts.pcto.logbookapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

import it.volta.ts.pcto.logbookapp.R;
import it.volta.ts.pcto.logbookapp.component_system.ComponentComposer;
import it.volta.ts.pcto.logbookapp.component_system.components.ComponentBase;
import it.volta.ts.pcto.logbookapp.component_system.components.special_components.SpecialComponents;
import it.volta.ts.pcto.logbookapp.singleton.QrCodeInfo;

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

        componentComposer.addComponentsToExistingScrollview(findViewById(R.id.components_view));

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

        ArrayList<Button> upbuttonsList = new ArrayList<>();
        ArrayList<Button> downbuttonsList = new ArrayList<>();
        ArrayList<Button> deletebuttonsList = new ArrayList<>();

        findAllButtonsWithTag(findViewById(R.id.components_view), "upbutton", upbuttonsList);
        findAllButtonsWithTag(findViewById(R.id.components_view), "downbutton", downbuttonsList);
        findAllButtonsWithTag(findViewById(R.id.components_view), "deletebutton", deletebuttonsList);


        ((Button) findViewById(R.id.add_component)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                componentComposer.addView(ComponentsTestActivity.this, type);
                componentComposer.removeAllComponentToExistingScrollView(findViewById(R.id.components_view));
                componentComposer.addComponentsToExistingScrollview(findViewById(R.id.components_view));
            }
        });
        View.OnClickListener upclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        };

        View.OnClickListener downclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        };

        View.OnClickListener deleteclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parent = (View) view.getParent().getParent();

                ((ViewGroup)parent.getParent()).removeView(parent);
            }
        };

        for(Button b : upbuttonsList)
            b.setOnClickListener(upclickListener);
        for (Button b : downbuttonsList)
            b.setOnClickListener(downclickListener);
        for (Button b : deletebuttonsList)
            b.setOnClickListener(deleteclickListener);

    }

    private void findAllButtonsWithTag(View view, String tag, List<Button> buttonList) {
        if(view == null || tag==null) return;

        if (view.getTag()!=null) {
            if(view instanceof Button && view.getTag().toString().equals(tag)) {
                buttonList.add((Button) view);
            }
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = viewGroup.getChildAt(i);
                findAllButtonsWithTag(childView, tag, buttonList);
            }
        }
    }
}