package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;
import java.util.List;

public class MainDuJoueur {
    private List<Card> cartes;

    public MainDuJoueur(List<Card> cartes) {
        this.cartes = new ArrayList<>(cartes);
    }

    public void ajouter(Card carte) {
        this.cartes.add(carte);
    }

    public void retirer(Card carte) {
        this.cartes.remove(carte);
    }

    public void retirerCarteAleatoire() {
        if (!cartes.isEmpty()) {
            int indexAleatoire = (int) (Math.random() * cartes.size());
            cartes.remove(indexAleatoire);
        }
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
