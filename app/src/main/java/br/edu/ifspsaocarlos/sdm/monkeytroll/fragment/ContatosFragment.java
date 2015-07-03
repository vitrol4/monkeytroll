package br.edu.ifspsaocarlos.sdm.monkeytroll.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.monkeytroll.R;
import br.edu.ifspsaocarlos.sdm.monkeytroll.model.Contato;
import br.edu.ifspsaocarlos.sdm.monkeytroll.util.ContatoSessionManager;
import br.edu.ifspsaocarlos.sdm.monkeytroll.util.MkTrollConstants;
import br.edu.ifspsaocarlos.sdm.monkeytroll.util.RequestManager;

public class ContatosFragment extends ListFragment {

    ListAdapter listAdapter;
    private Context context;
    private ProgressDialog progressDialog;
    private ContatoSessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        sessionManager = new ContatoSessionManager(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Procurando ...");

        carregaContatos();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Contato contato = (Contato) l.getItemAtPosition(position);
        Bundle args = new Bundle();
        args.putString("destinatarioId", contato.getId());

        Fragment fragment = new MensagemFragment();
        fragment.setArguments(args);
        getFragmentManager().beginTransaction()
                .addToBackStack(fragment.getClass().getName())
                .replace(R.id.mainContent, fragment)
                .commit();
    }

    private void carregaContatos() {
        String url = MkTrollConstants.ENDPOINT.concat(MkTrollConstants.CONTATO);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<Contato> contatos = new ArrayList<>();
                            Contato contato;
                            JSONArray array = response.getJSONArray("contatos");
                            for (int i = 0; i < array.length(); i++) {
                                contato = new Contato();
                                JSONObject objContato = array.getJSONObject(i);
                                contato.setId(objContato.getString(Contato.KEY_ID));
                                contato.setNomeCompleto(objContato.getString(Contato.KEY_NOME_COMPLETO));
                                contato.setApelido(objContato.getString(Contato.KEY_APELIDO));
                                if (contato.isMkt() && !contato.getApelido().equals(sessionManager.getUserDetails().get(ContatoSessionManager.KEY_APELIDO))) {
                                    contatos.add(contato);
                                }
                            }

                            listAdapter = new ArrayAdapter<>(context,
                                    android.R.layout.simple_list_item_1, contatos);
                            setListAdapter(listAdapter);

                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                        } catch (Exception e) {
                            Log.e("MkTroll", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "O app te trollou! Tente novamente",
                        Toast.LENGTH_SHORT).show();
            }
        });
        progressDialog.show();
        RequestManager.getInstance(context).add(request);
    }
}
