package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.classes.Cartes.*;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void testCardConstructor() {
        Card card = new Card(CardRank.AS, CardColor.CARREAU);
        assertEquals(CardRank.AS, card.getCardRank());
        assertEquals(CardColor.CARREAU, card.getCardColor());
    }

    @Test
    public void testCardToString() {
        Card card = new Card(CardRank.AS, CardColor.CARREAU);
        assertEquals("AS de CARREAU", card.toString());
    }

    @Test
    public void testCardGetCardName() {
        Card card = new Card(CardRank.AS, CardColor.CARREAU);
        assertEquals("AS de CARREAU", card.CardEnChaine(card));
    }

}
