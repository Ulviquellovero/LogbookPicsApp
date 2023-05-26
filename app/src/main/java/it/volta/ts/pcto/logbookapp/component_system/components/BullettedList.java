package it.volta.ts.pcto.logbookapp.component_system.components;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import it.volta.ts.pcto.logbookapp.R;
import it.volta.ts.pcto.logbookapp.json.JSONOnUiUpdate;

public class BullettedList extends ComponentBase{
    private String value;
    private String prefix;

    public BullettedList() {
        super.componentType = ComponentType.BULLETTEDLIST;
        super.compoentTag = "bulletedlist";
        this.prefix = " · ";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void setFields(JSONObject jObj) {
        this.value = jObj.optString("title", "Field 'title' not found");
    }

    @Override
    public void componentToView(Context ctx, JSONOnUiUpdate jsonOnUiUpdate) {
        super.view = new LinearLayout(ctx);
        super.view.setTag(super.compoentTag);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);;
        super.view.setLayoutParams(params);
        super.view.setOrientation(LinearLayout.HORIZONTAL);

        // textview
        TextView tv = new TextView(ctx);
        tv.setText(prefix);

        EditText editText = new EditText(ctx);
        editText.setText(value);

        super.view.addView(tv);
        super.view.addView(editText);
        super.view.addView(addMoveUpDownButton(ctx));
    }

    @Override
    public JSONObject componentToJson() throws JSONException {
        JSONObject component = new JSONObject();

        component.put("type", "list");
        component.put("title", value);

        return component;
    }
}
