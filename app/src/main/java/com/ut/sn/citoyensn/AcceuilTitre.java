package com.ut.sn.citoyensn;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class AcceuilTitre extends Fragment {
    ListView lv;
    TextView textView1,textView2;
    OnButtonClick onButtonClick;
    OntitreClick ontitreClick;

    String dataParsed,nom;
    JSONObject jsonOject;
    JSONArray jsonArray;
    ArticlesAdapter articlesAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_acceuil_titre, container, false);

        lv=(ListView) view.findViewById(R.id.listviewTitre);
        textView2=(TextView)view.findViewById(R.id.texview2);
        textView1=(TextView)view.findViewById(R.id.texview1);


        articlesAdapter=new ArticlesAdapter(view.getContext(),R.layout.rowarticles);
        lv.setAdapter(articlesAdapter);
        Bundle bundle=getArguments();
        nom=bundle.getString("nom");

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
                JSONObject jo=jsonArray.getJSONObject(count);
                if (nom.equals(jo.getString("nom"))){
                    textView2.setText(nom +" : "+jo.getString("contenu"));
                    JSONArray articles=jo.getJSONArray("articles");
                    String result="";
                    int i=0;
                    while(i<articles.length()){
                        JSONObject joar=articles.getJSONObject(i);
                        result=joar.getString("nom");
                        JSONArray paragraphes=joar.getJSONArray("paragraphes");
                        result += " : " + (paragraphes.getJSONObject(0)).getString("contenu");
                        articlesAdapter.add(result);
                        i++;
                    }
                    String text=" Accueil ";
                    SpannableString ss=new SpannableString(text);
                    ClickableSpan clik=new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View view) {
                            onButtonClick.retourner();
                        }

                    };
                    ss.setSpan(clik,0,text.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textView1.setText(ss);
                    textView1.setMovementMethod(LinkMovementMethod.getInstance());
                    break;
                }
                count++;
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ontitreClick.articleSelectionne(nom,position);
            }
        });
        return view;
    }


    public class ArticlesAdapter extends ArrayAdapter{

        List list=new ArrayList();

        public ArticlesAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }
        public void add(String object) {
            super.add(object);
            list.add(object);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Nullable
        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            ArticleHolder articleHolder;
            if (row == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.rowarticles, parent, false);
                articleHolder = new ArticleHolder();
                articleHolder.tx_nom = (TextView) row.findViewById(R.id.articlescontenu);
                row.setTag(articleHolder);
            } else {
                articleHolder = (ArticleHolder) row.getTag();
            }
            articleHolder.tx_nom.setText((this.getItem(position)).toString());

            return row;
        }

    }
    static class ArticleHolder{
        TextView tx_nom;
    }

    public interface OnButtonClick{
        public void retourner();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity=(Activity)context;
        try {
            onButtonClick=(OnButtonClick)activity;
            ontitreClick=(OntitreClick)activity;
        }
        catch (Exception e){}
    }
    public interface OntitreClick{
        public void articleSelectionne(String nomTitre,int nArticle);
    }


}
