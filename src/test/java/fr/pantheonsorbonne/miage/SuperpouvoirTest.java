package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.classes.Joueur.Joueur;
import fr.pantheonsorbonne.miage.game.classes.Superpouvoir.*;
import fr.pantheonsorbonne.miage.game.classes.Table.Deck;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SuperpouvoirTest {
    private static class TestSuperpouvoir extends Superpouvoir {

        @Override
        protected void Utiliser(Joueur joueur, Deck deck) {
        }

        @Override
        protected void Utiliser(Joueur joueur, Joueur adversaire) {
        }

        @Override
        public void tirerCarteVisible(Joueur joueur, Deck deck) {
        }

        @Override
        public void tirerCarteInvisible(Joueur joueur, Deck deck) {
        }

        @Override
        public void devoilerCarte(Joueur joueur, Joueur ennemis) {
        }

        @Override
        public void enleverCarte(Joueur joueur, Joueur ennemis) {
        }
    }    

    @Test
    public void testPeutSuperpouvoir() {
        Joueur joueur = new Joueur("Alice");
        TestSuperpouvoir superpouvoir = new TestSuperpouvoir();

        // Set up the conditions for the test
        joueur.setPileDeJetons(15);

        // Execute the method being tested
        boolean result = superpouvoir.peutSuperpouvoir(joueur);

        // Make assertions
        assertTrue(result);
    }

    // ... other tests ...

    @Test
    public void testEnleverSuperpouvoir() {
        TestSuperpouvoir superpouvoir = new TestSuperpouvoir();

        // Set up the conditions for the test
        int initialUtilisations = superpouvoir.getNbUtilisations();

        // Execute the method being tested
        superpouvoir.enleverSuperpouvoir();

        // Make assertions
        assertEquals(initialUtilisations + 1, superpouvoir.getNbUtilisations());
    }

    @Test
    public void testSansPouvoir() {
        Joueur joueur = new Joueur("Bob");
        TestSuperpouvoir superpouvoir = new TestSuperpouvoir();

        // Set up the conditions for the test
        superpouvoir.redonneDroit();

        // Execute the method being tested
        boolean result = superpouvoir.sansPouvoir(joueur);

        // Make assertions
        assertFalse(result);
    }


    @Test
    public void testVerificationWithEnoughJetons() {
        Joueur joueur = new Joueur("Charlie");
        TestSuperpouvoir superpouvoir = new TestSuperpouvoir();

        // Set up the conditions for the test
        joueur.setPileDeJetons(12);

        // Execute the method being tested
        superpouvoir.verification(joueur, 10);

        // Make assertions
        assertTrue(superpouvoir.sansPouvoir(joueur));
        assertEquals(2, joueur.getPileDeJetons());
    }

    
}


