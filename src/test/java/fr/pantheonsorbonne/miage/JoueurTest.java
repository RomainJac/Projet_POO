package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.classes.Cartes.*;
import fr.pantheonsorbonne.miage.game.classes.Joueur.Joueur;
import fr.pantheonsorbonne.miage.game.classes.Joueur.MainDuJoueur;
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
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.ROI, CardColor.COEUR));
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
        assertFalse(joueur.isTapis());

        jetonsRestants = joueur.miser(60);
        assertEquals(100, joueur.getMise());
        assertTrue(joueur.isTapis());
    }

    @Test
    void testProbabiliteDeGagner() {
        Joueur joueur = new Joueur("Test", 100);
        Deck deck = new Deck();

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.ROI, CardColor.COEUR));
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
                new Card(CardRank.AS, CardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(cards);
        joueur.setMain(main);
        assertEquals(1, joueur.getMainDuJoueur().getMainDuJoueur().size());
        assertTrue(joueur.getMainDuJoueur().getMainDuJoueur().containsAll(cards));
    }

    @Test
    void testEnleverCarte() {
        Joueur joueur = new Joueur("Test", 100);
        List<Card> cards = Arrays.asList(
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.ROI, CardColor.COEUR));
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
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.ROI, CardColor.COEUR))));

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
    void testFaireChoixWithProbabiliteEntre20et50() {
        // Arrange
        Joueur joueur = new Joueur("Test", 100);
        Deck deck = new Deck();
        MainDuCroupier croupier = new MainDuCroupier(deck);
        List<Card> cards = Arrays.asList(
                new Card(CardRank.TROIS, CardColor.PIQUE),
                new Card(CardRank.TROIS, CardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(cards);
        joueur.setMain(main);

        // Act
        int choix = joueur.faireChoix(croupier, 50, 2);

        // Assert
        assertEquals(1, choix);
    }

    @Test
    void testFaireChoixAutre() {
        // Arrange
        Joueur joueur = new Joueur("Test", 100);
        Deck deck = new Deck();
        MainDuCroupier croupier = new MainDuCroupier(deck);
        List<Card> cards = Arrays.asList(
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.ROI, CardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(cards);
        joueur.setMain(main);

        // Act
        int choix = joueur.faireChoix(croupier, 50, 1);

        // Assert
        assertTrue(choix >= 2 && choix <= 3);
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
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.ROI, CardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(cards);
        joueur.setMain(main);

        List<String> cardNames = joueur.getCardNames();
        assertEquals(2, cardNames.size());
        assertTrue(cardNames.contains("AS de PIQUE"));
        assertTrue(cardNames.contains("ROI de COEUR"));
    }
    @Test
    void testGetCardNamesWithMainVide() {
        Joueur joueur = new Joueur("Test", 100);

        List<String> cardNames = joueur.getCardNames();

        assertTrue(cardNames.isEmpty());
    }

    @Test
    void testProbabiliteDeGagnerDifferentesConditions1() {
        Joueur joueur = new Joueur("Test", 100);
        Deck deck = new Deck();

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.DAME, CardColor.COEUR));
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
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.DAME, CardColor.COEUR));
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
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.AS, CardColor.COEUR));
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
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.ROI, CardColor.COEUR));
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
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.DAME, CardColor.COEUR));
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
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.ROI, CardColor.PIQUE));
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
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.ROI, CardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        assertEquals(main, joueur.getMainDuJoueur());
    }

    @Test
    void testProbabiliteDeGagnerAvecDeuxRoi() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.ROI, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.ROI, CardColor.PIQUE));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(CardRank.ROI, CardColor.COEUR),
                new Card(CardRank.ROI, CardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(80, probabilite);
    }
    @Test
    void testProbabiliteDeGagnerAvecDeuxDames() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DAME, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DAME, CardColor.PIQUE));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(CardRank.DAME, CardColor.COEUR),
                new Card(CardRank.DAME, CardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(75, probabilite);
    }
    @Test
    void testProbabiliteDeGagnerAvecDeuxVALET() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.VALET, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.VALET, CardColor.PIQUE));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(CardRank.VALET, CardColor.COEUR),
                new Card(CardRank.VALET, CardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(70, probabilite);
    }
    @Test
    void testProbabiliteDeGagnerAvecDeuxDix() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DIX, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DIX, CardColor.PIQUE));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(CardRank.DIX, CardColor.COEUR),
                new Card(CardRank.DIX, CardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(65, probabilite);
    }
    @Test
    void testProbabiliteDeGagnerAvecDeuxNEUF() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.NEUF, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.NEUF, CardColor.PIQUE));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(CardRank.NEUF, CardColor.COEUR),
                new Card(CardRank.NEUF, CardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(60, probabilite);
    }
    @Test
    void testProbabiliteDeGagnerAvecDeuxHUIT() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.HUIT, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.HUIT, CardColor.PIQUE));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(CardRank.HUIT, CardColor.COEUR),
                new Card(CardRank.HUIT, CardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(55, probabilite);
    }
    @Test
    void testProbabiliteDeGagnerAvecDeuxSEPT() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.SEPT, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.SEPT, CardColor.PIQUE));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(CardRank.SEPT, CardColor.COEUR),
                new Card(CardRank.SEPT, CardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(50, probabilite);
    }
    @Test
    void testProbabiliteDeGagnerAvecDeuxSIX() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.SIX, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.SIX, CardColor.PIQUE));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(CardRank.SIX, CardColor.COEUR),
                new Card(CardRank.SIX, CardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(45, probabilite);
    }
    @Test
    void testProbabiliteDeGagnerAvecDeuxCINQ() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.CINQ, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.CINQ, CardColor.PIQUE));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(CardRank.CINQ, CardColor.COEUR),
                new Card(CardRank.CINQ, CardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(40, probabilite);
    }
    @Test
    void testProbabiliteDeGagnerAvecDeuxQUATRE() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.QUATRE, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.QUATRE, CardColor.PIQUE));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(CardRank.QUATRE, CardColor.COEUR),
                new Card(CardRank.QUATRE, CardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(35, probabilite);
    }
    @Test
    void testProbabiliteDeGagnerAvecDeuxTROIS() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.TROIS, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.TROIS, CardColor.PIQUE));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(CardRank.TROIS, CardColor.COEUR),
                new Card(CardRank.TROIS, CardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(30, probabilite);
    }
    @Test
    void testProbabiliteDeGagnerAvecDeuxDEUX() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DEUX, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DEUX, CardColor.PIQUE));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(CardRank.DEUX, CardColor.COEUR),
                new Card(CardRank.DEUX, CardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(20, probabilite);
    }
    

    @Test
    void testProbabiliteDeGagnerAvecTroisCartesIdentiques() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DAME, CardColor.CARREAU));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DAME, CardColor.CARREAU));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DAME, CardColor.CARREAU));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(CardRank.ROI, CardColor.COEUR),
                new Card(CardRank.DIX, CardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(35, probabilite);
    }

    @Test
    void testProbabiliteDeGagnerAvecCouleursDifferentesDansCroupier() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.SEPT, CardColor.CARREAU));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.NEUF, CardColor.TREFLE));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.CINQ, CardColor.COEUR));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(CardRank.ROI, CardColor.PIQUE),
                new Card(CardRank.DAME, CardColor.PIQUE));
        MainDuJoueur main = new MainDuJoueur(mainDuJoueur);
        joueur.setMain(main);

        int probabilite = joueur.probabiliteDeGagner(mainCroupier);
        assertEquals(50, probabilite);
    }

    @Test
    void testProbabiliteDeGagnerAvecMemeCouleurEtTroisCartesIdentiquesDansCroupier() {
        Joueur joueur = new Joueur("Test", 100);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DAME, CardColor.CARREAU));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DAME, CardColor.CARREAU));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.DAME, CardColor.CARREAU));

        List<Card> mainDuJoueur = Arrays.asList(
                new Card(CardRank.ROI, CardColor.COEUR),
                new Card(CardRank.DIX, CardColor.COEUR));
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
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.ROI, CardColor.COEUR));
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
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.DAME, CardColor.COEUR));
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
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.ROI, CardColor.PIQUE));
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
        assertEquals(80, joueur.getPileDeJetons());
        assertFalse(joueur.isTapis());
    }

    @Test
    void testMiserAvecMiseNegative() {
        Joueur joueur = new Joueur("Test", 100);
        joueur.miser(-20);
        assertEquals(0, joueur.getMise());
        assertEquals(100, joueur.getPileDeJetons());
    }

    @Test
    void testMiserAvecMontantSuperieurAJetonsRestants() {
        Joueur joueur = new Joueur("Test", 100);
        joueur.miser(150);
        assertEquals(100, joueur.getMise());
        assertTrue(joueur.isTapis());
        assertEquals(0, joueur.getPileDeJetons());
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
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.ROI, CardColor.COEUR))));

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
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.ROI, CardColor.COEUR));
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
                new Card(CardRank.ROI, CardColor.COEUR));
        MainDuJoueur main = new MainDuJoueur(cartes);
        joueur.setMain(main);
        assertFalse(joueur.getMainDuJoueur().getMainDuJoueur().isEmpty());

        joueur.enleverCarte();

        assertTrue(joueur.getMainDuJoueur().getMainDuJoueur().isEmpty());
    }



    @Test
    public void testFaireChoixSuperPouvoirWithHighJetons() {
        // Arrange
        Joueur joueur = new Joueur("Player1", 400);  // Jetons > 300
        
        // Act
        int choix = joueur.faireChoixSuperPouvoir();

        // Assert
        assertEquals(4, choix);  // Should choose superpouvoir option 4
    }

    @Test
    public void testFaireChoixSuperPouvoirWithLowJetons() {
        // Arrange
        Joueur joueur = new Joueur("Player1", 200);  // Jetons <= 300
        

        // Act
        int choix = joueur.faireChoixSuperPouvoir();

        // Assert
        assertEquals(5, choix);  // Should choose superpouvoir option 5
    }
}
