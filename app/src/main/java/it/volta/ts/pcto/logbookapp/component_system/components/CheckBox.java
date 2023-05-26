package it.volta.ts.pcto.logbookapp.component_system.components;

import android.app.ActionBar;
import android.content.Context;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import it.volta.ts.pcto.logbookapp.R;
import it.volta.ts.pcto.logbookapp.json.JSONOnUiUpdate;

public class CheckBox extends ComponentBase{
    public String value;
    public boolean onOff;
    public CheckBox() {
        super.componentType = ComponentType.CHECKBOX;
        super.compoentTag = "checkbox";
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
    public void componentToView(Context ctx, JSONOnUiUpdate jsonOnUiUpdate) {
        super.view = new LinearLayout(ctx);

        super.view.setTag(super.compoentTag);
        // TODO: set id somehow

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);;
        super.view.setLayoutParams(params);
        super.view.setOrientation(LinearLayout.HORIZONTAL);

        // check box
        android.widget.CheckBox checkBox = new android.widget.CheckBox(ctx);
        checkBox.setChecked(onOff);

        EditText et =new EditText(ctx);
        et.setText(value);


        super.view.addView(checkBox);
        super.view.addView(et);
        super.view.addView(addMoveUpDownButton(ctx));
    }

    @Override
    public JSONObject componentToJson() throws JSONException {
        JSONObject component = new JSONObject();

        component.put("type", "checkbox");
        component.put("title", value);
        component.put("default", onOff);

        return component;
    }
}
