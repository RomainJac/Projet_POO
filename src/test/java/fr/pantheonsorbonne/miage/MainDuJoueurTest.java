package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.classes.Joueur.MainDuJoueur;
import fr.pantheonsorbonne.miage.game.classes.Table.Card;

import org.junit.jupiter.api.Test;
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
    void testSupprimerCarte() {
        List<Card> cartes = new ArrayList<>();
        cartes.add(new Card(Card.cardRank.AS, Card.cardColor.PIQUE));
        cartes.add(new Card(Card.cardRank.ROI, Card.cardColor.COEUR));

        MainDuJoueur mainDuJoueur = new MainDuJoueur(cartes);
        int tailleInitiale = mainDuJoueur.getMainDuJoueur().size();
        mainDuJoueur.supprimerCarte();
        assertEquals(tailleInitiale - 1, mainDuJoueur.getMainDuJoueur().size());
    }

    @Test
    void testTirerCarte() {
        List<Card> cartes = new ArrayList<>();
        MainDuJoueur mainDuJoueur = new MainDuJoueur(cartes);
        Card carte1 = new Card(Card.cardRank.AS, Card.cardColor.PIQUE);

        mainDuJoueur.tirerCarte(carte1);

        // Vérifier que la carte est ajoutée correctement
        assertEquals(1, mainDuJoueur.getCardNames().size());
        assertTrue(mainDuJoueur.getCardNames().contains(carte1.toString()));

        mainDuJoueur.tirerCarte(null);

        // Vérifier que la taille de la liste ne change pas
        assertEquals(1, mainDuJoueur.getCardNames().size());
    }


    @Test
    void testDevoilerCarte() {
        List<Card> cartes = new ArrayList<>();
        Card carte1 = new Card(Card.cardRank.AS, Card.cardColor.PIQUE);
        MainDuJoueur mainDuJoueur = new MainDuJoueur(cartes);

        mainDuJoueur.tirerCarte(carte1);

        // Enregistrez l'état initial de la visibilité de la carte
        boolean etatInitial = carte1.estVisible();
        mainDuJoueur.devoilerCarte(0);

        // Vérifier que l'état de la visibilité de la carte a changé
        assertTrue(carte1.estVisible() != etatInitial);
    }
}

    

