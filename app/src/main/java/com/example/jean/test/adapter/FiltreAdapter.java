package com.example.jean.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jean.test.R;
import com.example.jean.test.modele.Filtre;

import java.util.ArrayList;

/**
 * Created by JEAN on 19/01/2017.
 */

public class FiltreAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ViewHolder holder;
    ArrayList<Filtre> lesFiltres = new ArrayList<>();
    private String[] choixFiltre;

    public FiltreAdapter(ArrayList<Filtre> lesFiltres, Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.lesFiltres = lesFiltres;
    }


    @Override
    public int getCount() {
        return lesFiltres.size();
    }

    @Override
    public Object getItem(int position) {
        return lesFiltres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        public TextView myTextView;
        public TextView myTextViewF;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment_filtre, null);
            holder.myTextViewF = (TextView) convertView.findViewById(R.id.contentEdit);
            holder.myTextView = (TextView) convertView.findViewById(R.id.contentText);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final String filtre = lesFiltres.get(position).getFiltre();
        holder.myTextView.setText(filtre);
        holder.myTextViewF.setEnabled(false);
        holder.myTextViewF.setText("------------");
        return convertView;
    }
}
