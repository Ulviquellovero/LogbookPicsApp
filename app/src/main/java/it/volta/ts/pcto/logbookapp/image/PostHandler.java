package it.volta.ts.pcto.logbookapp.image;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import it.volta.ts.pcto.logbookapp.json.JSONTask;
import it.volta.ts.pcto.logbookapp.singleton.QrCodeInfo;

// TODO: This is a temporary position and it might be moved in the future
public class PostHandler {
    private String postUrl;

    private Context ctx;
    private Activity activity;

    private void printToScreen(String message){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public PostHandler(Activity activity, Context ctx, String postUrl) {
        if(QrCodeInfo.jsonTask==null)
            QrCodeInfo.jsonTask = new JSONTask(ctx);
        this.postUrl = postUrl;
        this.activity=activity;
        this.ctx=ctx;

        QrCodeInfo.jsonTask.uploadJSON(postUrl, new JSONTask.JSONCallback() {
            @Override
            public void onCallbackSuccessful() {
                Log.d(this.getClass().getSimpleName().toString(), "JSON posted successfully");
                printToScreen("Your JSON was posted");
            }

            @Override
            public void onCallbackFailed() {
                Log.d(this.getClass().getSimpleName().toString(), "Error while posting JSON");
                printToScreen("An error occurred while POSTing your JSON");
            }
        });
    }
}
