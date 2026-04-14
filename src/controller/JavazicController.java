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
            if ("admin".equalsIgnoreCase(admin.getNomUtilisateur())) {
                return;
            }
        }
        systeme.ajouterAdministrateur(new Administrateur(1, "loued", "1234"));
    }

    public void sauvegarder() {
        systeme.sauvegarder(FICHIER_SAUVEGARDE);
    }

    public Utilisateur connecter(String login, String mdp) {
        return systeme.seConnecter(login, mdp);
    }

    public Abonne creerCompte(String login, String mdp) {
        return systeme.creerCompte(login, mdp);
    }

    public List<Morceau> getTousLesMorceaux() {
        return systeme.getCatalogue().getMorceaux();
    }

    public List<Album> getTousLesAlbums() {
        return systeme.getCatalogue().getAlbums();
    }

    public List<Artiste> getTousLesArtistes() {
        return systeme.getCatalogue().getArtistes();
    }

    public List<Groupe> getTousLesGroupes() {
        return systeme.getCatalogue().getGroupes();
    }

    public Morceau rechercherMorceau(String titre) {
        return systeme.getCatalogue().rechercherMorceauParTitre(titre);
    }

    public Album rechercherAlbum(String titre) {
        return systeme.getCatalogue().rechercherAlbumParTitre(titre);
    }

    public Artiste rechercherArtiste(String nom) {
        return systeme.getCatalogue().rechercherArtisteParNom(nom);
    }

    public Groupe rechercherGroupe(String nom) {
        return systeme.getCatalogue().rechercherGroupeParNom(nom);
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

    public List<Playlist> getPlaylistsAbonne(Abonne abonne) {
        return abonne.getPlaylists();
    }

    public List<Morceau> getHistoriqueAbonne(Abonne abonne) {
        return abonne.getHistorique().getMorceauxEcoutes();
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

        return morceau.supprimerAvis(abonne);
    }

    public List<Avis> getAvisDuMorceau(String titreMorceau) {
        Morceau morceau = rechercherMorceau(titreMorceau);

        if (morceau == null) {
            return null;
        }

        return morceau.getAvis();
    }

    public boolean ajouterArtiste(String nom) {
        if (nom == null || nom.isBlank()) {
            return false;
        }

        if (systeme.getCatalogue().rechercherArtisteParNom(nom) != null) {
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

        if (systeme.getCatalogue().rechercherGroupeParNom(nom) != null) {
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

    public boolean ajouterAlbumAvecArtiste(String titre, int annee, String nomArtiste) {
        if (titre == null || titre.isBlank() || nomArtiste == null || nomArtiste.isBlank()) {
            return false;
        }

        if (systeme.getCatalogue().rechercherAlbumParTitre(titre) != null) {
            return false;
        }

        Artiste artiste = systeme.getCatalogue().rechercherArtisteParNom(nomArtiste);
        if (artiste == null) {
            return false;
        }

        Album album = new Album(
                systeme.getCatalogue().getAlbums().size() + 1,
                titre,
                annee,
                artiste
        );

        systeme.getCatalogue().ajouterAlbum(album);
        artiste.ajouterAlbum(album);
        return true;
    }

    public boolean ajouterAlbumAvecGroupe(String titre, int annee, String nomGroupe) {
        if (titre == null || titre.isBlank() || nomGroupe == null || nomGroupe.isBlank()) {
            return false;
        }

        if (systeme.getCatalogue().rechercherAlbumParTitre(titre) != null) {
            return false;
        }

        Groupe groupe = systeme.getCatalogue().rechercherGroupeParNom(nomGroupe);
        if (groupe == null) {
            return false;
        }

        Album album = new Album(
                systeme.getCatalogue().getAlbums().size() + 1,
                titre,
                annee,
                groupe
        );

        systeme.getCatalogue().ajouterAlbum(album);
        groupe.ajouterAlbum(album);
        return true;
    }

    public boolean supprimerAlbum(String titre) {
        Album album = systeme.getCatalogue().rechercherAlbumParTitre(titre);

        if (album == null) {
            return false;
        }

        if (album.getArtiste() != null) {
            album.getArtiste().retirerAlbum(album);
        }

        if (album.getGroupe() != null) {
            album.getGroupe().retirerAlbum(album);
        }

        systeme.getCatalogue().supprimerAlbum(album);
        return true;
    }

    public boolean ajouterMorceauAvecArtiste(String titre, double duree, String genre, String nomArtiste, String titreAlbum) {
        if (titre == null || titre.isBlank() ||
                genre == null || genre.isBlank() ||
                nomArtiste == null || nomArtiste.isBlank() ||
                titreAlbum == null || titreAlbum.isBlank() ||
                duree <= 0) {
            return false;
        }

        if (systeme.getCatalogue().rechercherMorceauParTitre(titre) != null) {
            return false;
        }

        Artiste artiste = systeme.getCatalogue().rechercherArtisteParNom(nomArtiste);
        Album album = systeme.getCatalogue().rechercherAlbumParTitre(titreAlbum);

        if (artiste == null || album == null) {
            return false;
        }

        if (album.getArtiste() == null || !album.getArtiste().getNom().equalsIgnoreCase(nomArtiste)) {
            return false;
        }

        Morceau morceau = new Morceau(
                systeme.getCatalogue().getMorceaux().size() + 1,
                titre,
                duree,
                genre,
                artiste,
                album
        );

        systeme.getCatalogue().ajouterMorceau(morceau);
        artiste.ajouterMorceau(morceau);
        album.ajouterMorceau(morceau);

        return true;
    }

    public boolean ajouterMorceauAvecGroupe(String titre, double duree, String genre, String nomGroupe, String titreAlbum) {
        if (titre == null || titre.isBlank() ||
                genre == null || genre.isBlank() ||
                nomGroupe == null || nomGroupe.isBlank() ||
                titreAlbum == null || titreAlbum.isBlank() ||
                duree <= 0) {
            return false;
        }

        if (systeme.getCatalogue().rechercherMorceauParTitre(titre) != null) {
            return false;
        }

        Groupe groupe = systeme.getCatalogue().rechercherGroupeParNom(nomGroupe);
        Album album = systeme.getCatalogue().rechercherAlbumParTitre(titreAlbum);

        if (groupe == null || album == null) {
            return false;
        }

        if (album.getGroupe() == null || !album.getGroupe().getNom().equalsIgnoreCase(nomGroupe)) {
            return false;
        }

        Morceau morceau = new Morceau(
                systeme.getCatalogue().getMorceaux().size() + 1,
                titre,
                duree,
                genre,
                groupe,
                album
        );

        systeme.getCatalogue().ajouterMorceau(morceau);
        groupe.ajouterMorceau(morceau);
        album.ajouterMorceau(morceau);

        return true;
    }

    public boolean supprimerMorceau(String titre) {
        Morceau morceau = rechercherMorceau(titre);

        if (morceau == null) {
            return false;
        }

        if (morceau.getArtiste() != null) {
            morceau.getArtiste().retirerMorceau(morceau);
        }

        if (morceau.getGroupe() != null) {
            morceau.getGroupe().retirerMorceau(morceau);
        }

        if (morceau.getAlbum() != null) {
            morceau.getAlbum().retirerMorceau(morceau);
        }

        systeme.getCatalogue().supprimerMorceau(morceau);
        return true;
    }

    public List<Morceau> getMorceauxParArtiste(String nomArtiste) {
        return systeme.getCatalogue().rechercherMorceauxParArtiste(nomArtiste);
    }

    public List<Morceau> getMorceauxParGroupe(String nomGroupe) {
        return systeme.getCatalogue().rechercherMorceauxParGroupe(nomGroupe);
    }

    public List<Album> getAlbumsParArtiste(String nomArtiste) {
        return systeme.getCatalogue().rechercherAlbumsParArtiste(nomArtiste);
    }

    public List<Album> getAlbumsParGroupe(String nomGroupe) {
        return systeme.getCatalogue().rechercherAlbumsParGroupe(nomGroupe);
    }

    public List<Morceau> getMorceauxDunAlbum(String titreAlbum) {
        Album album = systeme.getCatalogue().rechercherAlbumParTitre(titreAlbum);

        if (album != null) {
            return album.getMorceaux();
        }

        return new ArrayList<>();
    }

    public String getNomInterpreteDuMorceau(String titreMorceau) {
        Morceau morceau = rechercherMorceau(titreMorceau);

        if (morceau == null) {
            return null;
        }

        return morceau.getNomInterprete();
    }

    public String getTypeInterpreteDuMorceau(String titreMorceau) {
        Morceau morceau = rechercherMorceau(titreMorceau);

        if (morceau == null) {
            return null;
        }

        return morceau.getTypeInterprete();
    }

    public String getNomInterpreteDeAlbum(String titreAlbum) {
        Album album = rechercherAlbum(titreAlbum);

        if (album == null) {
            return null;
        }

        return album.getNomInterprete();
    }

    public String getTypeInterpreteDeAlbum(String titreAlbum) {
        Album album = rechercherAlbum(titreAlbum);

        if (album == null) {
            return null;
        }

        return album.getTypeInterprete();
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

    public List<String> getNomsArtistes() {
        List<String> noms = new ArrayList<>();
        for (Artiste artiste : systeme.getCatalogue().getArtistes()) {
            noms.add(artiste.getNom());
        }
        return noms;
    }

    public List<String> getNomsGroupes() {
        List<String> noms = new ArrayList<>();
        for (Groupe groupe : systeme.getCatalogue().getGroupes()) {
            noms.add(groupe.getNom());
        }
        return noms;
    }

    public List<String> getTitresAlbums() {
        List<String> titres = new ArrayList<>();
        for (Album album : systeme.getCatalogue().getAlbums()) {
            titres.add(album.getTitre());
        }
        return titres;
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