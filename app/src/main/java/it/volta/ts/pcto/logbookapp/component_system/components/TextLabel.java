package it.volta.ts.pcto.logbookapp.component_system.components;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import it.volta.ts.pcto.logbookapp.R;
import it.volta.ts.pcto.logbookapp.json.JSONOnUiUpdate;

public class TextLabel extends ComponentBase{
    private String value;

    public TextLabel() {
        super.componentType = ComponentType.TEXTLABEL;
        super.compoentTag = "textlabel";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void setFields(JSONObject jObj) {
        this.value = jObj.optString("title");
    }

    @Override
    public void componentToView(Context ctx, JSONOnUiUpdate jsonOnUiUpdate) {
        super.view = new LinearLayout(ctx);
        super.view.setTag(super.compoentTag);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);;
        super.view.setLayoutParams(params);


        super.view.setOrientation(LinearLayout.HORIZONTAL);

        // textview
        EditText editText = new EditText(ctx);
        editText.setText(value);

        editText.addTextChangedListener(new TextWatcher() {
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

        super.view.addView(editText);
        super.view.addView(addMoveUpDownButton(ctx));

    }

    @Override
    public JSONObject componentToJson() throws JSONException {
        JSONObject component = new JSONObject();
        component.put("type", "text");
        component.put("title", value);
        component.put("default", "Insert text here");

        return component;
    }

}
