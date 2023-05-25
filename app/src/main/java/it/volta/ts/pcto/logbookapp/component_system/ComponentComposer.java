package it.volta.ts.pcto.logbookapp.component_system;

import android.content.Context;
import android.widget.ScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import it.volta.ts.pcto.logbookapp.component_system.components.BullettedList;
import it.volta.ts.pcto.logbookapp.component_system.components.CheckBox;
import it.volta.ts.pcto.logbookapp.component_system.components.ComponentBase;
import it.volta.ts.pcto.logbookapp.component_system.components.ComponentType;
import it.volta.ts.pcto.logbookapp.component_system.components.TextLabel;
import it.volta.ts.pcto.logbookapp.singleton.QrCodeInfo;

public class ComponentComposer {

    private JSONObject newJson;
    private JSONObject root;
    private HashMap<String, ComponentType> compSelector;
    private ArrayList<ComponentBase> list;

    public ComponentComposer() {
        newJson = QrCodeInfo.postJSON;
        root = QrCodeInfo.jsonTask.getRootJSON();

        // hash map init
        compSelector = new HashMap<>();
        compSelector.put("text", ComponentType.TEXTLABEL);
        compSelector.put("dropdown", ComponentType.DROPDOWN);
        compSelector.put("bullettedlist", ComponentType.BULLETTEDLIST);
        compSelector.put("checkbox", ComponentType.CHECKBOX);
        compSelector.put("", ComponentType.UNKNOWN);
    }

    public void readComponentsFromJson(Context ctx) throws JSONException {
        JSONArray componentList = root.getJSONArray("GUIdescription");

        for(int i=0; i<componentList.length();i++){
            JSONObject jObj = componentList.getJSONObject(i);
            String value = jObj.optString("type");
            ComponentBase c = resolveType(value);
            c.setFields(jObj);
            c.componentToView(ctx);
            this.list.add(c);
        }
    }

    public void addComponentsToExistingScrollview(ScrollView view){
        for (int i = 0; i< list.size(); i++){
            view.addView(list.get(i).getView());
        }
    }

    private ComponentBase resolveType(String type){
        switch (compSelector.get(type)){
            case CHECKBOX:
                return new CheckBox();
            case BULLETTEDLIST:
                return new BullettedList();
            default:
                break;
        }
        return new TextLabel();
    }


}
