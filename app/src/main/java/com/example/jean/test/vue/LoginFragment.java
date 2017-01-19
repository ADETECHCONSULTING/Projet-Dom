package com.example.jean.test.vue;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private String TAG = "Login activity...";
    private EditText loginEmail;
    private ArrayList<Utilisateur> lesUtilisateurs;
    private EditText loginMdp;
    private Button btnLogin;
    private TextView txtInscription;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginEmail = (EditText) view.findViewById(R.id.loginEmail);
        loginMdp = (EditText) view.findViewById(R.id.loginPw);
        btnLogin = (Button) view.findViewById(R.id.btnConnexion);
        txtInscription = (TextView) view.findViewById(R.id.txtRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        txtInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InscriptionFragment inscriptionFragment = new InscriptionFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, inscriptionFragment);
                fragmentTransaction.commit();
            }
        });
        return view;
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
                            MainFragment mainFragment = new MainFragment();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragmentContainer, mainFragment);
                            fragmentTransaction.commit();

                        }else{
                            Log.d(TAG, "Failed");
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
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
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
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
