package br.edu.ifspsaocarlos.sdm.monkeytroll.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by victor on 02/07/15.
 */
public class RequestManager {

    private static RequestManager instance;
    private final Context context;
    private RequestQueue queue;

    private RequestManager(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(this.context);
    }

    public static RequestManager getInstance(Context context) {
        if (instance == null) {
            instance = new RequestManager(context);
        }
        return instance;
    }

    public synchronized void add(Request request) {
        queue.add(request);
    }

    public RequestQueue getQueue() {
        return queue;
    }

}
