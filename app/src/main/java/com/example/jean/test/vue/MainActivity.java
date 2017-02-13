package com.example.jean.test.vue;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.test.R;
import com.example.jean.test.modele.Annonce;
import com.example.jean.test.modele.Filtre;
import com.example.jean.test.modele.Ville;

import java.util.ArrayList;

import de.cketti.mailto.EmailIntentBuilder;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView = null;
    private Toolbar toolbar = null;
    private Intent intent;
    private String email;
    private String contenu;
    private String name;
    private TextView txtEmail;
    private TextView txtName;
    private MainFragment mainFragment;
    private LoginFragment loginFragment;
    private ArrayList<Filtre> lesFiltres;
    private ArrayList<String> selectedFiltres;
    private ArrayList<Ville> lesVilles;
    public ArrayList<Annonce> mesAnnoncesFavorites;
    public ArrayList<Annonce> mesAnnoncesLike;

    private InscriptionFragment inscriptionFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().getStringExtra("email") != null){
            intent = getIntent();
            email = intent.getStringExtra("email");
            name = intent.getStringExtra("name");
            txtEmail = (TextView) findViewById(R.id.email);
            txtName = (TextView) findViewById(R.id.name);
            txtEmail.setText(email);
            txtName.setText(name);
        }

        if(getIntent().getParcelableArrayExtra("annoncesLike") != null){
            mesAnnoncesLike = getIntent().getParcelableArrayListExtra("annoncesLike");
            mesAnnoncesFavorites = getIntent().getParcelableArrayListExtra("annonceFav");
        }
        //Activer le fragment
        mainFragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, mainFragment);
        fragmentTransaction.commit();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Un bug envoyez-nous le rapport d'erreur !", Snackbar.LENGTH_LONG)
                        .setAction("Envoyez", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Ecrivez-nous");
                                final EditText input = new EditText(MainActivity.this);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                builder.setView(input);

                                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        contenu = input.getText().toString();
                                        sendEmail(contenu);
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                builder.show();
                            }
                        }).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void sendEmail(String contenu) {
        Intent emailIntent = EmailIntentBuilder.from(MainActivity.this)
                .to("a.traore@domoum.com")
                .subject("Correctifs: Application Domoum")
                .body(contenu)
                .build();
        try{
            startActivity(emailIntent);
        }catch (Exception e){
            Toast.makeText(this, "Il vous faut un support de messagerie. Par exemple : Gmail, Yahoo, etc...", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if(count == 0){
            super.onBackPressed();
        }else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        int id = item.getItemId();
        if(id == R.id.nav_home){
            MainFragment mainFragment = new MainFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, mainFragment);
            fragmentTransaction.addToBackStack("home");
            fragmentTransaction.commit();
        }else if (id == R.id.nav_connexion) {
            loginFragment = new LoginFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, loginFragment);
            fragmentTransaction.addToBackStack("connexion");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_inscription) {
            inscriptionFragment = new InscriptionFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, inscriptionFragment);
            fragmentTransaction.addToBackStack("inscription");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_like) {
            if(mesAnnoncesLike == null) {
                Toast.makeText(this, "Vous n'avez aucun coups de coeurs", Toast.LENGTH_SHORT).show();
            }else{
                LikeFragment likeFragment = new LikeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, likeFragment);
                fragmentTransaction.addToBackStack("mes annonces like");
                fragmentTransaction.commit();
            }
        } else if (id == R.id.nav_fav) {
            if(mesAnnoncesFavorites == null) {
                Toast.makeText(this, "Vous n'avez aucun favoris", Toast.LENGTH_SHORT).show();
            }else{
                FavFragment favFragment = new FavFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, favFragment);
                fragmentTransaction.addToBackStack("mes annonces fav");
                fragmentTransaction.commit();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setFiltre(ArrayList<Filtre> lesFiltres){
        this.lesFiltres = lesFiltres;
    }

    public ArrayList<Filtre> getLesFiltres() {
        return lesFiltres;
    }

    public ArrayList<String> getSelectedFiltres() {
        return selectedFiltres;
    }

    public void setSelectedFiltres(ArrayList<String> selectedFiltres) {
        this.selectedFiltres = selectedFiltres;
    }

    public ArrayList<Ville> getLesVilles() {
        return lesVilles;
    }

    public void setLesVilles(ArrayList<Ville> lesVilles) {
        this.lesVilles = lesVilles;
    }

    public ArrayList<Annonce> getMesAnnoncesFavorites() {
        return mesAnnoncesFavorites;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
