package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.classes.Card;
import fr.pantheonsorbonne.miage.game.classes.MainDuJoueur;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class MainDuJoueurTest {

    @Test
    void testGetCardNames() {
        List<Card> cartes = new ArrayList<>();
        cartes.add(new Card(Card.cardRank.AS, Card.cardColor.PIQUE));
        cartes.add(new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        cartes.add(new Card(Card.cardRank.DAME, Card.cardColor.CARREAU));

        MainDuJoueur mainDuJoueur = new MainDuJoueur(cartes);

        List<String> expectedCardNames = List.of("AS de PIQUE", "ROI de COEUR", "DAME de CARREAU");
        assertEquals(expectedCardNames, mainDuJoueur.getCardNames());
    }

    @Test
    void testGetMainDuJoueur() {
        List<Card> cartes = new ArrayList<>();
        cartes.add(new Card(Card.cardRank.DEUX, Card.cardColor.TREFLE));
        cartes.add(new Card(Card.cardRank.SEPT, Card.cardColor.CARREAU));

        MainDuJoueur mainDuJoueur = new MainDuJoueur(cartes);

        assertEquals(cartes, mainDuJoueur.getMainDuJoueur());
    }

    @Test
    void testEmptyMainDuJoueur() {
        MainDuJoueur mainDuJoueur = new MainDuJoueur(new ArrayList<>());

        assertTrue(mainDuJoueur.getCardNames().isEmpty());

        assertTrue(mainDuJoueur.getMainDuJoueur().isEmpty());
    }

    @Test
    void testTirerCarte() {
        List<Card> cartes = new ArrayList<>();
        MainDuJoueur mainDuJoueur = new MainDuJoueur(cartes);
        Card carte1 = new Card(Card.cardRank.AS, Card.cardColor.PIQUE);

        // Tirer une carte
        mainDuJoueur.tirerCarte(carte1);

        // Vérifier que la carte est ajoutée correctement
        assertEquals(1, mainDuJoueur.getCardNames().size());
        assertTrue(mainDuJoueur.getCardNames().contains(carte1.toString()));

        // Tirer une autre carte (null)
        mainDuJoueur.tirerCarte(null);

        // Vérifier que la taille de la liste ne change pas
        assertEquals(1, mainDuJoueur.getCardNames().size());
    }

    @Test
    void testDevoilerCarte() {
        List<Card> cartes = new ArrayList<>();
        Card carte1 = new Card(Card.cardRank.AS, Card.cardColor.PIQUE);
        MainDuJoueur mainDuJoueur = new MainDuJoueur(cartes);

        // Tirer une carte
        mainDuJoueur.tirerCarte(carte1);

        // Devoiler la carte à l'index 0
        mainDuJoueur.devoilerCarte(0);

        // Vérifier que la méthode montre() est appelée sur la carte
        // Vous devrez peut-être utiliser des mocks pour vérifier cela

        // Devoiler une carte à un index invalide
        mainDuJoueur.devoilerCarte(1);

        // Vérifier que rien ne se passe (pas d'erreur)
    }
}
