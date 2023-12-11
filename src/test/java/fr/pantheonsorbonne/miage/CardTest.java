package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.classes.Table.Card;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void testCardConstructor() {
        Card card = new Card(Card.cardRank.AS, Card.cardColor.CARREAU);
        assertEquals(Card.cardRank.AS, card.getCardRank());
        assertEquals(Card.cardColor.CARREAU, card.getCardColor());
    }

    @Test
    public void testCardToString() {
        Card card = new Card(Card.cardRank.AS, Card.cardColor.CARREAU);
        assertEquals("AS de CARREAU", card.toString());
    }

    @Test
    public void testCardGetCardName() {
        Card card = new Card(Card.cardRank.AS, Card.cardColor.CARREAU);
        assertEquals("AS de CARREAU", card.getCardName());
    }

}
