package eu.elettra.logbookpics.component_system.components;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import eu.elettra.logbookpics.json.JSONOnUiUpdate;

public class BullettedList extends ComponentBase{
    private String value;
    private String prefix;

    public BullettedList() {
        super();
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
    public void componentToEditableView(Context ctx, JSONOnUiUpdate jsonOnUiUpdate) {
        super.editView = new LinearLayout(ctx);
        super.editView.setTag(super.compoentTag);

        // View UUID
        super.editView.setId(this.hashCode());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);;
        super.editView.setLayoutParams(params);
        super.editView.setOrientation(LinearLayout.VERTICAL);

        // container
        LinearLayout container = new LinearLayout(ctx);
        container.setLayoutParams(params);
        container.setOrientation(LinearLayout.HORIZONTAL);

        TextView tv = new TextView(ctx);
        tv.setText(prefix);

        EditText editText = new EditText(ctx);
        editText.setText(value);


        container.addView(tv);
        container.addView(editText);
        //

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

        super.editView.addView(container);
        super.editView.addView(addMoveUpDownButton(ctx));
    }

    @Override
    public void componentToPrettyView(Context ctx) {
        super.prettyView = new LinearLayout(ctx);

        TextView tv = new TextView(ctx);
        tv.setText(" · "+value);

        super.prettyView.addView(tv);
    }

    @Override
    public JSONObject componentToJson() throws JSONException {
        JSONObject component = new JSONObject();

        component.put("type", "list");
        component.put("title", value);

        return component;
    }
}
