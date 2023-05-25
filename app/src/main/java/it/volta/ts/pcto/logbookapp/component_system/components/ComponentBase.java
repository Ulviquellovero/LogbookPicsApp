package it.volta.ts.pcto.logbookapp.component_system.components;

import android.content.Context;
import android.widget.LinearLayout;

import org.json.JSONObject;

public abstract class ComponentBase {
    public ComponentType componentType;
    protected LinearLayout view;

    public abstract void setFields(JSONObject jObj);
    public abstract void componentToView(Context ctx);

    public ComponentType getComponentType() {
        return componentType;
    }

    public LinearLayout getView() {
        return view;
    }
}
