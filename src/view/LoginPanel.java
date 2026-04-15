package view;

import controller.JavazicController;
import model.Abonne;
import model.Administrateur;
import model.Utilisateur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginPanel extends JPanel {

    public LoginPanel(JavazicController controller, JFrame frame) {

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel titre = new JLabel("Bienvenue sur JAVAZIC", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 28));
        titre.setForeground(new Color(40, 40, 40));
        titre.setBorder(new EmptyBorder(10, 10, 30, 10));
        add(titre, BorderLayout.NORTH);

        JPanel centreWrapper = new JPanel(new GridBagLayout());
        centreWrapper.setBackground(new Color(245, 247, 250));

        JPanel centre = new JPanel(new GridLayout(2, 2, 15, 15));
        centre.setBackground(Color.WHITE);
        centre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(25, 25, 25, 25)
        ));
        centre.setPreferredSize(new Dimension(420, 130));

        JLabel labelLogin = new JLabel("Login :");
        labelLogin.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JTextField login = new JTextField();
        login.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JLabel labelMdp = new JLabel("Mot de passe :");
        labelMdp.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JPasswordField mdp = new JPasswordField();
        mdp.setFont(new Font("SansSerif", Font.PLAIN, 15));

        centre.add(labelLogin);
        centre.add(login);
        centre.add(labelMdp);
        centre.add(mdp);

        centreWrapper.add(centre);
        add(centreWrapper, BorderLayout.CENTER);

        JPanel boutons = new JPanel(new GridLayout(2, 2, 15, 15));
        boutons.setBackground(new Color(245, 247, 250));
        boutons.setBorder(new EmptyBorder(25, 80, 10, 80));

        JButton btnLogin = creerBouton("Connexion");
        JButton btnCreerCompte = creerBouton("Créer un compte");
        JButton btnVisiteur = creerBouton("Continuer en tant que visiteur");
        JButton btnQuitter = creerBouton("Quitter");

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
            System.exit(0);
        });
    }

    private JButton creerBouton(String texte) {
        JButton bouton = new JButton(texte);
        bouton.setFocusPainted(false);
        bouton.setFont(new Font("SansSerif", Font.BOLD, 14));
        bouton.setBackground(new Color(52, 120, 246));
        bouton.setForeground(Color.WHITE);
        bouton.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
        return bouton;
    }
}