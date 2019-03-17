package com.ut.sn.citoyensn;

/**
 * Created by TeufRnsB on 17.10.2018.
 */

public class Titre {
    private String nom,contenu;

    public Titre(String nom, String contenu) {
        this.setNom(nom);
        this.setContenu(contenu);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}
