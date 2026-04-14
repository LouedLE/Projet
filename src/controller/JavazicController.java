package controller;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class JavazicController {

    private static final String FICHIER_SAUVEGARDE = "sauvegarde.dat";
    private static final int LIMITE_ECOUTES_VISITEUR = 5;

    private final SystemeJavazic systeme;
    private int nombreEcoutesVisiteur;

    public JavazicController(SystemeJavazic systeme) {
        this.systeme = systeme;
        this.nombreEcoutesVisiteur = 0;
    }

    public void initialiserAdminParDefaut() {
        for (Administrateur admin : systeme.getAdministrateurs()) {
            if ("admin".equals(admin.getNomUtilisateur())) {
                return;
            }
        }
        systeme.ajouterAdministrateur(new Administrateur(1, "loued", "1234"));
    }

    public Utilisateur connecter(String login, String mdp) {
        return systeme.seConnecter(login, mdp);
    }

    public Abonne creerCompte(String login, String mdp) {
        return systeme.creerCompte(login, mdp);
    }

    public void sauvegarder() {
        systeme.sauvegarder(FICHIER_SAUVEGARDE);
    }

    public List<Morceau> getTousLesMorceaux() {
        return systeme.getCatalogue().getMorceaux();
    }

    public Morceau rechercherMorceau(String titre) {
        return systeme.getCatalogue().rechercherMorceauParTitre(titre);
    }

    public boolean peutEcouterEnVisiteur() {
        return nombreEcoutesVisiteur < LIMITE_ECOUTES_VISITEUR;
    }

    public boolean ecouterEnVisiteur(String titre) {
        Morceau morceau = rechercherMorceau(titre);
        if (morceau == null || !peutEcouterEnVisiteur()) {
            return false;
        }

        morceau.incrementerEcoutes();
        nombreEcoutesVisiteur++;
        return true;
    }

    public int getNombreEcoutesVisiteur() {
        return nombreEcoutesVisiteur;
    }

    public int getLimiteEcoutesVisiteur() {
        return LIMITE_ECOUTES_VISITEUR;
    }

    public boolean creerPlaylist(Abonne abonne, String nomPlaylist) {
        if (abonne == null || nomPlaylist == null || nomPlaylist.isBlank()) {
            return false;
        }
        abonne.creerPlaylist(abonne.getPlaylists().size() + 1, nomPlaylist);
        return true;
    }

    public boolean renommerPlaylist(Abonne abonne, String ancienNom, String nouveauNom) {
        Playlist playlist = trouverPlaylist(abonne, ancienNom);
        if (playlist == null || nouveauNom == null || nouveauNom.isBlank()) {
            return false;
        }
        abonne.renommerPlaylist(playlist, nouveauNom);
        return true;
    }

    public boolean supprimerPlaylist(Abonne abonne, String nomPlaylist) {
        Playlist playlist = trouverPlaylist(abonne, nomPlaylist);
        if (playlist == null) {
            return false;
        }
        abonne.supprimerPlaylist(playlist);
        return true;
    }

    public boolean ajouterMorceauAPlaylist(Abonne abonne, String nomPlaylist, String titreMorceau) {
        Playlist playlist = trouverPlaylist(abonne, nomPlaylist);
        Morceau morceau = rechercherMorceau(titreMorceau);

        if (playlist == null || morceau == null) {
            return false;
        }

        abonne.ajouterMorceauAPlaylist(playlist, morceau);
        return true;
    }

    public boolean retirerMorceauDePlaylist(Abonne abonne, String nomPlaylist, String titreMorceau) {
        Playlist playlist = trouverPlaylist(abonne, nomPlaylist);
        Morceau morceau = rechercherMorceau(titreMorceau);

        if (playlist == null || morceau == null) {
            return false;
        }

        abonne.retirerMorceauDePlaylist(playlist, morceau);
        return true;
    }

    public boolean ecouterCommeAbonne(Abonne abonne, String titreMorceau) {
        Morceau morceau = rechercherMorceau(titreMorceau);
        if (abonne == null || morceau == null) {
            return false;
        }

        abonne.ecouterMorceau(morceau);
        return true;
    }

    public boolean ajouterOuModifierAvis(Abonne abonne, String titreMorceau, int note, String commentaire) {
        Morceau morceau = rechercherMorceau(titreMorceau);

        if (abonne == null || morceau == null || note < 0 || note > 5) {
            return false;
        }

        morceau.ajouterOuModifierAvis(abonne, note, commentaire);
        return true;
    }

    public boolean supprimerAvis(Abonne abonne, String titreMorceau) {
        Morceau morceau = rechercherMorceau(titreMorceau);
        if (abonne == null || morceau == null) {
            return false;
        }

        morceau.supprimerAvis(abonne);
        return true;
    }

    public List<Avis> getAvisDuMorceau(String titreMorceau) {
        Morceau morceau = rechercherMorceau(titreMorceau);
        if (morceau == null) {
            return null;
        }
        return morceau.getAvis();
    }

    public boolean ajouterMorceau(String titre, double duree, String genre) {
        if (titre == null || titre.isBlank() || genre == null || genre.isBlank() || duree <= 0) {
            return false;
        }

        Morceau morceau = new Morceau(
                systeme.getCatalogue().getMorceaux().size() + 1,
                titre,
                duree,
                genre
        );
        systeme.getCatalogue().ajouterMorceau(morceau);
        return true;
    }

    public boolean supprimerMorceau(String titre) {
        Morceau morceau = rechercherMorceau(titre);
        if (morceau == null) {
            return false;
        }
        systeme.getCatalogue().supprimerMorceau(morceau);
        return true;
    }

    public boolean ajouterAlbum(String titre, int annee) {
        if (titre == null || titre.isBlank()) {
            return false;
        }

        Album album = new Album(systeme.getCatalogue().getAlbums().size() + 1, titre, annee);
        systeme.getCatalogue().ajouterAlbum(album);
        return true;
    }

    public boolean supprimerAlbum(String titre) {
        Album album = systeme.getCatalogue().rechercherAlbumParTitre(titre);
        if (album == null) {
            return false;
        }
        systeme.getCatalogue().supprimerAlbum(album);
        return true;
    }

    public boolean ajouterArtiste(String nom) {
        if (nom == null || nom.isBlank()) {
            return false;
        }

        Artiste artiste = new Artiste(systeme.getCatalogue().getArtistes().size() + 1, nom);
        systeme.getCatalogue().ajouterArtiste(artiste);
        return true;
    }

    public boolean supprimerArtiste(String nom) {
        Artiste artiste = systeme.getCatalogue().rechercherArtisteParNom(nom);
        if (artiste == null) {
            return false;
        }
        systeme.getCatalogue().supprimerArtiste(artiste);
        return true;
    }

    public boolean ajouterGroupe(String nom) {
        if (nom == null || nom.isBlank()) {
            return false;
        }

        Groupe groupe = new Groupe(systeme.getCatalogue().getGroupes().size() + 1, nom);
        systeme.getCatalogue().ajouterGroupe(groupe);
        return true;
    }

    public boolean supprimerGroupe(String nom) {
        Groupe groupe = systeme.getCatalogue().rechercherGroupeParNom(nom);
        if (groupe == null) {
            return false;
        }
        systeme.getCatalogue().supprimerGroupe(groupe);
        return true;
    }

    public boolean suspendreAbonne(String login) {
        Abonne abonne = systeme.rechercherAbonneParNomUtilisateur(login);
        if (abonne == null) {
            return false;
        }
        abonne.setSuspendu(true);
        return true;
    }

    public boolean supprimerAbonne(String login) {
        Abonne abonne = systeme.rechercherAbonneParNomUtilisateur(login);
        if (abonne == null) {
            return false;
        }
        systeme.supprimerAbonne(abonne);
        return true;
    }

    public int getNombreUtilisateurs() {
        return systeme.getNombreUtilisateurs();
    }

    public int getNombreAbonnes() {
        return systeme.getNombreAbonnes();
    }

    public int getNombreAdministrateurs() {
        return systeme.getNombreAdministrateurs();
    }

    public int getNombreMorceaux() {
        return systeme.getCatalogue().getMorceaux().size();
    }

    public int getNombreAlbums() {
        return systeme.getCatalogue().getAlbums().size();
    }

    public int getNombreArtistes() {
        return systeme.getCatalogue().getArtistes().size();
    }

    public int getNombreGroupes() {
        return systeme.getCatalogue().getGroupes().size();
    }

    public int getNombreTotalEcoutes() {
        return systeme.getNombreTotalEcoutes();
    }

    private Playlist trouverPlaylist(Abonne abonne, String nomPlaylist) {
        if (abonne == null || nomPlaylist == null) {
            return null;
        }

        for (Playlist playlist : abonne.getPlaylists()) {
            if (playlist.getNom().equalsIgnoreCase(nomPlaylist)) {
                return playlist;
            }
        }
        return null;
    }
}