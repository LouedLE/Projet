package model;

import java.io.Serializable;

public abstract class Utilisateur implements Serializable {
    protected int id;
    protected String nomUtilisateur;
    protected String motDePasse;

    public Utilisateur(int id, String nomUtilisateur, String motDePasse) {
        this.id = id;
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
    }

    public int getId() {
        return id;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return "Utilisateur{id=" + id + ", nomUtilisateur='" + nomUtilisateur + "'}";
    }
}