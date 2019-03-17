package com.ut.sn.citoyensn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CodeActivite extends AppCompatActivity {
    Toolbar toolbar;
    int itemselectionne=1;
    ListView lv;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codeshome);
        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv=findViewById(R.id.listviewcode);
        ArrayList<String> items=new ArrayList<>();
        items.add("Code de l'eau");
        items.add("Code de l'environnement");
        items.add("Code de la sécurité sociale");
        items.add("Code pétrolier");
        items.add("Code des télécommunications");
        items.add("Code des collectivités locales");
        items.add("Code des marchés publics");
        items.add("Code des douanes");
        items.add("Code minier");
        items.add("Code forestier");
        adapter=new ArrayAdapter(this,R.layout.rowarticles1,items);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(CodeActivite.this, adapter.getItem(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });



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
        switch (item.getItemId()) {
            case R.id.recherche_id:
                Toast.makeText(this, "Icon recherche", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.petit_id:
                Toast.makeText(this, "Icon petit", Toast.LENGTH_SHORT).show();
                item.setChecked(true);
                itemselectionne = 1;
                return true;
            case R.id.moyen_id:
                Toast.makeText(this, "Icon moyen", Toast.LENGTH_SHORT).show();
                item.setChecked(true);
                itemselectionne = 2;
                return true;
            case R.id.grand_id:
                Toast.makeText(this, "Icon grand", Toast.LENGTH_SHORT).show();
                item.setChecked(true);
                itemselectionne = 3;
                return true;
            case R.id.theme_id:
                Toast.makeText(this, "Icon theme", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
