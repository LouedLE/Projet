package model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Playlist implements Serializable {
    private int id;
    private String nom;
    private List<Morceau> morceaux;

    public Playlist(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.morceaux = new ArrayList<>();
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

    public List<Morceau> getMorceaux() {
        return morceaux;
    }

    public void ajouterMorceau(Morceau m) {
        if (!morceaux.contains(m)) {
            morceaux.add(m);
        }
    }

    public void retirerMorceau(Morceau m) {
        morceaux.remove(m);
    }

    public void renommer(String nouveauNom) {
        this.nom = nouveauNom;
    }

    @Override
    public String toString() {
        return "Playlist{id=" + id + ", nom='" + nom + "', nbMorceaux=" + morceaux.size() + "}";
    }
}