package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.classes.Joueur.Joueur;
import fr.pantheonsorbonne.miage.game.classes.Joueur.MainDuJoueur;
import fr.pantheonsorbonne.miage.game.classes.Superpouvoir.GestionSuperpouvoir;
import fr.pantheonsorbonne.miage.game.classes.Table.Card;
import fr.pantheonsorbonne.miage.game.classes.Table.Deck;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GestionSuperpouvoirTest {

    @Test
    public void testTirerCarteVisible() {
        Joueur joueur = new Joueur("aymeric", 100);
        MainDuJoueur mainJoueur = new MainDuJoueur(Arrays.asList(
                new Card(Card.cardRank.DEUX, Card.cardColor.PIQUE),
                new Card(Card.cardRank.TROIS, Card.cardColor.CARREAU)
        ));
        joueur.setMainDuJoueur(mainJoueur);
        Deck deck = new Deck();
        deck.initialiserDeck();                

        GestionSuperpouvoir gestionSuperpouvoir = new GestionSuperpouvoir();
        gestionSuperpouvoir.tirerCarteVisible(joueur, deck); 

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
        Deck deck = new Deck();
        deck.initialiserDeck();
                

        GestionSuperpouvoir gestionSuperpouvoir = new GestionSuperpouvoir();
        gestionSuperpouvoir.tirerCarteInvisible(joueur, deck); 

        assertEquals(3, joueur.getMainDuJoueur().getMainDuJoueur().size());
    } 

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


    

}
