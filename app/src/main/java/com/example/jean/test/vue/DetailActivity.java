package com.example.jean.test.vue;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.test.R;
import com.example.jean.test.modele.Annonce;

import java.util.ArrayList;

import de.cketti.mailto.EmailIntentBuilder;

public class DetailActivity extends AppCompatActivity {
    private TextView vdetail;
    private TextView txtEnSavoirPlus;
    private ImageView vimg;
    private ImageView vimgPartenaire;
    private Button redirection;
    private TextView vprix;
    private String title;
    private String[] urlImg;
    private String url;
    private String prix;
    private String city;
    private String content;
    private String detailMaison;
    private String pays = "";
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
    private LinearLayout linearHS;
    private String[] lesPhotos;
    private String solourl;
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
                    Toast.makeText(DetailActivity.this, "L'annonce a été ajouté à vos coups de coeurs", Toast.LENGTH_SHORT).show();
                    like.setImageDrawable(getResources().getDrawable(R.drawable.favorite_black_48x48));
                    boolLike = true;
                }else{
                    like.setImageDrawable(getResources().getDrawable(R.drawable.favorite_outline_black_48x48));
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
                    fav.setImageDrawable(getResources().getDrawable(R.drawable.star_black_48x48));
                    boolFavoris = true;
                }else{
                    fav.setImageDrawable(getResources().getDrawable(R.drawable.star_outline_black_48x48));
                    boolFavoris = false;
                }
            }
        });

        /**
         * Gestion du logo partenaire
         */
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
        vtitle.setText(title);
        vcity.setText(city +" - "+ pays);
        vprix.setText(prix+" €");
        vdetail.setText(detailMaison);
        getLogoByID(logoId);
        vcontent.setText(content);
        //new ImageAsyncTask(vimg).execute(solourl);
    }

    /**
     * Récupère les données de la page précedente via l'intent
     */
    private void RecupIntent() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        urlImg = intent.getStringArrayExtra("urlImg");
        url = intent.getStringExtra("url");
        prix = intent.getStringExtra("prix");
        city = intent.getStringExtra("city");
        content = intent.getStringExtra("content");
        detailMaison = intent.getStringExtra("detailMaison");
        pays = intent.getStringExtra("pays");
        logoId = intent.getIntExtra("logoId",0);
        pos = intent.getIntExtra("position", 0);
        id = intent.getStringExtra("id");
        solourl = intent.getStringExtra("soloimg");
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
        linearHS = (LinearLayout) findViewById(R.id.linearDetail);
    }

    /**
     * Recupère l'id du logo pour afficher le bon logo partenaire sur l'annonce selectionnée (logoId à regler)
     * @param logoId
     */
    private void getLogoByID(int logoId) {
        switch (logoId){
            case 0:
                vlogo.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.roomlala, 108,108));
                break;
            case 1:
                vlogo.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.logo, 108,108));
                break;
            default:
                vlogo.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.logo, 108,108));
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

    public String caractereASupprimer(String url){
        String _url = "";
        if(url.contains("null")){
            _url = url.replace("null","");
        }else{
            _url = url;
        }
        return _url;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
