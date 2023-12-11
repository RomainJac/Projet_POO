package fr.pantheonsorbonne.miage.game.classes.Table;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.game.classes.Cartes.Card;

public class MainDuCroupier {
    private List<Card> mainDuCroupier;
    private Deck deck;

    public MainDuCroupier(Deck deck) {
        this.deck = deck;
        this.mainDuCroupier = new ArrayList<>();
    }

    public void tirerCarte(int i) {
        switch (i) {
            case 1:
                initialiserMainDuCroupier(3);
                break;
            case 2:
                ajouterALaMainDuCroupier();
                break;
            case 3:
                ajouterALaMainDuCroupier();
                break;
        }

    }

    public void ajouterALaMainDuCroupier() {
        if (mainDuCroupier.size() < 5) {
            mainDuCroupier.add(this.deck.tirer());
        }
    }

    public void initialiserMainDuCroupier(int nombreDeCarte) {
        for (int i = 0; i < nombreDeCarte; i++) {
            ajouterALaMainDuCroupier();
        }
    }

    public void ajouterALaMainDuCroupierCarte(Card card) {
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
