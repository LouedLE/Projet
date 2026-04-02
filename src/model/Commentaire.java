package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Commentaire {
    private static int compteur = 1;

    private int id;
    private String contenu;
    private Abonne auteur;
    private LocalDateTime dateCreation;

    public Commentaire(String contenu, Abonne auteur) {
        this.id = compteur++;
        this.contenu = contenu;
        this.auteur = auteur;
        this.dateCreation = LocalDateTime.now();
    }

    // Getters
    public int getId() { return id; }
    public String getContenu() { return contenu; }
    public Abonne getAuteur() { return auteur; }
    public LocalDateTime getDateCreation() { return dateCreation; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("[%s] %s : \"%s\"", dateCreation.format(fmt), auteur.getPseudo(), contenu);
    }
}
