package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.classes.Card;
import fr.pantheonsorbonne.miage.game.classes.CombinaisonGagnante;
import fr.pantheonsorbonne.miage.game.classes.ConditionDeVictoire;
import fr.pantheonsorbonne.miage.game.classes.MainDuCroupier;
import fr.pantheonsorbonne.miage.game.classes.MainDuJoueur;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConditionDeVictoireTest {

    @Test
    void testTrouverMeilleureCombinaisonQuinteFlush() {
        // Créer une main avec une quinte flush
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        MainDuJoueur mainJoueur = new MainDuJoueur(Arrays.asList(
            new Card(Card.cardRank.DEUX, Card.cardColor.PIQUE),
            new Card(Card.cardRank.TROIS, Card.cardColor.CARREAU)
        ));

        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.DIX, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.VALET, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.DAME, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.AS, Card.cardColor.COEUR));

        CombinaisonGagnante combinaison = ConditionDeVictoire.trouverMeilleureCombinaison(mainCroupier, mainJoueur);
        assertEquals(new CombinaisonGagnante(CombinaisonGagnante.Victoire.QUINTE_FLUSH, Card.cardRank.AS), combinaison);
    }

    @Test
    void testTrouverQuinte() {
        // Créez une liste de cartes pour le test
        MainDuCroupier mainCroupier = new MainDuCroupier(null);

        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.DIX, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.VALET, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.DAME, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.AS, Card.cardColor.COEUR));

        CombinaisonGagnante quinte = ConditionDeVictoire.trouverQuinte(mainCroupier.getMainDuCroupier());
        assertNotNull(quinte);
        assertEquals(new CombinaisonGagnante(CombinaisonGagnante.Victoire.QUINTE_FLUSH, Card.cardRank.AS), quinte);
    }
    
    @Test
    void testSontConsecutives() {
        // Créez une liste de cartes pour le test
        MainDuCroupier mainCroupier = new MainDuCroupier(null);

        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.DEUX, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.TROIS, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.QUATRE, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.CINQ, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.SIX, Card.cardColor.COEUR));

        boolean consecutives = ConditionDeVictoire.sontConsecutives(mainCroupier.getMainDuCroupier());
        assertTrue(consecutives);
    }

    @Test
    void testTrouverCarteLaPlusHaute() {
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        
        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.DIX, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.VALET, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.DAME, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupier(new Card(Card.cardRank.AS, Card.cardColor.COEUR));

        Card.cardRank carteLaPlusHaute = ConditionDeVictoire.trouverCarteLaPlusHaute(mainCroupier.getMainDuCroupier());
        assertEquals(Card.cardRank.AS, carteLaPlusHaute);
    }

    

    // Ajoutez d'autres tests pour les autres méthodes de la classe ConditionDeVictoire
}
