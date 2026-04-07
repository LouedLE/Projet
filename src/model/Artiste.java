package model;

import java.util.ArrayList;
import java.util.List;

public class Artiste {
    // Attributs
    private int id;
    private String nom;
    // Constructeur
    public Artiste(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    // Getters
    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    // Setter
    public void setNom(String nom) {
        this.nom = nom;
    }
    // toString
    @Override
    public String toString() {
        return "Artiste{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
