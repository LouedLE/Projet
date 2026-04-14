package model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Abonne extends Utilisateur implements Serializable {
    private boolean suspendu;
    private List<Playlist> playlists;
    private HistoriqueEcoute historique;

    public Abonne(int id, String nomUtilisateur, String motDePasse) {
        super(id, nomUtilisateur, motDePasse);
        this.suspendu = false;
        this.playlists = new ArrayList<>();
        this.historique = new HistoriqueEcoute();
    }

    public boolean isSuspendu() {
        return suspendu;
    }

    public void setSuspendu(boolean suspendu) {
        this.suspendu = suspendu;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public HistoriqueEcoute getHistorique() {
        return historique;
    }

    public Playlist creerPlaylist(int id, String nom) {
        Playlist p = new Playlist(id, nom);
        playlists.add(p);
        return p;
    }

    public void supprimerPlaylist(Playlist p) {
        playlists.remove(p);
    }

    public void ecouterMorceau(Morceau m) {
        if (m != null) {
            historique.ajouterEcoute(m);
            m.incrementerEcoutes();
            System.out.println("Lecture du morceau : " + m.getTitre());
        }
    }

    public void renommerPlaylist(Playlist playlist, String nouveauNom) {
        if (playlist != null) {
            playlist.renommer(nouveauNom);
        }
    }

    public void ajouterMorceauAPlaylist(Playlist playlist, Morceau morceau) {
        if (playlist != null && morceau != null) {
            playlist.ajouterMorceau(morceau);
        }
    }

    public void retirerMorceauDePlaylist(Playlist playlist, Morceau morceau) {
        if (playlist != null && morceau != null) {
            playlist.retirerMorceau(morceau);
        }
    }


    @Override
    public String toString() {
        return "Abonne{id=" + id + ", nomUtilisateur='" + nomUtilisateur + "', suspendu=" + suspendu + "}";
    }
}