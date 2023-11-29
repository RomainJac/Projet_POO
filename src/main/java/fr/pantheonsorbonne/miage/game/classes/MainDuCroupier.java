package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;
import java.util.List;

public class MainDuCroupier {
    private List<Card> mainDuCroupier;
    private Deck deck;

    public MainDuCroupier(Deck deck) {
        this.deck = (Deck) deck;
        this.mainDuCroupier = new ArrayList<>();
    }


    public void tirerCarte(int i) {
        switch (i) {
            case 1 :
                for (int x = 0; x < 2; x++) {
                    ajouterALaMainDuCroupier(deck.tirer());
                };
            case 2 :
                ajouterALaMainDuCroupier(deck.tirer());
            case 3 :
                ajouterALaMainDuCroupier(deck.tirer());
        }

    }

    public void ajouterALaMainDuCroupier(Card card) {
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
