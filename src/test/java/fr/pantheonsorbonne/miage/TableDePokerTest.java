package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;
import fr.pantheonsorbonne.miage.game.classes.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TableDePokerTest {

    @Test
    void testChangementBlinds() {
        List<Joueur> joueurs = new ArrayList<>();
        joueurs.add(new Joueur("Joueur1", 100));
        joueurs.add(new Joueur("Joueur2", 100));
        TableDePoker table = new TableDePoker(joueurs);
        table.initialiserBlinds();

        Joueur grosseBlindActuelle = table.grosseBlind.getJoueur();
        Joueur petiteBlindActuelle = table.petiteBlind.getJoueur();
        Joueur dealerBlindActuel = table.dealerBlind.getJoueur();

        table.changerBlinds();

        assertNotEquals(grosseBlindActuelle, table.grosseBlind.getJoueur());
        assertNotEquals(petiteBlindActuelle, table.petiteBlind.getJoueur());
        assertNotEquals(dealerBlindActuel, table.dealerBlind.getJoueur());
    }

    @Test
    void testAugmenterBlinds() {
        List<Joueur> joueurs = new ArrayList<>();
        joueurs.add(new Joueur("Joueur1", 100));
        joueurs.add(new Joueur("Joueur2", 100));
        TableDePoker table = new TableDePoker(joueurs);
        table.initialiserBlinds();

        table.augmenterBlinds();

        assertEquals(4, table.grosseBlind.getValeur());
        assertEquals(2, table.petiteBlind.getValeur()); 
    }


}
