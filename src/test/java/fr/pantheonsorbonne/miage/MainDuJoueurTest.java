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
}
