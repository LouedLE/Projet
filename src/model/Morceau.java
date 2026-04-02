package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Morceau {
    private static int compteur = 1;

    private int id;
    private String titre;
    private int dureeSecondes;
    private String genre;
    private Album album;
    private List<Artiste> artistes;
    private List<Commentaire> commentaires;
    private List<Integer> notes; // notes de 1 à 5
    private boolean extraitDisponible;

    public Morceau(String titre, int dureeSecondes, String genre, boolean extraitDisponible) {
        this.id = compteur++;
        this.titre = titre;
        this.dureeSecondes = dureeSecondes;
        this.genre = genre;
        this.extraitDisponible = extraitDisponible;
        this.artistes = new ArrayList<>();
        this.commentaires = new ArrayList<>();
        this.notes = new ArrayList<>();
    }

    public void ajouterArtiste(Artiste artiste) {
        if (!artistes.contains(artiste)) {
            artistes.add(artiste);
        }
    }

    public void ajouterCommentaire(Commentaire commentaire) {
        commentaires.add(commentaire);
    }

    public void ajouterNote(int note) {
        if (note < 1 || note > 5) throw new IllegalArgumentException("La note doit être entre 1 et 5.");
        notes.add(note);
    }

    public double getMoyenneNotes() {
        if (notes.isEmpty()) return 0.0;
        return notes.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public String getDureeFormatee() {
        int min = dureeSecondes / 60;
        int sec = dureeSecondes % 60;
        return String.format("%d:%02d", min, sec);
    }

    // Getters / Setters
    public int getId() { return id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public int getDureeSecondes() { return dureeSecondes; }
    public void setDureeSecondes(int dureeSecondes) { this.dureeSecondes = dureeSecondes; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public Album getAlbum() { return album; }
    public void setAlbum(Album album) { this.album = album; }
    public List<Artiste> getArtistes() { return artistes; }
    public List<Commentaire> getCommentaires() { return commentaires; }
    public List<Integer> getNotes() { return notes; }
    public boolean isExtraitDisponible() { return extraitDisponible; }
    public void setExtraitDisponible(boolean extraitDisponible) { this.extraitDisponible = extraitDisponible; }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) - %s | Note: %.1f/5",
                id, titre, getDureeFormatee(), genre, getMoyenneNotes());
    }
}
