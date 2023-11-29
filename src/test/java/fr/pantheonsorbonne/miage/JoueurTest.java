package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.classes.Card;
import fr.pantheonsorbonne.miage.game.classes.Deck;
import fr.pantheonsorbonne.miage.game.classes.Joueur;
import fr.pantheonsorbonne.miage.game.classes.MainDuCroupier;
import fr.pantheonsorbonne.miage.game.classes.MainDuJoueur;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class JoueurTest {

    void testMiser() {
        Joueur joueur = new Joueur("Test", 100);

        int jetonsRestants = joueur.miser(50);
        assertEquals(50, joueur.getMise());
        assertEquals(50, jetonsRestants);

        jetonsRestants = joueur.miser(-10);
        assertEquals(50, joueur.getMise());
        assertEquals(50, jetonsRestants);

        jetonsRestants = joueur.miser(80);
        assertEquals(50, joueur.getMise());
        assertEquals(0, jetonsRestants);
        assertTrue(joueur.isTapis());
    }

    @Test
    void testAGagne() {
        Joueur joueur = new Joueur("Test", 100);
        joueur.aGagné(50);
        assertEquals(150, joueur.getPileDeJetons());
    }

    @Test
    void testAfficherMain() {
        Joueur joueur = new Joueur("Test", 100);

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        joueur.afficherMain();

        System.setOut(System.out);

        String consoleOutput = outputStream.toString().trim();
        assertTrue(consoleOutput.contains("Test a la main suivante :"));
        assertTrue(consoleOutput.contains("AS de PIQUE"));
        assertTrue(consoleOutput.contains("ROI de COEUR"));
    }

    @Test
    void testProbabiliteDeGagner() {
        Joueur joueur = new Joueur("Test", 100);
        Deck deck = new Deck();

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);
        MainDuCroupier croupier = new MainDuCroupier(deck);

        int probabilite = joueur.probabiliteDeGagner(croupier);
        assertTrue(probabilite >= 0 && probabilite <= 100);
    }

}
