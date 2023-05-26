package it.volta.ts.pcto.logbookapp.component_system;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import it.volta.ts.pcto.logbookapp.R;
import it.volta.ts.pcto.logbookapp.component_system.components.BullettedList;
import it.volta.ts.pcto.logbookapp.component_system.components.CheckBox;
import it.volta.ts.pcto.logbookapp.component_system.components.ComponentBase;
import it.volta.ts.pcto.logbookapp.component_system.components.ComponentType;
import it.volta.ts.pcto.logbookapp.component_system.components.TextLabel;
import it.volta.ts.pcto.logbookapp.json.JSONOnUiUpdate;
import it.volta.ts.pcto.logbookapp.singleton.QrCodeInfo;

public class ComponentComposer {

    private JSONObject newJson;
    private JSONObject root;
    private JSONOnUiUpdate jsonOnUiUpdate;
    private HashMap<String, ComponentType> compSelector;
    private static ArrayList<ComponentBase> list;

    public ComponentComposer(Activity act) {
        root = QrCodeInfo.jsonTask.getRootJSON();
        QrCodeInfo.postJSON = root;
        newJson = QrCodeInfo.postJSON;

        // arraylist init
        list = new ArrayList<ComponentBase>();

        // hash map init
        compSelector = new HashMap<>();
        compSelector.put("text", ComponentType.TEXTLABEL);
        compSelector.put("dropdown", ComponentType.DROPDOWN);
        compSelector.put("bullettedlist", ComponentType.BULLETTEDLIST);
        compSelector.put("checkbox", ComponentType.CHECKBOX);
        compSelector.put("", ComponentType.UNKNOWN);

        specialComponentsListeners(act);
    }

    private void specialComponentsListeners(Activity act){
        EditText titleRef = (EditText) act.findViewById(R.id.new_title);
        EditText infoRef = (EditText) act.findViewById(R.id.new_info);


        titleRef.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String titleValue = charSequence.toString();
                try {
                    jsonOnUiUpdate.titleUpdate(titleValue);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        infoRef.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String infoValue = charSequence.toString();
                try {
                    jsonOnUiUpdate.infoUpdate(infoValue);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

    }

    /*
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
    */
    public void readComponentsFromJson(Context ctx) throws JSONException {
        JSONObject componentList = root.optJSONObject("GUIdescription");

        Iterator<String> keys = componentList.keys();

        while (keys.hasNext()){
            JSONObject jObj = (JSONObject) componentList.get(keys.next());

            String value = jObj.optString("type");
            ComponentBase c = resolveType(value);

            if(c==null) continue;

            c.setFields(jObj);
            //c.componentToView(ctx); // TODO: JSON on ui update should be passed here
            list.add(c);
        }

        // adding components to json on ui update
        jsonOnUiUpdate = new JSONOnUiUpdate(list);
    }

    public void addViewsToComponents(Context ctx){
        for (ComponentBase c : list){
            c.componentToView(ctx, jsonOnUiUpdate);
        }
    }

    public void addComponentsToExistingScrollview(LinearLayout view){
        for (ComponentBase e : list){
            view.addView(e.getView());
        }
    }

    public void removeAllComponentToExistingScrollView(LinearLayout view){
        view.removeAllViews();
    }

    private ComponentBase resolveType(String type){
        if(!compSelector.containsKey(type))
            return null;

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


    public ArrayList<ComponentBase> getList() {
        return list;
    }

    public void setList(ArrayList<ComponentBase> list) {
        this.list = list;
    }
}
