package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.classes.Cartes.*;
import fr.pantheonsorbonne.miage.game.classes.Joueur.Joueur;
import fr.pantheonsorbonne.miage.game.classes.Joueur.MainDuJoueur;
import fr.pantheonsorbonne.miage.game.classes.Superpouvoir.GestionSuperpouvoir;
import fr.pantheonsorbonne.miage.game.classes.Table.Blind;
import fr.pantheonsorbonne.miage.game.classes.Table.Deck;
import fr.pantheonsorbonne.miage.game.classes.Table.MainDuCroupier;
import fr.pantheonsorbonne.miage.game.classes.Table.TableDePoker;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableDePokerTest {
    TableDePoker table = new TableDePoker();

    @Test
    void testGererChoixSuivre() {
        Joueur joueur = new Joueur("Player1", 100);
        table.joueursActifs.add(joueur);

        int initialMisesTotales = table.getMisesTotales();
        int miseMaximale = 50;

        int result = table.gererChoix(joueur, 1, miseMaximale);

        assertEquals(initialMisesTotales + miseMaximale, result);
        assertEquals(miseMaximale, joueur.getMise());
        assertEquals(true, table.joueursActifs.contains(joueur));
    }

    @Test
    void testGererChoixSeCoucher() {
        Joueur joueur = new Joueur("Player1", 100);
        table.joueursActifs.add(joueur);

        int initialMisesTotales = table.getMisesTotales();

        int result = table.gererChoix(joueur, 2, 0);

        assertEquals(initialMisesTotales, result);
        assertTrue(table.joueursActifs.isEmpty());
    }

    @Test
    void testGererChoixRelancer() {
        Joueur joueur = new Joueur("Player1", 100);
        table.joueursActifs.add(joueur);

        int initialMisesTotales = table.getMisesTotales();
        int relanceAmount = 50;

        int result = table.gererChoix(joueur, 3, 0);

        assertEquals(initialMisesTotales + relanceAmount, result);
        assertEquals(relanceAmount, joueur.getMise());
    }

    @Test
    void testFaireRelanceSufficientFunds() {
        Joueur joueur = new Joueur("Player1", 100);
        int initialMiseMaximale = table.getMiseMaximale();

        int result = table.faireRelance(joueur, 40);

        assertEquals(initialMiseMaximale + 40, result);
        assertEquals(initialMiseMaximale + 40, joueur.getMise());
    }

    @Test
    void testFaireRelanceInsufficientFunds() {
        Joueur joueur = new Joueur("Player1", 30);
        int initialMiseMaximale = table.getMiseMaximale();

        int result = table.faireRelance(joueur, 40);

        assertEquals(initialMiseMaximale + 30, result);
        assertEquals(30, joueur.getMise());
    }

    @Test
    public void testDistribuerGainsWithWinningPlayers() {
        Joueur joueur1 = new Joueur("Joueur1", 100);
        Joueur joueur2 = new Joueur("Joueur2", 150);
        Joueur joueur3 = new Joueur("Joueur3", 200);

        List<Joueur> joueursGagnants = new ArrayList<>();
        joueursGagnants.add(joueur1);
        joueursGagnants.add(joueur2);
        table.setMisesTotales(100);

        table.distribuerGains(joueursGagnants);

        assertEquals(150, joueur1.getPileDeJetons());
        assertEquals(200, joueur2.getPileDeJetons());
        assertEquals(200, joueur3.getPileDeJetons());
    }

    // @Test
    // public void testDistribuerGainsTapisInférieur() {
    //     Joueur joueur1 = new Joueur("Joueur1", 100);
    //     joueur1.setMise(100);
    //     joueur1.isTapis();

    //     List<Joueur> joueursGagnants = new ArrayList<>();
    //     joueursGagnants.add(joueur1);
    //     table.setMisesTotales(100);

    //     table.distribuerGains(joueursGagnants);

    //     assertEquals(200, joueur1.getPileDeJetons());
    //     // assertEquals(0, joueursGagnants.size());
    // }

    // @Test
    // public void testDistribuerGainsWithTapisSupérieur() {
    //     Joueur joueur1 = new Joueur("Joueur1", 100);
    //     joueur1.setMise(100);
    //     joueur1.isTapis();

    //     List<Joueur> joueursGagnants = new ArrayList<>();
    //     joueursGagnants.add(joueur1);
    //     table.setMisesTotales(300);

    //     table.distribuerGains(joueursGagnants);

    //     assertEquals(400, joueur1.getPileDeJetons());
    // }
    @Test
    public void testDistribuerGainsWithTapisMiseSup() {
        // Arrange
        Joueur joueur1 = new Joueur("Joueur1", 100);
        Joueur joueur2 = new Joueur("Joueur2", 100);
        joueur1.miser(100);
        joueur1.isTapis();
        TableDePoker table = new TableDePoker(joueur1,joueur2);        
        table.setMisesTotales(250);
        List<Joueur> joueursGagnants = new ArrayList<>();
        joueursGagnants.add(joueur1);
        int initialMisesTotales=table.getMisesTotales();

        table.distribuerGains(joueursGagnants);
        int updatedMisesTotales=table.getMisesTotales();
        // Assert
        assertEquals(200, joueur1.getPileDeJetons());
        assertEquals(initialMisesTotales-(joueur1.getMise()*2),updatedMisesTotales);
        assertEquals(0, joueursGagnants.size());
    }

    @Test
    public void testDistribuerGainsWithTapisMiseInf() {
        // Arrange
        Joueur joueur1 = new Joueur("Joueur1", 100);
        Joueur joueur2 = new Joueur("Joueur2", 100);
        joueur1.miser(100);
        joueur1.isTapis();
        TableDePoker table = new TableDePoker(joueur1,joueur2);        
        table.setMisesTotales(100);
        List<Joueur> joueursGagnants = new ArrayList<>();
        joueursGagnants.add(joueur1);
        int initialMisesTotales=table.getMisesTotales();

        table.distribuerGains(joueursGagnants);
        int updatedMisesTotales=table.getMisesTotales();
        // Assert
        assertEquals(100, joueur1.getPileDeJetons());
        assertFalse(initialMisesTotales-(joueur1.getMise()*2)==updatedMisesTotales);
        assertFalse(0==joueursGagnants.size());
    }


    @Test
    public void testRéinitialiserTable() {
        Joueur joueur1 = new Joueur("Player1");
        Joueur joueur2 = new Joueur("Player2");
        Joueur joueur3 = new Joueur("Player3");
        List<Joueur> joueursActifs = new ArrayList<>(Arrays.asList(joueur1, joueur2, joueur3));
        table.setJoueursActifs(joueursActifs);
        table.setMisesTotales(50);
        table.setMiseMaximale(30);

        table.réinitialiserTable();

        assertFalse(table.getJoueursActifs().isEmpty());
        assertEquals(0, table.getMisesTotales());
        assertEquals(0, table.getMiseMaximale());

    }

    @Test
    public void testAfficherInfosJoueur() {
        Joueur joueur = new Joueur("TestPlayer");
        List<String> cardNames = new ArrayList<>();
        cardNames.add("Card1");
        cardNames.add("Card2");
        joueur.setMain(new MainDuJoueur(new ArrayList<>()));
        joueur.getMainDuJoueur().tirerCarte(new Card(CardRank.AS, CardColor.COEUR));
        joueur.getMainDuJoueur().tirerCarte(new Card(CardRank.ROI, CardColor.PIQUE));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        table.afficherInfosJoueur(joueur);

        System.setOut(System.out);

        String printedOutput = outputStream.toString().trim();

        String expectedOutput = "Cartes de TestPlayer : AS de COEUR ROI de PIQUE";

        assertEquals(expectedOutput, printedOutput);
    }

    @Test
    public void testEnleverJoueurSansJeton() {
        Joueur joueur1 = new Joueur("Player1", 100);
        Joueur joueur2 = new Joueur("Player2", 0);
        Joueur joueur3 = new Joueur("Player3", 50);
        TableDePoker table = new TableDePoker(joueur1, joueur2, joueur3);

        assertEquals(3, table.getJoueursActifs().size());

        table.enleverJoueurSansJeton();

        assertEquals(2, table.getJoueursActifs().size());
        assertTrue(table.getJoueursActifs().contains(joueur1));
        assertFalse(table.getJoueursActifs().contains(joueur2));
        assertTrue(table.getJoueursActifs().contains(joueur3));
    }

    @Test
    public void testDistribuerCartes() {
        Joueur joueur1 = new Joueur("Player1");
        Joueur joueur2 = new Joueur("Player2");
        Joueur joueur3 = new Joueur("Player3");
        TableDePoker table = new TableDePoker(joueur1, joueur2, joueur3);

        for (Joueur joueur : table.getJoueursActifs()) {
            assertNull(joueur.getMainDuJoueur());
        }
        table.distribuerCartes();

        for (Joueur joueur : table.getJoueursActifs()) {
            assertNotNull(joueur.getMainDuJoueur());
            assertEquals(2, joueur.getMainDuJoueur().getMainDuJoueur().size());
        }
    }

    @Test
    public void testDeterminerGagnant() {
        Joueur joueur1 = new Joueur("Player1", 150);
        Joueur joueur2 = new Joueur("Player2", 150);
        TableDePoker table = new TableDePoker(joueur1, joueur2);
        table.setMisesTotales(300);

        MainDuJoueur mainJoueur = new MainDuJoueur(Arrays.asList(
                new Card(CardRank.AS, CardColor.PIQUE),
                new Card(CardRank.AS, CardColor.CARREAU)));
        MainDuJoueur mainJoueur2 = new MainDuJoueur(Arrays.asList(
                new Card(CardRank.DEUX, CardColor.CARREAU),
                new Card(CardRank.TROIS, CardColor.TREFLE)));

        joueur1.setMainDuJoueur(mainJoueur);
        joueur2.setMainDuJoueur(mainJoueur2);
        MainDuCroupier mainCroupier = new MainDuCroupier(null);
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.AS, CardColor.COEUR));
        mainCroupier.ajouterALaMainDuCroupierCarte(new Card(CardRank.AS, CardColor.TREFLE));

        table.determinerGagnant(table.getJoueursActifs());

        assertEquals(450, joueur1.getPileDeJetons());
    }
    

    @Test
    public void testReinitialiserJoueurs() {
        Joueur joueur1 = new Joueur("Player1", 100);
        Joueur joueur2 = new Joueur("Player2", 150);
        Joueur joueur3 = new Joueur("Player3",0);
        TableDePoker table = new TableDePoker(joueur1, joueur2);

        MainDuJoueur mainJoueur = new MainDuJoueur(Arrays.asList(
                new Card(CardRank.AS,CardColor.PIQUE),
                new Card(CardRank.AS,CardColor.CARREAU)));
        joueur1.setMainDuJoueur(mainJoueur);
        joueur2.setMainDuJoueur(mainJoueur);
        assertFalse(joueur1.getMainDuJoueur().getMainDuJoueur().isEmpty());
        assertFalse(joueur1.getMainDuJoueur().getMainDuJoueur().isEmpty());

        List<Joueur> result = table.réinitialiserJoueurs();

        for (Joueur joueur : result) {
            assertEquals(0, joueur.getMise());
            assertNull(joueur.getMainDuJoueur());
        }
        assertFalse(result.contains(joueur3));
    }

     @Test
    public void testAugmenterBlinds() {
        // Arrange
        Joueur joueur1 = new Joueur("Player1", 100);
        Joueur joueur2 = new Joueur("Player2", 150);
        TableDePoker table = new TableDePoker(joueur1, joueur2);

        // Set initial blinds
        table.grosseBlind = new Blind(20, joueur1);
        table.petiteBlind = new Blind(10, joueur2);
        table.grosseBlindParDefaut = 10;

        // Act
        table.augmenterBlinds();

        // Assert
        assertEquals(30, table.grosseBlind.getValeur());
        assertEquals(15, table.petiteBlind.getValeur()); 
    }


    @Test
    public void testAfficherToutesLesMains() {
        // Arrange
        Joueur joueur1 = new Joueur("Player1", 100);
        Joueur joueur2 = new Joueur("Player2", 150);
        TableDePoker table = new TableDePoker(joueur1, joueur2);

        MainDuJoueur mainJoueur = new MainDuJoueur(Arrays.asList(
                new Card(CardRank.AS,CardColor.PIQUE),
                new Card(CardRank.AS,CardColor.CARREAU)));
        joueur1.setMainDuJoueur(mainJoueur);
        joueur2.setMainDuJoueur(mainJoueur);

        // Redirect System.out to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Act
        table.afficherToutesLesMains();

        String sortieattendu = joueur1.getNom() + " a la main suivante :\r\n" +
                                "AS de PIQUE\r\n" +
                                "AS de CARREAU\r\n" +
                                joueur2.getNom() + " a la main suivante :\r\n" +
                                "AS de PIQUE\r\n" +
                                "AS de CARREAU";
        assertNotNull(outputStream.toString().trim());
        assertEquals(sortieattendu, outputStream.toString().trim());

        // Restore System.out
        System.setOut(System.out);
    }



    @Test
