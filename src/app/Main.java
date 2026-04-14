package app;

import model.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        SystemeJavazic systeme = SystemeJavazic.charger("sauvegarde.dat");

        // Admin par défaut
        Administrateur admin = new Administrateur(1, "loued", "1234");
        systeme.ajouterAdministrateur(admin);

        Scanner scanner = new Scanner(System.in);

        boolean quitter = false;

        while (!quitter) {
            System.out.println("\n=== JAVAZIC ===");
            System.out.println("1. Se connecter");
            System.out.println("2. Créer un compte");
            System.out.println("3. Continuer en visiteur");
            System.out.println("4. Quitter");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {

                case 1:
                    System.out.print("Nom d'utilisateur : ");
                    String login = scanner.nextLine();

                    System.out.print("Mot de passe : ");
                    String mdp = scanner.nextLine();

                    Utilisateur user = systeme.seConnecter(login, mdp);

                    if (user == null) {
                        System.out.println("Identifiants incorrects !");
                    } else if (user instanceof Administrateur) {
                        menuAdmin(scanner, systeme, (Administrateur) user);
                    } else if (user instanceof Abonne) {
                        menuAbonne(scanner, systeme, (Abonne) user);
                    }
                    break;

                case 2:
                    System.out.print("Choisir un nom d'utilisateur : ");
                    String newLogin = scanner.nextLine();

                    System.out.print("Choisir un mot de passe : ");
                    String newMdp = scanner.nextLine();

                    Abonne nouvelAbonne = systeme.creerCompte(newLogin, newMdp);

                    System.out.println("Compte créé : " + nouvelAbonne);
                    break;

                case 3:
                    menuVisiteur(scanner, systeme);
                    break;

                case 4:
                    systeme.sauvegarder("sauvegarde.dat");
                    quitter = true;
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Choix invalide");
            }
        }

        scanner.close();
    }

    // ================= MENU VISITEUR =================

    public static void menuVisiteur(Scanner scanner, SystemeJavazic systeme) {

        boolean retour = false;

        while (!retour) {
            System.out.println("\n--- MENU VISITEUR ---");
            System.out.println("1. Voir les morceaux");
            System.out.println("2. Rechercher un morceau");
            System.out.println("3. Retour");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {

                case 1:
                    for (Morceau m : systeme.getCatalogue().getMorceaux()) {
                        System.out.println(m);
                    }
                    break;

                case 2:
                    System.out.print("Titre : ");
                    String titre = scanner.nextLine();
                    Morceau m = systeme.getCatalogue().rechercherMorceauParTitre(titre);

                    if (m != null) {
                        System.out.println("Trouvé : " + m);
                    } else {
                        System.out.println("Aucun résultat");
                    }
                    break;

                case 3:
                    retour = true;
                    break;

                default:
                    System.out.println("Choix invalide");
            }
        }
    }

    // ================= MENU ABONNE =================

    public static void menuAbonne(Scanner scanner, SystemeJavazic systeme, Abonne abonne) {

        boolean retour = false;

        while (!retour) {
            System.out.println("\n--- MENU ABONNE ---");
            System.out.println("1. Voir catalogue");
            System.out.println("2. Créer playlist");
            System.out.println("3. Voir playlists");
            System.out.println("4. Écouter un morceau");
            System.out.println("5. Voir historique");
            System.out.println("6. Retour");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {

                case 1:
                    for (Morceau m : systeme.getCatalogue().getMorceaux()) {
                        System.out.println(m);
                    }
                    break;

                case 2:
                    System.out.print("Nom playlist : ");
                    String nom = scanner.nextLine();
                    abonne.creerPlaylist(abonne.getPlaylists().size() + 1, nom);
                    System.out.println("Playlist créée !");
                    break;

                case 3:
                    for (Playlist p : abonne.getPlaylists()) {
                        System.out.println(p);
                    }
                    break;

                case 4:
                    System.out.print("Titre du morceau : ");
                    String titre = scanner.nextLine();
                    Morceau m = systeme.getCatalogue().rechercherMorceauParTitre(titre);

                    if (m != null) {
                        abonne.ecouterMorceau(m);
                    } else {
                        System.out.println("Introuvable");
                    }
                    break;

                case 5:
                    System.out.println(abonne.getHistorique());
                    break;

                case 6:
                    retour = true;
                    break;

                default:
                    System.out.println("Choix invalide");
            }
        }
    }

    // ================= MENU ADMIN =================

    public static void menuAdmin(Scanner scanner, SystemeJavazic systeme, Administrateur admin) {

        boolean retour = false;

        while (!retour) {
            System.out.println("\n--- MENU ADMIN ---");
            System.out.println("1. Ajouter morceau");
            System.out.println("2. Voir catalogue");
            System.out.println("3. Voir statistiques");
            System.out.println("4. Retour");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {

                case 1:
                    System.out.print("Titre : ");
                    String titre = scanner.nextLine();

                    System.out.print("Durée : ");
                    double duree = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Genre : ");
                    String genre = scanner.nextLine();

                    Morceau m = new Morceau(
                            systeme.getCatalogue().getMorceaux().size() + 1,
                            titre,
                            duree,
                            genre
                    );

                    admin.ajouterMorceau(systeme.getCatalogue(), m);

                    System.out.println("Morceau ajouté !");
                    break;

                case 2:
                    for (Morceau mo : systeme.getCatalogue().getMorceaux()) {
                        System.out.println(mo);
                    }
                    break;

                case 3:
                    admin.afficherStatistiques(systeme);
                    break;

                case 4:
                    retour = true;
                    break;

                default:
                    System.out.println("Choix invalide");
            }
        }
    }
}