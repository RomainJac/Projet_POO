package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.classes.Card;
import fr.pantheonsorbonne.miage.game.classes.Deck;
import fr.pantheonsorbonne.miage.game.classes.Joueur;
import fr.pantheonsorbonne.miage.game.classes.MainDuCroupier;
import fr.pantheonsorbonne.miage.game.classes.MainDuJoueur;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class JoueurTest {

    @Test
    void testProbabiliteDeGagner() {
        Joueur joueur = new Joueur("Joueur");

        List<Card> cartesJoueur = new ArrayList<>();
        cartesJoueur.add(new Card(Card.cardRank.AS, Card.cardColor.COEUR));
        cartesJoueur.add(new Card(Card.cardRank.ROI, Card.cardColor.PIQUE));
        joueur.setMain(new MainDuJoueur(cartesJoueur));

        MainDuCroupier mainCroupier = new MainDuCroupier(new Deck());
        mainCroupier.tirerCarte(3);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertTrue(probabilite >= 0 && probabilite <= 100);
    }
}
