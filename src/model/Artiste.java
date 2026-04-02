package model;

import java.util.ArrayList;
import java.util.List;

public class Artiste {
    private static int compteur = 1;

    private int id;
    private String nom;
    private String biographie;
    private String nationalite;
    private List<Album> albums;
    private List<Morceau> morceaux;

    public Artiste(String nom, String biographie, String nationalite) {
        this.id = compteur++;
        this.nom = nom;
        this.biographie = biographie;
        this.nationalite = nationalite;
        this.albums = new ArrayList<>();
        this.morceaux = new ArrayList<>();
    }

    public void ajouterAlbum(Album album) {
        if (!albums.contains(album)) albums.add(album);
    }

    public void ajouterMorceau(Morceau morceau) {
        if (!morceaux.contains(morceau)) morceaux.add(morceau);
    }

    // Getters / Setters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getBiographie() { return biographie; }
    public void setBiographie(String biographie) { this.biographie = biographie; }
    public String getNationalite() { return nationalite; }
    public void setNationalite(String nationalite) { this.nationalite = nationalite; }
    public List<Album> getAlbums() { return albums; }
    public List<Morceau> getMorceaux() { return morceaux; }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) - %d album(s)", id, nom, nationalite, albums.size());
    }
}
