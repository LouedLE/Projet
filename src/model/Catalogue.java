package model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

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

    public void ajouterMorceau(Morceau m) {
        if (!morceaux.contains(m)) {
            morceaux.add(m);
        }
    }

    public void supprimerMorceau(Morceau m) {
        morceaux.remove(m);
    }

    public void ajouterAlbum(Album a) {
        if (!albums.contains(a)) {
            albums.add(a);
        }
    }

    public void supprimerAlbum(Album a) {
        albums.remove(a);
    }

    public void ajouterArtiste(Artiste a) {
        if (!artistes.contains(a)) {
            artistes.add(a);
        }
    }

    public void supprimerArtiste(Artiste a) {
        artistes.remove(a);
    }

    public void ajouterGroupe(Groupe g) {
        if (!groupes.contains(g)) {
            groupes.add(g);
        }
    }

    public void supprimerGroupe(Groupe g) {
        groupes.remove(g);
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

    @Override
    public String toString() {
        return "Catalogue{morceaux=" + morceaux.size() +  ", albums=" + albums.size() +  ", artistes=" + artistes.size() +  ", groupes=" + groupes.size() + "}";
    }
}