package com.example.jean.test.vue;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
    private ImageView img;
    private Bitmap background;
    private Bitmap imgNav;
    private BitmapDrawable backDrawable;
    private FrameLayout frameLayout;
    private int idBackground = R.drawable.background;
    private int idImage;
    private int idLayout = R.id.loginLayout;
    private FrameLayout cLayout;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        cLayout = (FrameLayout) view.findViewById(R.id.loginLayout);
        setBackground(cLayout, idBackground);
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

        // Teste si l'email est vide ou si le texte entré n'a pas le format EMAIL
        if(email.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginEmail.setError("Entrez une adresse email valide");
            valid = false;
        }else{
            loginEmail.setError(null);
        }

        // teste si le mot de passe est vide ou si il n'est pas conforme aux limites posées
        if(mdp.isEmpty() || mdp.length() <= 3 || mdp.length() >= 16){
            loginMdp.setError("Mot de passe compris entre 3 et 16 caractères");
            valid = false;
        }else{
            loginMdp.setError(null);
        }
        return valid;
    }

    public void onDestroyView(){
        super.onDestroyView();

    }

    /**
     * Applique une image d'arrière plan
     * @param id
     */
    public void loadBackground(int id){
        background = BitmapFactory.decodeStream(getResources().openRawResource(id));
        backDrawable = new BitmapDrawable(background);
        frameLayout.setBackgroundDrawable(backDrawable);
    }

    /**
     * Retire l'image d'arriere plan pour liberer de l'espace memoire
     */
    public void unloadBackground(){
        if(frameLayout != null){
            frameLayout.setBackgroundDrawable(null);
        }
        if(backDrawable != null){
            background.recycle();
        }
        backDrawable = null;
    }

    /**
     * Charge une image
     * @param id
     */
    public void loadImage(int id){
        imgNav = BitmapFactory.decodeStream(getResources().openRawResource(id));
        img.setImageBitmap(imgNav);
    }

    /**
     * retire une image de la vue
     */
    public void unloadImage(){
        if(img != null){
            img.setImageBitmap(null);
        }
        if(imgNav != null){
            imgNav.recycle();
        }
        imgNav = null;
    }


    /**
     * Appelle la methode load et unload de l'arriere plan
     * @param c
     * @param sourceId
     */
    public void setBackground(FrameLayout c, int sourceId){
        unloadBackground();
        frameLayout = c;
        loadBackground(sourceId);
    }

    /**
     * Appelle la methode load et unload de l'image
     * @param i
     * @param sourceId
     */
    public void setImg(ImageView i, int sourceId){
        unloadImage();
        img = i;
        loadImage(sourceId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
