package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
    private List<Card> Cards;
    private Random aleatoire = new Random();

    public Deck() {
        initialiserDeck();
    }

    public void initialiserDeck() {
        Cards = new ArrayList<>(52);
        int k = 0;
        for (int i = 0; i < 13; i++) {
            Cards.add(i, new Card(Card.cardRank.values()[k], Card.cardColor.TREFLE));
            k++;
        }
        k = 0;
        for (int i = 13; i < 26; i++) {
            Cards.add(i, new Card(Card.cardRank.values()[k], Card.cardColor.CARREAU));
            k++;
        }
        k = 0;
        for (int i = 26; i < 39; i++) {
            Cards.add(i, new Card(Card.cardRank.values()[k], Card.cardColor.PIQUE));
            k++;
        }
        k = 0;
        for (int i = 39; i < 52; i++) {
            Cards.add(i, new Card(Card.cardRank.values()[k], Card.cardColor.COEUR));
            k++;
        }
    }

    public ArrayList<Card> CardsAleatoires(int tailleDeck) {
        ArrayList<Card> CardsTirees = new ArrayList<>(tailleDeck);
        for (int i = 0; i < tailleDeck; i++) {
            CardsTirees.add(tirer());
        }
        return CardsTirees;
    }

    public Card tirer() {
        int indexAleatoire = aleatoire.nextInt(Cards.size());
        Card CardTiree = Cards.get(indexAleatoire);
        Cards.remove(indexAleatoire);
        return CardTiree;
    }

    public void retourAuDeck(List<Card> CardsRetournees) {
        Cards.addAll(CardsRetournees);
    }

    public static void reinitialiserDeck() {
        Deck deck = new Deck();
        deck.initialiserDeck();
    }

    public void defausser() {
        Cards.remove(aleatoire.nextInt(Cards.size()));
    }
}
