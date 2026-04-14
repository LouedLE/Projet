package view;

import controller.JavazicController;
import model.Morceau;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VisiteurPanel extends JPanel {

    private final JavazicController controller;
    private final JFrame frame;

    private final JTextArea area;
    private final JTextField champTitre;

    public VisiteurPanel(JavazicController controller, JFrame frame) {
        this.controller = controller;
        this.frame = frame;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Mode Visiteur", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel panelNord = new JPanel(new FlowLayout());
        panelNord.add(new JLabel("Titre du morceau :"));
        champTitre = new JTextField(20);
        panelNord.add(champTitre);
        add(panelNord, BorderLayout.BEFORE_FIRST_LINE);

        area = new JTextArea();
        area.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(area);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(3, 3, 8, 8));

        JButton btnAfficherCatalogue = new JButton("Voir catalogue");
        JButton btnRechercherMorceau = new JButton("Rechercher morceau");
        JButton btnEcouter = new JButton("Écouter (limité)");
        JButton btnVoirInterprete = new JButton("Voir interprète");
        JButton btnVoirMorceauxArtiste = new JButton("Morceaux artiste");
        JButton btnVoirMorceauxGroupe = new JButton("Morceaux groupe");
        JButton btnVoirMemeInterprete = new JButton("Même interprète");
        JButton btnRetour = new JButton("Retour");
        JButton btnQuitter = new JButton("Quitter");

        buttons.add(btnAfficherCatalogue);
        buttons.add(btnRechercherMorceau);
        buttons.add(btnEcouter);
        buttons.add(btnVoirInterprete);
        buttons.add(btnVoirMorceauxArtiste);
        buttons.add(btnVoirMorceauxGroupe);
        buttons.add(btnVoirMemeInterprete);
        buttons.add(btnRetour);
        buttons.add(btnQuitter);

        add(buttons, BorderLayout.SOUTH);

        btnAfficherCatalogue.addActionListener(e -> afficherCatalogue());

        btnRechercherMorceau.addActionListener(e -> {
            String titre = champTitre.getText().trim();

            if (titre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir un titre.");
                return;
            }

            Morceau morceau = controller.rechercherMorceau(titre);

            area.setText("");

            if (morceau == null) {
                area.setText("Aucun morceau trouvé.");
            } else {
                area.append("=== MORCEAU TROUVÉ ===\n");
                area.append(morceau.toString() + "\n");
            }
        });

        btnEcouter.addActionListener(e -> {
            String titre = champTitre.getText().trim();

            if (titre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir un titre.");
                return;
            }

            if (!controller.peutEcouterEnVisiteur()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Limite d'écoute atteinte : "
                                + controller.getNombreEcoutesVisiteur()
                                + "/" + controller.getLimiteEcoutesVisiteur()
                );
                return;
            }

            boolean ok = controller.ecouterEnVisiteur(titre);

            if (ok) {
                JOptionPane.showMessageDialog(
                        this,
                        "Lecture en cours...\nÉcoutes visiteur : "
                                + controller.getNombreEcoutesVisiteur()
                                + "/" + controller.getLimiteEcoutesVisiteur()
                );
            } else {
                JOptionPane.showMessageDialog(this, "Morceau introuvable.");
            }
        });

        btnVoirInterprete.addActionListener(e -> {
            String titre = champTitre.getText().trim();

            if (titre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir le titre d'un morceau.");
                return;
            }

            String nomInterprete = controller.getNomInterpreteDuMorceau(titre);
            String typeInterprete = controller.getTypeInterpreteDuMorceau(titre);

            area.setText("");

            if (nomInterprete == null || typeInterprete == null) {
                area.setText("Morceau introuvable.");
                return;
            }

            area.append("=== INTERPRÈTE DU MORCEAU ===\n");
            area.append("Morceau : " + titre + "\n");
            area.append("Type : " + typeInterprete + "\n");
            area.append("Nom : " + nomInterprete + "\n");
        });

        btnVoirMorceauxArtiste.addActionListener(e -> {
            String nomArtiste = JOptionPane.showInputDialog(this, "Nom de l'artiste :");

            if (nomArtiste == null || nomArtiste.trim().isEmpty()) {
                return;
            }

            List<Morceau> morceaux = controller.getMorceauxParArtiste(nomArtiste.trim());

            area.setText("");

            if (morceaux.isEmpty()) {
                area.setText("Aucun morceau trouvé pour cet artiste.");
                return;
            }

            area.append("=== MORCEAUX DE L'ARTISTE : " + nomArtiste + " ===\n");
            for (Morceau m : morceaux) {
                area.append(m + "\n");
            }
        });

        btnVoirMorceauxGroupe.addActionListener(e -> {
            String nomGroupe = JOptionPane.showInputDialog(this, "Nom du groupe :");

            if (nomGroupe == null || nomGroupe.trim().isEmpty()) {
                return;
            }

            List<Morceau> morceaux = controller.getMorceauxParGroupe(nomGroupe.trim());

            area.setText("");

            if (morceaux.isEmpty()) {
                area.setText("Aucun morceau trouvé pour ce groupe.");
                return;
            }

            area.append("=== MORCEAUX DU GROUPE : " + nomGroupe + " ===\n");
            for (Morceau m : morceaux) {
                area.append(m + "\n");
            }
        });

        btnVoirMemeInterprete.addActionListener(e -> {
            String titre = champTitre.getText().trim();

            if (titre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir le titre d'un morceau.");
                return;
            }

            afficherMorceauxDuMemeInterprete(titre);
        });

        btnRetour.addActionListener(e -> {
            frame.setContentPane(new LoginPanel(controller, frame));
            frame.revalidate();
            frame.repaint();
        });

        btnQuitter.addActionListener(e -> {
            controller.sauvegarder();
            frame.dispose();
        });
    }

    private void afficherCatalogue() {
        area.setText("");
        List<Morceau> morceaux = controller.getTousLesMorceaux();

        if (morceaux == null || morceaux.isEmpty()) {
            area.setText("Aucun morceau dans le catalogue.");
            return;
        }

        area.append("=== CATALOGUE ===\n");
        for (Morceau m : morceaux) {
            area.append(m + "\n");
        }
    }

    private void afficherMorceauxDuMemeInterprete(String titre) {
        String nomInterprete = controller.getNomInterpreteDuMorceau(titre);
        String typeInterprete = controller.getTypeInterpreteDuMorceau(titre);

        area.setText("");

        if (nomInterprete == null || typeInterprete == null) {
            area.setText("Morceau introuvable.");
            return;
        }

        List<Morceau> morceaux;

        if (typeInterprete.equalsIgnoreCase("artiste")) {
            morceaux = controller.getMorceauxParArtiste(nomInterprete);
            area.append("=== MORCEAUX DE L'ARTISTE : " + nomInterprete + " ===\n");
        } else {
            morceaux = controller.getMorceauxParGroupe(nomInterprete);
            area.append("=== MORCEAUX DU GROUPE : " + nomInterprete + " ===\n");
        }

        if (morceaux.isEmpty()) {
            area.append("Aucun morceau trouvé.\n");
            return;
        }

        for (Morceau m : morceaux) {
            area.append(m + "\n");
        }
    }
}