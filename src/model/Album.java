package model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Album implements Serializable {
    private int id;
    private String titre;
    private int annee;
    private List<Morceau> morceaux;

    public Album(int id, String titre, int annee) {
        this.id = id;
        this.titre = titre;
        this.annee = annee;
        this.morceaux = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
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


    @Override
    public String toString() {
        return "Album{id=" + id + ", titre='" + titre + "', annee=" + annee + "}";
    }
}