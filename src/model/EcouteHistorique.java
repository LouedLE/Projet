package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EcouteHistorique {
    private Morceau morceau;
    private LocalDateTime dateEcoute;

    public EcouteHistorique(Morceau morceau) {
        this.morceau = morceau;
        this.dateEcoute = LocalDateTime.now();
    }

    public Morceau getMorceau() { return morceau; }
    public LocalDateTime getDateEcoute() { return dateEcoute; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("%s - écouté le %s", morceau.getTitre(), dateEcoute.format(fmt));
    }
}
