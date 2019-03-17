package com.ut.sn.citoyensn;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RechercheFragment extends Fragment {

    ListView lv;
    String dataParsed;
    JSONObject jsonOject;
    JSONArray jsonArray;
    ArrayAdapter adapter;
    EditText editText;
    OnArticleclick1 onArticleclick;
    public RechercheFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_recherche, container, false);

        editText=view.findViewById(R.id.searchFilter);


        lv=view.findViewById(R.id.listSearch);
        ArrayList<String> items=new ArrayList<>();

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
            int count=1;
            while (count<jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);
                JSONArray articles = jo.getJSONArray("articles");
                String result = "";
                int i = 0;
                while (i < articles.length()) {
                    JSONObject joar = articles.getJSONObject(i);
                    result = joar.getString("nom");
                    JSONArray paragraphes = joar.getJSONArray("paragraphes");
                    result += " : " + (paragraphes.getJSONObject(0)).getString("contenu");
                    items.add(result);
                    i++;
                }
                count++;
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        adapter=new ArrayAdapter(view.getContext(),R.layout.rowarticles1,items);
        lv.setAdapter(adapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (RechercheFragment.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onArticleclick.articleclicke1(i);
            }
        });
        return view;
    }

    public interface OnArticleclick1{
        public void articleclicke1(int index);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity=(Activity)context;
        try {
            onArticleclick=(OnArticleclick1)activity;
        }
        catch (Exception e){}
    }
}
