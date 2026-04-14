package view;

import controller.JavazicController;
import model.Abonne;
import model.Album;
import model.Artiste;
import model.Avis;
import model.Groupe;
import model.Morceau;
import model.Playlist;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AbonnePanel extends JPanel {

    private final JavazicController controller;
    private final Abonne abonne;
    private final JFrame frame;

    private final JTextArea area;
    private final JTextField champTitre;

    public AbonnePanel(JavazicController controller, Abonne abonne, JFrame frame) {
        this.controller = controller;
        this.abonne = abonne;
        this.frame = frame;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Menu Abonné - " + abonne.getNomUtilisateur(), SwingConstants.CENTER);
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

        JPanel buttons = new JPanel(new GridLayout(8, 3, 8, 8));

        JButton btnAfficherCatalogue = new JButton("Voir morceaux");
        JButton btnAfficherAlbums = new JButton("Voir albums");
        JButton btnRechercherMorceau = new JButton("Rechercher morceau");
        JButton btnRechercherAlbum = new JButton("Rechercher album");
        JButton btnEcouter = new JButton("Écouter");
        JButton btnVoirInterprete = new JButton("Voir interprète");
        JButton btnVoirMemeInterprete = new JButton("Même interprète");

        JButton btnCreerPlaylist = new JButton("Créer playlist");
        JButton btnVoirPlaylists = new JButton("Voir playlists");
        JButton btnRenommerPlaylist = new JButton("Renommer playlist");
        JButton btnSupprimerPlaylist = new JButton("Supprimer playlist");
        JButton btnAjouterMorceauPlaylist = new JButton("Ajouter à playlist");
        JButton btnRetirerMorceauPlaylist = new JButton("Retirer de playlist");

        JButton btnAjouterAvis = new JButton("Ajouter / modifier avis");
        JButton btnVoirAvis = new JButton("Voir avis");
        JButton btnSupprimerAvis = new JButton("Supprimer mon avis");
        JButton btnVoirHistorique = new JButton("Voir historique");

        JButton btnVoirInfosArtiste = new JButton("Infos artiste");
        JButton btnVoirInfosGroupe = new JButton("Infos groupe");
        JButton btnVoirMorceauxArtiste = new JButton("Morceaux artiste");
        JButton btnVoirMorceauxGroupe = new JButton("Morceaux groupe");
        JButton btnVoirAlbumsArtiste = new JButton("Albums artiste");
        JButton btnVoirAlbumsGroupe = new JButton("Albums groupe");
        JButton btnVoirDetailsAlbum = new JButton("Détails album");

        JButton btnLogout = new JButton("Déconnexion");

        buttons.add(btnAfficherCatalogue);
        buttons.add(btnAfficherAlbums);
        buttons.add(btnRechercherMorceau);
        buttons.add(btnRechercherAlbum);
        buttons.add(btnEcouter);
        buttons.add(btnVoirInterprete);
        buttons.add(btnVoirMemeInterprete);

        buttons.add(btnCreerPlaylist);
        buttons.add(btnVoirPlaylists);
        buttons.add(btnRenommerPlaylist);
        buttons.add(btnSupprimerPlaylist);
        buttons.add(btnAjouterMorceauPlaylist);
        buttons.add(btnRetirerMorceauPlaylist);

        buttons.add(btnAjouterAvis);
        buttons.add(btnVoirAvis);
        buttons.add(btnSupprimerAvis);
        buttons.add(btnVoirHistorique);

        buttons.add(btnVoirInfosArtiste);
        buttons.add(btnVoirInfosGroupe);
        buttons.add(btnVoirMorceauxArtiste);
        buttons.add(btnVoirMorceauxGroupe);
        buttons.add(btnVoirAlbumsArtiste);
        buttons.add(btnVoirAlbumsGroupe);
        buttons.add(btnVoirDetailsAlbum);

        buttons.add(btnLogout);

        add(buttons, BorderLayout.SOUTH);

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

            boolean ok = controller.ecouterCommeAbonne(abonne, titre);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Lecture en cours...");
                afficherHistorique();
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

        btnCreerPlaylist.addActionListener(e -> {
            String nomPlaylist = JOptionPane.showInputDialog(this, "Nom de la playlist :");

            if (nomPlaylist == null || nomPlaylist.trim().isEmpty()) {
                return;
            }

            boolean ok = controller.creerPlaylist(abonne, nomPlaylist.trim());

            if (ok) {
                JOptionPane.showMessageDialog(this, "Playlist créée.");
                afficherPlaylists();
            } else {
                JOptionPane.showMessageDialog(this, "Impossible de créer la playlist.");
            }
        });

        btnVoirPlaylists.addActionListener(e -> afficherPlaylists());

        btnRenommerPlaylist.addActionListener(e -> {
            String ancienNom = JOptionPane.showInputDialog(this, "Nom actuel de la playlist :");
            if (ancienNom == null || ancienNom.trim().isEmpty()) {
                return;
            }

            String nouveauNom = JOptionPane.showInputDialog(this, "Nouveau nom :");
            if (nouveauNom == null || nouveauNom.trim().isEmpty()) {
                return;
            }

            boolean ok = controller.renommerPlaylist(abonne, ancienNom.trim(), nouveauNom.trim());

            if (ok) {
                JOptionPane.showMessageDialog(this, "Playlist renommée.");
                afficherPlaylists();
            } else {
                JOptionPane.showMessageDialog(this, "Playlist introuvable.");
            }
        });

        btnSupprimerPlaylist.addActionListener(e -> {
            String nomPlaylist = JOptionPane.showInputDialog(this, "Nom de la playlist à supprimer :");
            if (nomPlaylist == null || nomPlaylist.trim().isEmpty()) {
                return;
            }

            boolean ok = controller.supprimerPlaylist(abonne, nomPlaylist.trim());

            if (ok) {
                JOptionPane.showMessageDialog(this, "Playlist supprimée.");
                afficherPlaylists();
            } else {
                JOptionPane.showMessageDialog(this, "Playlist introuvable.");
            }
        });

        btnAjouterMorceauPlaylist.addActionListener(e -> {
            String titre = champTitre.getText().trim();

            if (titre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir un titre de morceau.");
                return;
            }

            String nomPlaylist = JOptionPane.showInputDialog(this, "Nom de la playlist :");
            if (nomPlaylist == null || nomPlaylist.trim().isEmpty()) {
                return;
            }

            boolean ok = controller.ajouterMorceauAPlaylist(abonne, nomPlaylist.trim(), titre);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Morceau ajouté à la playlist.");
                afficherPlaylists();
            } else {
                JOptionPane.showMessageDialog(this, "Impossible d'ajouter le morceau.");
            }
        });

        btnRetirerMorceauPlaylist.addActionListener(e -> {
            String titre = champTitre.getText().trim();

            if (titre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir un titre de morceau.");
                return;
            }

            String nomPlaylist = JOptionPane.showInputDialog(this, "Nom de la playlist :");
            if (nomPlaylist == null || nomPlaylist.trim().isEmpty()) {
                return;
            }

            boolean ok = controller.retirerMorceauDePlaylist(abonne, nomPlaylist.trim(), titre);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Morceau retiré de la playlist.");
                afficherPlaylists();
            } else {
                JOptionPane.showMessageDialog(this, "Impossible de retirer le morceau.");
            }
        });

        btnAjouterAvis.addActionListener(e -> {
            String titre = champTitre.getText().trim();

            if (titre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir le titre d'un morceau.");
                return;
            }

            String noteTexte = JOptionPane.showInputDialog(this, "Note (0 à 5) :");
            if (noteTexte == null || noteTexte.trim().isEmpty()) {
                return;
            }

            int note;
            try {
                note = Integer.parseInt(noteTexte.trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La note doit être un entier.");
                return;
            }

            String commentaire = JOptionPane.showInputDialog(this, "Commentaire :");
            if (commentaire == null) {
                return;
            }

            boolean ok = controller.ajouterOuModifierAvis(abonne, titre, note, commentaire);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Avis enregistré.");
                afficherAvisDuMorceau(titre);
            } else {
                JOptionPane.showMessageDialog(this, "Impossible d'enregistrer l'avis.");
            }
        });

        btnVoirAvis.addActionListener(e -> {
            String titre = champTitre.getText().trim();

            if (titre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir le titre d'un morceau.");
                return;
            }

            afficherAvisDuMorceau(titre);
        });

        btnSupprimerAvis.addActionListener(e -> {
            String titre = champTitre.getText().trim();

            if (titre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir le titre d'un morceau.");
                return;
            }

            boolean ok = controller.supprimerAvis(abonne, titre);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Avis supprimé.");
                afficherAvisDuMorceau(titre);
            } else {
                JOptionPane.showMessageDialog(this, "Aucun avis trouvé ou morceau introuvable.");
            }
        });

        btnVoirHistorique.addActionListener(e -> afficherHistorique());

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

    private void afficherPlaylists() {
        area.setText("");

        List<Playlist> playlists = controller.getPlaylistsAbonne(abonne);

        if (playlists.isEmpty()) {
            area.setText("Aucune playlist.");
            return;
        }

        area.append("=== PLAYLISTS ===\n");
        for (Playlist p : playlists) {
            area.append("Playlist : " + p.getNom() + "\n");

            if (p.getMorceaux().isEmpty()) {
                area.append("  (vide)\n");
            } else {
                for (Morceau m : p.getMorceaux()) {
                    area.append("  - " + m + "\n");
                }
            }
            area.append("\n");
        }
    }

    private void afficherHistorique() {
        area.setText("");

        List<Morceau> historique = controller.getHistoriqueAbonne(abonne);

        if (historique.isEmpty()) {
            area.setText("Historique vide.");
            return;
        }

        area.append("=== HISTORIQUE ===\n");
        for (Morceau m : historique) {
            area.append(m + "\n");
        }
    }

    private void afficherAvisDuMorceau(String titre) {
        area.setText("");

        List<Avis> avis = controller.getAvisDuMorceau(titre);

        if (avis == null) {
            area.setText("Morceau introuvable.");
            return;
        }

        if (avis.isEmpty()) {
            area.setText("Aucun avis pour ce morceau.");
            return;
        }

        area.append("=== AVIS : " + titre + " ===\n");
        for (Avis a : avis) {
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