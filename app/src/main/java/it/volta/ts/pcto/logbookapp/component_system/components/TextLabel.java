package it.volta.ts.pcto.logbookapp.component_system.components;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import it.volta.ts.pcto.logbookapp.R;

public class TextLabel extends ComponentBase{
    private String value;

    public TextLabel() {
        super.componentType = ComponentType.TEXTLABEL;
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
    public void componentToView(Context ctx) {
        super.view = new LinearLayout(ctx);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);;
        super.view.setLayoutParams(params);


        super.view.setOrientation(LinearLayout.HORIZONTAL);

        // textview
        EditText editText = new EditText(ctx);
        editText.setText(value);

        super.view.addView(editText);
        super.view.addView(addMoveUpDownButton(ctx));

    }
}
