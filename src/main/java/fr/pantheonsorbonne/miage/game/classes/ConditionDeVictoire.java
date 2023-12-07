package fr.pantheonsorbonne.miage.game.classes;

import java.util.*;

import fr.pantheonsorbonne.miage.game.classes.Card.cardColor;
import fr.pantheonsorbonne.miage.game.classes.Card.cardRank;

public class ConditionDeVictoire {

    public static CombinaisonGagnante trouverMeilleureCombinaison(MainDuCroupier mainDuCroupier, MainDuJoueur mainDuJoueur) {
        List<Card> mainGlobale = new ArrayList<>();
        mainGlobale.addAll(mainDuCroupier.getMainDuCroupier());
        mainGlobale.addAll(mainDuJoueur.getMainDuJoueur());
        CombinaisonGagnante combinaisonMultiple = trouverCombinaisonsMultiples(mainGlobale);
        CombinaisonGagnante quinteFlush = trouverQuinte(mainGlobale);

        if (quinteFlush != null) {
            return quinteFlush;
        }

        else if (combinaisonMultiple != null) {
            return combinaisonMultiple;
        }

        return new CombinaisonGagnante(CombinaisonGagnante.Victoire.CARTE_HAUTE, trouverCarteLaPlusHaute(mainGlobale));
    }
    public static CombinaisonGagnante trouverQuinte(List<Card> cartes) {
        Collections.sort(cartes, Comparator.comparing(Card::getCardRank));
        Map<cardColor, List<Card>> cartesParCouleur = new HashMap<>();
        for (Card carte : cartes) {
            if (!cartesParCouleur.containsKey(carte.getCardColor())) {
                cartesParCouleur.put(carte.getCardColor(), new ArrayList<>());
            }
            cartesParCouleur.get(carte.getCardColor()).add(carte);
        }
    
        for (List<Card> parCouleur : cartesParCouleur.values()) {
            if (parCouleur.size() >= 5 && sontConsecutives(parCouleur)) {
                return new CombinaisonGagnante(CombinaisonGagnante.Victoire.QUINTE_FLUSH);
            }
        }

        if (sontConsecutives(cartes)) {
            return new CombinaisonGagnante(CombinaisonGagnante.Victoire.QUINTE);
        }
        
        // Si aucune quinte flush n'est trouvée, renvoyer une quinte simple
        // change pas le code stp
        return null;
    }    
    

    public static boolean sontConsecutives(List<Card> cartes) {
        for (int i = 0; i < cartes.size() - 1; i++) {
            if (Math.abs(cartes.get(i).getCardRank().ordinal() - cartes.get(i + 1).getCardRank().ordinal()) != 1 ) {
                return false;
            }
        }
        return true;
    }

    public static CombinaisonGagnante trouverCombinaisonsMultiples(List<Card> main) {
        Map<cardRank, Integer> compteParRang = new HashMap<>();
        for (Card carte : main) {
            compteParRang.put(carte.getCardRank(), compteParRang.getOrDefault(carte.getCardRank(), 0) + 1);
        }
    
        cardRank rangCarre = null, rangBrelan = null, rangPaire = null;
        for (Map.Entry<cardRank, Integer> entry : compteParRang.entrySet()) {
            if (entry.getValue() == 4) {
                rangCarre = entry.getKey();
            } else if (entry.getValue() == 3) {
                rangBrelan = entry.getKey();
            } else if (entry.getValue() == 2) {
                rangPaire = (rangPaire == null) ? entry.getKey() : rangPaire;
            }
        }
    
        if (rangCarre != null) {
            return new CombinaisonGagnante(CombinaisonGagnante.Victoire.CARRE, rangCarre);
        }
        if (rangBrelan != null && rangPaire != null) {
            return new CombinaisonGagnante(CombinaisonGagnante.Victoire.FULL, rangBrelan);
        }
        if (rangBrelan != null) {
            return new CombinaisonGagnante(CombinaisonGagnante.Victoire.BRELAN, rangBrelan);
        }
        if (rangPaire != null) {
            return new CombinaisonGagnante(CombinaisonGagnante.Victoire.PAIRE, rangPaire);
        }
    
        return null;
    }
    

    public static cardRank trouverCarteLaPlusHaute(List<Card> main) {
        cardRank carteLaPlusHaute = cardRank.DEUX;
        for (Card carte : main) {
            if (carte.getCardRank().compareTo(carteLaPlusHaute) > 0) {
                carteLaPlusHaute = carte.getCardRank();
            }
        }
        return carteLaPlusHaute;
    }

}
