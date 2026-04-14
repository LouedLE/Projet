package model;

import java.io.Serializable;

public class Avis implements Serializable {
    private int note;
    private String commentaire;

    public Avis(int note, String commentaire) {
        this.note = note;
        this.commentaire = commentaire;
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
        return "Avis{note=" + note + ", commentaire='" + commentaire + "'}";
    }
}