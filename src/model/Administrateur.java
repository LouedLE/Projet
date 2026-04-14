package model;

import java.io.Serializable;

public class Administrateur extends Utilisateur implements Serializable {

    public Administrateur(int id, String nomUtilisateur, String motDePasse) {
        super(id, nomUtilisateur, motDePasse);
    }

    public void ajouterMorceau(Catalogue catalogue, Morceau morceau) {
        catalogue.ajouterMorceau(morceau);
    }

    public void supprimerMorceau(Catalogue catalogue, Morceau morceau) {
        catalogue.supprimerMorceau(morceau);
    }

    public void ajouterAlbum(Catalogue catalogue, Album album) {
        catalogue.ajouterAlbum(album);
    }

    public void supprimerAlbum(Catalogue catalogue, Album album) {
        catalogue.supprimerAlbum(album);
    }

    public void ajouterArtiste(Catalogue catalogue, Artiste artiste) {
        catalogue.ajouterArtiste(artiste);
    }

    public void supprimerArtiste(Catalogue catalogue, Artiste artiste) {
        catalogue.supprimerArtiste(artiste);
    }

    public void ajouterGroupe(Catalogue catalogue, Groupe groupe) {
        catalogue.ajouterGroupe(groupe);
    }

    public void supprimerGroupe(Catalogue catalogue, Groupe groupe) {
        catalogue.supprimerGroupe(groupe);
    }

    public void suspendreCompte(Abonne abonne) {
        abonne.setSuspendu(true);
    }

    public void reactiverCompte(Abonne abonne) {
        abonne.setSuspendu(false);
    }

    public void supprimerCompte(SystemeJavazic systeme, Abonne abonne) {
        systeme.supprimerAbonne(abonne);
    }

    public void afficherStatistiques(SystemeJavazic systeme) {
        System.out.println("Nombre d'abonnés : " + systeme.getNombreAbonnes());
        System.out.println("Nombre d'administrateurs : " + systeme.getNombreAdministrateurs());
        System.out.println("Nombre de morceaux : " + systeme.getCatalogue().getMorceaux().size());
        System.out.println("Nombre d'albums : " + systeme.getCatalogue().getAlbums().size());
        System.out.println("Nombre d'artistes : " + systeme.getCatalogue().getArtistes().size());
        System.out.println("Nombre de groupes : " + systeme.getCatalogue().getGroupes().size());
    }

    @Override
    public String toString() {
        return "Administrateur{id=" + id + ", nomUtilisateur='" + nomUtilisateur + "'}";
    }
}