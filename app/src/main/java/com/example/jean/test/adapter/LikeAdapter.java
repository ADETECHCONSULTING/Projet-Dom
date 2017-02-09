package com.example.jean.test.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jean.test.R;
import com.example.jean.test.modele.Annonce;
import com.example.jean.test.modele.ImageAsyncTask;
import com.example.jean.test.vue.DetailActivity;

import java.util.ArrayList;

/**
 * Created by JEAN on 09/02/2017.
 */

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.AnnonceViewHolder> {
    private int logoId;
    private ArrayList<Annonce> mesAnnoncesLike;
    private Context context;

    public LikeAdapter(Context context, ArrayList<Annonce> mesAnnoncesLike){
        this.context = context;
        this.mesAnnoncesLike = mesAnnoncesLike;

    }

    @Override
    public AnnonceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_annonce_cell, parent, false);
        return new AnnonceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnnonceViewHolder holder, int position) {
        Annonce current = mesAnnoncesLike.get(position);
        holder.setElement(current);
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return mesAnnoncesLike.size();
    }

    public class AnnonceViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView title;
        private TextView prix;
        private TextView city;
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
                    String _title = mesAnnoncesLike.get(position).getTitle();
                    String _urlImg = mesAnnoncesLike.get(position).getPicture_url();
                    String _prix = mesAnnoncesLike.get(position).getPrice();
                    String _city = mesAnnoncesLike.get(position).getCity();
                    String _content = mesAnnoncesLike.get(position).getContent();
                    String link = mesAnnoncesLike.get(position).getUrl() + "?utm_medium=Affiliation-interne&utm_source=DOM&utm_content=annonce";
                    String _pays = "Belgique";
                    String _id = mesAnnoncesLike.get(position).getId();
                    String _detailMaison = mesAnnoncesLike.get(position).getType();



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

        public void setElement(Annonce current) {
            this.title.setText(current.getTitle());
            this.city.setText(current.getCity());
            this.prix.setText(current.getPrice());
            new ImageAsyncTask(img).execute(current.getPicture_url());
            this.logo.setImageDrawable(itemView.getResources().getDrawable(getLogoPartenaire(current.getPicture_url())));
        }
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

    /**
     * Choisis le bon logo partenaire
     * @param url
     * @return
     */
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



    /**
     * Traduit la periode en français
     * @param period
     * @return
     */
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

    /**
     * Recherche dans le tableau donnée en parametre si l'annonce est disponible by l'id
     * @param id
     * @return
     */
    private Annonce findAnnonce(String id, ArrayList<Annonce> mesAnnonces){
        Annonce annonce = null;
        for(Annonce uneAnnonce : mesAnnonces){
            if(uneAnnonce.getId() == id){
                annonce = uneAnnonce;
            }
        }
        return annonce;
    }
}
