package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;
import java.util.List;

public class MainDuCroupier {
    private List<Card> mainDuCroupier;
    private Deck deck;

    public MainDuCroupier(Deck deck) {
        this.deck = deck;
        this.mainDuCroupier = new ArrayList<>();
    }

    public void flop() {
        if (deck == null) {
            System.out.println("Erreur : le deck n'est pas correctement initialisé.");
            return;
        }
    
        for (int i = 0; i < 3; i++) {
            ajouterALaMainDuCroupier(deck.tirer());
        }
    }

    //need to delete duplicate code
    public void turn() {
        ajouterALaMainDuCroupier(deck.tirer());
    }

    public void river() {
        ajouterALaMainDuCroupier(deck.tirer());
    }

    private void ajouterALaMainDuCroupier(Card card) {
        if (mainDuCroupier.size() < 5) {
            mainDuCroupier.add(card);
        }
    }

    public List<Card> getMainDuCroupier() {
        return this.mainDuCroupier;
    }

    public void afficherMain() {
        for (Card card : this.mainDuCroupier) {
            System.out.println(card.toString());
        }
    }

    public void vider() {
        this.mainDuCroupier.clear();
    }
}
