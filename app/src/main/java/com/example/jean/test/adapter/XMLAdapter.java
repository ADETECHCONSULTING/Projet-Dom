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
    private int logoId;

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
        holder.position = position;
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
        private ImageView logo;
        private int position;

        public AnnonceViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.idTitle);
            img = (ImageView) itemView.findViewById(R.id.idImage);
            prix = (TextView) itemView.findViewById(R.id.idPrix);
            city = (TextView) itemView.findViewById(R.id.idCity);
            logo = (ImageView) itemView.findViewById(R.id.idLogoPartenaire);

            /**
             * Gestion du clic qui amenera à la Webview
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String _title = element.getElementsByTagName("title").item(0).getTextContent();
                    String _urlImg = element.getElementsByTagName("picture_url").item(0).getTextContent();
                    String _prix = element.getElementsByTagName("price").item(0).getTextContent();
                    String _city = element.getElementsByTagName("city").item(0).getTextContent();
                    String _content = element.getElementsByTagName("content").item(0).getTextContent();
                    String link = element.getElementsByTagName("url").item(0).getTextContent()+"?utm_medium=Affiliation-interne&utm_source=DOM&utm_content=annonce";
                    String _pays = getNomPays(element.getElementsByTagName("country").item(0).getTextContent());
                    String _id = element.getElementsByTagName("id").item(0).getTextContent();
                    String _detailMaison = "";
                    try {
                        _detailMaison = element.getElementsByTagName("floor_area").item(0).getTextContent() + " m², " + element.getElementsByTagName("capacity").item(0).getTextContent() + " place(s), " + element.getElementsByTagName("property_type").item(0).getTextContent();
                    }catch (Exception e){
                        try{
                            _detailMaison = element.getElementsByTagName("property_type").item(0).getTextContent();
                        }catch (Exception e2){
                            try{
                                _detailMaison = element.getElementsByTagName("floor_area").item(0).getTextContent() + " m², " + element.getElementsByTagName("capacity").item(0).getTextContent()+" place(s)";
                            }catch (Exception e3){
                                e.printStackTrace();
                                Log.d("Erreur detailMaison : ", "");
                                e2.printStackTrace();
                            }
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
                    intent.putExtra("logoId", logoId);
                    intent.putExtra("detailMaison", _detailMaison);
                    intent.putExtra("position", position);
                    intent.putExtra("id", _id);
                    context.startActivity(intent);
                }
            });
        }

        /**
         * Donne les valeurs aux vues dans le holder
         * @param element
         */
        public void setElement(Element element){
            this.element = element;
            this.title.setText(element.getElementsByTagName("title").item(0).getTextContent());
            this.city.setText(element.getElementsByTagName("city").item(0).getTextContent());
            this.prix.setText(element.getElementsByTagName("price").item(0).getTextContent() + " € / " + traductionPeriod(mAttribute(element, "price", "period")));
            new ImageAsyncTask(img).execute(element.getElementsByTagName("picture_url").item(0).getTextContent());
            this.logo.setImageDrawable(itemView.getResources().getDrawable(getLogoPartenaire(element.getElementsByTagName("picture_url").item(0).getTextContent())));
        }

        /**
         * Recupere le nom du Pays par son libellé
         * @param pays
         * @return
         */
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
                case "CH" :
                    nomPays = "Suisse";
                    break;
                default:
                    nomPays = null;
                    break;
            }
            return nomPays;
        }

        public int getLogoPartenaire(String url){
            int logo;
            if(url.contains("roomlala")){
                logo = R.drawable.roomlala;
                logoId = 1;
            }else {
                logo = R.drawable.logo;
                logoId = 0;
            }
            return logo;
        }

        private String mAttribute(Element e, String tag, String nameAttr) {
            String attr;
            e = (Element) e.getElementsByTagName(tag).item(0);
            attr = e.getAttribute(nameAttr);
            return attr;
        }

        private String traductionPeriod(String period){
            String periodTr ="";
            switch (period){
                case "monthly":
                    periodTr = "mois";
                    break;
                case "weekly":
                    periodTr = "semaines";
                    break;
                case "daily":
                    periodTr = "jours";
                    break;
            }
            return periodTr;
        }

    }


}
