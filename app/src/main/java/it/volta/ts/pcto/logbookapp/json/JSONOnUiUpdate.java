package it.volta.ts.pcto.logbookapp.json;

import android.util.Log;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import it.volta.ts.pcto.logbookapp.component_system.components.ComponentBase;
import it.volta.ts.pcto.logbookapp.singleton.QrCodeInfo;

public class JSONOnUiUpdate {
    private ArrayList<ComponentBase> list;
    private JSONObject json;

    public JSONOnUiUpdate(ArrayList<ComponentBase> list) {
        this.list = list;
    }

    public void componentUpdate() throws JSONException {
        json = QrCodeInfo.postJSON;

        JSONObject parent = json.optJSONObject("GUIdescription");

        Iterator<String> keys = parent.keys();

        while (keys.hasNext()){
            JSONObject jObj = (JSONObject) parent.get(keys.next());
            parent.remove(jObj.toString());
        }

        int i = 0;
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
