package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;
import java.util.List;

public class MainDuJoueur {
    private List<Card> main;

    public List<Card > getMainDuJoueur() {
        return this.main;
    }

    public MainDuJoueur(List<Card > Card) {
        this.main = new ArrayList<>(Card);
    }

    public void ajouter(Card Card ) {
        this.main.add(Card);
    }

    public void retirer(Card Card ) {
        this.main.remove(Card);
    }

    public void retirerCarteAleatoire() {
        if (!main.isEmpty()) {
            int indexAleatoire = (int) (Math.random() * main.size());
            this.main.remove(indexAleatoire);
        }
    }
}
