package app;

import controller.JavazicController;
import model.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SystemeJavazic systeme = SystemeJavazic.charger("sauvegarde.dat");
        JavazicController controller = new JavazicController(systeme);

        controller.initialiserAdminParDefaut();

        Scanner scanner = new Scanner(System.in);
        boolean quitter = false;

        while (!quitter) {
            System.out.println("\n=== JAVAZIC ===");
            System.out.println("1. Se connecter");
            System.out.println("2. Créer un compte");
            System.out.println("3. Continuer en visiteur");
            System.out.println("4. Quitter");
            System.out.print("Choix : ");

            int choix = lireEntier(scanner);

            switch (choix) {
                case 1:
                    connecterUtilisateur(scanner, controller);
                    break;

                case 2:
                    creerCompte(scanner, controller);
                    break;

                case 3:
                    menuVisiteur(scanner, controller);
                    break;

                case 4:
                    controller.sauvegarder();
                    quitter = true;
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }

        scanner.close();
    }

    private static void connecterUtilisateur(Scanner scanner, JavazicController controller) {
        System.out.print("Login : ");
        String login = scanner.nextLine();

        System.out.print("Mot de passe : ");
        String mdp = scanner.nextLine();

        Utilisateur user = controller.connecter(login, mdp);

        if (user == null) {
            System.out.println("Identifiants incorrects ou compte suspendu.");
            return;
        }

        if (user instanceof Administrateur) {
            menuAdmin(scanner, controller, (Administrateur) user);
        } else if (user instanceof Abonne) {
            menuAbonne(scanner, controller, (Abonne) user);
        }
    }

    private static void creerCompte(Scanner scanner, JavazicController controller) {
        System.out.print("Choisir un login : ");
        String login = scanner.nextLine();

        System.out.print("Choisir un mot de passe : ");
        String mdp = scanner.nextLine();

        Abonne abonne = controller.creerCompte(login, mdp);

        if (abonne != null) {
            System.out.println("Compte créé : " + abonne.getNomUtilisateur());
        } else {
            System.out.println("Impossible de créer le compte.");
        }
    }

    public static void menuVisiteur(Scanner scanner, JavazicController controller) {
        boolean retour = false;

        while (!retour) {
            System.out.println("\n--- MENU VISITEUR ---");
            System.out.println("1. Voir les morceaux");
            System.out.println("2. Rechercher un morceau");
            System.out.println("3. Écouter un morceau (limité)");
            System.out.println("4. Retour");
            System.out.print("Choix : ");

            int choix = lireEntier(scanner);

            switch (choix) {
                case 1:
                    afficherMorceaux(controller.getTousLesMorceaux());
                    break;

                case 2:
                    System.out.print("Titre du morceau : ");
                    String titreRecherche = scanner.nextLine();
                    Morceau trouve = controller.rechercherMorceau(titreRecherche);

                    if (trouve != null) {
                        System.out.println("Trouvé : " + trouve);
                    } else {
                        System.out.println("Aucun morceau trouvé.");
                    }
                    break;

                case 3:
                    if (!controller.peutEcouterEnVisiteur()) {
                        System.out.println("Limite d'écoute visiteur atteinte pour cette session.");
                        break;
                    }

                    System.out.print("Titre du morceau : ");
                    String titreEcoute = scanner.nextLine();
                    boolean ecouteOk = controller.ecouterEnVisiteur(titreEcoute);

                    if (ecouteOk) {
                        System.out.println("Lecture en cours...");
                        System.out.println("Écoutes visiteur : " + controller.getNombreEcoutesVisiteur() + "/" + controller.getLimiteEcoutesVisiteur());
                    } else {
                        System.out.println("Morceau introuvable.");
                    }
                    break;

                case 4:
                    retour = true;
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    public static void menuAbonne(Scanner scanner, JavazicController controller, Abonne abonne) {
        boolean retour = false;

        while (!retour) {
            System.out.println("\n--- MENU ABONNÉ ---");
            System.out.println("1. Voir le catalogue");
            System.out.println("2. Créer une playlist");
            System.out.println("3. Renommer une playlist");
            System.out.println("4. Supprimer une playlist");
            System.out.println("5. Ajouter un morceau à une playlist");
            System.out.println("6. Retirer un morceau d'une playlist");
            System.out.println("7. Voir mes playlists");
            System.out.println("8. Écouter un morceau");
            System.out.println("9. Voir mon historique");
            System.out.println("10. Noter ou modifier un avis");
            System.out.println("11. Supprimer mon avis");
            System.out.println("12. Voir les avis d'un morceau");
            System.out.println("13. Retour");
            System.out.print("Choix : ");

            int choix = lireEntier(scanner);

            switch (choix) {
                case 1:
                    afficherMorceaux(controller.getTousLesMorceaux());
                    break;

                case 2:
                    System.out.print("Nom de la playlist : ");
                    String nomPlaylist = scanner.nextLine();

                    if (controller.creerPlaylist(abonne, nomPlaylist)) {
                        System.out.println("Playlist créée.");
                    } else {
                        System.out.println("Impossible de créer la playlist.");
                    }
                    break;

                case 3:
                    System.out.print("Nom actuel de la playlist : ");
                    String ancienNom = scanner.nextLine();

                    System.out.print("Nouveau nom : ");
                    String nouveauNom = scanner.nextLine();

                    if (controller.renommerPlaylist(abonne, ancienNom, nouveauNom)) {
                        System.out.println("Playlist renommée.");
                    } else {
                        System.out.println("Playlist introuvable.");
                    }
                    break;

                case 4:
                    System.out.print("Nom de la playlist à supprimer : ");
                    String playlistSupprimer = scanner.nextLine();

                    if (controller.supprimerPlaylist(abonne, playlistSupprimer)) {
                        System.out.println("Playlist supprimée.");
                    } else {
                        System.out.println("Playlist introuvable.");
                    }
                    break;

                case 5:
                    System.out.print("Nom de la playlist : ");
                    String nomPlaylistAjout = scanner.nextLine();

                    System.out.print("Titre du morceau à ajouter : ");
                    String titreAjout = scanner.nextLine();

                    if (controller.ajouterMorceauAPlaylist(abonne, nomPlaylistAjout, titreAjout)) {
                        System.out.println("Morceau ajouté à la playlist.");
                    } else {
                        System.out.println("Impossible d'ajouter le morceau.");
                    }
                    break;

                case 6:
                    System.out.print("Nom de la playlist : ");
                    String nomPlaylistRetrait = scanner.nextLine();

                    System.out.print("Titre du morceau à retirer : ");
                    String titreRetrait = scanner.nextLine();

                    if (controller.retirerMorceauDePlaylist(abonne, nomPlaylistRetrait, titreRetrait)) {
                        System.out.println("Morceau retiré de la playlist.");
                    } else {
                        System.out.println("Impossible de retirer le morceau.");
                    }
                    break;

                case 7:
                    afficherPlaylists(abonne.getPlaylists());
                    break;

                case 8:
                    System.out.print("Titre du morceau : ");
                    String titreEcoute = scanner.nextLine();

                    if (controller.ecouterCommeAbonne(abonne, titreEcoute)) {
                        System.out.println("Lecture en cours...");
                    } else {
                        System.out.println("Morceau introuvable.");
                    }
                    break;

                case 9:
                    afficherHistorique(abonne);
                    break;

                case 10:
                    System.out.print("Titre du morceau : ");
                    String titreAvis = scanner.nextLine();

                    System.out.print("Note (0 à 5) : ");
                    int note = lireEntier(scanner);

                    System.out.print("Commentaire : ");
                    String commentaire = scanner.nextLine();

                    if (controller.ajouterOuModifierAvis(abonne, titreAvis, note, commentaire)) {
                        System.out.println("Avis enregistré.");
                    } else {
                        System.out.println("Impossible d'enregistrer l'avis.");
                    }
                    break;

                case 11:
                    System.out.print("Titre du morceau : ");
                    String titreSuppAvis = scanner.nextLine();

                    if (controller.supprimerAvis(abonne, titreSuppAvis)) {
                        System.out.println("Avis supprimé.");
                    } else {
                        System.out.println("Aucun avis trouvé ou morceau introuvable.");
                    }
                    break;

                case 12:
                    System.out.print("Titre du morceau : ");
                    String titreVoirAvis = scanner.nextLine();

                    List<Avis> avis = controller.getAvisDuMorceau(titreVoirAvis);

                    if (avis == null) {
                        System.out.println("Morceau introuvable.");
                    } else if (avis.isEmpty()) {
                        System.out.println("Aucun avis pour ce morceau.");
                    } else {
                        System.out.println("Avis du morceau :");
                        for (Avis avisItem : avis) {
                            System.out.println(avisItem);
                        }
                    }
                    break;

                case 13:
                    retour = true;
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    public static void menuAdmin(Scanner scanner, JavazicController controller, Administrateur admin) {
        boolean retour = false;

        while (!retour) {
            System.out.println("\n--- MENU ADMIN ---");
            System.out.println("1. Ajouter un morceau");
            System.out.println("2. Supprimer un morceau");
            System.out.println("3. Ajouter un album");
            System.out.println("4. Supprimer un album");
            System.out.println("5. Ajouter un artiste");
            System.out.println("6. Supprimer un artiste");
            System.out.println("7. Ajouter un groupe");
            System.out.println("8. Supprimer un groupe");
            System.out.println("9. Suspendre un compte abonné");
            System.out.println("10. Supprimer un compte abonné");
            System.out.println("11. Voir les statistiques");
            System.out.println("12. Retour");
            System.out.print("Choix : ");

            int choix = lireEntier(scanner);

            switch (choix) {
                case 1:
                    System.out.print("Titre : ");
                    String titre = scanner.nextLine();

                    System.out.print("Durée : ");
                    double duree = lireDouble(scanner);

                    System.out.print("Genre : ");
                    String genre = scanner.nextLine();

                    if (controller.ajouterMorceau(titre, duree, genre)) {
                        System.out.println("Morceau ajouté.");
                    } else {
                        System.out.println("Impossible d'ajouter le morceau.");
                    }
                    break;

                case 2:
                    System.out.print("Titre du morceau à supprimer : ");
                    String titreSup = scanner.nextLine();

                    if (controller.supprimerMorceau(titreSup)) {
                        System.out.println("Morceau supprimé.");
                    } else {
                        System.out.println("Morceau introuvable.");
                    }
                    break;

                case 3:
                    System.out.print("Titre de l'album : ");
                    String titreAlbum = scanner.nextLine();

                    System.out.print("Année : ");
                    int annee = lireEntier(scanner);

                    if (controller.ajouterAlbum(titreAlbum, annee)) {
                        System.out.println("Album ajouté.");
                    } else {
                        System.out.println("Impossible d'ajouter l'album.");
                    }
                    break;

                case 4:
                    System.out.print("Titre de l'album à supprimer : ");
                    String titreAlbumSup = scanner.nextLine();

                    if (controller.supprimerAlbum(titreAlbumSup)) {
                        System.out.println("Album supprimé.");
                    } else {
                        System.out.println("Album introuvable.");
                    }
                    break;

                case 5:
                    System.out.print("Nom de l'artiste : ");
                    String nomArtiste = scanner.nextLine();

                    if (controller.ajouterArtiste(nomArtiste)) {
                        System.out.println("Artiste ajouté.");
                    } else {
                        System.out.println("Impossible d'ajouter l'artiste.");
                    }
                    break;

                case 6:
                    System.out.print("Nom de l'artiste à supprimer : ");
                    String nomArtisteSup = scanner.nextLine();

                    if (controller.supprimerArtiste(nomArtisteSup)) {
                        System.out.println("Artiste supprimé.");
                    } else {
                        System.out.println("Artiste introuvable.");
                    }
                    break;

                case 7:
                    System.out.print("Nom du groupe : ");
                    String nomGroupe = scanner.nextLine();

                    if (controller.ajouterGroupe(nomGroupe)) {
                        System.out.println("Groupe ajouté.");
                    } else {
                        System.out.println("Impossible d'ajouter le groupe.");
                    }
                    break;

                case 8:
                    System.out.print("Nom du groupe à supprimer : ");
                    String nomGroupeSup = scanner.nextLine();

                    if (controller.supprimerGroupe(nomGroupeSup)) {
                        System.out.println("Groupe supprimé.");
                    } else {
                        System.out.println("Groupe introuvable.");
                    }
                    break;

                case 9:
                    System.out.print("Nom d'utilisateur de l'abonné à suspendre : ");
                    String loginSuspendre = scanner.nextLine();

                    if (controller.suspendreAbonne(loginSuspendre)) {
                        System.out.println("Compte suspendu.");
                    } else {
                        System.out.println("Abonné introuvable.");
                    }
                    break;

                case 10:
                    System.out.print("Nom d'utilisateur de l'abonné à supprimer : ");
                    String loginSupprimer = scanner.nextLine();

                    if (controller.supprimerAbonne(loginSupprimer)) {
                        System.out.println("Compte supprimé.");
                    } else {
                        System.out.println("Abonné introuvable.");
                    }
                    break;

                case 11:
                    System.out.println("\n--- STATISTIQUES ---");
                    System.out.println("Nombre d'utilisateurs : " + controller.getNombreUtilisateurs());
                    System.out.println("Nombre d'abonnés : " + controller.getNombreAbonnes());
                    System.out.println("Nombre d'administrateurs : " + controller.getNombreAdministrateurs());
                    System.out.println("Nombre de morceaux : " + controller.getNombreMorceaux());
                    System.out.println("Nombre d'albums : " + controller.getNombreAlbums());
                    System.out.println("Nombre d'artistes : " + controller.getNombreArtistes());
                    System.out.println("Nombre de groupes : " + controller.getNombreGroupes());
                    System.out.println("Nombre total d'écoutes : " + controller.getNombreTotalEcoutes());
                    break;

                case 12:
                    retour = true;
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private static void afficherMorceaux(List<Morceau> morceaux) {
        if (morceaux == null || morceaux.isEmpty()) {
            System.out.println("Aucun morceau disponible.");
            return;
        }

        System.out.println("\n--- CATALOGUE ---");
        for (Morceau morceau : morceaux) {
            System.out.println(morceau);
        }
    }

    private static void afficherPlaylists(List<Playlist> playlists) {
        if (playlists == null || playlists.isEmpty()) {
            System.out.println("Aucune playlist.");
            return;
        }

        System.out.println("\n--- PLAYLISTS ---");
        for (Playlist playlist : playlists) {
            System.out.println("Playlist : " + playlist.getNom());
            if (playlist.getMorceaux().isEmpty()) {
                System.out.println("  (vide)");
            } else {
                for (Morceau morceau : playlist.getMorceaux()) {
                    System.out.println("  - " + morceau);
                }
            }
        }
    }

    private static void afficherHistorique(Abonne abonne) {
        List<Morceau> historique = abonne.getHistorique().getMorceauxEcoutes();

        if (historique == null || historique.isEmpty()) {
            System.out.println("Historique vide.");
            return;
        }

        System.out.println("\n--- HISTORIQUE ---");
        for (Morceau morceau : historique) {
            System.out.println(morceau);
        }
    }

    private static int lireEntier(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Veuillez entrer un nombre entier : ");
            scanner.nextLine();
        }
        int valeur = scanner.nextInt();
        scanner.nextLine();
        return valeur;
    }

    private static double lireDouble(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.print("Veuillez entrer un nombre valide : ");
            scanner.nextLine();
        }
        double valeur = scanner.nextDouble();
        scanner.nextLine();
        return valeur;
    }
}