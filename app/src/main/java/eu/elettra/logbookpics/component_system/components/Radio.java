package eu.elettra.logbookpics.component_system.components;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.elettra.logbookpics.json.JSONOnUiUpdate;

public class Radio extends ComponentBase{
    public String[] values;
    public int on;

    public Radio() {
        super();
        values= new String[3];
        super.componentType = ComponentType.RADIO;
        super.compoentTag = "radio";
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public int getOn() {
        return on;
    }

    public void setOn(int on) {
        this.on = on;
    }

    @Override
    public void setFields(JSONObject jObj) {
        on=1;

        JSONArray jArr = jObj.optJSONArray("options");

        for(int i=0; i<jArr.length(); i++){
            if(values[i]==null) break;

            try {
                if(jArr.get(i) == jObj.optString("default")) on = i;
                values[i]= (String) jObj.optJSONArray("options").get(i);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void componentToEditableView(Context ctx, JSONOnUiUpdate jsonOnUiUpdate) {
        super.editView = new LinearLayout(ctx);

        super.editView.setTag(super.compoentTag);

        // View UUID
        super.editView.setId(this.hashCode());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        super.editView.setLayoutParams(params);
        super.editView.setOrientation(LinearLayout.VERTICAL);

        // adding three radio buttons
        RadioGroup radioGroup = new RadioGroup(ctx);
        for(int i=0;i<values.length; i++){
            radioGroup.addView(addRadioButtonEditable(ctx, values[i],i, (this.on==i), jsonOnUiUpdate));
        }

        super.editView.addView(radioGroup);
        super.editView.addView(addMoveUpDownButton(ctx));

    }

    private LinearLayout addRadioButtonEditable(Context ctx, String value, int id, boolean onn, JSONOnUiUpdate jsonOnUiUpdate){
        LinearLayout radioLayout = new LinearLayout(ctx);
        LinearLayout.LayoutParams radioParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        radioLayout.setOrientation(LinearLayout.VERTICAL);

        // adding radio button
        RadioButton rButton = new RadioButton(ctx);
        rButton.setChecked(onn);

        // on check button do...
        rButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                // update (?) maybe
                on = (b==true)? id : on;

                try {
                    jsonOnUiUpdate.componentUpdate();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        EditText et = new EditText(ctx);

        // on edit text do

        radioLayout.setLayoutParams(radioParams);

        radioLayout.addView(rButton);
        radioLayout.addView(et);
        return  radioLayout;
    }

    @Override
    public void componentToPrettyView(Context ctx) {
        super.prettyView = new LinearLayout(ctx);
        super.prettyView.setOrientation(LinearLayout.HORIZONTAL);

        RadioGroup radioGroup = new RadioGroup(ctx);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);

        for(int i=0; i<values.length;i++){
            RadioButton radioButton = new RadioButton(ctx);
            radioButton.setClickable(false);
            radioButton.setChecked(i==on);
            radioButton.setText(values[i]);

            radioGroup.addView(radioButton);
        }

        super.prettyView.addView(radioGroup);
    }

    @Override
    public JSONObject componentToJson() throws JSONException {
        JSONObject component = new JSONObject();

        component.put("type", "radio");
        JSONArray jArr = new JSONArray();

        for(String s : values)
            jArr.put(s);

        component.put("options", jArr);

        component.put("default", values[on]);

        return component;
    }
}
