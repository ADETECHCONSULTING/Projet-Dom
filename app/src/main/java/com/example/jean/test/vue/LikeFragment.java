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
import com.example.jean.test.adapter.LikeAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikeFragment extends Fragment {
    private MainActivity mainActivity;

    public LikeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Mes annonces coups de coeurs");
        mainActivity = new MainActivity();
        return inflater.inflate(R.layout.fragment_like, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.listLike);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        LikeAdapter likeAdapter = new LikeAdapter(getContext(),mainActivity.mesAnnoncesLike);
        rv.setAdapter(likeAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
