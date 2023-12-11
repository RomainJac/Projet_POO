package fr.pantheonsorbonne.miage.game.classes.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.pantheonsorbonne.miage.game.classes.Cartes.*;

public class Deck {
    private List<Card> Cards;
    
    public List<Card> getCards() {
        return Cards;
    }

    public void setCards(List<Card> cards) {
        Cards = cards;
    }

    private Random aleatoire = new Random();

    public Deck() {
        initialiserDeck();
    }

    public void initialiserDeck() {
        Cards = new ArrayList<>(52);
        int k = 0;
        for (int i = 0; i < 13; i++) {
            Cards.add(i, new Card(CardRank.values()[k], CardColor.TREFLE));
            k++;
        }
        k = 0;
        for (int i = 13; i < 26; i++) {
            Cards.add(i, new Card(CardRank.values()[k], CardColor.CARREAU));
            k++;
        }
        k = 0;
        for (int i = 26; i < 39; i++) {
            Cards.add(i, new Card(CardRank.values()[k], CardColor.PIQUE));
            k++;
        }
        k = 0;
        for (int i = 39; i < 52; i++) {
            Cards.add(i, new Card(CardRank.values()[k], CardColor.COEUR));
            k++;
        }
    }

    public ArrayList<Card> CarteAleatoires(int tailleDeck) {
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

    public static void reinitialiserDeck() {
        Deck deck = new Deck();
        deck.initialiserDeck();
    }

}
