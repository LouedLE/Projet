package model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private static int compteur = 1;

    private int id;
    private String nom;
    private Abonne proprietaire;
    private List<Morceau> morceaux;

    public Playlist(String nom, Abonne proprietaire) {
        this.id = compteur++;
        this.nom = nom;
        this.proprietaire = proprietaire;
        this.morceaux = new ArrayList<>();
    }

    public void ajouterMorceau(Morceau morceau) {
        if (!morceaux.contains(morceau)) {
            morceaux.add(morceau);
        } else {
            System.out.println("Ce morceau est déjà dans la playlist.");
        }
    }

    public boolean retirerMorceau(Morceau morceau) {
        return morceaux.remove(morceau);
    }

    public int getDureeTotaleSecondes() {
        return morceaux.stream().mapToInt(Morceau::getDureeSecondes).sum();
    }

    // Getters / Setters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Abonne getProprietaire() { return proprietaire; }
    public List<Morceau> getMorceaux() { return morceaux; }

    @Override
    public String toString() {
        int min = getDureeTotaleSecondes() / 60;
        return String.format("[%d] \"%s\" - %d morceau(x) (%d min)", id, nom, morceaux.size(), min);
    }
}
