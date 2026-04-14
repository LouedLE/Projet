package model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class HistoriqueEcoute implements Serializable {
    private List<Morceau> morceauxEcoutes;

    public HistoriqueEcoute() {
        this.morceauxEcoutes = new ArrayList<>();
    }

    public List<Morceau> getMorceauxEcoutes() {
        return morceauxEcoutes;
    }

    public void ajouterEcoute(Morceau m) {
        morceauxEcoutes.add(m);
    }

    @Override
    public String toString() {
        return "HistoriqueEcoute{nbEcoutes=" + morceauxEcoutes.size() + "}";
    }
}