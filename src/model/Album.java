package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Album implements Serializable {

    private int id;
    private String titre;
    private int annee;
    private Artiste artiste;
    private Groupe groupe;
    private List<Morceau> morceaux;

    public Album(int id, String titre, int annee, Artiste artiste) {
        this.id = id;
        this.titre = titre;
        this.annee = annee;
        this.artiste = artiste;
        this.groupe = null;
        this.morceaux = new ArrayList<>();
    }

    public Album(int id, String titre, int annee, Groupe groupe) {
        this.id = id;
        this.titre = titre;
        this.annee = annee;
        this.artiste = null;
        this.groupe = groupe;
        this.morceaux = new ArrayList<>();
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

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public Artiste getArtiste() {
        return artiste;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public List<Morceau> getMorceaux() {
        return morceaux;
    }

    public void ajouterMorceau(Morceau morceau) {
        if (morceau != null && !morceaux.contains(morceau)) {
            morceaux.add(morceau);
        }
    }

    public void retirerMorceau(Morceau morceau) {
        morceaux.remove(morceau);
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

    @Override
    public String toString() {
        return "Album{id=" + id +
                ", titre='" + titre + '\'' +
                ", annee=" + annee +
                ", interprete='" + getNomInterprete() + '\'' +
                ", nbMorceaux=" + morceaux.size() +
                '}';
    }
}