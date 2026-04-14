package view;

import controller.JavazicController;
import model.Administrateur;
import model.Album;
import model.Morceau;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminPanel extends JPanel {

    private final JavazicController controller;
    private final JFrame frame;

    private final JTextArea area;
    private final JTextField champTitre;

    public AdminPanel(JavazicController controller, Administrateur admin, JFrame frame) {

        this.controller = controller;
        this.frame = frame;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Menu Admin - " + admin.getNomUtilisateur(), SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel top = new JPanel(new FlowLayout());
        top.add(new JLabel("Titre :"));
        champTitre = new JTextField(20);
        top.add(champTitre);
        add(top, BorderLayout.BEFORE_FIRST_LINE);

        area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(5, 3, 8, 8));

        JButton btnAfficherCatalogue = new JButton("Voir catalogue");
        JButton btnAfficherAlbums = new JButton("Voir albums");
        JButton btnAjouterArtiste = new JButton("Ajouter artiste");
        JButton btnAjouterGroupe = new JButton("Ajouter groupe");
        JButton btnAjouterAlbum = new JButton("Ajouter album");
        JButton btnAjouterMorceau = new JButton("Ajouter morceau");
        JButton btnSupprimerMorceau = new JButton("Supprimer morceau");
        JButton btnSupprimerAlbum = new JButton("Supprimer album");
        JButton btnSupprimerArtiste = new JButton("Supprimer artiste");
        JButton btnSupprimerGroupe = new JButton("Supprimer groupe");
        JButton btnSuspendreAbonne = new JButton("Suspendre abonné");
        JButton btnSupprimerAbonne = new JButton("Supprimer abonné");
        JButton btnStats = new JButton("Statistiques");
        JButton btnLogout = new JButton("Déconnexion");

        buttons.add(btnAfficherCatalogue);
        buttons.add(btnAfficherAlbums);
        buttons.add(btnAjouterArtiste);
        buttons.add(btnAjouterGroupe);
        buttons.add(btnAjouterAlbum);
        buttons.add(btnAjouterMorceau);
        buttons.add(btnSupprimerMorceau);
        buttons.add(btnSupprimerAlbum);
        buttons.add(btnSupprimerArtiste);
        buttons.add(btnSupprimerGroupe);
        buttons.add(btnSuspendreAbonne);
        buttons.add(btnSupprimerAbonne);
        buttons.add(btnStats);
        buttons.add(btnLogout);

        add(buttons, BorderLayout.SOUTH);

        btnAfficherCatalogue.addActionListener(e -> afficherCatalogue());

        btnAfficherAlbums.addActionListener(e -> afficherAlbums());

        btnAjouterArtiste.addActionListener(e -> {
            String nom = JOptionPane.showInputDialog(this, "Nom de l'artiste :");

            if (nom == null || nom.trim().isEmpty()) {
                return;
            }

            boolean ok = controller.ajouterArtiste(nom.trim());

            if (ok) {
                JOptionPane.showMessageDialog(this, "Artiste ajouté.");
            } else {
                JOptionPane.showMessageDialog(this, "Impossible d'ajouter l'artiste.");
            }
        });

        btnAjouterGroupe.addActionListener(e -> {
            String nom = JOptionPane.showInputDialog(this, "Nom du groupe :");

            if (nom == null || nom.trim().isEmpty()) {
                return;
            }

            boolean ok = controller.ajouterGroupe(nom.trim());

            if (ok) {
                JOptionPane.showMessageDialog(this, "Groupe ajouté.");
            } else {
                JOptionPane.showMessageDialog(this, "Impossible d'ajouter le groupe.");
            }
        });

        btnAjouterAlbum.addActionListener(e -> {
            String titre = JOptionPane.showInputDialog(this, "Titre de l'album :");
            if (titre == null || titre.trim().isEmpty()) {
                return;
            }

            String anneeTexte = JOptionPane.showInputDialog(this, "Année :");
            if (anneeTexte == null || anneeTexte.trim().isEmpty()) {
                return;
            }

            String[] options = {"Artiste", "Groupe"};
            String type = (String) JOptionPane.showInputDialog(
                    this,
                    "Type d'interprète :",
                    "Choix de l'interprète",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (type == null) {
                return;
            }

            String nomInterprete = JOptionPane.showInputDialog(this, "Nom de l'interprète :");
            if (nomInterprete == null || nomInterprete.trim().isEmpty()) {
                return;
            }

            try {
                int annee = Integer.parseInt(anneeTexte.trim());
                boolean ok = false;

                if (type.equalsIgnoreCase("Artiste")) {
                    ok = controller.ajouterAlbumAvecArtiste(
                            titre.trim(),
                            annee,
                            nomInterprete.trim()
                    );
                } else if (type.equalsIgnoreCase("Groupe")) {
                    ok = controller.ajouterAlbumAvecGroupe(
                            titre.trim(),
                            annee,
                            nomInterprete.trim()
                    );
                }

                if (ok) {
                    JOptionPane.showMessageDialog(this, "Album ajouté.");
                    afficherAlbums();
                } else {
                    JOptionPane.showMessageDialog(this, "Impossible d'ajouter l'album.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "L'année doit être un entier.");
            }
        });

        btnAjouterMorceau.addActionListener(e -> {
            String titre = champTitre.getText().trim();

            if (titre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Titre du morceau requis.");
                return;
            }

            String dureeTexte = JOptionPane.showInputDialog(this, "Durée :");
            if (dureeTexte == null || dureeTexte.trim().isEmpty()) {
                return;
            }

            String genre = JOptionPane.showInputDialog(this, "Genre :");
            if (genre == null || genre.trim().isEmpty()) {
                return;
            }

            String[] options = {"Artiste", "Groupe"};
            String type = (String) JOptionPane.showInputDialog(
                    this,
                    "Type d'interprète :",
                    "Choix de l'interprète",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (type == null) {
                return;
            }

            String nomInterprete = JOptionPane.showInputDialog(this, "Nom de l'interprète :");
            if (nomInterprete == null || nomInterprete.trim().isEmpty()) {
                return;
            }

            String titreAlbum = JOptionPane.showInputDialog(this, "Titre de l'album :");
            if (titreAlbum == null || titreAlbum.trim().isEmpty()) {
                return;
            }

            try {
                double duree = Double.parseDouble(dureeTexte.trim());
                boolean ok = false;

                if (type.equalsIgnoreCase("Artiste")) {
                    ok = controller.ajouterMorceauAvecArtiste(
                            titre,
                            duree,
                            genre.trim(),
                            nomInterprete.trim(),
                            titreAlbum.trim()
                    );
                } else if (type.equalsIgnoreCase("Groupe")) {
                    ok = controller.ajouterMorceauAvecGroupe(
                            titre,
                            duree,
                            genre.trim(),
                            nomInterprete.trim(),
                            titreAlbum.trim()
                    );
                }

                if (ok) {
                    JOptionPane.showMessageDialog(this, "Morceau ajouté.");
                    champTitre.setText("");
                    afficherCatalogue();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Impossible d'ajouter le morceau.\nVérifie que l'artiste/groupe et l'album existent et correspondent."
                    );
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La durée doit être un nombre valide.");
            }
        });

        btnSupprimerMorceau.addActionListener(e -> {
            String titre = champTitre.getText().trim();

            if (titre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Saisis un titre de morceau.");
                return;
            }

            boolean ok = controller.supprimerMorceau(titre);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Morceau supprimé.");
                champTitre.setText("");
                afficherCatalogue();
            } else {
                JOptionPane.showMessageDialog(this, "Morceau introuvable.");
            }
        });

        btnSupprimerAlbum.addActionListener(e -> {
            String titre = JOptionPane.showInputDialog(this, "Titre de l'album à supprimer :");

            if (titre == null || titre.trim().isEmpty()) {
                return;
            }

            boolean ok = controller.supprimerAlbum(titre.trim());

            if (ok) {
                JOptionPane.showMessageDialog(this, "Album supprimé.");
                afficherAlbums();
            } else {
                JOptionPane.showMessageDialog(this, "Album introuvable.");
            }
        });

        btnSupprimerArtiste.addActionListener(e -> {
            String nom = JOptionPane.showInputDialog(this, "Nom de l'artiste à supprimer :");

            if (nom == null || nom.trim().isEmpty()) {
                return;
            }

            boolean ok = controller.supprimerArtiste(nom.trim());

            if (ok) {
                JOptionPane.showMessageDialog(this, "Artiste supprimé.");
            } else {
                JOptionPane.showMessageDialog(this, "Artiste introuvable.");
            }
        });

        btnSupprimerGroupe.addActionListener(e -> {
            String nom = JOptionPane.showInputDialog(this, "Nom du groupe à supprimer :");

            if (nom == null || nom.trim().isEmpty()) {
                return;
            }

            boolean ok = controller.supprimerGroupe(nom.trim());

            if (ok) {
                JOptionPane.showMessageDialog(this, "Groupe supprimé.");
            } else {
                JOptionPane.showMessageDialog(this, "Groupe introuvable.");
            }
        });

        btnSuspendreAbonne.addActionListener(e -> {
            String login = JOptionPane.showInputDialog(this, "Login de l'abonné à suspendre :");

            if (login == null || login.trim().isEmpty()) {
                return;
            }

            boolean ok = controller.suspendreAbonne(login.trim());

            if (ok) {
                JOptionPane.showMessageDialog(this, "Compte suspendu.");
            } else {
                JOptionPane.showMessageDialog(this, "Abonné introuvable.");
            }
        });

        btnSupprimerAbonne.addActionListener(e -> {
            String login = JOptionPane.showInputDialog(this, "Login de l'abonné à supprimer :");

            if (login == null || login.trim().isEmpty()) {
                return;
            }

            boolean ok = controller.supprimerAbonne(login.trim());

            if (ok) {
                JOptionPane.showMessageDialog(this, "Compte supprimé.");
            } else {
                JOptionPane.showMessageDialog(this, "Abonné introuvable.");
            }
        });

        btnStats.addActionListener(e -> afficherStats());

        btnLogout.addActionListener(e -> {
            frame.setContentPane(new LoginPanel(controller, frame));
            frame.revalidate();
            frame.repaint();
        });
    }

    private void afficherCatalogue() {
        area.setText("");

        List<Morceau> morceaux = controller.getTousLesMorceaux();

        if (morceaux.isEmpty()) {
            area.setText("Aucun morceau dans le catalogue.");
            return;
        }

        area.append("=== CATALOGUE DES MORCEAUX ===\n");
        for (Morceau m : morceaux) {
            area.append(m + "\n");
        }
    }

    private void afficherAlbums() {
        area.setText("");

        List<Album> albums = controller.getTousLesAlbums();

        if (albums.isEmpty()) {
            area.setText("Aucun album dans le catalogue.");
            return;
        }

        area.append("=== CATALOGUE DES ALBUMS ===\n");
        for (Album a : albums) {
            area.append(a + "\n");
        }
    }

    private void afficherStats() {
        area.setText("");

        area.append("=== STATISTIQUES ===\n");
        area.append("Utilisateurs : " + controller.getNombreUtilisateurs() + "\n");
        area.append("Abonnés : " + controller.getNombreAbonnes() + "\n");
        area.append("Administrateurs : " + controller.getNombreAdministrateurs() + "\n");
        area.append("Morceaux : " + controller.getNombreMorceaux() + "\n");
        area.append("Albums : " + controller.getNombreAlbums() + "\n");
        area.append("Artistes : " + controller.getNombreArtistes() + "\n");
        area.append("Groupes : " + controller.getNombreGroupes() + "\n");
        area.append("Écoutes totales : " + controller.getNombreTotalEcoutes() + "\n");
    }
}