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
    private List<Avis> avis = new ArrayList<>();

    public Morceau(int id, String titre, double duree, String genre) {
        this.id = id;
        this.titre = titre;
        this.duree = duree;
        this.genre = genre;
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

    public void ajouterOuModifierAvis(Abonne abonne, int note, String commentaire) {

        for (Avis a : avis) {
            if (a.getAuteurId() == abonne.getId()) {
                a.setNote(note);
                a.setCommentaire(commentaire);
                System.out.println("Avis modifié !");
                return;
            }
        }

        avis.add(new Avis(abonne, note, commentaire));
        System.out.println("Avis ajouté !");
    }

    public void supprimerAvis(Abonne abonne) {

        boolean removed = avis.removeIf(a -> a.getAuteurId() == abonne.getId());

        if (removed) {
            System.out.println("Avis supprimé !");
        } else {
            System.out.println("Aucun avis à supprimer");
        }
    }

    public void afficherAvis() {

        if (avis.isEmpty()) {
            System.out.println("Aucun avis pour ce morceau");
            return;
        }

        System.out.println("Avis du morceau :");

        for (Avis a : avis) {
            System.out.println(a);
        }
    }

    public List<Avis> getAvis() {
        return avis;
    }

    @Override
    public String toString() {
        return "Morceau{id=" + id + ", titre='" + titre + "', duree=" + duree
                + ", genre='" + genre + "', ecoutes=" + nombreEcoutes + "}";
    }
}