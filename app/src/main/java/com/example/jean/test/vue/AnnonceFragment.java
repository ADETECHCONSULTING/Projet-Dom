package com.example.jean.test.vue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.jean.test.R;
import com.example.jean.test.modele.Annonce;
import com.example.jean.test.modele.XMLAsynctask;
import com.example.jean.test.modele.XMLPullAsynctask;

import java.util.ArrayList;

public class AnnonceFragment extends Fragment {

    private XMLAsynctask xmlAsynctask;
    public ArrayList<Annonce> lesAnnonces;
    private String TAG = "AnnonceFragment";
    private String xmlURL = "https://fr-fr.roomlala.com/prod/file/welchome/ad_fr_be.xml";
    private ProgressBar bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Les offres disponibles");
        return inflater.inflate(R.layout.fragment_annonce,container,false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bar = (ProgressBar) view.findViewById(R.id.idProgress);
        XMLPullAsynctask xmlPullAsynctask = new XMLPullAsynctask(getContext(), view);
        xmlPullAsynctask.setBar(bar);
        xmlPullAsynctask.execute(xmlURL);
        /*final RecyclerView rv = (RecyclerView) view.findViewById(R.id.list);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setDrawingCacheEnabled(true);
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        XMLAdapter xmlAdapter = new XMLAdapter();
        rv.setAdapter(xmlAdapter);
        xmlAsynctask = new XMLAsynctask(xmlAdapter);
        xmlAsynctask.setBar(bar);
        xmlAsynctask.execute(xmlURL);*/
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(xmlAsynctask != null){
            xmlAsynctask.cancel(true);
        }
    }
}
