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
 */
public class ArticleFavoris extends Fragment {
    String dataParsed,shareBody="";;
    JSONObject jsonOject;
    JSONArray jsonArray;
    int  index;
    View view;
    DatabaseManager databaseManager;
    OnArticletRetour onArticletRetour;

    TextView textView1,textView2,textView3,textShare,textFavoris;

    public ArticleFavoris() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_article_favoris, container, false);

        textView1=view.findViewById(R.id.textretourfavoris);
        textView2=view.findViewById(R.id.nomarticlefavoris);
        textView3=view.findViewById(R.id.textArticlefavoris);
        shareBody="CONSTITUTION DE LA REPUBLIQUE DU SENEGAL DU 22 JANVIER 2001";

        String s=" Retour aux favoris ";
        SpannableString ss=new SpannableString(s);
        ClickableSpan clik=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                onArticletRetour.articleRetour();
            }
        };
        ss.setSpan(clik,0,s.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView1.setText(ss);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());

        Bundle bundle=getArguments();
        index=bundle.getInt("index");
        textFavoris=view.findViewById(R.id.articlefavoristext);
        databaseManager=new DatabaseManager(view.getContext());
        if(databaseManager.searchArtcile(index)){
            textFavoris.setCompoundDrawablesWithIntrinsicBounds(view.getContext().getResources().getDrawable(R.drawable.staron), null, null, null);
        }
        else {
            textFavoris.setCompoundDrawablesWithIntrinsicBounds(view.getContext().getResources().getDrawable(R.drawable.staroff), null, null, null);
        }

        String s2= "";
        SpannableString ss2=new SpannableString(s2);
        ClickableSpan clik2=new ClickableSpan() {
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
        ss2.setSpan(clik2,0,s2.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textFavoris.setText(ss2);
        textFavoris.setMovementMethod(LinkMovementMethod.getInstance());

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
            while (count<jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                JSONArray articles = jo.getJSONArray("articles");
                int i=0;
                while (i<articles.length()) {
                    JSONObject joar = articles.getJSONObject(i);
                    if (index==joar.getInt("index")){
                        shareBody+="\n\n"+joar.getString("nom");
                        textView2.setText(joar.getString("nom"));
                        JSONArray paragraphes = joar.getJSONArray("paragraphes");
                        int j=0;
                        while (j<paragraphes.length()){
                            result += (paragraphes.getJSONObject(j)).getString("contenu")+"\n\n";
                            j++;
                        }
                        shareBody+=result;
                        textView3.setText(result);
                        break;
                    }
                    i++;
                }
                count++;
            }
        }
        catch (Exception e){}
        textShare=view.findViewById(R.id.Sharearticlefavoris);
        String s1= "";
        SpannableString ss1=new SpannableString(s1);
        ClickableSpan clik1=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent share=new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(share,"Share Via"));
            }
        };
        ss1.setSpan(clik1,0,s1.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textShare.setText(ss1);
        textShare.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }

    public interface OnArticletRetour{
        public  void articleRetour();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity=(Activity)context;
        try{
            onArticletRetour=(OnArticletRetour) activity;
        }
        catch (Exception  e){}
    }
}
