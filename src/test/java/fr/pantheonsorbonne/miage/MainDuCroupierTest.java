package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.classes.Cartes.*;
import fr.pantheonsorbonne.miage.game.classes.Table.Deck;
import fr.pantheonsorbonne.miage.game.classes.Table.MainDuCroupier;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MainDuCroupierTest {

    @Test 
    void testAjouterALaMainDuCroupierCarte() {
        MainDuCroupier mainDuCroupier = new MainDuCroupier(null);
        Card carte1 = new Card(CardRank.AS, CardColor.PIQUE);
        Card carte2 = new Card(CardRank.ROI, CardColor.COEUR);
        Card carte3 = new Card(CardRank.DAME, CardColor.TREFLE);
        Card carte4 = new Card(CardRank.DIX, CardColor.CARREAU);
        Card carte5 = new Card(CardRank.NEUF, CardColor.PIQUE);
        Card carte6 = new Card(CardRank.HUIT, CardColor.COEUR);

        mainDuCroupier.ajouterALaMainDuCroupierCarte(carte1);
        mainDuCroupier.ajouterALaMainDuCroupierCarte(carte2);
        mainDuCroupier.ajouterALaMainDuCroupierCarte(carte3);
        mainDuCroupier.ajouterALaMainDuCroupierCarte(carte4);
        mainDuCroupier.ajouterALaMainDuCroupierCarte(carte5);

        assertEquals(5, mainDuCroupier.getMainDuCroupier().size());
        assertTrue(mainDuCroupier.getMainDuCroupier().contains(carte1));
        assertTrue(mainDuCroupier.getMainDuCroupier().contains(carte2));
        assertTrue(mainDuCroupier.getMainDuCroupier().contains(carte3));
        assertTrue(mainDuCroupier.getMainDuCroupier().contains(carte4));
        assertTrue(mainDuCroupier.getMainDuCroupier().contains(carte5));

        mainDuCroupier.ajouterALaMainDuCroupierCarte(carte6);

        assertEquals(5, mainDuCroupier.getMainDuCroupier().size());
        assertFalse(mainDuCroupier.getMainDuCroupier().contains(carte6));
    }

    @Test
    void testVider() {
        Deck deck = new Deck();
        MainDuCroupier mainDuCroupier = new MainDuCroupier(deck);

        mainDuCroupier.tirerCarte(1);

        assertFalse(mainDuCroupier.getMainDuCroupier().isEmpty());

        mainDuCroupier.viderMainCroupier();

        assertTrue(mainDuCroupier.getMainDuCroupier().isEmpty());
    }

     @Test
    void testAfficherMain() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        MainDuCroupier mainDuCroupier = new MainDuCroupier(null);
        Card carte1 = new Card(CardRank.AS, CardColor.PIQUE);
        Card carte2 = new Card(CardRank.ROI, CardColor.COEUR);
        mainDuCroupier.ajouterALaMainDuCroupierCarte(carte1);
        mainDuCroupier.ajouterALaMainDuCroupierCarte(carte2);

        mainDuCroupier.afficherMain();

        System.setOut(System.out);

        String consoleOutput = outputStream.toString().trim();

        assert consoleOutput.contains("AS de P");
        assert consoleOutput.contains("ROI de C");
    }
}
