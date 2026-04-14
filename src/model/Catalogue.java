package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Catalogue implements Serializable {

    private List<Morceau> morceaux;
    private List<Album> albums;
    private List<Artiste> artistes;
    private List<Groupe> groupes;

    public Catalogue() {
        this.morceaux = new ArrayList<>();
        this.albums = new ArrayList<>();
        this.artistes = new ArrayList<>();
        this.groupes = new ArrayList<>();
    }

    public List<Morceau> getMorceaux() {
        return morceaux;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public List<Artiste> getArtistes() {
        return artistes;
    }

    public List<Groupe> getGroupes() {
        return groupes;
    }

    public void ajouterMorceau(Morceau morceau) {
        if (morceau != null && !morceaux.contains(morceau)) {
            morceaux.add(morceau);
        }
    }

    public void supprimerMorceau(Morceau morceau) {
        morceaux.remove(morceau);
    }

    public void ajouterAlbum(Album album) {
        if (album != null && !albums.contains(album)) {
            albums.add(album);
        }
    }

    public void supprimerAlbum(Album album) {
        albums.remove(album);
    }

    public void ajouterArtiste(Artiste artiste) {
        if (artiste != null && !artistes.contains(artiste)) {
            artistes.add(artiste);
        }
    }

    public void supprimerArtiste(Artiste artiste) {
        artistes.remove(artiste);
    }

    public void ajouterGroupe(Groupe groupe) {
        if (groupe != null && !groupes.contains(groupe)) {
            groupes.add(groupe);
        }
    }

    public void supprimerGroupe(Groupe groupe) {
        groupes.remove(groupe);
    }

    public Morceau rechercherMorceauParTitre(String titre) {
        for (Morceau m : morceaux) {
            if (m.getTitre().equalsIgnoreCase(titre)) {
                return m;
            }
        }
        return null;
    }

    public Album rechercherAlbumParTitre(String titre) {
        for (Album a : albums) {
            if (a.getTitre().equalsIgnoreCase(titre)) {
                return a;
            }
        }
        return null;
    }

    public Artiste rechercherArtisteParNom(String nom) {
        for (Artiste a : artistes) {
            if (a.getNom().equalsIgnoreCase(nom)) {
                return a;
            }
        }
        return null;
    }

    public Groupe rechercherGroupeParNom(String nom) {
        for (Groupe g : groupes) {
            if (g.getNom().equalsIgnoreCase(nom)) {
                return g;
            }
        }
        return null;
    }

    public List<Morceau> rechercherMorceauxParArtiste(String nomArtiste) {
        Artiste artiste = rechercherArtisteParNom(nomArtiste);
        if (artiste != null) {
            return artiste.getMorceaux();
        }
        return new ArrayList<>();
    }

    public List<Morceau> rechercherMorceauxParGroupe(String nomGroupe) {
        Groupe groupe = rechercherGroupeParNom(nomGroupe);
        if (groupe != null) {
            return groupe.getMorceaux();
        }
        return new ArrayList<>();
    }

    public List<Album> rechercherAlbumsParArtiste(String nomArtiste) {
        Artiste artiste = rechercherArtisteParNom(nomArtiste);
        if (artiste != null) {
            return artiste.getAlbums();
        }
        return new ArrayList<>();
    }

    public List<Album> rechercherAlbumsParGroupe(String nomGroupe) {
        Groupe groupe = rechercherGroupeParNom(nomGroupe);
        if (groupe != null) {
            return groupe.getAlbums();
        }
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Catalogue{morceaux=" + morceaux.size() +
                ", albums=" + albums.size() +
                ", artistes=" + artistes.size() +
                ", groupes=" + groupes.size() +
                '}';
    }
}