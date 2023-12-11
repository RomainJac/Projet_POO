package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.classes.Joueur.Joueur;
import fr.pantheonsorbonne.miage.game.classes.Joueur.MainDuJoueur;
import fr.pantheonsorbonne.miage.game.classes.Table.Card;
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
        int relanceAmount = 40;

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

        //assertEquals(initialMiseMaximale + 30, result);
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

    @Test
    public void testDistribuerGainsTapisInférieur() {
        Joueur joueur1 = new Joueur("Joueur1", 100);
        joueur1.setMise(100);
        joueur1.isTapis();

        List<Joueur> joueursGagnants = new ArrayList<>();
        joueursGagnants.add(joueur1);
        table.setMisesTotales(100);

        table.distribuerGains(joueursGagnants);

        assertEquals(200, joueur1.getPileDeJetons());
        //assertEquals(0, joueursGagnants.size());
    }

    @Test
    public void testDistribuerGainsWithTapisSupérieur() {
        Joueur joueur1 = new Joueur("Joueur1", 100);
        joueur1.setMise(100);
        joueur1.isTapis();

        List<Joueur> joueursGagnants = new ArrayList<>();
        joueursGagnants.add(joueur1);
        table.setMisesTotales(300);

        table.distribuerGains(joueursGagnants);

        assertEquals(400, joueur1.getPileDeJetons());
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

        
        //assertTrue(table.getJoueursActifs().isEmpty()); 
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
        joueur.getMainDuJoueur().tirerCarte(new Card(Card.cardRank.AS, Card.cardColor.COEUR));
        joueur.getMainDuJoueur().tirerCarte(new Card(Card.cardRank.ROI, Card.cardColor.PIQUE));

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
        new Card(Card.cardRank.AS, Card.cardColor.PIQUE),
        new Card(Card.cardRank.AS, Card.cardColor.CARREAU)
    ));
    MainDuJoueur mainJoueur2 = new MainDuJoueur(Arrays.asList(
        new Card(Card.cardRank.DEUX, Card.cardColor.CARREAU),
        new Card(Card.cardRank.TROIS, Card.cardColor.TREFLE)
    ));

    joueur1.setMainDuJoueur(mainJoueur);
    joueur2.setMainDuJoueur(mainJoueur2);
    MainDuCroupier mainCroupier = new MainDuCroupier(null);
    mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.AS, Card.cardColor.COEUR));
    mainCroupier.ajouterALaMainDuCroupierCarte(new Card(Card.cardRank.AS, Card.cardColor.TREFLE));

    table.determinerGagnant(table.getJoueursActifs());

    //assertEquals(450, joueur1.getPileDeJetons());
} 

    
}