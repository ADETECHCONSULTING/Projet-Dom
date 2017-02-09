package com.example.jean.test.vue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jean.test.R;
import com.example.jean.test.adapter.XMLAdapter;
import com.example.jean.test.modele.XMLAsynctask;

public class AnnonceFragment extends Fragment {
    private XMLAsynctask xmlAsynctask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Les offres disponibles");
        return inflater.inflate(R.layout.fragment_annonce,container,false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.list);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        XMLAdapter xmlAdapter = new XMLAdapter();
        rv.setAdapter(xmlAdapter);
        String xmlURL = "https://fr-fr.roomlala.com/prod/file/welchome/ad_fr_be.xml";
        xmlAsynctask = new XMLAsynctask(xmlAdapter);
        xmlAsynctask.execute(xmlURL);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(xmlAsynctask != null){
            xmlAsynctask.cancel(true);
        }
    }
}
