package br.edu.ifspsaocarlos.sdm.monkeytroll.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifspsaocarlos.sdm.monkeytroll.R;
import br.edu.ifspsaocarlos.sdm.monkeytroll.adapter.MensagemListAdapter;
import br.edu.ifspsaocarlos.sdm.monkeytroll.model.Mensagem;
import br.edu.ifspsaocarlos.sdm.monkeytroll.util.ContatoSessionManager;
import br.edu.ifspsaocarlos.sdm.monkeytroll.util.MkTrollConstants;
import br.edu.ifspsaocarlos.sdm.monkeytroll.util.RequestManager;

/**
 * Created by victor on 03/07/15.
 */
public class MensagemFragment extends Fragment {

    private ContatoSessionManager sessionManager;
    private Context context;
    private ListView lstMensagens;
    private ImageButton btnEnviar;
    private ProgressDialog progressDialog;
    private String remetenteId, destinatarioId, assunto;
    private EditText edtCorpo;
    private List<Mensagem> mensagens;
    private MensagemListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_mensagem, null);
        lstMensagens = (ListView) view.findViewById(R.id.lstMensagens);
        btnEnviar = (ImageButton) view.findViewById(R.id.btnEnviar);
        edtCorpo = (EditText) view.findViewById(R.id.edtCorpo);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Carreagando ...");

        sessionManager = new ContatoSessionManager(context);

        remetenteId = sessionManager.getUserDetails().get(ContatoSessionManager.KEY_ID);
        destinatarioId = getArguments().getString("destinatarioId");
        assunto = MkTrollConstants.SIGLA.concat("R:").concat(remetenteId).concat("D:").concat(destinatarioId);

        carregaMensagens("0", remetenteId, destinatarioId);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarMensagem(remetenteId, destinatarioId, assunto, edtCorpo.getText().toString());
                edtCorpo.setText("");
                carregaMensagens("0", remetenteId, destinatarioId);
            }
        });

        return view;
    }

    private void carregaMensagens(final String mensagemId, final String remetenteId, final String destinatarioId) {
        mensagens = new ArrayList<Mensagem>() {
            @Override
            public synchronized boolean add(Mensagem object) {
                return super.add(object);
            }
        };

        String url = MkTrollConstants.ENDPOINT.concat(MkTrollConstants.MENSAGEM).concat("/")
                .concat(mensagemId).concat("/")
                .concat(remetenteId).concat("/")
                .concat(destinatarioId);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Mensagem mensagem;
                            JSONArray array = response.getJSONArray("mensagens");
                            for (int i = 0; i < array.length(); i++) {
                                mensagem = new Mensagem();
                                JSONObject objMensagem = array.getJSONObject(i);
                                mensagem.setId(objMensagem.getString("id"));
                                mensagem.setAssunto(objMensagem.getString("assunto"));
                                mensagem.setCorpo(objMensagem.getString("corpo"));
                                mensagem.setOrigem(objMensagem.getJSONObject("origem").getString("id"));
                                mensagem.setDestino(objMensagem.getJSONObject("destino").getString("id"));
                                mensagens.add(mensagem);
                            }

                            Collections.sort(mensagens, new Comparator<Mensagem>() {
                                @Override
                                public int compare(Mensagem mensagem, Mensagem t1) {
                                    int o2 = Integer.decode(mensagem.getId());
                                    int o1 = Integer.decode(t1.getId());
                                    return (o1 > o2 ? -1 : (o1 == o2 ? 0 : 1));
                                }
                            });

                            adapter = new MensagemListAdapter(getActivity(), mensagens);
                            lstMensagens.setAdapter(adapter);

                            carregaInverso(mensagemId, destinatarioId, remetenteId);

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

    private void carregaInverso(String mensagemId, String remetenteId, String destinatarioId) {
        String url = MkTrollConstants.ENDPOINT.concat(MkTrollConstants.MENSAGEM).concat("/")
                .concat(mensagemId).concat("/")
                .concat(remetenteId).concat("/")
                .concat(destinatarioId);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Mensagem mensagem;
                            JSONArray array = response.getJSONArray("mensagens");
                            for (int i = 0; i < array.length(); i++) {
                                mensagem = new Mensagem();
                                JSONObject objMensagem = array.getJSONObject(i);
                                mensagem.setId(objMensagem.getString("id"));
                                mensagem.setAssunto(objMensagem.getString("assunto"));
                                mensagem.setCorpo(objMensagem.getString("corpo"));
                                mensagem.setOrigem(objMensagem.getJSONObject("origem").getString("id"));
                                mensagem.setDestino(objMensagem.getJSONObject("destino").getString("id"));
                                mensagens.add(mensagem);
                            }

                            Collections.sort(mensagens, new Comparator<Mensagem>() {
                                @Override
                                public int compare(Mensagem mensagem, Mensagem t1) {
                                    int o2 = Integer.decode(mensagem.getId());
                                    int o1 = Integer.decode(t1.getId());
                                    return (o1 > o2 ? -1 : (o1 == o2 ? 0 : 1));
                                }
                            });
                            adapter = new MensagemListAdapter(getActivity(), mensagens);
                            lstMensagens.setAdapter(adapter);

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

    private void enviarMensagem(String remetenteId, String destinatarioId, String assunto, String corpo) {
        String url = MkTrollConstants.ENDPOINT.concat(MkTrollConstants.MENSAGEM);
        Map<String, String> map = new HashMap<>();
        map.put("origem_id", remetenteId);
        map.put("destino_id", destinatarioId);
        map.put("assunto", assunto);
        map.put("corpo", corpo);
        Log.d("MkTroll", map.toString());
        JSONObject body = new JSONObject(map);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Mensagem enviada",
                                Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "O app te trollou! Tente novamente",
                        Toast.LENGTH_SHORT).show();
            }
        });
        RequestManager.getInstance(context).add(request);
    }
}
