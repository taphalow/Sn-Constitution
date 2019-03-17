package com.ut.sn.citoyensn;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        dialog=new Dialog(this);
    }
    public void onClik(View view){
        switch (view.getId()){
            case R.id.card1 :
                Intent constitution=new Intent(Dashboard.this,ConstitutionHome.class);
                startActivity(constitution);
                break;
            case R.id.card2 :
                Intent codes=new Intent(Dashboard.this,CodeActivite.class);
                startActivity(codes);
                break;
            case R.id.card3 :
                Toast.makeText(this, "CardView 3 cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.card4 :
                Toast.makeText(this, "CardView 4 cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.card5 :
                Toast.makeText(this, "CardView 5 cliqué", Toast.LENGTH_SHORT).show();
                break;
            case R.id.card6 :
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
                break;
        }
    }
    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Etes vous sur de vouloir quitter?").setCancelable(false).setPositiveButton("oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            AlertDialog alert=builder.create();
            alert.show();
            return  true;
        }
        else
            return super.onKeyDown(keyCode, event);
    }

}
