package it.volta.ts.pcto.logbookapp.component_system.components;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import it.volta.ts.pcto.logbookapp.R;

public class BullettedList extends ComponentBase{
    private String value;
    private String prefix;

    public BullettedList() {
        super.componentType = ComponentType.BULLETTEDLIST;
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
    public void componentToView(Context ctx) {
        super.view = new LinearLayout(ctx);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) super.view.getLayoutParams();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        super.view.setLayoutParams(params);
        super.view.setOrientation(LinearLayout.HORIZONTAL);

        // textview
        TextView tv = new TextView(ctx);
        tv.setText(prefix+value);

        // adding the menu
        ImageView menu = new ImageView(ctx);
        menu.setImageResource(R.drawable.baseline_menu_24);

        super.view.addView(tv);
        super.view.addView(menu);
    }
}