void testChangerBlindsGrosseBlindNulle() {
    // Arrange
    Joueur joueur1 = new Joueur("Player1", 100);
    Joueur joueur2 = new Joueur("Player2", 100);
    TableDePoker table = new TableDePoker(joueur1, joueur2);    
    table.grosseBlindParDefaut = 10;

    // Act
    table.changerBlinds();

    Blind grosseBlind = table.getGrosseBlind();
    Blind petiteBlind = table.getPetiteBlind();
    Blind dealerBlind = table.getDealerBlind();

    assertNotNull(grosseBlind);
    assertNotNull(petiteBlind);
    assertNotNull(dealerBlind);

    assertEquals(10, grosseBlind.getValeur());
    assertEquals(5, petiteBlind.getValeur());
    assertEquals(joueur2, dealerBlind.getJoueur());
}


@Test
void testChangerBlindsWhenGrosseBlindIsNotNull() {
    // Arrange
    Joueur joueur1 = new Joueur("Player1", 100);
    Joueur joueur2 = new Joueur("Player2", 100);
    TableDePoker table = new TableDePoker(joueur1, joueur2);
    table.grosseBlindParDefaut = 10;

    table.changerBlinds();

    // Save the initial state for later comparison
    Blind initialGrosseBlind = table.getGrosseBlind();
    Blind initialPetiteBlind = table.getPetiteBlind();
    Blind initialDealerBlind = table.getDealerBlind();

    // Act
    table.changerBlinds();

    Blind updatedGrosseBlind = table.getGrosseBlind();
    Blind updatedPetiteBlind = table.getPetiteBlind();
    Blind updatedDealerBlind = table.getDealerBlind();    

    // Verify that the other blinds were not modified
    assertEquals(initialPetiteBlind, updatedPetiteBlind);
    assertEquals(initialDealerBlind, updatedDealerBlind);

    // Verify that the indices were updated
    int expectedIndexGrosseBlind = (initialGrosseBlind.getJoueur().equals(joueur1) ? 1 : 0);
    assertEquals(expectedIndexGrosseBlind, updatedGrosseBlind.getJoueur().equals(joueur1) ? 1 : 0);
}


