package com.ut.sn.citoyensn;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TeufRnsB on 17.10.2018.
 */

public class TitreAdapter extends ArrayAdapter {
    List list=new ArrayList();

    public TitreAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Titre object) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TitreHolder titreHolder;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            titreHolder = new TitreHolder();
            titreHolder.tx_nom = (TextView) row.findViewById(R.id.nom);
            titreHolder.tx_contenu = (TextView) row.findViewById(R.id.contenu);
            row.setTag(titreHolder);
        } else {
            titreHolder = (TitreHolder) row.getTag();
        }

        Titre titre = (Titre) this.getItem(position);
        titreHolder.tx_nom.setText(titre.getNom());
        titreHolder.tx_contenu.setText(titre.getContenu());

        return row;
    }

    static class TitreHolder{
        TextView tx_nom,tx_contenu;
    }
}
