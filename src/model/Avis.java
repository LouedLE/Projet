package model;

import java.io.Serializable;

public class Avis implements Serializable {

    private int auteurId;
    private String auteurNom;
    private int note;
    private String commentaire;

    public Avis(Abonne auteur, int note, String commentaire) {
        this.auteurId = auteur.getId();
        this.auteurNom = auteur.getNomUtilisateur();
        this.note = note;
        this.commentaire = commentaire;
    }

    public int getAuteurId() {
        return auteurId;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return auteurNom + " | Note: " + note + " | " + commentaire;
    }
}