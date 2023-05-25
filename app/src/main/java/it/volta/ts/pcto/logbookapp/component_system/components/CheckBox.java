package it.volta.ts.pcto.logbookapp.component_system.components;

import android.app.ActionBar;
import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONObject;

import it.volta.ts.pcto.logbookapp.R;

public class CheckBox extends ComponentBase{
    public String value;
    public boolean onOff;
    public CheckBox() {
        super.componentType = ComponentType.CHECKBOX;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void setFields(JSONObject jObj) {
        value = jObj.optString("title", "Field 'title' not found");
        onOff = jObj.optBoolean("default", false);
    }

    @Override
    public void componentToView(Context ctx) {
        super.view = new LinearLayout(ctx);

        // TODO: set id somehow

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);;
        super.view.setLayoutParams(params);
        super.view.setOrientation(LinearLayout.HORIZONTAL);

        // check box
        android.widget.CheckBox checkBox = new android.widget.CheckBox(ctx);
        checkBox.setText(value);
        checkBox.setChecked(onOff);

        // adding the menu
        ImageView menu = new ImageView(ctx);
        menu.setImageResource(R.drawable.baseline_menu_24);

        super.view.addView(checkBox);
        super.view.addView(menu);

    }
}
