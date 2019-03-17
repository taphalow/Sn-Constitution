package com.ut.sn.citoyensn;


import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



public class ConstitutionHome extends AppCompatActivity implements AccueilFragment.OnTitreSetListener ,
        AcceuilTitre.OnButtonClick , AcceuilTitre.OntitreClick , UnArticleFragment.OnretourClick ,
        TousLesArticles.OnArticleclick ,FragmentArticle12.OnfragmentRetour ,
        RechercheFragment.OnArticleclick1 , FavorisFragment.OnArticleclickFavoris , ArticleFavoris.OnArticletRetour {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    int itemselectionne=1;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }
        else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.constitutionhome);
        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        dialog=new Dialog(this);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new AccueilFragment());
        fragmentTransaction.commit();
        getSupportActionBar().setSubtitle("Accueil...");
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.accueil_id :
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new AccueilFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setSubtitle("Accueil...");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.favoris_id :
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new FavorisFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setSubtitle("Favoris...");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.articles_id :
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new TousLesArticles());
                        fragmentTransaction.commit();
                        getSupportActionBar().setSubtitle("Tous les articles...");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.apropos_id :
                        dialog.setContentView(R.layout.aboutlayout);
                        TextView close=dialog.findViewById(R.id.closes),propos=dialog.findViewById(R.id.aprodash);
                        String text2="\n\nJe vous serai reconnaissant de suggestions, des commentaires et des recommandations pour son amélioration."
                                +"\n\n"
                                +"CONSTITUTION DE LA REPUBLIQUE DU SENEGAL DU 22 JANVIER 2001"
                                +"\n\n\n"
                                +"Version: 1.0";
                        propos.setText(text2);

                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }



    @Override
    public void setTitre(String nom) {
        AcceuilTitre acceuilTitre=new AcceuilTitre();
        Bundle bundle=new Bundle();
        bundle.putString("nom",nom);
        acceuilTitre.setArguments(bundle);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, acceuilTitre);
        fragmentTransaction.commit();
        getSupportActionBar().setSubtitle("Accueil\\"+ nom+"\\");

    }


    @Override
    public void retourner() {
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container,new AccueilFragment());
        fragmentTransaction.commit();
        getSupportActionBar().setSubtitle("Accueil...");
    }

    @Override
    public void articleSelectionne(String nom,int nArticle) {
        UnArticleFragment unArticleFragment=new UnArticleFragment();
        Bundle bundle=new Bundle();
        bundle.putString("nomTitre",nom);
        bundle.putInt("nArticle",nArticle);
        unArticleFragment.setArguments(bundle);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, unArticleFragment);
        fragmentTransaction.commit();
        getSupportActionBar().setSubtitle("Accueil\\"+ nom+"\\");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        MenuItem itemPetit=menu.findItem(R.id.petit_id);
        MenuItem itemMoyen=menu.findItem(R.id.moyen_id);
        MenuItem itemGrand=menu.findItem(R.id.grand_id);
        if(itemselectionne==1){
            itemPetit.setChecked(true);
        }
        else if(itemselectionne==2){
            itemMoyen.setChecked(true);
        }
        else itemGrand.setChecked(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.recherche_id:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, new RechercheFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setSubtitle("Recherche par article...");
                return true;
            case R.id.petit_id:
                Toast.makeText(this, "Icon petit", Toast.LENGTH_SHORT).show();
                item.setChecked(true);
                itemselectionne=1;
                return true;
            case R.id.moyen_id:
                Toast.makeText(this, "Icon moyen", Toast.LENGTH_SHORT).show();
                item.setChecked(true);
                itemselectionne=2;
                return true;
            case R.id.grand_id:
                Toast.makeText(this, "Icon grand", Toast.LENGTH_SHORT).show();
                item.setChecked(true);
                itemselectionne=3;
                return true;
            case R.id.theme_id:
                if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restarApp();
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restarApp();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void restarApp(){
        Intent i=new Intent(this,ConstitutionHome.class);
        startActivity(i);
        //finish();
    }

    @Override
    public void retour(String nom) {
        AcceuilTitre acceuilTitre=new AcceuilTitre();
        Bundle bundle=new Bundle();
        bundle.putString("nom",nom);
        acceuilTitre.setArguments(bundle);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, acceuilTitre);
        fragmentTransaction.commit();
        getSupportActionBar().setSubtitle("Accueil\\"+ nom);
    }

    @Override
    public void articleclicke(int index) {
        FragmentArticle12 fragmentArticle12=new FragmentArticle12();
        Bundle bundle=new Bundle();
        bundle.putInt("index",index);
        fragmentArticle12.setArguments(bundle);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragmentArticle12);
        fragmentTransaction.commit();
        getSupportActionBar().setSubtitle("Tous les articles");
    }

    @Override
    public void fragmentRetour() {
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container,new TousLesArticles());
        fragmentTransaction.commit();
        getSupportActionBar().setSubtitle("Tous les articles...");
    }


    @Override
    public void articleclicke1(int index) {
        FragmentArticle12 fragmentArticle12=new FragmentArticle12();
        Bundle bundle=new Bundle();
        bundle.putInt("index",index);
        fragmentArticle12.setArguments(bundle);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragmentArticle12);
        fragmentTransaction.commit();
        getSupportActionBar().setSubtitle("Tous les articles...");
    }

    @Override
    public void articleclickefavoris(int index) {
        ArticleFavoris articleFavoris=new ArticleFavoris();
        Bundle bundle=new Bundle();
        bundle.putInt("index",index);
        articleFavoris.setArguments(bundle);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, articleFavoris);
        fragmentTransaction.commit();
        getSupportActionBar().setSubtitle("Favoris...");
    }

    @Override
    public void articleRetour() {
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container,new FavorisFragment());
        fragmentTransaction.commit();
        getSupportActionBar().setSubtitle("Favoris...");
    }
}
