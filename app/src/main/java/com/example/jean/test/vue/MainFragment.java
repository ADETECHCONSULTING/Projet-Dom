package com.example.jean.test.vue;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.jean.test.R;
import com.example.jean.test.modele.Filtre;
import com.example.jean.test.modele.FiltreRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private ToggleButton tAchat;
    private ToggleButton tVente;
    private ToggleButton tLocation;
    private ToggleButton tColocation;
    private ToggleButton tViager;
    private ToggleButton tEnchere;
    private String choixTypeAction;
    private ArrayList<Filtre> lesFiltres;
    AlertDialog dialog;
    private Button btnAutre;
    private TextView typeBien;
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
        tAchat = (ToggleButton) view.findViewById(R.id.tAchat);
        tVente = (ToggleButton) view.findViewById(R.id.tVente);
        tLocation = (ToggleButton) view.findViewById(R.id.tLocation);
        tColocation = (ToggleButton) view.findViewById(R.id.tColocation);
        tViager = (ToggleButton) view.findViewById(R.id.tViager);
        tEnchere = (ToggleButton) view.findViewById(R.id.tEnchere);
        autoLieu = (AutoCompleteTextView) view.findViewById(R.id.txtLieu);
        prix = (EditText) view.findViewById(R.id.txtPrix);
        typeBien = (TextView) view.findViewById(R.id.txtType);
        btnAutre = (Button) view.findViewById(R.id.btnAutre);
        lesFiltres = new ArrayList<>();
        selectedType = new ArrayList<>();
        tAchat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    choixTypeAction = "tAchat";
                    decolore();
                    btnColorBlue(1);
                }else{
                    return;
                }
            }
        });
        tVente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    choixTypeAction = "tVente";
                    decolore();
                    btnColorBlue(2);
                }else{
                    return;
                }
            }
        });

        tLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    choixTypeAction = "tLocation";
                    decolore();
                    btnColorBlue(3);
                }else{
                    return;
                }
            }
        });

        tColocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    choixTypeAction = "tColocation";
                    decolore();
                    btnColorBlue(4);
                }else{
                    return;
                }
            }
        });
        tViager.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    choixTypeAction = "tViager";
                    decolore();
                    btnColorBlue(5);
                }else{
                    return;
                }
            }
        });

        tEnchere.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    choixTypeAction = "tEnchere";
                    decolore();
                    btnColorBlue(6);
                }else{
                    return;
                }
            }
        });

        typeBien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selectedType.isEmpty()){
                    selectedType.clear();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Type de bien ?")
                        .setMultiChoiceItems(choixType, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int rangType, boolean isChecked) {
                                if(isChecked){
                                    selectedType.add(choixType[rangType]);
                                }else{
                                    if(selectedType.contains(choixType[rangType])){
                                        selectedType.remove(choixType[rangType]);
                                    }
                                }
                            }
                        })
                .setPositiveButton("valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = "Type de bien? ";
                        for(String type : selectedType){
                            value += type+",";
                        }
                        typeBien.setText(value);
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

        btnAutre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAGGG", "TEST TEST");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            boolean success = true;
                            if(success){
                                for(int i=0; i< jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    lesFiltres.add(new Filtre(jsonObject.getInt("id"), jsonObject.getString("filtre"), jsonObject.getString("specification")));
                                }
                                MainActivity mainActivity = (MainActivity) getActivity();
                                if(mainActivity != null){
                                    mainActivity.setFiltre(lesFiltres);
                                }
                                FiltreFragment filtreFragment = new FiltreFragment();
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragmentContainer, filtreFragment);
                                fragmentTransaction.commit();
                            }else{
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
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                FiltreRequest filtreRequest = new FiltreRequest("", "", responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(filtreRequest);
            }
        });

        // Inflate the layout for this fragment
        return view;

    }

    public void btnColorBlue(int btnNum){
        switch (btnNum){
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

    public void decolore(){
        tAchat.setTextColor(getResources().getColor(R.color.Black));
        tVente.setTextColor(getResources().getColor(R.color.Black));
        tLocation.setTextColor(getResources().getColor(R.color.Black));
        tColocation.setTextColor(getResources().getColor(R.color.Black));
        tViager.setTextColor(getResources().getColor(R.color.Black));
        tEnchere.setTextColor(getResources().getColor(R.color.Black));
    }
}
