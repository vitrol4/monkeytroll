package br.edu.ifspsaocarlos.sdm.monkeytroll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifspsaocarlos.sdm.monkeytroll.util.UserSessionManager;

public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText edtLogin, edtPassword;
    private Button btnEntrar;
    private UserSessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        sessionManager = new UserSessionManager(getBaseContext());

//        if (sessionManager.getUserDetails().get(UserSessionManager.KEY_TOKEN) != null) {
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            finish();
//        }

        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edtLogin.getText().toString();
                String password = edtPassword.getText().toString();
                if (email.trim().length() > 0 && password.trim().length() > 0) {
//                    new RequestToken().execute(email, password);
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Preencha os campos corretamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

