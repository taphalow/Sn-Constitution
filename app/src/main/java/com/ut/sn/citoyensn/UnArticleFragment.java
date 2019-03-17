package com.ut.sn.citoyensn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.

 * create an instance of this fragment.
 */
public class UnArticleFragment extends Fragment {

    TextView nomTitreView,textretour,article,textShare,textFavoris;

    String dataParsed,shareBody="";;
    JSONObject jsonOject;
    JSONArray jsonArray;
    OnretourClick onretourClick;
    View view;
    int index;
    DatabaseManager databaseManager;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            static int status=0;

    public UnArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_un_article, container, false);
        article=view.findViewById(R.id.textArticle);
        nomTitreView=view.findViewById(R.id.nomTitre);
        textretour=view.findViewById(R.id.textretour);
        shareBody="CONSTITUTION DE LA REPUBLIQUE DU SENEGAL DU 22 JANVIER 2001";

        Bundle bundle=getArguments();
        final String nomTitre=bundle.getString("nomTitre");
        int nArticle=bundle.getInt("nArticle");
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
            String result="";
            jsonOject=new JSONObject(dataParsed);
            jsonArray=jsonOject.getJSONArray("titres");
            int count=1;
            while (count<jsonArray.length()){
                JSONObject jo=jsonArray.getJSONObject(count);
                if (nomTitre.equals(jo.getString("nom"))) {
                    nomTitreView.setText(nomTitre+" : "+jo.getString("contenu"));
                    JSONArray articles = jo.getJSONArray("articles");
                    JSONObject joar = articles.getJSONObject(nArticle);
                    result += joar.getString("nom") + "\n\n";
                    index=joar.getInt("index");
                    JSONArray paragraphes = joar.getJSONArray("paragraphes");
                    int i=0;
                    while (i<paragraphes.length()){
                        result += (paragraphes.getJSONObject(i)).getString("contenu")+"\n\n";
                        i++;
                    }
                    String s= nomTitre + " ";
                    SpannableString ss=new SpannableString(s);
                    ClickableSpan clik=new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View view) {
                            onretourClick.retour(nomTitre);
                        }
                    };
                    ss.setSpan(clik,0,s.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textretour.setText(ss);
                    textretour.setMovementMethod(LinkMovementMethod.getInstance());
                    shareBody+="\n\n"+result;
                    article.setText(result);
                    break;
                }
                count++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        textFavoris=view.findViewById(R.id.textFavoris);
        databaseManager=new DatabaseManager(view.getContext());
        if(databaseManager.searchArtcile(index)){
            textFavoris.setCompoundDrawablesWithIntrinsicBounds(view.getContext().getResources().getDrawable(R.drawable.staron), null, null, null);
        }
        else {
            textFavoris.setCompoundDrawablesWithIntrinsicBounds(view.getContext().getResources().getDrawable(R.drawable.staroff), null, null, null);
        }
        String s1= "";
        SpannableString ss1=new SpannableString(s1);
        ClickableSpan clik1=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view1) {
                if( ! databaseManager.searchArtcile(index)) {
                    textFavoris.setCompoundDrawablesWithIntrinsicBounds(view.getContext().getResources().getDrawable(R.drawable.staron), null, null, null);
                    databaseManager.insertAtilce(index);
                }
                else {
                    textFavoris.setCompoundDrawablesWithIntrinsicBounds(view.getContext().getResources().getDrawable(R.drawable.staroff), null, null, null);
                    databaseManager.deleteArticle(index);
                }
            }

        };
        ss1.setSpan(clik1,0,s1.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textFavoris.setText(ss1);
        textFavoris.setMovementMethod(LinkMovementMethod.getInstance());


        textShare=view.findViewById(R.id.textShare);
        String s= "";
        SpannableString ss=new SpannableString(s);
        ClickableSpan clik=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent share=new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(share,"Share Via"));
            }
        };
        ss.setSpan(clik,0,s.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textShare.setText(ss);
        textShare.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }
    public interface OnretourClick{
        public void retour(String nom);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity=(Activity)context;
        try {
            onretourClick=(OnretourClick)activity;
        }
        catch (Exception e){}
    }


    @Override
    public void onDestroy() {
        databaseManager.close();
        super.onDestroy();
    }
}
