package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Artiste implements Serializable {

    private int id;
    private String nom;
    private List<Morceau> morceaux;
    private List<Album> albums;

    public Artiste(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.morceaux = new ArrayList<>();
        this.albums = new ArrayList<>();
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

    public List<Album> getAlbums() {
        return albums;
    }

    public void ajouterMorceau(Morceau morceau) {
        if (morceau != null && !morceaux.contains(morceau)) {
            morceaux.add(morceau);
        }
    }

    public void retirerMorceau(Morceau morceau) {
        morceaux.remove(morceau);
    }

    public void ajouterAlbum(Album album) {
        if (album != null && !albums.contains(album)) {
            albums.add(album);
        }
    }

    public void retirerAlbum(Album album) {
        albums.remove(album);
    }

    @Override
    public String toString() {
        return "Artiste{id=" + id +
                ", nom='" + nom + '\'' +
                ", nbMorceaux=" + morceaux.size() +
                ", nbAlbums=" + albums.size() +
                '}';
    }
}