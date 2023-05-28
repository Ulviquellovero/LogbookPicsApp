package eu.elettra.logbookpics.json;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import eu.elettra.logbookpics.component_system.components.ComponentBase;
import eu.elettra.logbookpics.singleton.QrCodeInfo;

public class JSONOnUiUpdate {
    private ArrayList<ComponentBase> list;
    private JSONObject json;

    public JSONOnUiUpdate(ArrayList<ComponentBase> list) {
        this.list = list;
    }

    public void componentUpdate() throws JSONException {
        json = QrCodeInfo.postJSON;

        JSONObject parent = json.optJSONObject("GUIdescription");

        Iterator<String> iterator = parent.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            iterator.remove();
        }

        int i = 0;
        for(ComponentBase componentBase : list){
            i++;
            parent.put("component_"+Integer.toString(i), componentBase.componentToJson());
        }

        QrCodeInfo.postJSON = json;
        Log.d("LogBookJsonDebug",QrCodeInfo.postJSON.optJSONObject("GUIdescription").toString());

    }

    public void componentUpdate(ArrayList<ComponentBase> newList) throws JSONException {
        this.list = newList;
        json = QrCodeInfo.postJSON;

        JSONObject parent = json.optJSONObject("GUIdescription");


        Iterator<String> iterator = parent.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            iterator.remove();
        }

        int i = 0;

        Log.d("LogBookJsonDebug", QrCodeInfo.postJSON.optString("GUIdescription").toString());

        for(ComponentBase componentBase : list){
            i++;
            parent.put("component_"+Integer.toString(i), componentBase.componentToJson());
        }

        QrCodeInfo.postJSON = json;
        Log.d("LogBookJsonDebug",QrCodeInfo.postJSON.optJSONObject("GUIdescription").toString());

    }

    public void titleUpdate(String title) throws JSONException{
        json = QrCodeInfo.postJSON;

        json.put("title", title);

        QrCodeInfo.postJSON = json;
        Log.d("LogBookJsonDebug",QrCodeInfo.postJSON.toString());
    }
    public void infoUpdate(String info) throws JSONException{
        json = QrCodeInfo.postJSON;

        json.put("info", info);

        QrCodeInfo.postJSON = json;
        Log.d("LogBookJsonDebug",QrCodeInfo.postJSON.toString());
    }

    public ArrayList<ComponentBase> getList() {
        return list;
    }

    public void setList(ArrayList<ComponentBase> list) {
        this.list = list;
    }
}
