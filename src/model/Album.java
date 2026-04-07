package model;

import java.util.ArrayList;
import java.util.List;

public class Album {
    // Attributs
    private int id;
    private String titre;
    private int annee;
    // Constructeur
    public Album(int id, String titre, int annee) {
        this.id = id;
        this.titre = titre;
        this.annee = annee;
    }
    // Getters
    public int getId() {
        return id;
    }
    public String getTitre() {
        return titre;
    }
    public int getAnnee() {
        return annee;
    }
    // Setters
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public void setAnnee(int annee) {
        this.annee = annee;
    }
    // toString
    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", annee=" + annee +
                '}';
    }
}
