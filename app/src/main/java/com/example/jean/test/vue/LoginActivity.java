package com.example.jean.test.vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.jean.test.R;
import com.example.jean.test.modele.LoginRequest;
import com.example.jean.test.modele.Utilisateur;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Un ecran d'identification (via email/mdp) -> identification par réseau social à voir plus tard
 */
public class LoginActivity extends AppCompatActivity {
    private String TAG = "Login activity...";
    private EditText loginEmail;
    private ArrayList<Utilisateur> lesUtilisateurs;
    private EditText loginMdp;
    private Button btnLogin;
    private TextView txtInscription;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginMdp = (EditText) findViewById(R.id.loginPw);
        btnLogin = (Button) findViewById(R.id.btnConnexion);
        txtInscription = (TextView) findViewById(R.id.txtRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        txtInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, InscriptionActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Methode qui se chargera de la connexion
     */
    private void login() {
        Log.d(TAG, "btnLogin");
        if(!validate()){
            return;
        }else{
            String email = loginEmail.getText().toString();
            String mdp = loginMdp.getText().toString();
            Log.d(TAG, email+" "+mdp);
            Response.Listener<String> responseListener = new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    try {
                        String[] login = response.split("%");
                        JSONObject jsonObject = new JSONObject(login[1]);
                        boolean success = jsonObject.getBoolean("success");
                        if(success){
                            String email = jsonObject.getString("email");
                            String name = jsonObject.getString("name");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("name", name);
                            startActivity(intent);

                        }else{
                            Log.d(TAG, "Failed");
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("Identifiant ou mot de passe incorrect")
                                    .setNegativeButton("Réessayer", null)
                                    .create()
                                    .show();
                        }
                        btnLogin.setEnabled(true);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            LoginRequest loginRequest = new LoginRequest(email, mdp, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        }
    }




    /**
     * Methode qui teste la validité du format des identifiants de connexion
     * @return
     */
    private boolean validate() {
        boolean valid = true;
        String email = loginEmail.getText().toString();
        String mdp = loginMdp.getText().toString();

        if(email.isEmpty() /*|| Patterns.EMAIL_ADDRESS.matcher(email).matches()*/){
            loginEmail.setError("Entrez une adresse email valide");
            valid = false;
        }else{
            loginEmail.setError(null);
        }

        if(mdp.isEmpty() || mdp.length() <= 3 || mdp.length() >= 16){
            loginMdp.setError("Mot de passe compris entre 3 et 16 caractères");
            valid = false;
        }else{
            loginMdp.setError(null);
        }
        return valid;
    }

}

