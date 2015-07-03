package br.edu.ifspsaocarlos.sdm.monkeytroll;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.monkeytroll.model.Contato;
import br.edu.ifspsaocarlos.sdm.monkeytroll.util.ContatoSessionManager;
import br.edu.ifspsaocarlos.sdm.monkeytroll.util.MkTrollConstants;
import br.edu.ifspsaocarlos.sdm.monkeytroll.util.RequestManager;

public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText edtLogin;
    private Button btnEntrar, btnSolicitarCadastro;
    private ContatoSessionManager sessionManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Procurando ...");
        sessionManager = new ContatoSessionManager(getBaseContext());

        if (sessionManager.getUserDetails().get(ContatoSessionManager.KEY_NOME_COMPLETO) != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        edtLogin = (EditText) findViewById(R.id.edtLogin);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnSolicitarCadastro = (Button) findViewById(R.id.btnSolicitarCadastro);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String apelido = edtLogin.getText().toString();
                if (apelido.contains(MkTrollConstants.SIGLA)) {
                    Toast.makeText(LoginActivity.this, "Você não pode entrar", Toast.LENGTH_SHORT).show();
                } else {
                    if (apelido.trim().length() > 0) {
                        final List<Contato> contatos = new ArrayList<>();
                        String url = MkTrollConstants.ENDPOINT
                                .concat(MkTrollConstants.CONTATO);
                        JsonObjectRequest request = new JsonObjectRequest(
                                Request.Method.GET,
                                url,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            Contato contato;
                                            JSONArray array = response.getJSONArray("contatos");
                                            for (int i = 0; i < array.length(); i++) {
                                                contato = new Contato();
                                                JSONObject objContato = array.getJSONObject(i);
                                                contato.setId(objContato.getString(Contato.KEY_ID));
                                                contato.setNomeCompleto(objContato.getString(Contato.KEY_NOME_COMPLETO));
                                                contato.setApelido(objContato.getString(Contato.KEY_APELIDO));
                                                if (contato.isMkt()) {
                                                    contatos.add(contato);
                                                }
                                            }

                                            for (Contato c : contatos) {
                                                if (apelido.equals(c.getApelido())) {
                                                    if (progressDialog.isShowing()) {
                                                        progressDialog.dismiss();
                                                    }
                                                    sessionManager.createUserLoginSession(apelido, c.getNomeCompleto());
                                                    Bundle args = new Bundle();
                                                    args.putSerializable("contatos", (Serializable) contatos);
                                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                                    i.putExtra("args", args);
                                                    startActivity(i);
                                                    finish();
                                                } else {
                                                    if (progressDialog.isShowing()) {
                                                        progressDialog.dismiss();
                                                    }
                                                    Toast.makeText(LoginActivity.this, "Você não está cadastrado",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } catch (Exception e) {
                                            Log.e("MkTroll", e.getMessage(), e);
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, "O app te trollou! Tente novamente",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        progressDialog.show();
                        RequestManager.getInstance(LoginActivity.this).add(request);
                    } else {
                        Toast.makeText(LoginActivity.this, "Preencha seu apelido corretamente", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnSolicitarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(i);
            }
        });
    }

}

