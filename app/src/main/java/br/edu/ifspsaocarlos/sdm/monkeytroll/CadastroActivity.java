package br.edu.ifspsaocarlos.sdm.monkeytroll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifspsaocarlos.sdm.monkeytroll.model.Contato;
import br.edu.ifspsaocarlos.sdm.monkeytroll.util.ContatoManager;
import br.edu.ifspsaocarlos.sdm.monkeytroll.util.MkTrollConstants;

public class CadastroActivity extends AppCompatActivity {

    private Button btnCadastrar;
    private EditText edtNome, edtApelido;
    private ContatoManager contatoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        contatoManager = new ContatoManager(this);
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtApelido = (EditText) findViewById(R.id.edtApelido);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contato contato = new Contato(null,
                        null,
                        MkTrollConstants.SIGLA + " - " + edtNome.getText().toString(),
                        edtApelido.getText().toString());
                if (contato.limitNomeApelido()) {
                    Toast.makeText(CadastroActivity.this, "Excedeu o limite de caracteres no nome", Toast.LENGTH_SHORT).show();
                    return;
                }
                contatoManager.cadastraContato(contato);
            }
        });
    }

}
