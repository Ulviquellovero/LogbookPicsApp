package it.volta.ts.pcto.logbookapp.component_system.components;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import it.volta.ts.pcto.logbookapp.R;

public abstract class ComponentBase {
    public ComponentType componentType;
    protected LinearLayout view;

    public abstract void setFields(JSONObject jObj);
    public abstract void componentToView(Context ctx);

    public LinearLayout addMoveUpDownButton(Context ctx){
        LinearLayout linearLayout = new LinearLayout(ctx);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        Button upButton = new Button(ctx);
        Button downButton = new Button(ctx);

        upButton.setText("↑");
        downButton.setText("↓");

        upButton.setTag("upbutton");
        downButton.setTag("downbutton");
        return linearLayout;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public LinearLayout getView() {
        return view;
    }
}
