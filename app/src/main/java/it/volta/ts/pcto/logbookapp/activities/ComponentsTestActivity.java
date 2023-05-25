package it.volta.ts.pcto.logbookapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.volta.ts.pcto.logbookapp.R;
import it.volta.ts.pcto.logbookapp.component_system.ComponentComposer;
import it.volta.ts.pcto.logbookapp.component_system.components.ComponentBase;
import it.volta.ts.pcto.logbookapp.singleton.QrCodeInfo;

public class ComponentsTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_components);

        ((EditText) findViewById(R.id.new_title)).setText(QrCodeInfo.jsonTask.getRootJSON().optString("title"));
        ((EditText) findViewById(R.id.new_info)).setText(QrCodeInfo.jsonTask.getRootJSON().optString("info"));

        ComponentComposer componentComposer = new ComponentComposer();

        try {
            componentComposer.readComponentsFromJson(ComponentsTestActivity.this);
        } catch (JSONException e) {
            Log.e("LogBookError",e.getMessage());
            throw new RuntimeException(e);
        }

        componentComposer.addComponentsToExistingScrollview(findViewById(R.id.components_view));


        ArrayList<Button> upbuttonsList = new ArrayList<>();
        ArrayList<Button> downbuttonsList = new ArrayList<>();
        findAllButtonsWithTag(findViewById(R.id.components_view), "upbutton", upbuttonsList);
        findAllButtonsWithTag(findViewById(R.id.components_view), "downbutton", downbuttonsList);

        View.OnClickListener upclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout parent = (LinearLayout) view.getParent().getParent();
                ArrayList<ComponentBase> list = componentComposer.getList();

                if(list.indexOf(parent)==-1 || list.indexOf(parent)-1==-1) return;

                Collections.swap(list, list.indexOf(parent), list.indexOf(parent)-1);

                componentComposer.setList(list);

                componentComposer.removeAllComponentToExistingScrollView(findViewById(R.id.components_view));
                componentComposer.addComponentsToExistingScrollview(findViewById(R.id.components_view));

            }
        };

        View.OnClickListener downclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout parent = (LinearLayout) view.getParent().getParent();
                ArrayList<ComponentBase> list = componentComposer.getList();

                if(list.indexOf(parent)==-1 || list.indexOf(parent)+1==-1) return;

                Collections.swap(list, list.indexOf(parent), list.indexOf(parent)+1);

                componentComposer.setList(list);

                componentComposer.removeAllComponentToExistingScrollView(findViewById(R.id.components_view));
                componentComposer.addComponentsToExistingScrollview(findViewById(R.id.components_view));
            }
        };

        for(Button b : upbuttonsList)
            b.setOnClickListener(upclickListener);
        for (Button b : downbuttonsList)
            b.setOnClickListener(downclickListener);

    }

    private void findAllButtonsWithTag(View view, String tag, List<Button> buttonList) {
        if (view instanceof Button && view.getTag().equals(tag)) {
            buttonList.add((Button) view);
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