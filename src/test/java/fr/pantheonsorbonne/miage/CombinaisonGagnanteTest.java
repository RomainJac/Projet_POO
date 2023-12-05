package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.classes.CombinaisonGagnante;
import fr.pantheonsorbonne.miage.game.classes.Card;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CombinaisonGagnanteTest {

    @Test
    void testEquals() {
        CombinaisonGagnante carteHaute1 = new CombinaisonGagnante(CombinaisonGagnante.Victoire.CARTE_HAUTE);
        CombinaisonGagnante carteHaute2 = new CombinaisonGagnante(CombinaisonGagnante.Victoire.CARTE_HAUTE);
        CombinaisonGagnante paire1 = new CombinaisonGagnante(CombinaisonGagnante.Victoire.PAIRE);
        CombinaisonGagnante paire2 = new CombinaisonGagnante(CombinaisonGagnante.Victoire.PAIRE, Card.cardRank.DEUX);

        assertTrue(carteHaute1.equals(carteHaute2));
        assertFalse(carteHaute1.equals(paire1));
        assertFalse(paire1.equals(paire2));
    }

    @Test
    void testToString() {
        CombinaisonGagnante carteHaute = new CombinaisonGagnante(CombinaisonGagnante.Victoire.CARTE_HAUTE);
        CombinaisonGagnante full = new CombinaisonGagnante(CombinaisonGagnante.Victoire.FULL, Card.cardRank.ROI);

        assertEquals("null CARTE_HAUTE", carteHaute.toString());
        assertEquals("ROI FULL", full.toString());
    }

    @Test
    void testCompareTo() {
        CombinaisonGagnante carteHaute = new CombinaisonGagnante(CombinaisonGagnante.Victoire.CARTE_HAUTE);
        CombinaisonGagnante paire = new CombinaisonGagnante(CombinaisonGagnante.Victoire.PAIRE);
        CombinaisonGagnante paireAvecHauteCarte = new CombinaisonGagnante(CombinaisonGagnante.Victoire.PAIRE, Card.cardRank.DAME);
        CombinaisonGagnante paireAvecBasseCarte = new CombinaisonGagnante(CombinaisonGagnante.Victoire.PAIRE, Card.cardRank.CINQ);

        
        assertTrue(carteHaute.compareTo(paire) < 0);

        // Test compareTo avec des combinaisons de victoire identiques, mais des rangs de carte différents
        assertTrue(paireAvecHauteCarte.compareTo(paireAvecBasseCarte) > 0);
        assertTrue(paireAvecBasseCarte.compareTo(paireAvecHauteCarte) < 0);
        

        // Test compareTo avec des combinaisons identiques
        CombinaisonGagnante autrePaireAvecCarte = new CombinaisonGagnante(CombinaisonGagnante.Victoire.PAIRE, Card.cardRank.DAME);
        assertEquals(0, paireAvecHauteCarte.compareTo(autrePaireAvecCarte));
    }
}
