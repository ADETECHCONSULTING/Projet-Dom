package com.example.jean.test.vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.jean.test.R;
import com.example.jean.test.modele.InscriptionRequest;

import org.json.JSONObject;

public class InscriptionActivity extends AppCompatActivity {
    private EditText email;
    private EditText mdpConfirm;
    private EditText mdp;
    private EditText name;
    private Button btnInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        email = (EditText) findViewById(R.id.registerEmail);
        mdp = (EditText) findViewById(R.id.registerPw);
        mdpConfirm = (EditText) findViewById(R.id.registerPwConfirm);
        name = (EditText) findViewById(R.id.registerName);
        btnInscription = (Button) findViewById(R.id.btnInscription);

        btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validate()){
                    return;
                }else {
                    final String sEmail = email.getText().toString();
                    final String sMdp = mdp.getText().toString();
                    final String sName = name.getText().toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonObject = new JSONObject(response);

                                boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    Intent intent = new Intent(InscriptionActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(InscriptionActivity.this);
                                    builder.setMessage("Login déjà existant")
                                            .setNegativeButton("Réessayer", null)
                                            .create()
                                            .show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    InscriptionRequest inscriptionRequest = new InscriptionRequest(sEmail, sMdp, sName, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(InscriptionActivity.this);
                    queue.add(inscriptionRequest);
                }
            }
        });

    }

    private boolean validate() {
        boolean valid = true;
        String vEmail = email.getText().toString();
        String vMdp = mdp.getText().toString();
        String vMdpConfirm = mdpConfirm.getText().toString();
        String vName = name.getText().toString();
        try{

        }catch (Exception e){

        }

        if(vEmail.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(vEmail).matches()){
            email.setError("Entrez une adresse email valide");
            valid = false;
        }else{
            email.setError(null);
        }

        if(vMdp.isEmpty() || vMdp.length() <= 3 || vMdp.length() >= 16){
            mdp.setError("Mot de passe compris entre 3 et 16 caractères");
            valid = false;
        }else{
            mdp.setError(null);
        }
        if(vName.isEmpty()){
            name.setError("Il vous faut entrer un nom");
            valid = false;
        }else{
            name.setError(null);
        }
        if(!vMdp.equals(vMdpConfirm)){
            mdpConfirm.setError("les mots de passes sont différents");
            valid = false;
        }else{
            mdpConfirm.setError(null);
        }
        return valid;
    }


}
