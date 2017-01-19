package com.example.jean.test.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
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
    ArrayList<Filtre> lesFiltres = new ArrayList<>();
    private ArrayList<String> selectedType;
    private String[] choixFiltre;

    public FiltreAdapter(ArrayList<Filtre> lesFiltres, Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.lesFiltres = lesFiltres;
        selectedType = new ArrayList<>();
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
        public EditText myEditText;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment_filtre, null);
            holder.myEditText = (EditText) convertView.findViewById(R.id.contentEdit);
            holder.myTextView = (TextView) convertView.findViewById(R.id.contentText);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final String filtre = lesFiltres.get(position).getFiltre();
        final String[] specifications = lesFiltres.get(position).getSpecification().split(",");
        holder.myTextView.setText(filtre);
        holder.myEditText.setEnabled(false);
        holder.myTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                if(!selectedType.isEmpty()){
                    selectedType.clear();
                }
                builder.setTitle(filtre)
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
                                holder.myEditText.setText(value);
                            }
                        });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
        return convertView;
    }
}
