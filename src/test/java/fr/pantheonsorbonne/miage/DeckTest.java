<<<<<<< HEAD
=======
package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import fr.pantheonsorbonne.miage.game.classes.Card;
import fr.pantheonsorbonne.miage.game.classes.Deck;

public class DeckTest {

    @Test
    public void testInitialiserDeck() {
        Deck deck = new Deck();
        deck.initialiserDeck();
        assertEquals(52, deck.getCards().size());
        // Ajoutez d'autres assertions pour vérifier que toutes les cartes sont présentes
    }

    @Test
    public void testTirer() {
        Deck deck = new Deck();
        deck.initialiserDeck();
        int initialSize = deck.getCards().size();

        Card cardTiree = deck.tirer();

        assertNotNull(cardTiree);
        assertEquals(initialSize - 1, deck.getCards().size());
    }

    @Test
    public void testCardsAleatoires() {
        Deck deck = new Deck();
        deck.initialiserDeck();
        int tailleDeck = 5;

        ArrayList<Card> cardsTirees = deck.CardsAleatoires(tailleDeck);

        assertNotNull(cardsTirees);
        assertEquals(tailleDeck, cardsTirees.size());
    }

    
}

>>>>>>> 1709d9d273db48b1eb16593f18ff95f83d62ceef
