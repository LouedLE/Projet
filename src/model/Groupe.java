package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Groupe implements Serializable {
    private int id;
    private String nom;
    private List<Morceau> morceaux;

    public Groupe(int id, String nom) {
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

    public void ajouterMorceau(Morceau morceau) {
        if (morceau != null && !morceaux.contains(morceau)) {
            morceaux.add(morceau);
        }
    }

    public void retirerMorceau(Morceau morceau) {
        morceaux.remove(morceau);
    }

    @Override
    public String toString() {
        return "Groupe{id=" + id + ", nom='" + nom + "'}";
    }
}