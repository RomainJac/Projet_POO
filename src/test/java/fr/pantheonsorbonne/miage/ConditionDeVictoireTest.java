package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.classes.Card;
import fr.pantheonsorbonne.miage.game.classes.CombinaisonGagnante;
import fr.pantheonsorbonne.miage.game.classes.ConditionDeVictoire;
import fr.pantheonsorbonne.miage.game.classes.Deck;
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

        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DIX, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.VALET, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DAME, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.AS, Card.cardColor.COEUR));

        CombinaisonGagnante combinaison = ConditionDeVictoire.trouverMeilleureCombinaison(mainCroupier, mainJoueur);
        assertEquals(new CombinaisonGagnante(CombinaisonGagnante.Victoire.QUINTE_FLUSH), combinaison);
    }

    @Test
    void testTrouverQuinte() {
        // Créez une liste de cartes pour le test
        MainDuCroupier mainCroupier = new MainDuCroupier(null);

        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DIX, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.VALET, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DAME, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.AS, Card.cardColor.COEUR));

        CombinaisonGagnante quinte = ConditionDeVictoire.trouverQuinte(mainCroupier.getMainDuCroupier());
        assertNotNull(quinte);
    }

    
    @Test
    void testTrouverCombinaisonsMultiplesPaire() {
        // Créer une main avec une paire
        MainDuCroupier mainCroupier = new MainDuCroupier(new Deck());

        // Ajouter une paire à la main du croupier
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DIX, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DIX, Card.cardColor.PIQUE));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.CINQ, Card.cardColor.TREFLE));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.SEPT, Card.cardColor.CARREAU));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.AS, Card.cardColor.COEUR));

        CombinaisonGagnante combinaison = ConditionDeVictoire.trouverCombinaisonsMultiples(mainCroupier.getMainDuCroupier());
        assertEquals(new CombinaisonGagnante(CombinaisonGagnante.Victoire.PAIRE, Card.cardRank.DIX), combinaison);
    }

    @Test
    void testTrouverCombinaisonsMultiplesBrelan() {
        // Créer une main avec un brelan
        MainDuCroupier mainCroupier = new MainDuCroupier(new Deck());

        // Ajouter un brelan à la main du croupier
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DIX, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DIX, Card.cardColor.PIQUE));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DIX, Card.cardColor.TREFLE));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.SEPT, Card.cardColor.CARREAU));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.AS, Card.cardColor.COEUR));

        CombinaisonGagnante combinaison = ConditionDeVictoire.trouverCombinaisonsMultiples(mainCroupier.getMainDuCroupier());
        assertEquals(new CombinaisonGagnante(CombinaisonGagnante.Victoire.BRELAN, Card.cardRank.DIX), combinaison);
    }

    @Test
    void testTrouverCombinaisonsMultiplesCarre() {
        // Créer une main avec un carré
        MainDuCroupier mainCroupier = new MainDuCroupier(new Deck());

        // Ajouter un carré à la main du croupier
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DIX, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DIX, Card.cardColor.PIQUE));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DIX, Card.cardColor.TREFLE));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DIX, Card.cardColor.CARREAU));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.AS, Card.cardColor.COEUR));

        CombinaisonGagnante combinaison = ConditionDeVictoire.trouverCombinaisonsMultiples(mainCroupier.getMainDuCroupier());
        // Assurez-vous d'adapter le test en fonction des cartes ajoutées à la main du croupier
        assertEquals(new CombinaisonGagnante(CombinaisonGagnante.Victoire.CARRE, Card.cardRank.DIX), combinaison);
    }
    
    @Test
    void testSontConsecutives() {
        // Créez une liste de cartes pour le test
        MainDuCroupier mainCroupier = new MainDuCroupier(null);

        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DEUX, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.TROIS, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.QUATRE, Card.cardColor.TREFLE));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.CINQ, Card.cardColor.COEUR));        
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.SIX, Card.cardColor.COEUR));

        boolean consecutives = ConditionDeVictoire.sontConsecutives(mainCroupier.getMainDuCroupier());
        assertTrue(consecutives);
    }

    @Test
    void testTrouverCarteLaPlusHaute() {
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DIX, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.VALET, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DAME, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.AS, Card.cardColor.COEUR));

        Card.cardRank carteLaPlusHaute = ConditionDeVictoire.trouverCarteLaPlusHaute(mainCroupier.getMainDuCroupier());
        assertEquals(Card.cardRank.AS, carteLaPlusHaute);
    }

    

    
}
