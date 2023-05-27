package it.volta.ts.pcto.logbookapp.json;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import it.volta.ts.pcto.logbookapp.singleton.QrCodeInfo;

public class JSONTask
{
    private JSONObject root;
    private RequestQueue volleyQueue;
    private Context ctx;

    // callback
    public interface JSONCallback {
        void onCallbackSuccessful();
        void onCallbackFailed();
    }


    public JSONTask(Context ctx) {
        this.ctx=ctx;
        this.volleyQueue = Volley.newRequestQueue(this.ctx);
    }

    // method is now posting the new json
    public void uploadJSON(String url, final JSONCallback callback){
        // provided a json has been loaded in the first place
        if (QrCodeInfo.postJSON ==null){
            Log.e(this.getClass().getSimpleName().toString(), "Root JSON object is null");
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, QrCodeInfo.postJSON,
                    res ->{
                        // callback
                        Log.d(this.getClass().getSimpleName().toString(), "JSON posted successfully");
                        callback.onCallbackSuccessful();
                    },
                    err -> {
                        Log.e(this.getClass().getSimpleName().toString(), err.toString());
                        callback.onCallbackFailed();
                    }
                );
        volleyQueue.add(jsonObjectRequest);
    }

    public void loadJSON(String url, final JSONCallback callback) {
        // since the response we get from the api is in JSON, we
        // need to use `JsonObjectRequest` for parsing the
        // request response
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                // we are using GET HTTP request method
                Request.Method.GET,
                // url we want to send the HTTP request to
                url,
                // this parameter is used to send a JSON object to the
                // server, since this is not required in our case,
                // we are keeping it `null`
                null,

                // lambda function for handling the case
                // when the HTTP request succeeds
                (Response.Listener<JSONObject>) response -> {
                    root = response;

                    // initiate callback
                    callback.onCallbackSuccessful();
                },

                // lambda function for handling the case
                // when the HTTP request fails
                (Response.ErrorListener) error -> {
                    // initiate negative callback
                    callback.onCallbackFailed();

                    Log.e("JSONTask", error.toString());

                }
        );

        // add the json request object created above
        // to the Volley request queue
        volleyQueue.add(jsonObjectRequest);
    }

    public JSONObject getRootJSON(){
        return root;
    }

    public String getRootJSONString(){
        return (root!=null) ? root.toString() : "undefined";
    }

}
