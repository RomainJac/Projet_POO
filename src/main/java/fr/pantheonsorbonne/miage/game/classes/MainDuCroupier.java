package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;
import java.util.List;

public class MainDuCroupier {
    private List<Card> mainDuCroupier;
    private Deck Deck;

    public MainDuCroupier(Deck Deck) {
        this.mainDuCroupier = new ArrayList<>();
        this.Deck = Deck;
    }

    public void flop() {
        this.ajouterALaMainDuCroupier(Deck.tirer());
        this.ajouterALaMainDuCroupier(Deck.tirer());
        this.ajouterALaMainDuCroupier(Deck.tirer());
    }

    public void turn() {
        this.ajouterALaMainDuCroupier(Deck.tirer());
    }

    public void river() {
        this.ajouterALaMainDuCroupier(Deck.tirer());
    }

    public void ajouterALaMainDuCroupier(Card Card) {
        this.mainDuCroupier.add(Card);
    }

    public void ajouterALaMainDuCroupier(List<Card> Cards) {
        for (Card Card : Cards) {
            ajouterALaMainDuCroupier(Card);
        }
    }

    public List<Card> getMainDuCroupier() {
        return this.mainDuCroupier;
    }

    public void afficherMain() {
        System.out.println("Le croupier a la main suivante :");
        for (Card Card : this.mainDuCroupier) {
            System.out.println(Card);
        }
    }

    public void vider() {
        this.mainDuCroupier.clear();
    }

    public void definirMain(List<Card> Cards) {
        this.mainDuCroupier = Cards;
    }
}
