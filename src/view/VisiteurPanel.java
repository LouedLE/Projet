package view;

import controller.JavazicController;
import model.Album;
import model.Artiste;
import model.Groupe;
import model.Morceau;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Mode Visiteur", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(40, 40, 40));
        title.setBorder(new EmptyBorder(5, 5, 15, 5));
        add(title, BorderLayout.NORTH);

        JPanel panelNord = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelNord.setBackground(new Color(245, 247, 250));

        JLabel labelTitre = new JLabel("Titre du morceau :");
        labelTitre.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panelNord.add(labelTitre);

        champTitre = new JTextField(22);
        champTitre.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panelNord.add(champTitre);

        add(panelNord, BorderLayout.BEFORE_FIRST_LINE);

        area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setBackground(Color.WHITE);
        area.setForeground(new Color(30, 30, 30));
        area.setMargin(new Insets(12, 12, 12, 12));

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                new EmptyBorder(0, 0, 0, 0)
        ));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(5, 3, 10, 10));
        buttons.setBackground(new Color(245, 247, 250));
        buttons.setBorder(new EmptyBorder(15, 0, 0, 0));

        JButton btnAfficherCatalogue = creerBouton("Voir morceaux");
        JButton btnAfficherAlbums = creerBouton("Voir albums");
        JButton btnRechercherMorceau = creerBouton("Rechercher morceau");
        JButton btnRechercherAlbum = creerBouton("Rechercher album");
        JButton btnEcouter = creerBouton("Écouter (limité)");
        JButton btnVoirInterprete = creerBouton("Voir interprète");
        JButton btnVoirMemeInterprete = creerBouton("Même interprète");
        JButton btnVoirInfosArtiste = creerBouton("Infos artiste");
        JButton btnVoirInfosGroupe = creerBouton("Infos groupe");
        JButton btnVoirMorceauxArtiste = creerBouton("Morceaux artiste");
        JButton btnVoirMorceauxGroupe = creerBouton("Morceaux groupe");
        JButton btnVoirAlbumsArtiste = creerBouton("Albums artiste");
        JButton btnVoirAlbumsGroupe = creerBouton("Albums groupe");
        JButton btnVoirDetailsAlbum = creerBouton("Détails album");
        JButton btnRetour = creerBouton("Retour");
        JButton btnQuitter = creerBoutonRouge("Quitter");

        buttons.add(btnAfficherCatalogue);
        buttons.add(btnAfficherAlbums);
        buttons.add(btnRechercherMorceau);
        buttons.add(btnRechercherAlbum);
        buttons.add(btnEcouter);
        buttons.add(btnVoirInterprete);
        buttons.add(btnVoirMemeInterprete);
        buttons.add(btnVoirInfosArtiste);
        buttons.add(btnVoirInfosGroupe);
        buttons.add(btnVoirMorceauxArtiste);
        buttons.add(btnVoirMorceauxGroupe);
        buttons.add(btnVoirAlbumsArtiste);
        buttons.add(btnVoirAlbumsGroupe);
        buttons.add(btnVoirDetailsAlbum);
        buttons.add(btnRetour);

        add(buttons, BorderLayout.SOUTH);

        JPanel panelEst = new JPanel(new BorderLayout());
        panelEst.setBackground(new Color(245, 247, 250));
        panelEst.setBorder(new EmptyBorder(15, 10, 0, 0));
        panelEst.add(btnQuitter, BorderLayout.NORTH);
        add(panelEst, BorderLayout.EAST);

        btnAfficherCatalogue.addActionListener(e -> afficherCatalogue());

        btnAfficherAlbums.addActionListener(e -> afficherAlbums());

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
                area.append(morceau + "\n");
            }
        });

        btnRechercherAlbum.addActionListener(e -> {
            String titreAlbum = JOptionPane.showInputDialog(this, "Titre de l'album :");

            if (titreAlbum == null || titreAlbum.trim().isEmpty()) {
                return;
            }

            Album album = controller.rechercherAlbum(titreAlbum.trim());

            area.setText("");

            if (album == null) {
                area.setText("Album introuvable.");
            } else {
                area.append("=== ALBUM TROUVÉ ===\n");
                area.append(album + "\n");
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

        btnVoirMemeInterprete.addActionListener(e -> {
            String titre = champTitre.getText().trim();

            if (titre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir le titre d'un morceau.");
                return;
            }

            afficherMorceauxDuMemeInterprete(titre);
        });

        btnVoirInfosArtiste.addActionListener(e -> {
            String nomArtiste = JOptionPane.showInputDialog(this, "Nom de l'artiste :");

            if (nomArtiste == null || nomArtiste.trim().isEmpty()) {
                return;
            }

            Artiste artiste = controller.rechercherArtiste(nomArtiste.trim());

            area.setText("");

            if (artiste == null) {
                area.setText("Artiste introuvable.");
                return;
            }

            area.append("=== INFOS ARTISTE ===\n");
            area.append(artiste + "\n");
            area.append("Nombre de morceaux : " + artiste.getMorceaux().size() + "\n");
            area.append("Nombre d'albums : " + artiste.getAlbums().size() + "\n");
        });

        btnVoirInfosGroupe.addActionListener(e -> {
            String nomGroupe = JOptionPane.showInputDialog(this, "Nom du groupe :");

            if (nomGroupe == null || nomGroupe.trim().isEmpty()) {
                return;
            }

            Groupe groupe = controller.rechercherGroupe(nomGroupe.trim());

            area.setText("");

            if (groupe == null) {
                area.setText("Groupe introuvable.");
                return;
            }

            area.append("=== INFOS GROUPE ===\n");
            area.append(groupe + "\n");
            area.append("Nombre de morceaux : " + groupe.getMorceaux().size() + "\n");
            area.append("Nombre d'albums : " + groupe.getAlbums().size() + "\n");
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

        btnVoirAlbumsArtiste.addActionListener(e -> {
            String nomArtiste = JOptionPane.showInputDialog(this, "Nom de l'artiste :");

            if (nomArtiste == null || nomArtiste.trim().isEmpty()) {
                return;
            }

            List<Album> albums = controller.getAlbumsParArtiste(nomArtiste.trim());

            area.setText("");

            if (albums.isEmpty()) {
                area.setText("Aucun album trouvé pour cet artiste.");
                return;
            }

            area.append("=== ALBUMS DE L'ARTISTE : " + nomArtiste + " ===\n");
            for (Album a : albums) {
                area.append(a + "\n");
            }
        });

        btnVoirAlbumsGroupe.addActionListener(e -> {
            String nomGroupe = JOptionPane.showInputDialog(this, "Nom du groupe :");

            if (nomGroupe == null || nomGroupe.trim().isEmpty()) {
                return;
            }

            List<Album> albums = controller.getAlbumsParGroupe(nomGroupe.trim());

            area.setText("");

            if (albums.isEmpty()) {
                area.setText("Aucun album trouvé pour ce groupe.");
                return;
            }

            area.append("=== ALBUMS DU GROUPE : " + nomGroupe + " ===\n");
            for (Album a : albums) {
                area.append(a + "\n");
            }
        });

        btnVoirDetailsAlbum.addActionListener(e -> {
            String titreAlbum = JOptionPane.showInputDialog(this, "Titre de l'album :");

            if (titreAlbum == null || titreAlbum.trim().isEmpty()) {
                return;
            }

            afficherDetailsAlbum(titreAlbum.trim());
        });

        btnRetour.addActionListener(e -> {
            frame.setContentPane(new LoginPanel(controller, frame));
            frame.revalidate();
            frame.repaint();
        });

        btnQuitter.addActionListener(e -> {
            controller.sauvegarder();
            System.exit(0);
        });
    }

    private JButton creerBouton(String texte) {
        JButton bouton = new JButton(texte);
        bouton.setFocusPainted(false);
        bouton.setFont(new Font("SansSerif", Font.BOLD, 13));
        bouton.setBackground(new Color(52, 120, 246));
        bouton.setForeground(Color.WHITE);
        bouton.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        return bouton;
    }

    private JButton creerBoutonRouge(String texte) {
        JButton bouton = new JButton(texte);
        bouton.setFocusPainted(false);
        bouton.setFont(new Font("SansSerif", Font.BOLD, 13));
        bouton.setBackground(new Color(220, 53, 69));
        bouton.setForeground(Color.WHITE);
        bouton.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        return bouton;
    }

    private void afficherCatalogue() {
        area.setText("");
        List<Morceau> morceaux = controller.getTousLesMorceaux();

        if (morceaux == null || morceaux.isEmpty()) {
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

        if (albums == null || albums.isEmpty()) {
            area.setText("Aucun album dans le catalogue.");
            return;
        }

        area.append("=== CATALOGUE DES ALBUMS ===\n");
        for (Album a : albums) {
            area.append(a + "\n");
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

    private void afficherDetailsAlbum(String titreAlbum) {
        Album album = controller.rechercherAlbum(titreAlbum);
        List<Morceau> morceaux = controller.getMorceauxDunAlbum(titreAlbum);

        area.setText("");

        if (album == null) {
            area.setText("Album introuvable.");
            return;
        }

        area.append("=== DÉTAILS DE L'ALBUM ===\n");
        area.append(album + "\n");
        area.append("Interprète : " + controller.getNomInterpreteDeAlbum(titreAlbum) + "\n");
        area.append("Type : " + controller.getTypeInterpreteDeAlbum(titreAlbum) + "\n");
        area.append("\n--- MORCEAUX ---\n");

        if (morceaux.isEmpty()) {
            area.append("Aucun morceau dans cet album.\n");
            return;
        }

        for (Morceau m : morceaux) {
            area.append(m + "\n");
        }
    }
}