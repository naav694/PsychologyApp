package mx.rokegcode.psychologyapp.model.api;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class VolleyClient {

    private static VolleyClient mInstance;

    public static synchronized VolleyClient getInstance() {
        if (mInstance == null) {
            mInstance = new VolleyClient();
        }
        return mInstance;
    }

    public RequestFuture<JSONObject> createRequest(Context context, String url, int tipo, JSONObject json) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(tipo, url, json, future, future);
        requestQueue.add(request);
        requestQueue.addRequestFinishedListener(request1 -> requestQueue.getCache().clear());
        return future;
    }
}
