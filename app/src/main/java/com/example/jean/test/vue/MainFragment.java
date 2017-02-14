package com.example.jean.test.vue;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.example.jean.test.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private ToggleButton tAchat;
    private ImageView img;
    private Bitmap background;
    private Bitmap imgNav;
    private BitmapDrawable backDrawable;
    private FrameLayout frameLayout;
    private int idBackground = R.drawable.background;
    private int idImage;
    private int idLayout = R.id.activity_main;
    private FrameLayout cLayout;
    private Button btnAchat;
    private Button btnLocation;
    private Button btnColocation;
    private Button btnViager;
    private Button btnEchere;
    private Button btnCrowdfunding;
    private Button btnTout;
    private int btnChoix;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        TypefaceProvider.registerDefaultIconSets();
        cLayout = (FrameLayout) view.findViewById(idLayout);
        setBackground(cLayout, idBackground);
        btnAchat = (Button) view.findViewById(R.id.btnAchat);
        btnLocation = (Button) view.findViewById(R.id.btnLocation);
        btnColocation = (Button) view.findViewById(R.id.btnColocation);
        btnViager = (Button) view.findViewById(R.id.btnViager);
        btnEchere = (Button) view.findViewById(R.id.btnEnchere);
        btnCrowdfunding = (Button) view.findViewById(R.id.btnCrowdfunding);
        btnTout = (Button) view.findViewById(R.id.btnTout);
        getActivity().setTitle("");
        btnAchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnColocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnonceFragment annonceFragment = new AnnonceFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("colocation");
                fragmentTransaction.replace(R.id.fragmentContainer, annonceFragment);
                fragmentTransaction.commit();
            }
        });
        /**
         * Au clic sur le btnAutre la base est appelé pour remplir le tableau de filtre
         */
        /*btnAutre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAGGG", "TEST TEST");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            boolean success = true;
                            if (success) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    lesFiltres.add(new Filtre(jsonObject.getInt("id"), jsonObject.getString("filtre"), jsonObject.getString("specification")));
                                }
                                MainActivity mainActivity = (MainActivity) getActivity();
                                if (mainActivity != null) {
                                    mainActivity.setFiltre(lesFiltres);
                                }
                                FiltreFragment filtreFragment = new FiltreFragment();
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragmentContainer, filtreFragment);
                                fragmentTransaction.commit();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Connexion échoué")
                                        .setMessage("Cette application a besoin d'une connexion à internet. Vérifiez que vous êtes bien connecté")
                                        .setPositiveButton("Réessayer", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                return;
                                            }
                                        });
                                Dialog dialog = builder.create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                FiltreRequest filtreRequest = new FiltreRequest("", "", responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(filtreRequest);
            }
        });*/

        btnTout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnonceFragment annonceFragment = new AnnonceFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, annonceFragment);
                fragmentTransaction.commit();

            }
        });
        // Inflate the layout for this fragment
        return view;

    }


    /**
     * Charge l'image d'arriere plan
     * @param id
     */
    public void loadBackground(int id) {
        background = BitmapFactory.decodeStream(getResources().openRawResource(id));
        backDrawable = new BitmapDrawable(background);
        frameLayout.setBackgroundDrawable(backDrawable);
    }

    /**
     * Décharge l'image d'arrière plan
     */
    public void unloadBackground() {
        if (frameLayout != null) {
            frameLayout.setBackgroundDrawable(null);
        }
        if (backDrawable != null) {
            background.recycle();
        }
        backDrawable = null;
    }

    /**
     * Charge une image quelquonque
     * @param id
     */
    public void loadImage(int id) {
        imgNav = BitmapFactory.decodeStream(getResources().openRawResource(id));
        img.setImageBitmap(imgNav);
    }

    /**
     * Décharge une image quelquonque
     */
    public void unloadImage() {
        if (img != null) {
            img.setImageBitmap(null);
        }
        if (imgNav != null) {
            imgNav.recycle();
        }
        imgNav = null;
    }

    /**
     * Met en place l'image d'arrière plan chargée
     * @param c
     * @param sourceId
     */
    public void setBackground(FrameLayout c, int sourceId) {
        unloadBackground();
        frameLayout = c;
        loadBackground(sourceId);
    }

    /**
     * Met en place l'image quelquonque chargée
     * @param i
     * @param sourceId
     */
    public void setImg(ImageView i, int sourceId) {
        unloadImage();
        img = i;
        loadImage(sourceId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void onBackPressed(){

    }
}

