package view;

import controller.JavazicController;
import model.Administrateur;
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
        top.add(new JLabel("Titre du morceau :"));
        champTitre = new JTextField(20);
        top.add(champTitre);
        add(top, BorderLayout.BEFORE_FIRST_LINE);

        area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(4, 3, 8, 8));

        JButton btnAfficher = new JButton("Voir catalogue");
        JButton btnAjouter = new JButton("Ajouter morceau");
        JButton btnSupprimer = new JButton("Supprimer morceau");
        JButton btnAjouterArtiste = new JButton("Ajouter artiste");
        JButton btnSupprimerArtiste = new JButton("Supprimer artiste");
        JButton btnAjouterGroupe = new JButton("Ajouter groupe");
        JButton btnSupprimerGroupe = new JButton("Supprimer groupe");
        JButton btnSuspendre = new JButton("Suspendre abonné");
        JButton btnSupprimerAbonne = new JButton("Supprimer abonné");
        JButton btnStats = new JButton("Statistiques");
        JButton btnLogout = new JButton("Déconnexion");

        buttons.add(btnAfficher);
        buttons.add(btnAjouter);
        buttons.add(btnSupprimer);
        buttons.add(btnAjouterArtiste);
        buttons.add(btnSupprimerArtiste);
        buttons.add(btnAjouterGroupe);
        buttons.add(btnSupprimerGroupe);
        buttons.add(btnSuspendre);
        buttons.add(btnSupprimerAbonne);
        buttons.add(btnStats);
        buttons.add(btnLogout);

        add(buttons, BorderLayout.SOUTH);

        btnAfficher.addActionListener(e -> afficherCatalogue());

        btnAjouter.addActionListener(e -> {
            String titre = champTitre.getText().trim();

            if (titre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Titre requis.");
                return;
            }

            String dureeStr = JOptionPane.showInputDialog(this, "Durée :");
            if (dureeStr == null || dureeStr.trim().isEmpty()) {
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

            try {
                double duree = Double.parseDouble(dureeStr.trim());
                boolean ok = false;

                if (type.equalsIgnoreCase("Artiste")) {
                    ok = controller.ajouterMorceauAvecArtiste(
                            titre,
                            duree,
                            genre.trim(),
                            nomInterprete.trim()
                    );
                } else if (type.equalsIgnoreCase("Groupe")) {
                    ok = controller.ajouterMorceauAvecGroupe(
                            titre,
                            duree,
                            genre.trim(),
                            nomInterprete.trim()
                    );
                }

                if (ok) {
                    JOptionPane.showMessageDialog(this, "Morceau ajouté.");
                    champTitre.setText("");
                    afficherCatalogue();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Impossible d'ajouter le morceau.\nVérifie que l'artiste ou le groupe existe déjà."
                    );
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La durée doit être un nombre valide.");
            }
        });

        btnSupprimer.addActionListener(e -> {
            String titre = champTitre.getText().trim();

            if (titre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Saisis un titre à supprimer.");
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

        btnSuspendre.addActionListener(e -> {
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

        area.append("=== CATALOGUE ===\n");
        for (Morceau m : morceaux) {
            area.append(m.toString() + "\n");
        }
    }

    private void afficherStats() {
        area.setText("");

        area.append("=== STATISTIQUES ===\n");
        area.append("Utilisateurs : " + controller.getNombreUtilisateurs() + "\n");
        area.append("Morceaux : " + controller.getNombreMorceaux() + "\n");
        area.append("Albums : " + controller.getNombreAlbums() + "\n");
        area.append("Artistes : " + controller.getNombreArtistes() + "\n");
        area.append("Groupes : " + controller.getNombreGroupes() + "\n");
        area.append("Écoutes totales : " + controller.getNombreTotalEcoutes() + "\n");
    }
}