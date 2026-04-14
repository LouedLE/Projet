package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Morceau implements Serializable {

    private int id;
    private String titre;
    private double duree;
    private String genre;
    private int nombreEcoutes;

    private Artiste artiste;
    private Groupe groupe;
    private Album album;

    private List<Avis> avis;

    public Morceau(int id, String titre, double duree, String genre, Artiste artiste, Album album) {
        this.id = id;
        this.titre = titre;
        this.duree = duree;
        this.genre = genre;
        this.artiste = artiste;
        this.groupe = null;
        this.album = album;
        this.nombreEcoutes = 0;
        this.avis = new ArrayList<>();
    }

    public Morceau(int id, String titre, double duree, String genre, Groupe groupe, Album album) {
        this.id = id;
        this.titre = titre;
        this.duree = duree;
        this.genre = genre;
        this.artiste = null;
        this.groupe = groupe;
        this.album = album;
        this.nombreEcoutes = 0;
        this.avis = new ArrayList<>();
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

    public int getNombreEcoutes() {
        return nombreEcoutes;
    }

    public void incrementerEcoutes() {
        this.nombreEcoutes++;
    }

    public Artiste getArtiste() {
        return artiste;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public Album getAlbum() {
        return album;
    }

    public String getNomInterprete() {
        if (artiste != null) {
            return artiste.getNom();
        }
        if (groupe != null) {
            return groupe.getNom();
        }
        return "Inconnu";
    }

    public String getTypeInterprete() {
        if (artiste != null) {
            return "artiste";
        }
        if (groupe != null) {
            return "groupe";
        }
        return "inconnu";
    }

    public List<Avis> getAvis() {
        return avis;
    }

    public void ajouterOuModifierAvis(Abonne abonne, int note, String commentaire) {
        for (Avis a : avis) {
            if (a.getAuteurId() == abonne.getId()) {
                a.setNote(note);
                a.setCommentaire(commentaire);
                return;
            }
        }

        avis.add(new Avis(abonne, note, commentaire));
    }

    public boolean supprimerAvis(Abonne abonne) {
        return avis.removeIf(a -> a.getAuteurId() == abonne.getId());
    }

    @Override
    public String toString() {
        return "Morceau{id=" + id +
                ", titre='" + titre + '\'' +
                ", duree=" + duree +
                ", genre='" + genre + '\'' +
                ", album='" + (album != null ? album.getTitre() : "Aucun") + '\'' +
                ", interprete='" + getNomInterprete() + '\'' +
                ", ecoutes=" + nombreEcoutes +
                '}';
    }
}