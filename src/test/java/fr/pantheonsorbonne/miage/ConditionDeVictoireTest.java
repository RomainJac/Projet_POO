package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.classes.Cartes.*;
import fr.pantheonsorbonne.miage.game.classes.Joueur.MainDuJoueur;
import fr.pantheonsorbonne.miage.game.classes.Logique.CombinaisonGagnante;
import fr.pantheonsorbonne.miage.game.classes.Logique.ConditionDeVictoire;
import fr.pantheonsorbonne.miage.game.classes.Table.Deck;
import fr.pantheonsorbonne.miage.game.classes.Table.MainDuCroupier;

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
            new Card(CardRank.DEUX, CardColor.PIQUE),
            new Card(CardRank.TROIS, CardColor.CARREAU)
        ));

        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DIX, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.VALET, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DAME, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.ROI, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.AS, CardColor.COEUR));

        CombinaisonGagnante combinaison = ConditionDeVictoire.trouverMeilleureCombinaison(mainCroupier, mainJoueur);
        assertEquals(new CombinaisonGagnante(CombinaisonGagnante.Victoire.QUINTE_FLUSH), combinaison);
    }

    @Test
    void testTrouverQuinte() {
        // Créez une liste de cartes pour le test
        MainDuCroupier mainCroupier = new MainDuCroupier(null);

        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DIX, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.VALET, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DAME, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.ROI, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.AS, CardColor.COEUR));

        CombinaisonGagnante quinte = ConditionDeVictoire.trouverQuinte(mainCroupier.getMainDuCroupier());
        assertNotNull(quinte);
    }

    
    @Test
    void testTrouverCombinaisonsMultiplesPaire() {
        // Créer une main avec une paire
        MainDuCroupier mainCroupier = new MainDuCroupier(new Deck());

        // Ajouter une paire à la main du croupier
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DIX, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DIX, CardColor.PIQUE));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.CINQ, CardColor.TREFLE));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.SEPT, CardColor.CARREAU));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.AS, CardColor.COEUR));

        CombinaisonGagnante combinaison = ConditionDeVictoire.trouverCombinaisonsMultiples(mainCroupier.getMainDuCroupier());
        assertEquals(new CombinaisonGagnante(CombinaisonGagnante.Victoire.PAIRE, CardRank.DIX), combinaison);
    }

    @Test
    void testTrouverCombinaisonsMultiplesBrelan() {
        // Créer une main avec un brelan
        MainDuCroupier mainCroupier = new MainDuCroupier(new Deck());

        // Ajouter un brelan à la main du croupier
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DIX, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DIX, CardColor.PIQUE));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DIX, CardColor.TREFLE));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.SEPT, CardColor.CARREAU));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.AS, CardColor.COEUR));

        CombinaisonGagnante combinaison = ConditionDeVictoire.trouverCombinaisonsMultiples(mainCroupier.getMainDuCroupier());
        assertEquals(new CombinaisonGagnante(CombinaisonGagnante.Victoire.BRELAN, CardRank.DIX), combinaison);
    }

    @Test
    void testTrouverCombinaisonsMultiplesCarre() {
        // Créer une main avec un carré
        MainDuCroupier mainCroupier = new MainDuCroupier(new Deck());

        // Ajouter un carré à la main du croupier
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DIX, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DIX, CardColor.PIQUE));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DIX, CardColor.TREFLE));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DIX, CardColor.CARREAU));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.AS, CardColor.COEUR));

        CombinaisonGagnante combinaison = ConditionDeVictoire.trouverCombinaisonsMultiples(mainCroupier.getMainDuCroupier());
        // Assurez-vous d'adapter le test en fonction des cartes ajoutées à la main du croupier
        assertEquals(new CombinaisonGagnante(CombinaisonGagnante.Victoire.CARRE, CardRank.DIX), combinaison);
    }
    
    @Test
    void testSontConsecutives() {
        // Créez une liste de cartes pour le test
        MainDuCroupier mainCroupier = new MainDuCroupier(null);

        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DEUX, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.TROIS, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.QUATRE, CardColor.TREFLE));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.CINQ, CardColor.COEUR));        
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.SIX, CardColor.COEUR));

        boolean consecutives = ConditionDeVictoire.sontConsecutives(mainCroupier.getMainDuCroupier());
        assertTrue(consecutives);
    }

    @Test
    void testTrouverCarteLaPlusHaute() {
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DIX, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.VALET, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DAME, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.ROI, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.AS, CardColor.COEUR));

        CardRank carteLaPlusHaute = ConditionDeVictoire.trouverCarteLaPlusHaute(mainCroupier.getMainDuCroupier());
        assertEquals(CardRank.AS, carteLaPlusHaute);
    }

    

    
}
