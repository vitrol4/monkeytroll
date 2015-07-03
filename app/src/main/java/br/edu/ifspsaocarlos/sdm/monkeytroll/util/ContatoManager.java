package br.edu.ifspsaocarlos.sdm.monkeytroll.util;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import br.edu.ifspsaocarlos.sdm.monkeytroll.model.Contato;

/**
 * Created by victor on 02/07/15.
 */
public class ContatoManager {
    final Activity activity;

    public ContatoManager(Activity activity) {
        this.activity = activity;
    }

    public void cadastraContato(Contato contato) {
        String url = MkTrollConstants.ENDPOINT
                .concat(MkTrollConstants.CONTATO);
        JSONObject body = new JSONObject(contato.toMap());
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (Integer.decode(response.getString("id")) > 0) {
                        Toast.makeText(activity, "Cadastro realizado com sucesso",
                                Toast.LENGTH_SHORT).show();
                        activity.finish();
                    }
                } catch (Exception e) {
                    Log.e("MkTroll", e.getMessage(), e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "O app te trollou! Tente novamente",
                        Toast.LENGTH_SHORT).show();
            }
        });
        RequestManager.getInstance(activity).add(request);
    }
}
