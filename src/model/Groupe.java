package model;

import java.io.Serializable;

public class Groupe implements Serializable {
    private int id;
    private String nom;

    public Groupe(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Groupe{id=" + id + ", nom='" + nom + "'}";
    }
}