package com.example.jean.test.vue;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.test.R;
import com.example.jean.test.modele.Annonce;
import com.example.jean.test.modele.ImageAsyncTask;

import java.util.ArrayList;

import de.cketti.mailto.EmailIntentBuilder;

public class DetailActivity extends Activity {
    private TextView vdetail;
    private TextView txtEnSavoirPlus;
    private ImageView vimg;
    private ImageView vimgPartenaire;
    private Button redirection;
    private TextView vprix;
    private String title;
    private String urlImg;
    private String url;
    private String prix;
    private String city;
    private String content;
    private String detailMaison;
    private String pays;
    private int logoId;
    private ArrayList<Annonce> mesAnnoncesLike;
    private ArrayList<Annonce> mesAnnoncesFavorites;
    private int pos;
    private String id;
    private TextView vcity;
    private TextView vcontent;
    private ImageView share;
    private ImageView like;
    private ImageView fav;
    private TextView vtitle;
    private Boolean boolLike = false;
    private Boolean boolFavoris = false;
    private ImageView vlogo;
    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mesAnnoncesLike = new ArrayList<>();
        mesAnnoncesFavorites = new ArrayList<>();
        mainActivity = new MainActivity();
        setTitle(title);
        RecupIntent();
        lesFindViewByID();
        AffichageDetail();

        /**
         * Renvoie sur la webview qui affichera le contenu internet sans quitter l'appli
         */
        redirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirection = new Intent(DetailActivity.this, WebviewActivity.class);
                redirection.putExtra("url", url);
                startActivity(redirection);
            }
        });

        /**
         * Renvoie sur la webview qui affichera le contenu internet sans quitter l'appli
         */
        txtEnSavoirPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirection = new Intent(DetailActivity.this, WebviewActivity.class);
                redirection.putExtra("url", url);
                startActivity(redirection);
            }
        });

        /**
         * Gestion du partage à un ami
         */
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sdk = Build.VERSION.SDK_INT;
                share.setBackgroundDrawable(getResources().getDrawable(R.drawable.email_black_48x48));
                Intent emailIntent = EmailIntentBuilder.from(DetailActivity.this)
                        .body(Html.fromHtml("<br> Hé ! J'ai trouvé une annonce qui pourrait t'interresser ! </br>"+url).toString())
                        .build();
                try {
                    startActivity(emailIntent);
                }catch (Exception e){
                    Toast.makeText(DetailActivity.this, "Il vous faut un support de messagerie. Par exemple : Gmail, Yahoo, etc...", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /**
         * Gestion des annonces coups de coeurs
         */
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolLike) {
                    mesAnnoncesLike.add(new Annonce(id,url,title,detailMaison,content,prix,city,urlImg));
                    mainActivity.setMesAnnoncesLike(mesAnnoncesLike);
                    Toast.makeText(DetailActivity.this, "L'annonce a été ajouté à vos coups de coeurs", Toast.LENGTH_SHORT).show();
                    like.setImageDrawable(getResources().getDrawable(R.drawable.favorite_black_48x48));
                    boolLike = true;
                }else{
                    like.setImageDrawable(getResources().getDrawable(R.drawable.favorite_outline_black_48x48));
                    Annonce mAnnonce = findAnnonce(id, mesAnnoncesLike);
                    mesAnnoncesLike.remove(mAnnonce);
                    mainActivity.setMesAnnoncesLike(mesAnnoncesLike);
                    boolLike = false;
                }
            }
        });

        /**
         * gestion des annonces favorites
         */
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolFavoris) {
                    Toast.makeText(DetailActivity.this, "L'annonce a été ajouté à vos favoris", Toast.LENGTH_SHORT).show();
                    mesAnnoncesFavorites.add(new Annonce(id, url,title,detailMaison,content,prix,city,urlImg));
                    mainActivity.setMesAnnoncesFavorites(mesAnnoncesFavorites);
                    fav.setImageDrawable(getResources().getDrawable(R.drawable.star_black_48x48));
                    boolFavoris = true;
                }else{
                    fav.setImageDrawable(getResources().getDrawable(R.drawable.star_outline_black_48x48));
                    Annonce mAnnonce = findAnnonce(id, mesAnnoncesFavorites);
                    mesAnnoncesFavorites.remove(mAnnonce);
                    mainActivity.setMesAnnoncesFavorites(mesAnnoncesFavorites);
                    boolFavoris = false;
                }
            }
        });

        vlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirection = new Intent(DetailActivity.this, WebviewActivity.class);
                redirection.putExtra("url", url);
                startActivity(redirection);
            }
        });
        
    }

    /**
     * Affiche les détails concernant l'offre selectionnée
     */
    private void AffichageDetail() {
        new ImageAsyncTask(vimg).execute(urlImg);
        vtitle.setText(title);
        vcity.setText(city +" - "+ pays);
        vprix.setText(prix+" €");
        vdetail.setText(detailMaison);
        getLogoByID(logoId);
        vcontent.setText(content);
    }

    /**
     * Récupère les données de la page précedente via l'intent
     */
    private void RecupIntent() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        urlImg = intent.getStringExtra("urlImg");
        url = intent.getStringExtra("url");
        prix = intent.getStringExtra("prix");
        city = intent.getStringExtra("city");
        content = intent.getStringExtra("content");
        detailMaison = intent.getStringExtra("detailMaison");
        pays = intent.getStringExtra("pays");
        logoId = intent.getIntExtra("logoId",0);
        pos = intent.getIntExtra("position", 0);
        id = intent.getStringExtra("id");
    }

    /**
     * Bind les views à leurs ID sur le layout
     */
    private void lesFindViewByID() {
        vcity = (TextView) findViewById(R.id.idCityDetail);
        vprix = (TextView) findViewById(R.id.idPrixDetail);
        vcontent = (TextView) findViewById(R.id.idContentDetail);
        vimg = (ImageView) findViewById(R.id.idImgDetail);
        share = (ImageView) findViewById(R.id.idShare);
        like = (ImageView) findViewById(R.id.idFav);
        fav = (ImageView) findViewById(R.id.idSave);
        vimgPartenaire = (ImageView) findViewById(R.id.imgPartenaire);
        vdetail = (TextView) findViewById(R.id.idDetailPiece);
        redirection = (Button) findViewById(R.id.idPlus);
        txtEnSavoirPlus = (TextView) findViewById(R.id.idTextPlus);
        vtitle = (TextView) findViewById(R.id.idTitleDetail);
        vlogo = (ImageView) findViewById(R.id.idLogoDetail);
    }

    /**
     * Recupère l'id du logo pour afficher le bon logo partenaire sur l'annonce selectionnée
     * @param logoId
     */
    private void getLogoByID(int logoId) {
        switch (logoId){
            case 0:
                vlogo.setImageDrawable(getResources().getDrawable(R.drawable.logo));
                break;
            case 1:
                vlogo.setImageDrawable(getResources().getDrawable(R.drawable.roomlala));
                break;
            default:
                vlogo.setImageDrawable(getResources().getDrawable(R.drawable.logo));
                break;
        }
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
