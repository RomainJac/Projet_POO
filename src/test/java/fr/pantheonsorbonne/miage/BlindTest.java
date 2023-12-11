package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.classes.Joueur.Joueur;
import fr.pantheonsorbonne.miage.game.classes.Table.Blind;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BlindTest {

    private Blind blind;
    private Joueur joueur;

    @Test
    public void testGetValeur() {
        blind = new Blind(10);
        assertEquals(10, blind.getValeur());
    }

    @Test
    public void testGetJoueur() {
        joueur = new Joueur("Romain");
        blind = new Blind(10, joueur);
        assertEquals(joueur, blind.getJoueur());
    }

    @Test
    public void testAugmenter() {
        joueur = new Joueur("Romain");
        blind = new Blind(10, joueur);
        blind.augmenter(5);
        assertEquals(15, blind.getValeur());
    }

    @Test
    public void testSetJoueur() {
        joueur = new Joueur("Romain");
        blind = new Blind(10, joueur);
        Joueur nouveauJoueur = new Joueur("Bob");
        blind.setJoueur(nouveauJoueur);
        assertEquals(nouveauJoueur, blind.getJoueur());
    }


}
