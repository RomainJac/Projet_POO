package fr.pantheonsorbonne.miage.game.classes.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.pantheonsorbonne.miage.game.classes.Cartes.*;

/**
 * Classe représentant un paquet de cartes.
 */
public class Deck {
    private List<Card> Cards;
    private Random aleatoire = new Random();

    public List<Card> getCards() {
        return Cards;
    }

    public void setCards(List<Card> cards) {
        Cards = cards;
    }

    public Deck() {
        initialiserDeck();
    }

    /**
     * Initialise le paquet de cartes avec 52 cartes de quatre couleurs d'un jeu de
     * Poker standard.
     */
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

    /**
     * Tire un nombre spécifié de cartes aléatoires du paquet.
     *
     * @param nbCartes Le nombre de cartes à tirer.
     * @return Une liste de cartes tirées.
     */
    public ArrayList<Card> CarteAleatoires(int nbCartes) {
        ArrayList<Card> CardsTirees = new ArrayList<>(nbCartes);
        for (int i = 0; i < nbCartes; i++) {
            CardsTirees.add(tirer());
        }
        return CardsTirees;
    }

    /**
     * Tire une carte aléatoire du paquet.

     * @return La carte tirée.
     */
    public Card tirer() {
        int indexAleatoire = aleatoire.nextInt(Cards.size());
        Card CardTiree = Cards.get(indexAleatoire);
        Cards.remove(indexAleatoire);
        return CardTiree;
    }

    /**
     * Réinitialise le paquet de cartes en créant un nouveau paquet.
     */
    public static void reinitialiserDeck() {
        Deck deck = new Deck();
        deck.initialiserDeck();
    }
}
