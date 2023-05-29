package eu.elettra.logbookpics.component_system.components;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import eu.elettra.logbookpics.json.JSONOnUiUpdate;

public class CheckBox extends ComponentBase{
    public String value;
    public boolean onOff;
    public CheckBox() {
        super();
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
    public void componentToEditableView(Context ctx, JSONOnUiUpdate jsonOnUiUpdate) {
        super.editView = new LinearLayout(ctx);

        super.editView.setTag(super.compoentTag);

        // View UUID
        super.editView.setId(this.hashCode());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);;
        super.editView.setLayoutParams(params);
        super.editView.setOrientation(LinearLayout.VERTICAL);

        // check box
        android.widget.CheckBox checkBox = new android.widget.CheckBox(ctx);
        checkBox.setChecked(onOff);

        EditText et =new EditText(ctx);
        et.setText(value);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                value = charSequence.toString();

                try {
                    jsonOnUiUpdate.componentUpdate();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }


            @Override
            public void afterTextChanged(Editable editable) {}
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                onOff = b;

                try {
                    jsonOnUiUpdate.componentUpdate();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });



        super.editView.addView(checkBox);
        super.editView.addView(et);
        super.editView.addView(addMoveUpDownButton(ctx));
    }

    @Override
    public void componentToPrettyView(Context ctx) {
        super.prettyView = new LinearLayout(ctx);

        android.widget.CheckBox checkBox = new android.widget.CheckBox(ctx);
        checkBox.setChecked(onOff);
        checkBox.setText(value);
        checkBox.setClickable(false);

        super.prettyView.addView(checkBox);
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
