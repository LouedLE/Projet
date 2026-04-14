package model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class SystemeJavazic implements Serializable {
    private Catalogue catalogue;
    private List<Abonne> abonnes;
    private List<Administrateur> administrateurs;

    public SystemeJavazic() {
        this.catalogue = new Catalogue();
        this.abonnes = new ArrayList<>();
        this.administrateurs = new ArrayList<>();
    }

    public Catalogue getCatalogue() {
        return catalogue;
    }

    public List<Abonne> getAbonnes() {
        return abonnes;
    }

    public List<Administrateur> getAdministrateurs() {
        return administrateurs;
    }

    public void ajouterAbonne(Abonne abonne) {
        if (!abonnes.contains(abonne)) {
            abonnes.add(abonne);
        }
    }

    public void supprimerAbonne(Abonne abonne) {
        abonnes.remove(abonne);
    }

    public void ajouterAdministrateur(Administrateur administrateur) {
        if (!administrateurs.contains(administrateur)) {
            administrateurs.add(administrateur);
        }
    }

    public int getNombreAbonnes() {
        return abonnes.size();
    }

    public int getNombreAdministrateurs() {
        return administrateurs.size();
    }

    public Abonne creerCompte(String nomUtilisateur, String motDePasse) {
        int id = abonnes.size() + 1;
        Abonne abonne = new Abonne(id, nomUtilisateur, motDePasse);
        abonnes.add(abonne);
        return abonne;
    }

    public Utilisateur seConnecter(String nomUtilisateur, String motDePasse) {

        // Vérifier les abonnés
        for (Abonne a : abonnes) {
            if (a.getNomUtilisateur().equals(nomUtilisateur)
                    && a.getMotDePasse().equals(motDePasse)) {

                if (a.isSuspendu()) {
                    System.out.println("Compte suspendu !");
                    return null;
                }

                return a;
            }
        }

        // Vérifier les administrateurs
        for (Administrateur admin : administrateurs) {
            if (admin.getNomUtilisateur().equals(nomUtilisateur)
                    && admin.getMotDePasse().equals(motDePasse)) {

                return admin;
            }
        }

        return null;
    }

    public void sauvegarder(String fichier) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier));
            oos.writeObject(this);
            oos.close();
            System.out.println("Données sauvegardées !");
        } catch (IOException e) {
            System.out.println("Erreur sauvegarde : " + e.getMessage());
        }
    }

    public static SystemeJavazic charger(String fichier) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier));
            SystemeJavazic systeme = (SystemeJavazic) ois.readObject();
            ois.close();
            System.out.println("Données chargées !");
            return systeme;
        } catch (Exception e) {
            System.out.println("Aucune sauvegarde trouvée, nouveau système créé.");
            return new SystemeJavazic();
        }
    }

    @Override
    public String toString() {
        return "SystemeJavazic{abonnes=" + abonnes.size() +
                ", administrateurs=" + administrateurs.size() + "}";
    }
}