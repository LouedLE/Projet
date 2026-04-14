package model;

import java.io.Serializable;

public class Morceau implements Serializable {
    private int id;
    private String titre;
    private double duree;
    private String genre;

    public Morceau(int id, String titre, double duree, String genre) {
        this.id = id;
        this.titre = titre;
        this.duree = duree;
        this.genre = genre;
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

    public double getDuree() {
        return duree;
    }

    public void setDuree(double duree) {
        this.duree = duree;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Morceau{id=" + id + ", titre='" + titre + "', duree=" + duree + ", genre='" + genre + "'}";
    }
}