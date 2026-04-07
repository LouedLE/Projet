package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Morceau {

    // Attributs
    private int id;
    private String titre;
    private double duree;
    private String genre;

    // Constructeur
    public Morceau(int id, String titre, double duree, String genre) {
        this.id = id;
        this.titre = titre;
        this.duree = duree;
        this.genre = genre;
    }
    // Getters
    public int getId() {
        return id;
    }
    public String getTitre() {
        return titre;
    }
    public double getDuree() {
        return duree;
    }
    public String getGenre() {
        return genre;
    }
    // Setters (optionnels)
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public void setDuree(double duree) {
        this.duree = duree;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    // Méthode lireExtrait
    public void lireExtrait() {
        System.out.println("Lecture d'un extrait de : " + titre + "...");
    }
    // toString
    @Override
    public String toString() {
        return "Morceau{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", duree=" + duree +
                ", genre='" + genre + '\'' +
                '}';
    }
}