@Test
void testDemanderPaiementBlinds() {
    // Arrange
    Joueur joueur1 = new Joueur("Player1", 100);
    Joueur joueur2 = new Joueur("Player2", 100);
    TableDePoker table = new TableDePoker(joueur1, joueur2);

    table.changerBlinds();

    // Act
    int misesTotales = table.demanderPaiementBlinds();

    // Assert
    assertEquals(15, misesTotales);

    // Verify joueur1's state
    assertEquals(90, joueur1.getPileDeJetons());  // Initial: 100 - GrosseBlind(10)
    assertEquals(10, joueur1.getMise());           // GrosseBlind

    // Verify joueur2's state
    assertEquals(95, joueur2.getPileDeJetons());  // Initial: 100 - PetiteBlind(5)
    assertEquals(5, joueur2.getMise());            // PetiteBlind
}


@Test
    void testInitialiserTour() {
        // Arrange
        Joueur joueur1 = new Joueur("Player1", 100);
        Joueur joueur2 = new Joueur("Player2", 100);
        TableDePoker table = new TableDePoker(joueur1, joueur2);

        // Act
        table.initialiserTour();

        // Verify that deck is initialized
        assertNotNull(table.deck);

        // Verify that cards are distributed to players
        for (Joueur joueur : table.getJoueursActifs()) {
            assertNotNull(joueur.getMainDuJoueur());
            assertEquals(2, joueur.getMainDuJoueur().getMainDuJoueur().size());
        }

        // Verify that blinds are changed
        Blind grosseBlind = table.getGrosseBlind();
        Blind petiteBlind = table.getPetiteBlind();
        Blind dealerBlind = table.getDealerBlind();

        assertNotNull(grosseBlind);
        assertNotNull(petiteBlind);
        assertNotNull(dealerBlind);

        assertEquals(10, grosseBlind.getValeur());
        assertEquals(5, petiteBlind.getValeur());
    }


    // @Test
    // void testGererSuperpouvoir() {
    //     // Create test objects
    //     Joueur joueur = new Joueur("TestPlayer", 1000);
    //     Deck deck = new Deck();
    //     Joueur ennemis = new Joueur("EnemyPlayer", 1000);
    //     GestionSuperpouvoir gestionSuperpouvoir = new GestionSuperpouvoir();

    //     // Test case 1: Choosing option 1 should call tirerCarteVisible method
    //     TableDePoker table = new TableDePoker(joueur);
    //     table.gererSuperpouvoir(joueur, 1, deck, ennemis);

    //     // Assert that the player's hand size has increased
    //     assertEquals(1, joueur.getMainDuJoueur().getMainDuJoueur().size());

    //     // Assert that the player's pile of chips has been reduced by 50
    //     assertEquals(950, joueur.getPileDeJetons());

    //     // Test case 2: Choosing option 2 should call tirerCarteInvisible method
    //     table.gererSuperpouvoir(joueur, 2, deck, ennemis);

    //     // Assert that the player's hand size has increased
    //     assertEquals(2, joueur.getMainDuJoueur().getMainDuJoueur().size());

    //     // Assert that the player's pile of chips has been reduced by 100
    //     assertEquals(850, joueur.getPileDeJetons());

    //     // Test case 3: Choosing option 3 should call devoilerCarte method
    //     table.gererSuperpouvoir(joueur, 4, deck, ennemis);

    //     // Assert that the enemy player's card is now visible
    //     assertTrue(ennemis.getMainDuJoueur().getMainDuJoueur().get(0).estVisible());

    //     // Assert that the player's pile of chips has been reduced by 50
    //     assertEquals(800, joueur.getPileDeJetons());

    //     // Test case 4: Choosing option 4 should call enleverCarte method
    //     table.gererSuperpouvoir(joueur, 3, deck, ennemis);

    //     // Assert that the enemy player's card has been removed
    //     assertTrue(ennemis.getMainDuJoueur().getMainDuJoueur().isEmpty());

    //     // Assert that the player's pile of chips has been reduced by 100
    //     assertEquals(700, joueur.getPileDeJetons());

    //     // Test case 5: Choosing option 5 should do nothing
    //     table.gererSuperpouvoir(joueur, 5, deck, ennemis);
    // }

    @Test
    void testGetNombreDeTours() {
        // Create a test object
        Joueur joueur1 = new Joueur("TestPlayer", 1000);
        Joueur joueur2 = new Joueur("TestPlayer", 1000);
        TableDePoker table = new TableDePoker(joueur1,joueur2);
        List<Joueur> joueursActifs = new ArrayList<>(Arrays.asList(joueur1, joueur2));
        table.setJoueursActifs(joueursActifs);

        // Assert that the initial number of turns is 0
        assertEquals(0, table.getNombreDeTours());

        table.réinitialiserTable();

        // Assert that the number of turns has been incremented accordingly
        // (Update the expected value based on the actual behavior of your code)
        assertEquals(1, table.getNombreDeTours());
    }

    // @Test
    // void testRun() {
    //     // Redirect System.out for testing output
    //     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    //     System.setOut(new PrintStream(outputStream));

    //     // Create a test object
    //     Joueur joueur = new Joueur("TestPlayer", 1000);
    //     TableDePoker table = new TableDePoker(joueur);

    //     // Call the run method
    //     table.run();

    //     // Restore the normal System.out
    //     System.setOut(System.out);
    //     assertEquals(/, outputStream.toString().trim());
    // }

}
