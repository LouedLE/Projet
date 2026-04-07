import model.Morceau;
import model.Album;
import model.Artiste;
import model.Groupe;

public class Main {
    public static void main(String[] args) {

        // Création des objets
        Morceau morceau = new Morceau(1, "Shape of You", 3.5, "Pop");
        Album album = new Album(1, "Divide", 2017);
        Artiste artiste = new Artiste(1, "Ed Sheeran");
        Groupe groupe = new Groupe(1, "Imagine Dragons");

        // Affichage des objets
        System.out.println(morceau);
        System.out.println(album);
        System.out.println(artiste);
        System.out.println(groupe);

        // Test de la méthode lireExtrait()
        morceau.lireExtrait();
    }
}