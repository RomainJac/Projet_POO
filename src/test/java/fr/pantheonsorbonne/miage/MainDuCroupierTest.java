package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;
import fr.pantheonsorbonne.miage.game.classes.MainDuCroupier;
import fr.pantheonsorbonne.miage.game.classes.Deck;
import fr.pantheonsorbonne.miage.game.classes.Card;

import static org.junit.jupiter.api.Assertions.*;

public class MainDuCroupierTest {

    @Test
    void testAjouterALaMainDuCroupier() {
        Deck deck = new Deck();
        MainDuCroupier mainDuCroupier = new MainDuCroupier(deck);

        mainDuCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.AS, Card.cardColor.COEUR));
        assertEquals(1, mainDuCroupier.getMainDuCroupier().size());

        mainDuCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.ROI, Card.cardColor.PIQUE));
        assertEquals(2, mainDuCroupier.getMainDuCroupier().size());
    }

    @Test
    void testVider() {
        Deck deck = new Deck();
        MainDuCroupier mainDuCroupier = new MainDuCroupier(deck);

        mainDuCroupier.tirerCarte(1);

        assertFalse(mainDuCroupier.getMainDuCroupier().isEmpty());

        mainDuCroupier.vider();

        assertTrue(mainDuCroupier.getMainDuCroupier().isEmpty());
    }
}
