package model;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private static int compteur = 1;

    private int id;
    private String titre;
    private int annee;
    private String genre;
    private Artiste artiste;
    private Groupe groupe;
    private List<Morceau> morceaux;

    // Constructeur pour un album d'artiste solo
    public Album(String titre, int annee, String genre, Artiste artiste) {
        this.id = compteur++;
        this.titre = titre;
        this.annee = annee;
        this.genre = genre;
        this.artiste = artiste;
        this.morceaux = new ArrayList<>();
    }

    // Constructeur pour un album de groupe
    public Album(String titre, int annee, String genre, Groupe groupe) {
        this.id = compteur++;
        this.titre = titre;
        this.annee = annee;
        this.genre = genre;
        this.groupe = groupe;
        this.morceaux = new ArrayList<>();
    }

    public void ajouterMorceau(Morceau morceau) {
        if (!morceaux.contains(morceau)) {
            morceaux.add(morceau);
            morceau.setAlbum(this);
        }
    }

    public void retirerMorceau(Morceau morceau) {
        morceaux.remove(morceau);
    }

    public int getDureeTotaleSecondes() {
        return morceaux.stream().mapToInt(Morceau::getDureeSecondes).sum();
    }

    public String getAuteur() {
        if (artiste != null) return artiste.getNom();
        if (groupe != null) return groupe.getNom();
        return "Inconnu";
    }

    // Getters / Setters
    public int getId() { return id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public int getAnnee() { return annee; }
    public void setAnnee(int annee) { this.annee = annee; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public Artiste getArtiste() { return artiste; }
    public Groupe getGroupe() { return groupe; }
    public List<Morceau> getMorceaux() { return morceaux; }

    @Override
    public String toString() {
        return String.format("[%d] \"%s\" - %s (%d) | %d morceau(x)", id, titre, getAuteur(), annee, morceaux.size());
    }
}
