package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.classes.Joueur.Joueur;
import fr.pantheonsorbonne.miage.game.classes.Joueur.MainDuJoueur;
import fr.pantheonsorbonne.miage.game.classes.Superpouvoir.GestionSuperpouvoir;
import fr.pantheonsorbonne.miage.game.classes.Table.Card;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GestionSuperpouvoirTest {

    /* @Test
    public void testTirerCarteVisible() {
        Joueur joueur = new Joueur("aymeric", 100);
        MainDuJoueur mainJoueur = new MainDuJoueur(Arrays.asList(
                new Card(Card.cardRank.DEUX, Card.cardColor.PIQUE),
                new Card(Card.cardRank.TROIS, Card.cardColor.CARREAU)
        ));
        joueur.setMainDuJoueur(mainJoueur);

        GestionSuperpouvoir gestionSuperpouvoir = new GestionSuperpouvoir();
        gestionSuperpouvoir.tirerCarteVisible(joueur, null); 

        // Assertions
        assertEquals(3, joueur.getMainDuJoueur().getMainDuJoueur().size());
    }

    @Test
    public void testTirerCarteInvisible() {
        Joueur joueur = new Joueur("aymeric", 100);
        MainDuJoueur mainJoueur = new MainDuJoueur(Arrays.asList(
                new Card(Card.cardRank.DEUX, Card.cardColor.PIQUE),
                new Card(Card.cardRank.TROIS, Card.cardColor.CARREAU)
        ));
        joueur.setMainDuJoueur(mainJoueur);

        GestionSuperpouvoir gestionSuperpouvoir = new GestionSuperpouvoir();
        gestionSuperpouvoir.tirerCarteInvisible(joueur, null); 

        // Assertions
        assertEquals(3, joueur.getMainDuJoueur().getMainDuJoueur().size());
    }  */

    @Test
    public void testDevoilerCarte() {
        GestionSuperpouvoir gestionSuperpouvoir = new GestionSuperpouvoir();
        Joueur joueur = new Joueur("aymeric", 100);
        Joueur ennemi = new Joueur("romain", 100);

        
        ennemi.setMainDuJoueur(new MainDuJoueur(Arrays.asList(
            new Card(Card.cardRank.QUATRE, Card.cardColor.PIQUE),
            new Card(Card.cardRank.CINQ, Card.cardColor.CARREAU)
        )));

        
        gestionSuperpouvoir.devoilerCarte(joueur, ennemi);

        assertEquals(2, ennemi.getMainDuJoueur().getMainDuJoueur().size());
        assertEquals(50, joueur.getPileDeJetons());
}


    @Test
    public void testEnleverCarte() {
        GestionSuperpouvoir gestionSuperpouvoir = new GestionSuperpouvoir();
        Joueur joueur = new Joueur("aymeric", 100);
        Joueur ennemi = new Joueur("romain", 100);

    
        ennemi.setMainDuJoueur(new MainDuJoueur(Arrays.asList(
            new Card(Card.cardRank.QUATRE, Card.cardColor.PIQUE),
            new Card(Card.cardRank.CINQ, Card.cardColor.CARREAU)
        )));

        
        gestionSuperpouvoir.enleverCarte(joueur, ennemi);

        assertEquals(1, ennemi.getMainDuJoueur().getMainDuJoueur().size());
        assertEquals(0, joueur.getPileDeJetons());
}

}
