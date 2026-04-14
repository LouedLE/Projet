package view;

import controller.JavazicController;
import model.Abonne;
import model.Administrateur;
import model.Utilisateur;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    public LoginPanel(JavazicController controller, JFrame frame) {

        setLayout(new BorderLayout());

        JLabel titre = new JLabel("Bienvenue sur JAVAZIC", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 22));
        add(titre, BorderLayout.NORTH);

        JPanel centre = new JPanel(new GridLayout(3, 2, 10, 10));

        JTextField login = new JTextField();
        JPasswordField mdp = new JPasswordField();

        centre.add(new JLabel("Login :"));
        centre.add(login);
        centre.add(new JLabel("Mot de passe :"));
        centre.add(mdp);

        add(centre, BorderLayout.CENTER);

        JPanel boutons = new JPanel(new GridLayout(2, 2, 10, 10));

        JButton btnLogin = new JButton("Connexion");
        JButton btnCreerCompte = new JButton("Créer un compte");
        JButton btnVisiteur = new JButton("Continuer en tant que visiteur");
        JButton btnQuitter = new JButton("Quitter");

        boutons.add(btnLogin);
        boutons.add(btnCreerCompte);
        boutons.add(btnVisiteur);
        boutons.add(btnQuitter);

        add(boutons, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> {
            String nomUtilisateur = login.getText().trim();
            String motDePasse = new String(mdp.getPassword());

            if (nomUtilisateur.isEmpty() || motDePasse.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir le login et le mot de passe.");
                return;
            }

            Utilisateur user = controller.connecter(nomUtilisateur, motDePasse);

            if (user == null) {
                JOptionPane.showMessageDialog(this, "Identifiants incorrects ou compte suspendu.");
            } else if (user instanceof Abonne) {
                frame.setContentPane(new AbonnePanel(controller, (Abonne) user, frame));
                frame.revalidate();
                frame.repaint();
            } else if (user instanceof Administrateur) {
                frame.setContentPane(new AdminPanel(controller, (Administrateur) user, frame));
                frame.revalidate();
                frame.repaint();
            }
        });

        btnCreerCompte.addActionListener(e -> {
            String nomUtilisateur = JOptionPane.showInputDialog(this, "Choisissez un login :");
            if (nomUtilisateur == null || nomUtilisateur.trim().isEmpty()) {
                return;
            }

            String motDePasse = JOptionPane.showInputDialog(this, "Choisissez un mot de passe :");
            if (motDePasse == null || motDePasse.trim().isEmpty()) {
                return;
            }

            Abonne abonne = controller.creerCompte(nomUtilisateur.trim(), motDePasse.trim());

            if (abonne != null) {
                JOptionPane.showMessageDialog(this, "Compte créé avec succès.");
            } else {
                JOptionPane.showMessageDialog(this, "Impossible de créer le compte.");
            }
        });

        btnVisiteur.addActionListener(e -> {
            frame.setContentPane(new VisiteurPanel(controller, frame));
            frame.revalidate();
            frame.repaint();
        });

        btnQuitter.addActionListener(e -> {
            controller.sauvegarder();
            frame.dispose();
        });
    }
}