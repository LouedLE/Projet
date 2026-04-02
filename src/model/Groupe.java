package model;

import java.util.ArrayList;
import java.util.List;

public class Groupe {
    private static int compteur = 1;

    private int id;
    private String nom;
    private String description;
    private int anneeCreation;
    private List<Artiste> membres;
    private List<Album> albums;

    public Groupe(String nom, String description, int anneeCreation) {
        this.id = compteur++;
        this.nom = nom;
        this.description = description;
        this.anneeCreation = anneeCreation;
        this.membres = new ArrayList<>();
        this.albums = new ArrayList<>();
    }

    public void ajouterMembre(Artiste artiste) {
        if (!membres.contains(artiste)) membres.add(artiste);
    }

    public void retirerMembre(Artiste artiste) {
        membres.remove(artiste);
    }

    public void ajouterAlbum(Album album) {
        if (!albums.contains(album)) albums.add(album);
    }

    // Getters / Setters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getAnneeCreation() { return anneeCreation; }
    public void setAnneeCreation(int anneeCreation) { this.anneeCreation = anneeCreation; }
    public List<Artiste> getMembres() { return membres; }
    public List<Album> getAlbums() { return albums; }

    @Override
    public String toString() {
        return String.format("[%d] %s (fondé en %d) - %d membre(s)", id, nom, anneeCreation, membres.size());
    }
}
