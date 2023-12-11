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

        // Ajouter 5 cartes à la main du croupier
        mainDuCroupier.ajouterALaMainDuCroupierCarte(carte1);
        mainDuCroupier.ajouterALaMainDuCroupierCarte(carte2);
        mainDuCroupier.ajouterALaMainDuCroupierCarte(carte3);
        mainDuCroupier.ajouterALaMainDuCroupierCarte(carte4);
        mainDuCroupier.ajouterALaMainDuCroupierCarte(carte5);

        // Vérifier que les cartes sont ajoutées correctement
        assertEquals(5, mainDuCroupier.getMainDuCroupier().size());
        assertTrue(mainDuCroupier.getMainDuCroupier().contains(carte1));
        assertTrue(mainDuCroupier.getMainDuCroupier().contains(carte2));
        assertTrue(mainDuCroupier.getMainDuCroupier().contains(carte3));
        assertTrue(mainDuCroupier.getMainDuCroupier().contains(carte4));
        assertTrue(mainDuCroupier.getMainDuCroupier().contains(carte5));

        // Ajouter une carte de plus (6e carte)
        mainDuCroupier.ajouterALaMainDuCroupierCarte(carte6);

        // Vérifier que la taille de la main du croupier reste à 5
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
        // Rediriger la sortie standard pour capturer l'affichage
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Créer une main du croupier avec deux cartes
        MainDuCroupier mainDuCroupier = new MainDuCroupier(null);
        Card carte1 = new Card(CardRank.AS, CardColor.PIQUE);
        Card carte2 = new Card(CardRank.ROI, CardColor.COEUR);
        mainDuCroupier.ajouterALaMainDuCroupierCarte(carte1);
        mainDuCroupier.ajouterALaMainDuCroupierCarte(carte2);

        // Appeler la méthode afficherMain
        mainDuCroupier.afficherMain();

        // Remettre la sortie standard à sa valeur normale
        System.setOut(System.out);

        // Récupérer la sortie capturée
        String consoleOutput = outputStream.toString().trim();

        // Vérifier si la méthode toString() des cartes a été appelée et si la sortie est correcte
        assert consoleOutput.contains("AS de P");
        assert consoleOutput.contains("ROI de C");
    }
}
