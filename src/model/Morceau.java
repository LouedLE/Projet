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

    private List<Avis> avis = new ArrayList<>();

    public Morceau(int id, String titre, double duree, String genre, Artiste artiste) {
        this.id = id;
        this.titre = titre;
        this.duree = duree;
        this.genre = genre;
        this.artiste = artiste;
        this.groupe = null;
        this.nombreEcoutes = 0;
    }

    public Morceau(int id, String titre, double duree, String genre, Groupe groupe) {
        this.id = id;
        this.titre = titre;
        this.duree = duree;
        this.genre = genre;
        this.groupe = groupe;
        this.artiste = null;
        this.nombreEcoutes = 0;
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

    public String getNomInterprete() {
        if (artiste != null) {
            return artiste.getNom();
        }
        if (groupe != null) {
            return groupe.getNom();
        }
        return "Inconnu";
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

    public void supprimerAvis(Abonne abonne) {
        avis.removeIf(a -> a.getAuteurId() == abonne.getId());
    }

    @Override
    public String toString() {
        return "Morceau{id=" + id + ", titre='" + titre + '\'' + ", duree=" + duree + ", genre='" + genre + '\'' + ", interprete='" + getNomInterprete() + '\'' + ", ecoutes=" + nombreEcoutes + '}';
    }
}