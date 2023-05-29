package eu.elettra.logbookpics.component_system;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import eu.elettra.logbookpics.R;
import eu.elettra.logbookpics.component_system.components.BullettedList;
import eu.elettra.logbookpics.component_system.components.CheckBox;
import eu.elettra.logbookpics.component_system.components.ComponentBase;
import eu.elettra.logbookpics.component_system.components.ComponentType;
import eu.elettra.logbookpics.component_system.components.TextLabel;
import eu.elettra.logbookpics.json.JSONOnUiUpdate;
import eu.elettra.logbookpics.singleton.QrCodeInfo;

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

    public void addView(Context ctx, String type){
        ComponentBase nComponentBase = resolveType(type);
        //addViewsToComponents(ctx);
        list.add(nComponentBase);
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


    public void readComponentsFromJson(Context ctx) throws JSONException {
        JSONObject componentList = root.optJSONObject("GUIdescription");

        Iterator<String> keys = componentList.keys();

        while (keys.hasNext()){
            JSONObject jObj = (JSONObject) componentList.get(keys.next());

            String value = jObj.optString("type");
            ComponentBase c = resolveType(value);

            Log.d("LogBookDebug", jObj.optString("type"));

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
            c.componentToEditableView(ctx, jsonOnUiUpdate);
        }
    }

    public void addComponentsToExistingScrollview(LinearLayout view, Context ctx){
        for (ComponentBase e : list){
            if(e == null || ctx==null) continue;
            if(e.getEditView() == null){
                addViewsToComponents(ctx);

            }
            Log.d("LogBookDebug", e.toString());
            view.addView(e.getEditView());
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

    public void removeElementByView(LinearLayout rootView, LinearLayout view, Context ctx){
        for (Iterator<ComponentBase> iterator = list.iterator(); iterator.hasNext();) {
            ComponentBase c = iterator.next();
            Log.d("LogBookTest", Integer.toString(c.getUuid()) + " "+ Integer.toString(view.getId()));
            if(c.getUuid()==view.getId()){
                // remove from list
                iterator.remove();
                Log.d("LogBookDebug", list.toString());
                // remove from view
                removeAllComponentToExistingScrollView(rootView);
                addComponentsToExistingScrollview(rootView, ctx);

                // call on componentupdate json
                try {
                    jsonOnUiUpdate.componentUpdate(list);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void resetAllChanges(){
        // TODO
    }
    public void moveElement(int moveDirection, LinearLayout rootView, LinearLayout view, Context ctx){
        for (Iterator<ComponentBase> iterator = list.iterator(); iterator.hasNext();) {
            ComponentBase c = iterator.next();
            if(c.getUuid()==view.getId()){

                if(list.indexOf(c)==-1 || list.indexOf(c)+moveDirection==-1) continue;

                Log.d("LogBookDebug", Integer.toString(list.indexOf(c)));
                Log.d("LogBookDebug", Integer.toString(list.indexOf(c)+moveDirection));
                try{
                    Collections.swap(list, list.indexOf(c), list.indexOf(c)+moveDirection);
                } catch (IndexOutOfBoundsException i){
                    Log.e("LogBookError", i.toString());
                }

                // redraw view
                removeAllComponentToExistingScrollView(rootView);
                addComponentsToExistingScrollview(rootView, ctx);

                // call on componentupdate json
                try {
                    jsonOnUiUpdate.componentUpdate(list);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                return;
            }
        }
    }

    // Static (at least temporarily
    public static void addPrettyViews(Context ctx){
        for (ComponentBase c : list)
            c.componentToPrettyView(ctx);
    }

    public static void addPrettyViewsToExsitingLinearLayout(LinearLayout view, Context ctx){
        for (ComponentBase e : list){
            if(e == null || ctx==null) continue;
            if(e.getPrettyView() == null){
                addPrettyViews(ctx);

            }
            Log.d("LogBookDebug", e.toString());
            view.addView(e.getPrettyView());
        }
    }

    // Get & Set
    public ArrayList<ComponentBase> getList() {
        return list;
    }

    public void setList(ArrayList<ComponentBase> list) {
        this.list = list;
    }
}
