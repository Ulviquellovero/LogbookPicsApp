package it.volta.ts.pcto.logbookapp.component_system.components;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import it.volta.ts.pcto.logbookapp.json.JSONOnUiUpdate;

public abstract class ComponentBase {
    public ComponentType componentType;
    protected LinearLayout editView;
    protected String value;
    protected String compoentTag;

    protected int uuid;

    public ComponentBase() {
        this.uuid = this.hashCode();
    }

    public abstract void setFields(JSONObject jObj);
    public abstract void componentToEditableView(Context ctx, JSONOnUiUpdate jsonOnUiUpdate);

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

    public LinearLayout getEditView() {
        return editView;
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

    public int getUuid() {
        return uuid;
    }
}
