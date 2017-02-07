package com.example.jean.test.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jean.test.R;
import com.example.jean.test.modele.ImageAsyncTask;
import com.example.jean.test.modele.XMLAsynctask;
import com.example.jean.test.vue.DetailActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by JEAN on 06/02/2017.
 */

public class XMLAdapter extends RecyclerView.Adapter<XMLAdapter.AnnonceViewHolder> implements XMLAsynctask.DocumentConsumer{
    Document document = null;

    @Override
    public AnnonceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_annonce_cell, parent, false);
        return new AnnonceViewHolder(view);
    }

    /**
     * Recupère le n ième item dans document xml et le passe au ViewHolder pour l'afficher
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(AnnonceViewHolder holder, int position) {
        Element item = (Element) document.getElementsByTagName("ad").item(position);
        holder.setElement(item);
    }

    @Override
    public int getItemCount() {
        if(this.document != null){
            return document.getElementsByTagName("ad").getLength();
        }else{
            return 0;
        }
    }

    /**
     * Methode qui vient de l'interface DocumentConsumer, elle affecte la propriété document et raffraichie la liste
     * @param document
     */
    @Override
    public void setXMLDocument(Document document) {
        this.document = document;
        notifyDataSetChanged();
    }

    public class AnnonceViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView title;
        private TextView prix;
        private TextView city;
        private Element element;
        private String prices = "";

        public AnnonceViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.idTitle);
            img = (ImageView) itemView.findViewById(R.id.idImage);
            prix = (TextView) itemView.findViewById(R.id.idPrix);
            city = (TextView) itemView.findViewById(R.id.idCity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String _title = element.getElementsByTagName("title").item(0).getTextContent();
                    String _urlImg = element.getElementsByTagName("picture_url").item(0).getTextContent();
                    String _prix = element.getElementsByTagName("price").item(0).getTextContent();
                    String _city = element.getElementsByTagName("city").item(0).getTextContent();
                    String _content = element.getElementsByTagName("content").item(0).getTextContent();
                    String link = element.getElementsByTagName("url").item(0).getTextContent();
                    String _pays = getNomPays(element.getElementsByTagName("country").item(0).getTextContent());
                    String _detailMaison = null;
                    try {
                        _detailMaison = element.getElementsByTagName("floor_area").item(0).getTextContent() + " m², " + element.getElementsByTagName("capacity").item(0).getTextContent() + " place(s), " + element.getElementsByTagName("property_type").item(0).getTextContent();
                    }catch (Exception e){
                        try{
                            _detailMaison = element.getElementsByTagName("floor_area").item(0).getTextContent() + " m², " + element.getElementsByTagName("capacity").item(0).getTextContent() + " place(s)";
                        }catch (Exception e2){
                            e.printStackTrace();
                            Log.d("Erreur detailMaison : ", "");
                            e2.printStackTrace();
                        }
                    }


                    final Context context = itemView.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("title", _title);
                    intent.putExtra("urlImg", _urlImg);
                    intent.putExtra("prix", _prix);
                    intent.putExtra("city", _city);
                    intent.putExtra("content", _content);
                    intent.putExtra("url", link);
                    intent.putExtra("pays", _pays);
                    intent.putExtra("detailMaison", _detailMaison);
                    context.startActivity(intent);
                }
            });
        }

        public void setElement(Element element){
            this.element = element;
            this.title.setText(element.getElementsByTagName("title").item(0).getTextContent());
            this.city.setText(element.getElementsByTagName("city").item(0).getTextContent());
            this.prix.setText(element.getElementsByTagName("price").item(0).getTextContent() + " €" + " / mois");
            new ImageAsyncTask(img).execute(element.getElementsByTagName("picture_url").item(0).getTextContent());

        }

        public String getNomPays(String pays){
            String nomPays;
            switch (pays){
                case "BE":
                    nomPays = "Belgique";
                    break;
                case "FR":
                    nomPays = "France";
                    break;
                case "EN":
                    nomPays = "Angleterre";
                    break;
                default:
                    nomPays = null;
                    break;
            }
            return nomPays;
        }
    }
}
