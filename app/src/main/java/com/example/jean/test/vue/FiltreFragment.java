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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jean.test.R;
import com.example.jean.test.adapter.FiltreAdapter;
import com.example.jean.test.modele.Filtre;

import java.util.ArrayList;


public class FiltreFragment extends Fragment {
    private ArrayList<Filtre> lesFiltres;
    private ListView myList;
    private FiltreAdapter filtreAdapter;
    private Button btnAnnuler;
    private Button btnConfirmer;
    FiltreFragment filtreFragment;
    private ArrayList<String> selectedType;

    public FiltreFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtre_list, container, false);
        if(savedInstanceState != null){
            filtreFragment = (FiltreFragment) getFragmentManager().getFragment(savedInstanceState, "filtreFragment");
        }
        MainActivity mainActivity = (MainActivity) getActivity();
        lesFiltres = ((MainActivity) getActivity()).getLesFiltres();
        selectedType = new ArrayList<>();
        btnAnnuler = (Button) view.findViewById(R.id.btnAnnuler);
        btnConfirmer = (Button) view.findViewById(R.id.btnConfirm);
        String test = "";
        if(mainActivity != null){
            test = "je suis pas null";
            lesFiltres = mainActivity.getLesFiltres();
        }else{
            test = "je suis null";
            lesFiltres.add(new Filtre(1,"Pas de connexion...", "Null, Null, Null"));
        }
        myList = (ListView) view.findViewById(R.id.myListV);
        Log.d("TEST LISTVIEW", test);

        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragment mainFragment = new MainFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, mainFragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);
        filtreAdapter = new FiltreAdapter(lesFiltres, getActivity());
        myList.setAdapter(filtreAdapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                 final String[] specifications = lesFiltres.get(position).getSpecification().split(",");
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        if(!selectedType.isEmpty()){
                            selectedType.clear();
                        }
                        builder.setTitle(lesFiltres.get(position).getFiltre())
                                .setMultiChoiceItems(specifications, null, new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                                        if(isChecked){
                                            selectedType.add(specifications[position]);
                                        }else{
                                            if(selectedType.contains(specifications[position])){
                                                selectedType.remove(specifications[position]);
                                            }
                                        }
                                    }
                                })
                                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                })
                                .setPositiveButton("Sauvegarder", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String value = "";
                                        for(String type : selectedType){
                                            value += type+" ,";
                                        }
                                        updateView(position, value);
                                    }
                                });
                        Dialog dialog = builder.create();
                        dialog.show();


                    }
                });
            }

    public void updateView(int index, String value){
        View v = myList.getChildAt(index -
                myList.getFirstVisiblePosition());

        if(v == null) {
            return;
        }
        TextView updatedText = (TextView) v.findViewById(R.id.contentEdit);
        updatedText.setText(""+value);
    }

    public void onSaveInstanceState(Bundle savedState) {

        super.onSaveInstanceState(savedState);
        getFragmentManager().putFragment(savedState, "filtreFragment", filtreFragment);
    }

    public void onDestroyView(){
        super.onDestroyView();

    }
}




