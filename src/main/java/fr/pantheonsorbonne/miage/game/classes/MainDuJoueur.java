package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;
import java.util.List;

public class MainDuJoueur {
    private List<Card> cartes;

    public MainDuJoueur(List<Card> cartes) {
        this.cartes = new ArrayList<>(cartes);
    }

    public List<String> getCardNames() {
        List<String> cardNames = new ArrayList<>();
        for (Card carte : this.cartes) {
            cardNames.add(carte.getCardName());
        }
        return cardNames;
    }

    public List<Card> getMainDuJoueur() {
        return this.cartes;
    }
}
