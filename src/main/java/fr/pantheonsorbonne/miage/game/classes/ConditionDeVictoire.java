package fr.pantheonsorbonne.miage.game.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.pantheonsorbonne.miage.game.classes.Card.cardColor;
import fr.pantheonsorbonne.miage.game.classes.Card.cardRank;

public class ConditionDeVictoire {

    public static CombinaisonGagnante trouverMeilleureCombinaison(MainDuCroupier mainDuCroupier, MainDuJoueur mainDuJoueur) {
        List<Card> mainConsideree = new ArrayList<>();
        mainConsideree.addAll(mainDuCroupier.getMainDuCroupier());
        mainConsideree.addAll(mainDuJoueur.getMainDuJoueur());

        CombinaisonGagnante quinteFlush = trouverQuinteFlush(mainConsideree);
        if (quinteFlush != null) {
            return quinteFlush;
        }

        CombinaisonGagnante combinaisonMultiple = trouverCombinaisonsMultiples(mainConsideree);
        if (combinaisonMultiple != null) {
            return combinaisonMultiple;
        }

        return new CombinaisonGagnante(CombinaisonGagnante.ConditionDeVictoire.CARTE_HAUTE, trouverCarteLaPlusHaute(mainConsideree));
    }

    private static CombinaisonGagnante trouverQuinteFlush(List<Card> main) {
        Map<cardColor, List<Card>> cartesParCouleur = new HashMap<>();
        for (Card carte : main) {
            if (!cartesParCouleur.containsKey(carte.getcardColor())) {
                cartesParCouleur.put(carte.getcardColor(), new ArrayList<>());
            }
            cartesParCouleur.get(carte.getcardColor()).add(carte);
        }

        for (List<Card> cartes : cartesParCouleur.values()) {
            if (cartes.size() >= 5) {
                CombinaisonGagnante quinteFlushParCouleur = trouverQuinteFlushParCouleur(cartes);
                if (quinteFlushParCouleur != null) {
                    return quinteFlushParCouleur;
                }
            }
        }

        return null;
    }

    private static CombinaisonGagnante trouverQuinteFlushParCouleur(List<Card> cartes) {
        cartes.sort((c1, c2) -> c2.getcardRank().compareTo(c1.getcardRank()));

        for (int i = 0; i <= cartes.size() - 5; i++) {
            if (sontConsecutives(cartes.subList(i, i + 5))) {
                cardRank rangLePlusHaut = cartes.get(i).getcardRank();
                return new CombinaisonGagnante(CombinaisonGagnante.ConditionDeVictoire.QUINTE_FLUSH, rangLePlusHaut);
            }
        }
        return null;
    }

    private static boolean sontConsecutives(List<Card> cartes) {
        for (int i = 0; i < cartes.size() - 1; i++) {
            if (cartes.get(i).getcardRank().ordinal() - cartes.get(i + 1).getcardRank().ordinal() != 1) {
                return false;
            }
        }
        return true;
    }

    private static CombinaisonGagnante trouverCombinaisonsMultiples(List<Card> main) {
        Map<cardRank, Integer> compteParRang = new HashMap<>();
        for (Card carte : main) {
            compteParRang.put(carte.getcardRank(), compteParRang.getOrDefault(carte.getcardRank(), 0) + 1);
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
            return new CombinaisonGagnante(CombinaisonGagnante.ConditionDeVictoire.CARRE, rangCarre);
        }
        if (rangBrelan != null && rangPaire != null) {
            return new CombinaisonGagnante(CombinaisonGagnante.ConditionDeVictoire.FULL, rangBrelan);
        }
        if (rangBrelan != null) {
            return new CombinaisonGagnante(CombinaisonGagnante.ConditionDeVictoire.BRELAN, rangBrelan);
        }
        if (rangPaire != null) {
            return new CombinaisonGagnante(CombinaisonGagnante.ConditionDeVictoire.PAIRE, rangPaire);
        }

        return null;
    }

    private static cardRank trouverCarteLaPlusHaute(List<Card> main) {
        cardRank carteLaPlusHaute = cardRank.DEUX;
        for (Card carte : main) {
            if (carte.getcardRank().compareTo(carteLaPlusHaute) > 0) {
                carteLaPlusHaute = carte.getcardRank();
            }
        }
        return carteLaPlusHaute;
    }
}
