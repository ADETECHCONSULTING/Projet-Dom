package com.example.jean.test.vue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jean.test.R;
import com.example.jean.test.adapter.FiltreAdapter;
import com.example.jean.test.modele.Filtre;

import java.util.ArrayList;


public class FiltreFragment extends Fragment {
    private ArrayList<Filtre> lesFiltres;
    private ListView myList;
    private FiltreAdapter filtreAdapter;
    public FiltreFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtre_list, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
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
        return view;
    }

    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);
        filtreAdapter = new FiltreAdapter(lesFiltres, getActivity());
        myList.setAdapter(filtreAdapter);
    }




}
