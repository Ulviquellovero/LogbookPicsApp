package it.volta.ts.pcto.logbookapp.component_system;

import android.util.Log;

import it.volta.ts.pcto.logbookapp.singleton.QrCodeInfo;

public class ComponentComposer {
    
    public ComponentComposer() {
        // For testing purposes only:
        Log.d("ComponentComposer", QrCodeInfo.jsonTask.getRootJSON().optString("title"));
    }
}
