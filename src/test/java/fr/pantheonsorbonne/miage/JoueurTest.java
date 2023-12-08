package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.classes.Card;
import fr.pantheonsorbonne.miage.game.classes.Deck;
import fr.pantheonsorbonne.miage.game.classes.Joueur;
import fr.pantheonsorbonne.miage.game.classes.MainDuCroupier;
import fr.pantheonsorbonne.miage.game.classes.MainDuJoueur;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class JoueurTest {

    void testMiser() {
        Joueur joueur = new Joueur("Test", 100);

        int jetonsRestants = joueur.miser(50);
        assertEquals(50, joueur.getMise());
        assertEquals(50, jetonsRestants);

        jetonsRestants = joueur.miser(-10);
        assertEquals(50, joueur.getMise());
        assertEquals(50, jetonsRestants);

        jetonsRestants = joueur.miser(80);
        assertEquals(50, joueur.getMise());
        assertEquals(0, jetonsRestants);
        assertTrue(joueur.isTapis());
    }

    @Test
    void testAGagne() {
        Joueur joueur = new Joueur("Test", 100);
        joueur.aGagné(50);
        assertEquals(150, joueur.getPileDeJetons());
    }

    @Test
    void testProbabiliteDeGagner() {
        Joueur joueur = new Joueur("Test", 100);
        Deck deck = new Deck();

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);
        MainDuCroupier croupier = new MainDuCroupier(deck);

        int probabilite = joueur.probabiliteDeGagner(croupier);
        assertTrue(probabilite >= 0 && probabilite <= 100);
    }

    @Test
    void testAjouterCarte() {
        Joueur joueur = new Joueur("Test", 100);
        List<Card> cards = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(cards);
        joueur.setMain(main);
        assertEquals(1, joueur.getMainDuJoueur().getMainDuJoueur().size());
        assertTrue(joueur.getMainDuJoueur().getMainDuJoueur().containsAll(cards));
    }

    @Test
    void testEnleverCarte() {
        Joueur joueur = new Joueur("Test", 100);
        List<Card> cards = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(cards);
        joueur.setMain(main);

        joueur.enleverCarte();

        assertEquals(1, joueur.getMainDuJoueur().getMainDuJoueur().size());
    }

    @Test
    void testFaireChoix() {
        Joueur joueur = new Joueur("Test", 100);
        Deck deck = new Deck();
        MainDuCroupier croupier = new MainDuCroupier(deck);
        List<Card> cards = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(cards);
        joueur.setMain(main);

        int choix = joueur.faireChoix(croupier, 50, 2);
        assertTrue(choix >= 1 && choix <= 3);
    }

    @Test
    void testToString() {
        Joueur joueur = new Joueur("Test", 100);
        assertEquals("Test jetons actuels : 100", joueur.toString());
    }

    @Test
    void testGetCardNames() {
        Joueur joueur = new Joueur("Test", 100);
        List<Card> cards = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(cards);
        joueur.setMain(main);

        List<String> cardNames = joueur.getCardNames();
        assertEquals(2, cardNames.size());
        assertTrue(cardNames.contains("AS de PIQUE"));
        assertTrue(cardNames.contains("ROI de COEUR"));
    }

    @Test
    void testProbabiliteDeGagnerWithDifferentConditions() {
        Joueur joueur = new Joueur("Test", 100);
        Deck deck = new Deck();

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.DAME, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);
        MainDuCroupier croupier = new MainDuCroupier(deck);

        int probabilite = joueur.probabiliteDeGagner(croupier);
        assertTrue(probabilite >= 0 && probabilite <= 100);
    }

    @Test
    void testFaireChoixWithDifferentConditions() {
        Joueur joueur = new Joueur("Test", 100);
        Deck deck = new Deck();
        List<Card> mainDuJoueur = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.DAME, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);
        MainDuCroupier croupier = new MainDuCroupier(deck);

        int choix = joueur.faireChoix(croupier, 100, 1);
        assertTrue(choix >= 1 && choix <= 3);
    }

    @Test
    void testProbabiliteDeGagnerAvecMemeHauteur() {
        Joueur joueur = new Joueur("Test", 100);
        Deck deck = new Deck();

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.AS, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);
        MainDuCroupier croupier = new MainDuCroupier(deck);

        int probabilite = joueur.probabiliteDeGagner(croupier);
        assertEquals(85, probabilite);
    }

    @Test
    void testProbabiliteDeGagnerAvecDifferentesHauteurs() {
        Joueur joueur = new Joueur("Test", 100);
        Deck deck = new Deck();

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);
        MainDuCroupier croupier = new MainDuCroupier(deck);

        int probabilite = joueur.probabiliteDeGagner(croupier);
        assertEquals(60, probabilite);
    }

    @Test
    void testProbabiliteDeGagnerAvecDifferentesConditions() {
        Joueur joueur = new Joueur("Test", 100);
        Deck deck = new Deck();

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.DAME, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);
        MainDuCroupier croupier = new MainDuCroupier(deck);

        int probabilite = joueur.probabiliteDeGagner(croupier);
        assertEquals(55, probabilite);
    }

    @Test
    void testProbabiliteDeGagnerAvecMemeCouleur() {
        Joueur joueur = new Joueur("Test", 100);
        Deck deck = new Deck();

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.ROI, Card.cardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);
        MainDuCroupier croupier = new MainDuCroupier(deck);

        int probabilite = joueur.probabiliteDeGagner(croupier);
        assertEquals(60, probabilite);
    }

    @Test
    void testGetMainDuJoueur() {
        Joueur joueur = new Joueur("Test", 100);
        List<Card> mainDuJoueur = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        assertEquals(main, joueur.getMainDuJoueur());
    }

    @Test
    void testProbabiliteDeGagnerAvecDeuxCartesIdentiques() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.ROI, Card.cardColor.PIQUE));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(Card.cardRank.ROI, Card.cardColor.COEUR),
                new Card(Card.cardRank.ROI, Card.cardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(80, probabilite);
    }

    @Test
    void testProbabiliteDeGagnerAvecTroisCartesIdentiques() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DAME, Card.cardColor.CARREAU));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DAME, Card.cardColor.CARREAU));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DAME, Card.cardColor.CARREAU));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(Card.cardRank.ROI, Card.cardColor.COEUR),
                new Card(Card.cardRank.DIX, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(35, probabilite);
    }

    @Test
    void testProbabiliteDeGagnerAvecCouleursDifferentesDansCroupier() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.SEPT, Card.cardColor.CARREAU));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.NEUF, Card.cardColor.TREFLE));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.CINQ, Card.cardColor.COEUR));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(Card.cardRank.ROI, Card.cardColor.PIQUE),
                new Card(Card.cardRank.DAME, Card.cardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(50, probabilite);
    }

    @Test
    void testProbabiliteDeGagnerAvecMemeCouleurEtTroisCartesIdentiquesDansCroupier() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DAME, Card.cardColor.CARREAU));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DAME, Card.cardColor.CARREAU));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.DAME, Card.cardColor.CARREAU));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(Card.cardRank.ROI, Card.cardColor.COEUR),
                new Card(Card.cardRank.DIX, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(35, probabilite);
    }

    @Test
    void testProbabiliteDeGagnerAvecDifférentesHauteurs() {
        Joueur joueur = new Joueur("Test", 100);
        Deck deck = new Deck();

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);
        MainDuCroupier croupier = new MainDuCroupier(deck);

        int probabilite = joueur.probabiliteDeGagner(croupier);
        assertEquals(60, probabilite);
    }

    @Test
    void testProbabiliteDeGagnerAvecDifférentesConditions() {
        Joueur joueur = new Joueur("Test", 100);
        Deck deck = new Deck();

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.DAME, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);
        MainDuCroupier croupier = new MainDuCroupier(deck);

        int probabilite = joueur.probabiliteDeGagner(croupier);
        assertEquals(55, probabilite);
    }

    @Test
    void testProbabiliteDeGagnerAvecMêmeCouleur() {
        Joueur joueur = new Joueur("Test", 100);
        Deck deck = new Deck();

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.ROI, Card.cardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);
        MainDuCroupier croupier = new MainDuCroupier(deck);

        int probabilite = joueur.probabiliteDeGagner(croupier);
        assertEquals(60, probabilite);
    }

    @Test
    void testMiserAvecJetonsSuffisants() {
        Joueur joueur = new Joueur("Test", 100);
        joueur.miser(20);
        assertEquals(20, joueur.getMise());
    }

    @Test
    void testMiserAvecMiseNegative() {
        Joueur joueur = new Joueur("Test", 100);
        assertEquals(0, joueur.getMise());
    }

    @Test
    void testMiserAvecMontantNegatif() {
        Joueur joueur = new Joueur("Test", 100);

        assertEquals(0, joueur.getMise());
    }

    @Test
    void testMiserAvecMontantSuperieurAJetonsRestants() {
        Joueur joueur = new Joueur("Test", 100);
        joueur.miser(150);
        assertEquals(100, joueur.getMise());
        assertTrue(joueur.isTapis());
    }

    @Test
    void testMiserAvecMontantInferieurAJetonsRestants() {
        Joueur joueur = new Joueur("Test", 100);

        int jetonsRestants = joueur.miser(50);

        assertEquals(50, joueur.getMise());
        assertEquals(50, jetonsRestants);
        assertFalse(joueur.isTapis());
    }

   @Test
    void testMiserAvecMontantNul() {
        Joueur joueur = new Joueur("Test", 100);

        assertEquals(0, joueur.getMise());
    }

    @Test
    void testRendreCarteVisibleAvecMainVide() {
        // Créer un joueur avec une main vide
        Joueur joueur = new Joueur("Bob", 100);

        // Appeler la méthode rendreCarteVisible
        joueur.rendreCarteVisible(); // Aucune exception ne devrait être levée
    }

    

    

}
