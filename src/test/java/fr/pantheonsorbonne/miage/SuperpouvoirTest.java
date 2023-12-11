package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.classes.Joueur.Joueur;
import fr.pantheonsorbonne.miage.game.classes.Superpouvoir.*;
import fr.pantheonsorbonne.miage.game.classes.Table.Deck;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SuperpouvoirTest {
    private static class TestSuperpouvoir extends Superpouvoir {

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

        joueur.setPileDeJetons(15);

        boolean result = superpouvoir.peutSuperpouvoir(joueur);

        assertTrue(result);
    }

    @Test
    public void testEnleverSuperpouvoir() {
        TestSuperpouvoir superpouvoir = new TestSuperpouvoir();

        int initialUtilisations = superpouvoir.getNbUtilisations();

        superpouvoir.enleverSuperpouvoir();

        assertEquals(initialUtilisations + 1, superpouvoir.getNbUtilisations());
    }

    @Test
    public void testSansPouvoir() {
        Joueur joueur = new Joueur("Bob");
        TestSuperpouvoir superpouvoir = new TestSuperpouvoir();

        superpouvoir.redonneDroit();

        boolean result = superpouvoir.sansPouvoir(joueur);

        assertFalse(result);
    }

    @Test
    public void testVerificationWithEnoughJetons() {
        Joueur joueur = new Joueur("Charlie");
        TestSuperpouvoir superpouvoir = new TestSuperpouvoir();

        joueur.setPileDeJetons(12);

        superpouvoir.verification(joueur, 10);

        assertTrue(superpouvoir.sansPouvoir(joueur));
        assertEquals(2, joueur.getPileDeJetons());
    }

}
