package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.classes.Cartes.*;
import fr.pantheonsorbonne.miage.game.classes.Joueur.MainDuJoueur;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class MainDuJoueurTest {

    @Test
    void testGetCardNames() {
        List<Card> cartes = new ArrayList<>();
        cartes.add(new Card(CardRank.AS, CardColor.PIQUE));
        cartes.add(new Card(CardRank.ROI, CardColor.COEUR));
        cartes.add(new Card(CardRank.DAME, CardColor.CARREAU));

        MainDuJoueur mainDuJoueur = new MainDuJoueur(cartes);

        List<String> expectedCardNames = List.of("AS de PIQUE", "ROI de COEUR", "DAME de CARREAU");
        assertEquals(expectedCardNames, mainDuJoueur.getCardNames());
    }

    @Test
    void testGetMainDuJoueur() {
        List<Card> cartes = new ArrayList<>();
        cartes.add(new Card(CardRank.DEUX, CardColor.TREFLE));
        cartes.add(new Card(CardRank.SEPT, CardColor.CARREAU));

        MainDuJoueur mainDuJoueur = new MainDuJoueur(cartes);

        assertEquals(cartes, mainDuJoueur.getMainDuJoueur());
    }

    @Test
    void testSupprimerCarte() {
        List<Card> cartes = new ArrayList<>();
        cartes.add(new Card(CardRank.AS, CardColor.PIQUE));
        cartes.add(new Card(CardRank.ROI, CardColor.COEUR));

        MainDuJoueur mainDuJoueur = new MainDuJoueur(cartes);
        int tailleInitiale = mainDuJoueur.getMainDuJoueur().size();
        mainDuJoueur.supprimerCarte();
        assertEquals(tailleInitiale - 1, mainDuJoueur.getMainDuJoueur().size());
    }

    @Test
    void testTirerCarte() {
        List<Card> cartes = new ArrayList<>();
        MainDuJoueur mainDuJoueur = new MainDuJoueur(cartes);
        Card carte1 = new Card(CardRank.AS, CardColor.PIQUE);

        mainDuJoueur.tirerCarte(carte1);

        assertEquals(1, mainDuJoueur.getCardNames().size());
        assertTrue(mainDuJoueur.getCardNames().contains(carte1.toString()));

        mainDuJoueur.tirerCarte(null);

        assertEquals(1, mainDuJoueur.getCardNames().size());
    }


    @Test
    void testDevoilerCarte() {
        List<Card> cartes = new ArrayList<>();
        Card carte1 = new Card(CardRank.AS, CardColor.PIQUE);
        MainDuJoueur mainDuJoueur = new MainDuJoueur(cartes);

        mainDuJoueur.tirerCarte(carte1);

        boolean etatInitial = carte1.estVisible();
        mainDuJoueur.devoilerCarte(0);

        assertTrue(carte1.estVisible() != etatInitial);
    }
}

    

