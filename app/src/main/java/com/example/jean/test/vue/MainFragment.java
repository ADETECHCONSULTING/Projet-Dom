package com.example.jean.test.vue;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.jean.test.R;
import com.example.jean.test.modele.Annonce;
import com.example.jean.test.modele.Filtre;
import com.example.jean.test.modele.FiltreRequest;
import com.example.jean.test.modele.Ville;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private ToggleButton tAchat;
    private ImageView img;
    private Bitmap background;
    private String KEY_URL = "https://fr-fr.roomlala.com/prod/file/welchome/ads_fr.xml";
    private URL url;
    private Bitmap imgNav;
    private BitmapDrawable backDrawable;
    private ConstraintLayout constraintLayout;
    private int idBackground = R.drawable.background2;
    private int idImage;
    private int idLayout = R.id.activity_main;
    private ArrayList<Annonce> lesAnnonces;
    private ConstraintLayout cLayout;
    private ToggleButton tVente;
    private ToggleButton tLocation;
    private ToggleButton tColocation;
    private ToggleButton tViager;
    private ToggleButton tEnchere;
    private String choixTypeAction;
    private Button btnRechercher;
    private ArrayList<Filtre> lesFiltres;
    private ArrayList<Ville> lesVilles;
    AlertDialog dialog;
    private Button btnAutre;
    private TextView typeBien;
    private EditText editType;
    private AutoCompleteTextView autoLieu;
    private EditText prix;
    private ArrayList<String> selectedType;
    private String[] choixType = {"Maison", "Appartement", "Parking/Garage", "Villa", "Chateau", "Loft", "A rénover", "Cave", "Chalet, Mobil-home", "terrain"};

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        cLayout = (ConstraintLayout) view.findViewById(idLayout);
        setBackground(cLayout, idBackground);
        tAchat = (ToggleButton) view.findViewById(R.id.tAchat);
        tVente = (ToggleButton) view.findViewById(R.id.tVente);
        tLocation = (ToggleButton) view.findViewById(R.id.tLocation);
        tColocation = (ToggleButton) view.findViewById(R.id.tColocation);
        tViager = (ToggleButton) view.findViewById(R.id.tViager);
        tEnchere = (ToggleButton) view.findViewById(R.id.tEnchere);
        autoLieu = (AutoCompleteTextView) view.findViewById(R.id.autoLieu);
        prix = (EditText) view.findViewById(R.id.txtPrix);
        typeBien = (TextView) view.findViewById(R.id.txtType);
        btnAutre = (Button) view.findViewById(R.id.btnAutre);
        btnRechercher = (Button) view.findViewById(R.id.btnConfirmer);
        editType = (EditText) view.findViewById(R.id.editType);
        lesFiltres = new ArrayList<>();
        lesVilles = new ArrayList<>();
        selectedType = new ArrayList<>();
        try {
            url = new URL("https://fr-fr.roomlala.com/xxx/xxx/xxx/ads_fr.xml");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        lesAnnonces = new ArrayList<>();

        tAchat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    choixTypeAction = "tAchat";
                    decolore();
                    btnColorBlue(1);
                } else {
                    return;
                }
            }
        });
        tVente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    choixTypeAction = "tVente";
                    decolore();
                    btnColorBlue(2);
                } else {
                    return;
                }
            }
        });

        tLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    choixTypeAction = "tLocation";
                    decolore();
                    btnColorBlue(3);
                } else {
                    return;
                }
            }
        });

        tColocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    choixTypeAction = "tColocation";
                    decolore();
                    btnColorBlue(4);
                } else {
                    return;
                }
            }
        });
        tViager.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    choixTypeAction = "tViager";
                    decolore();
                    btnColorBlue(5);
                } else {
                    return;
                }
            }
        });

        tEnchere.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    choixTypeAction = "tEnchere";
                    decolore();
                    btnColorBlue(6);
                } else {
                    return;
                }
            }
        });

        typeBien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedType.isEmpty()) {
                    selectedType.clear();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Type de bien ?")
                        .setMultiChoiceItems(choixType, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int rangType, boolean isChecked) {
                                if (isChecked) {
                                    selectedType.add(choixType[rangType]);
                                } else {
                                    if (selectedType.contains(choixType[rangType])) {
                                        selectedType.remove(choixType[rangType]);
                                    }
                                }
                            }
                        })
                        .setPositiveButton("valider", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String value = "";
                                for (String type : selectedType) {
                                    value += type + ",";
                                }
                                editType.setText(value);
                            }
                        })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedType.clear();
                            }
                        });
                dialog = builder.create();
                dialog.show();
            }
        });


        /**
         * Au clic sur le btnAutre la base est appelé pour remplir le tableau de filtre
         */
        btnAutre.setOnClickListener(new View.OnClickListener() {
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
        });

        btnRechercher.setOnClickListener(new View.OnClickListener() {
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

    public void btnColorBlue(int btnNum) {
        switch (btnNum) {
            case 1:
                tAchat.setTextColor(getResources().getColor(R.color.BlueDomoumDark));
                break;
            case 2:
                tVente.setTextColor(getResources().getColor(R.color.BlueDomoumDark));
                break;
            case 3:
                tLocation.setTextColor(getResources().getColor(R.color.BlueDomoumDark));
                break;
            case 4:
                tColocation.setTextColor(getResources().getColor(R.color.BlueDomoumDark));
                break;
            case 5:
                tViager.setTextColor(getResources().getColor(R.color.BlueDomoumDark));
                break;
            case 6:
                tEnchere.setTextColor(getResources().getColor(R.color.BlueDomoumDark));
                break;
        }
    }

    public void decolore() {
        tAchat.setTextColor(getResources().getColor(R.color.Black));
        tVente.setTextColor(getResources().getColor(R.color.Black));
        tLocation.setTextColor(getResources().getColor(R.color.Black));
        tColocation.setTextColor(getResources().getColor(R.color.Black));
        tViager.setTextColor(getResources().getColor(R.color.Black));
        tEnchere.setTextColor(getResources().getColor(R.color.Black));
    }


    public void loadBackground(int id) {
        background = BitmapFactory.decodeStream(getResources().openRawResource(id));
        backDrawable = new BitmapDrawable(background);
        constraintLayout.setBackgroundDrawable(backDrawable);
    }

    public void unloadBackground() {
        if (constraintLayout != null) {
            constraintLayout.setBackgroundDrawable(null);
        }
        if (backDrawable != null) {
            background.recycle();
        }
        backDrawable = null;
    }

    public void loadImage(int id) {
        imgNav = BitmapFactory.decodeStream(getResources().openRawResource(id));
        img.setImageBitmap(imgNav);
    }

    public void unloadImage() {
        if (img != null) {
            img.setImageBitmap(null);
        }
        if (imgNav != null) {
            imgNav.recycle();
        }
        imgNav = null;
    }

    public void setBackground(ConstraintLayout c, int sourceId) {
        unloadBackground();
        constraintLayout = c;
        loadBackground(sourceId);
    }

    public void setImg(ImageView i, int sourceId) {
        unloadImage();
        img = i;
        loadImage(sourceId);
    }

    public ArrayList<Annonce> parseXML(URL url) {
        ArrayList<Annonce> lesAnnonces = new ArrayList<>();
        try {
            // Récupère l'ensemble du document xml à l'url donnée
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            // Récupère les élèments par le tag 'ad'
            NodeList nodes = doc.getElementsByTagName("ad");

            // Pour chaque 'ad' on recupère l'id, l'url, le titre, le type d'action(for rent...), le contenu de l'annonce,
            // le type de propriété (chambre...), et la ville
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                NodeList id = element.getElementsByTagName("id");
                NodeList _url = element.getElementsByTagName("url");
                NodeList title = element.getElementsByTagName("title");
                NodeList type = element.getElementsByTagName("type");
                NodeList content = element.getElementsByTagName("content");
                NodeList price = element.getElementsByTagName("price");
                NodeList property_type = element.getElementsByTagName("property_type");
                NodeList city = element.getElementsByTagName("city");
                //NodeList picture_url = element.getElementsByTagName("picture_url");

                Element idLine = (Element) id.item(0);
                Element urlLine = (Element) _url.item(0);
                Element titleLine = (Element) title.item(0);
                Element typeLine = (Element) type.item(0);
                Element contentLine = (Element) content.item(0);
                Element priceLine = (Element) price.item(0);
                Element propertyTypeLine = (Element) property_type.item(0);
                Element cityLine = (Element) city.item(0);

                //On ajoute le tout dans un tableau de type Annonce
                lesAnnonces.add(new Annonce(idLine.getTextContent(), urlLine.getTextContent(), titleLine.getTextContent(), typeLine.getTextContent(), contentLine.getTextContent(), priceLine.getTextContent(), propertyTypeLine.getTextContent(), cityLine.getTextContent()));
                return lesAnnonces;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

