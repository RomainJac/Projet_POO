package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;
import fr.pantheonsorbonne.miage.game.classes.MainDuCroupier;
import fr.pantheonsorbonne.miage.game.classes.Deck;

import static org.junit.jupiter.api.Assertions.*;

public class MainDuCroupierTest {

    @Test
    void testAjouterALaMainDuCroupier() {
        Deck deck = new Deck();
        MainDuCroupier mainDuCroupier = new MainDuCroupier(deck);

        mainDuCroupier.ajouterALaMainDuCroupier();
        assertEquals(1, mainDuCroupier.getMainDuCroupier().size());

        mainDuCroupier.ajouterALaMainDuCroupier(2);
        assertEquals(3, mainDuCroupier.getMainDuCroupier().size());

        mainDuCroupier.ajouterALaMainDuCroupier(3);
        assertEquals(5, mainDuCroupier.getMainDuCroupier().size());
        mainDuCroupier.vider();

        mainDuCroupier.ajouterALaMainDuCroupier(4);
        assertEquals(4, mainDuCroupier.getMainDuCroupier().size());

        mainDuCroupier.ajouterALaMainDuCroupier(5);
        assertEquals(5, mainDuCroupier.getMainDuCroupier().size());
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
