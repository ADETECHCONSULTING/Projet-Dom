package com.example.jean.test.vue;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.test.R;
import com.example.jean.test.modele.ImageAsyncTask;

public class DetailActivity extends AppCompatActivity {
    private TextView vdetail;
    private TextView vredirection;
    private ImageView vimg;
    private ImageView vimgPartenaire;
    private Button redirection;
    private TextView vprix;
    private TextView vcity;
    private TextView vcontent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String urlImg = intent.getStringExtra("urlImg");
        final String url = intent.getStringExtra("url");
        String prix = intent.getStringExtra("prix");
        String city = intent.getStringExtra("city");
        String content = intent.getStringExtra("content");
        String detailMaison = intent.getStringExtra("detailMaison");
        String pays = intent.getStringExtra("pays");
        setTitle(title);

        vcity = (TextView) findViewById(R.id.idCityDetail);
        vprix = (TextView) findViewById(R.id.idPrixDetail);
        vcontent = (TextView) findViewById(R.id.idContentDetail);
        vimg = (ImageView) findViewById(R.id.idImgDetail);
        vimgPartenaire = (ImageView) findViewById(R.id.imgPartenaire);
        vdetail = (TextView) findViewById(R.id.idDetailPiece);
        redirection = (Button) findViewById(R.id.idPlus);
        vredirection = (TextView) findViewById(R.id.idTextPlus);

        new ImageAsyncTask(vimg).execute(urlImg);
        vcity.setText(city +" - "+ pays);
        vprix.setText(prix+" €");
        vdetail.setText(detailMaison);
        vcontent.setText(content);
        redirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this, ""+url, Toast.LENGTH_SHORT).show();
            }
        });

        vredirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Redirection")
                        .setMessage("Pour en savoir plus vous allez être redirigé vers l'annonceur. Voulez-vous continuez ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("Retour", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
            }
        });
    }
}
