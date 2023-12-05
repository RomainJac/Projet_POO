package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static CombinaisonGagnante trouverQuinte(List<Card> cartes) {
        cartes.sort((c1, c2) -> c2.getCardRank().compareTo(c1.getCardRank()));
        if (sontConsecutives(cartes)){
            Map<cardColor, List<Card>> cartesParCouleur = new HashMap<>();
            for (Card carte : cartes) {
                if (!cartesParCouleur.containsKey(carte.getCardColor())) {
                    cartesParCouleur.put(carte.getCardColor(), new ArrayList<>());
                }
                cartesParCouleur.get(carte.getCardColor()).add(carte);
            }
            for (List<Card> parCouleur : cartesParCouleur.values()) {
                if (parCouleur.size() >= 5) {
                    return new CombinaisonGagnante(CombinaisonGagnante.Victoire.QUINTE_FLUSH);
                }
                else {
                    return new CombinaisonGagnante(CombinaisonGagnante.Victoire.QUINTE);
                }
            }
        }
        return null;
    }

    private static boolean sontConsecutives(List<Card> cartes) {
        for (int i = 0; i < cartes.size() - 1; i++) {
            if (cartes.get(i).getCardRank().ordinal() - cartes.get(i + 1).getCardRank().ordinal() != 1) {
                return false;
            }
        }
        return true;
    }

    private static CombinaisonGagnante trouverCombinaisonsMultiples(List<Card> main) {
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
            return new CombinaisonGagnante(CombinaisonGagnante.Victoire.CARRE);
        }
        if (rangBrelan != null && rangPaire != null) {
            return new CombinaisonGagnante(CombinaisonGagnante.Victoire.FULL);
        }
        if (rangBrelan != null) {
            return new CombinaisonGagnante(CombinaisonGagnante.Victoire.BRELAN);
        }
        if (rangPaire != null) {
            return new CombinaisonGagnante(CombinaisonGagnante.Victoire.PAIRE);
        }

        return null;
    }

    private static cardRank trouverCarteLaPlusHaute(List<Card> main) {
        cardRank carteLaPlusHaute = cardRank.DEUX;
        for (Card carte : main) {
            if (carte.getCardRank().compareTo(carteLaPlusHaute) > 0) {
                carteLaPlusHaute = carte.getCardRank();
            }
        }
        return carteLaPlusHaute;
    }

}
