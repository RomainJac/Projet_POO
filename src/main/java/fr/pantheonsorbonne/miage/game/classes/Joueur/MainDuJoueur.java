package fr.pantheonsorbonne.miage.game.classes.Joueur;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.pantheonsorbonne.miage.game.classes.Table.Card;

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

    public void supprimerCarte() {
        if (!this.cartes.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(this.cartes.size());
            this.cartes.remove(index);
        }
    }
       
    public void tirerCarte(Card carte) {
        if (carte != null) {
            this.cartes.add(carte);
        }
    }
    
    public void devoilerCarte(int index) {
        if (index >= 0 && index < this.cartes.size()) {
            this.cartes.get(index).montre();
        }
    }

    public List<Card> getMainDuJoueur() {
        return this.cartes;
    }
}
