package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.classes.Joueur.Joueur;
import fr.pantheonsorbonne.miage.game.classes.Joueur.MainDuJoueur;
import fr.pantheonsorbonne.miage.game.classes.Table.Card;
import fr.pantheonsorbonne.miage.game.classes.Table.Deck;
import fr.pantheonsorbonne.miage.game.classes.Table.MainDuCroupier;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class JoueurTest {

    @Test
    void testgetNom() {
        Joueur joueur = new Joueur("aymeric");
        assertEquals("aymeric", joueur.getNom());
    }

    @Test
    void testGetNom() {
        Joueur joueur = new Joueur("romain", 20);
        assertEquals("romain", joueur.getNom());
    }

    @Test
    void testGetPileDeJetons() {
        Joueur joueur = new Joueur("aymeric", 150);
        assertEquals(150, joueur.getPileDeJetons());
    }

    @Test
    void testSetPileDeJetons() {
        Joueur joueur = new Joueur("romain");
        joueur.setPileDeJetons(200);
        assertEquals(200, joueur.getPileDeJetons());
    }

    @Test
    void testSetMise() {
        Joueur joueur = new Joueur("David");
        joueur.setMise(50);
        assertEquals(50, joueur.getMise());
    }

    @Test
    void testGetCombinaison() {
        Joueur joueur = new Joueur("Eve");
        assertNull(joueur.getCombinaison());
    }

    @Test
    void testSetMainDuJoueur() {
        Joueur joueur = new Joueur("Frank");
        List<Card> main = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        MainDuJoueur mainDuJoueur = new MainDuJoueur(main);
        joueur.setMainDuJoueur(mainDuJoueur);
        assertEquals(mainDuJoueur, joueur.getMainDuJoueur());
    }

    @Test
    void testMiser() {
        Joueur joueur = new Joueur("Test", 100);

        int jetonsRestants = joueur.miser(50);
        assertEquals(50, joueur.getMise());
        assertEquals(50, jetonsRestants);

        jetonsRestants = joueur.miser(60);
        assertEquals(100, joueur.getMise());
        assertTrue(joueur.isTapis());
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
    void testAfficherMain() {
        // Créer un joueur avec une main spécifique
        Joueur joueur = new Joueur("aymeric", 100);
        joueur.setMainDuJoueur(new MainDuJoueur(Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.ROI, Card.cardColor.COEUR))));

        // Capturer la sortie standard dans un ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Appeler la méthode afficherMain()
        joueur.afficherMain();

        // Récupérer le texte imprimé
        String[] outputLines = outputStream.toString().trim().split("\\r?\\n"); // Diviser en lignes

        // Réinitialiser la sortie standard
        System.setOut(System.out);

        // Vérifier que les lignes imprimées correspondent aux attentes
        assertAll(
                () -> assertEquals("aymeric a la main suivante :", outputLines[0]),
                () -> assertEquals("AS de PIQUE", outputLines[1]),
                () -> assertEquals("ROI de COEUR", outputLines[2]));
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
    void testProbabiliteDeGagnerDifferentesConditions1() {
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
    void testFaireChoixDifferentesConditions() {
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
    void testProbabiliteDeGagnerAvecDifferentesConditions2() {
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
        joueur.miser(-20);
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
        joueur.miser(0);
        assertEquals(0, joueur.getMise());
    }

    @Test
    void testRendreCarteVisibleAvecMainVide() {
        Joueur joueur = new Joueur("Bob", 100);

        // Utiliser un ByteArrayOutputStream pour capturer la sortie standard
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        joueur.rendreCarteVisible();

        // Récupérer la sortie standard capturée
        String output = outputStream.toString().trim();

        // Rétablir la sortie standard originale
        System.setOut(System.out);

        assertEquals("", output);
    }

    @Test
    void testRendreCarteVisibleAvecMainNonVide() {
        Joueur joueur = new Joueur("Alice", 100);
        joueur.setMainDuJoueur(new MainDuJoueur(Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.ROI, Card.cardColor.COEUR))));

        // Utiliser un ByteArrayOutputStream pour capturer la sortie standard
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        joueur.rendreCarteVisible();

        System.setOut(System.out);

        assertTrue(true);
    }

    @Test
    void testSetMain() {
        Joueur joueur = new Joueur("John", 100);
        List<Card> cartes = Arrays.asList(
                new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
                new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(cartes);

        joueur.setMain(main);

        assertEquals(main, joueur.getMainDuJoueur());
    }

    @Test
    void testIsTapis() {
        Joueur joueur = new Joueur("Alice", 200);
        assertFalse(joueur.isTapis());

        joueur.miser(200);

        assertTrue(joueur.isTapis());
    }

    @Test
    void testIsTapisSommeNulle() {
        Joueur joueur = new Joueur("Alice", 200);
        assertFalse(joueur.isTapis());

        joueur.miser(0);

        assertFalse(joueur.isTapis());
    }

    @Test
    void testIsTapisSommeNégative() {
        Joueur joueur = new Joueur("Alice", 200);
        assertFalse(joueur.isTapis());

        joueur.miser(-300);

        assertFalse(joueur.isTapis());
    }

    @Test
    void testAGagné() {
        Joueur joueur = new Joueur("Charlie", 400);
        int montantGain = 100;

        joueur.aGagné(montantGain);

        assertEquals(400 + montantGain, joueur.getPileDeJetons());
    }

    @Test
    void testAPerdu() {
        Joueur joueur = new Joueur("David", 500);
        int montantPerte = 200;

        joueur.aPerdu(montantPerte);

        assertEquals(500 - montantPerte, joueur.getPileDeJetons());
    }

    @Test
    void testReinitialiserMain() {
        Joueur joueur = new Joueur("Frank", 700);
        List<Card> cartes = Arrays.asList(
                new Card(Card.cardRank.ROI, Card.cardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(cartes);
        joueur.setMain(main);
        assertFalse(joueur.getMainDuJoueur().getMainDuJoueur().isEmpty());

        joueur.enleverCarte();

        assertTrue(joueur.getMainDuJoueur().getMainDuJoueur().isEmpty());
    }

    @Test
    public void testFaireChoixSuperPouvoir() {
        Joueur joueurAvecSuperPouvoir = new Joueur("Testeur1", 400);

        Joueur joueurSansSuperPouvoir = new Joueur("Testeur2", 300);

        assertEquals(4, joueurAvecSuperPouvoir.faireChoixSuperPouvoir());

        assertEquals(5, joueurSansSuperPouvoir.faireChoixSuperPouvoir());
    }

}
