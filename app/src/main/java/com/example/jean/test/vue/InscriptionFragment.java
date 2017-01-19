package com.example.jean.test.vue;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Patterns;
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
import com.example.jean.test.modele.InscriptionRequest;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class InscriptionFragment extends Fragment {
    private EditText email;
    private EditText mdpConfirm;
    private EditText mdp;
    private EditText name;
    private Button btnInscription;
    private TextView txtConnexion;

    public InscriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inscription, container, false);
        email = (EditText) view.findViewById(R.id.registerEmail);
        mdp = (EditText) view.findViewById(R.id.registerPw);
        mdpConfirm = (EditText) view.findViewById(R.id.registerPwConfirm);
        name = (EditText) view.findViewById(R.id.registerName);
        btnInscription = (Button) view.findViewById(R.id.btnInscription);
        txtConnexion = (TextView) view.findViewById(R.id.labelDejaInscrit);
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
                                    MainFragment mainFragment = new MainFragment();
                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragmentContainer, mainFragment);
                                    fragmentTransaction.commit();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
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
                    RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    queue.add(inscriptionRequest);
                }
            }
        });

        txtConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment loginFragment = new LoginFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, loginFragment);
                fragmentTransaction.commit();

            }
        });
        return view;
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
