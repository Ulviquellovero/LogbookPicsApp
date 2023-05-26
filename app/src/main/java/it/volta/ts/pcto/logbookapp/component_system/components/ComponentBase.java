package it.volta.ts.pcto.logbookapp.component_system.components;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import it.volta.ts.pcto.logbookapp.R;
import it.volta.ts.pcto.logbookapp.json.JSONOnUiUpdate;

public abstract class ComponentBase {
    public ComponentType componentType;
    protected LinearLayout view;
    protected String value;
    protected String compoentTag;

    public abstract void setFields(JSONObject jObj);
    public abstract void componentToView(Context ctx, JSONOnUiUpdate jsonOnUiUpdate);

    public abstract JSONObject componentToJson() throws JSONException;

    public LinearLayout addMoveUpDownButton(Context ctx){
        LinearLayout linearLayout = new LinearLayout(ctx);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        Button upButton = new Button(ctx);
        Button downButton = new Button(ctx);
        Button deleteButton = new Button(ctx);

        upButton.setText("↑");
        downButton.setText("↓");
        deleteButton.setText(" — ");

        upButton.setTag("upbutton");
        downButton.setTag("downbutton");
        deleteButton.setTag("deletebutton");

        linearLayout.addView(upButton);
        linearLayout.addView(downButton);
        linearLayout.addView(deleteButton);
        return linearLayout;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public LinearLayout getView() {
        return view;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCompoentTag() {
        return compoentTag;
    }
}
