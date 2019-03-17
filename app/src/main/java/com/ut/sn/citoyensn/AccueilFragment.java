package com.ut.sn.citoyensn;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccueilFragment extends Fragment {
        String dataParsed;
        JSONObject jsonOject;
        JSONArray jsonArray;
        TitreAdapter titreadapter;
        ListView listView;
        TextView textView;
        JSONObject preambule;
        JSONArray paragraphes;
        OnTitreSetListener onTitreSetListener;

        public AccueilFragment() {

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_accueil, container, false);
            listView=(ListView)view.findViewById(R.id.listview);
            textView=(TextView)view.findViewById(R.id.preambule);
            titreadapter=new TitreAdapter(view.getContext(),R.layout.row_layout);
            listView.setAdapter(titreadapter);

            InputStream jsonFile=null;
            try{
                jsonFile=view.getContext().getAssets().open("fichier.json");
                int size=jsonFile.available();
                byte[] buffer=new byte[size];
                jsonFile.read(buffer);
                jsonFile.close();
                dataParsed=new String(buffer,"UTF-8");
            }
            catch (IOException e){
                e.printStackTrace();
            }
            try {
                jsonOject=new JSONObject(dataParsed);
                jsonArray=jsonOject.getJSONArray("titres");
                String nom,contenu,ps="";
                preambule=jsonArray.getJSONObject(0);
                ps+=preambule.getString("nom")+"\n\n";
                paragraphes=preambule.getJSONArray("paragraphes");
                int i=0;
                while (i<paragraphes.length()){
                    JSONObject jo1=paragraphes.getJSONObject(i);
                    ps+=jo1.getString("contenu")+"\n\n";
                    i++;
                }
                textView.setText(ps);
                int count=1;
                while (count<jsonArray.length()){
                    JSONObject jo=jsonArray.getJSONObject(count);
                    nom=jo.getString("nom");
                    contenu=jo.getString("contenu");
                    Titre titre=new Titre(nom,contenu);
                    titreadapter.add(titre);
                    count++;
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Titre titre1=(Titre)titreadapter.getItem(position);
                    onTitreSetListener.setTitre(titre1.getNom());
                }
            });

            return view;
        }

        public interface OnTitreSetListener{
            public void setTitre(String nom);
        }

        @Override
        public void onAttach(Context activity) {
            super.onAttach(activity);
            Activity activity1=(Activity)activity;
            try {
                onTitreSetListener = (OnTitreSetListener) activity1;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

}
