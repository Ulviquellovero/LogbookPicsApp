package it.volta.ts.pcto.logbookapp.component_system.components;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

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
    public void componentToEditableView(Context ctx, JSONOnUiUpdate jsonOnUiUpdate) {
        super.editView = new LinearLayout(ctx);
        super.editView.setTag(super.compoentTag);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);;
        super.editView.setLayoutParams(params);


        super.editView.setOrientation(LinearLayout.VERTICAL);

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


        super.editView.addView(editText);
        super.editView.addView(addMoveUpDownButton(ctx));

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
