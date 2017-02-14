package com.example.jean.test.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by JEAN on 06/02/2017.
 */

public class XMLAdapterArray extends RecyclerView.Adapter<XMLAdapterArray.AnnonceViewHolder>{
    private int logoId;
    private ImageView img;
    private Bitmap imgNav;
    private Context context;
    private ArrayList<Annonce> lesAnnonces;

    public XMLAdapterArray(Context context, ArrayList<Annonce> lesAnnonces){
        this.context = context;
        this.lesAnnonces = lesAnnonces;
    }
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
        Annonce current = lesAnnonces.get(position);
        holder.setAnnonce(current);
    }

    @Override
    public int getItemCount() {
        return lesAnnonces.size();
    }



    public class AnnonceViewHolder extends RecyclerView.ViewHolder{
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
                    String _title = lesAnnonces.get(getAdapterPosition()).getTitle();
                    //String[] _urlImg = lesAnnonces.get(getAdapterPosition()).getPicture_url();
                    String urlimg = lesAnnonces.get(getAdapterPosition()).getPicture_url();
                    String _prix = lesAnnonces.get(getAdapterPosition()).getPrice();
                    String _city = lesAnnonces.get(getAdapterPosition()).getCity();
                    String _content = lesAnnonces.get(getAdapterPosition()).getContent();
                    String link = lesAnnonces.get(getAdapterPosition()).getUrl()+"?utm_medium=Affiliation-interne&utm_source=DOM&utm_content=annonce";
                    String _pays = lesAnnonces.get(getAdapterPosition()).getCity();
                    String _id = lesAnnonces.get(getAdapterPosition()).getId();
                    String _detailMaison = lesAnnonces.get(getAdapterPosition()).getType();

                    final Context context = itemView.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("title", _title);
                    //intent.putExtra("urlImg", _urlImg);
                    intent.putExtra("prix", _prix);
                    intent.putExtra("city", _city);
                    intent.putExtra("content", _content);
                    intent.putExtra("url", link);
                    intent.putExtra("pays", _pays);
                    intent.putExtra("logoId", logoId);
                    intent.putExtra("detailMaison", _detailMaison);
                    intent.putExtra("position", position);
                    intent.putExtra("id", _id);
                    intent.putExtra("soloimg", urlimg);
                    context.startActivity(intent);
                }
            });
        }

        /**
         * Donne les valeurs aux vues dans le holder
         * @param annonce
         */
        public void setAnnonce(Annonce annonce){
            city.setText(annonce.getCity() + " - " + getNomPays(annonce.getCountry()));
            title.setText(annonce.getTitle());

            prix.setText(annonce.getPrice() + " € / "+ annonce.getPriceAttrib());
            new ImageAsyncTask(img).execute(annonce.getPicture_url());
            logo.setImageBitmap(decodeSampledBitmapFromResource(itemView.getResources(), R.drawable.roomlala, 48,48));
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

        public String[] getLesPhotos(NodeList ele){
            String[] tab = new String[ele.getLength()];
            for(int i=0; i < ele.getLength(); i++ ){
                tab[i] += ele.item(i).getTextContent();
            }
            return tab;
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

    /**
     * Code AndroidDevelopper pour resize une image
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * Code AndroidDevelopper pour resize une image
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


}
