package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.classes.CombinaisonGagnante;
import fr.pantheonsorbonne.miage.game.classes.Card;

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
}
