package fr.pantheonsorbonne.miage.game.classes.Logique;

import java.util.*;

import fr.pantheonsorbonne.miage.game.classes.Cartes.Card;
import fr.pantheonsorbonne.miage.game.classes.Cartes.CardColor;
import fr.pantheonsorbonne.miage.game.classes.Cartes.CardInverse;
import fr.pantheonsorbonne.miage.game.classes.Cartes.CardRank;
import fr.pantheonsorbonne.miage.game.classes.Joueur.MainDuJoueur;
import fr.pantheonsorbonne.miage.game.classes.Table.MainDuCroupier;

public class ConditionDeVictoire {

    public static CombinaisonGagnante trouverMeilleureCombinaison(MainDuCroupier mainDuCroupier,
            MainDuJoueur mainDuJoueur) {
        List<Card> mainGlobale = new ArrayList<>();
        for (Card card : mainDuCroupier.getMainDuCroupier()) {
            mainGlobale.add(card);
        }
        for (Card card : mainDuJoueur.getMainDuJoueur()) {
            mainGlobale.add(card);
        }

        CombinaisonGagnante quinte = trouverQuinte(mainGlobale);

        CombinaisonGagnante combinaisonMultiple = trouverCombinaisonsMultiples(mainGlobale);

        if (quinte != null)
            return quinte;

        else if (combinaisonMultiple != null) {
            return combinaisonMultiple;
        }

        return new CombinaisonGagnante(CombinaisonGagnante.Victoire.CARTE_HAUTE, trouverCarteLaPlusHaute(mainGlobale));
    }

    public static CombinaisonGagnante trouverMeilleureCombinaison(List<Card> main) {
        CombinaisonGagnante combinaisonMultiple = trouverCombinaisonsMultiples(main);
        CombinaisonGagnante quinteFlush = trouverQuinte(main);

        if (quinteFlush != null) {
            return quinteFlush;
        } else if (combinaisonMultiple != null) {
            return combinaisonMultiple;
        }

        return new CombinaisonGagnante(CombinaisonGagnante.Victoire.CARTE_HAUTE, trouverCarteLaPlusHaute(main));
    }

    public static CombinaisonGagnante trouverMeilleureCombinaison(CardColor carteInverse, MainDuCroupier mainDuCroupier,
            MainDuJoueur mainDuJoueur) {
        List<Card> mainGlobale = new ArrayList<>();
        for (Card card : mainDuCroupier.getMainDuCroupier()) {
            mainGlobale.add(card);
        }
        for (Card card : mainDuJoueur.getMainDuJoueur()) {
            mainGlobale.add(card);
        }
        if (carteInverse == null) {
            return trouverMeilleureCombinaison(mainDuCroupier, mainDuJoueur);
        }

        mainGlobale = inverserCartes(carteInverse, mainGlobale);
        return trouverMeilleureCombinaison(mainGlobale);

    }

    private static List<Card> inverserCartes(CardColor couleurInverse, List<Card> hand) {
        List<Card> listeAtout = new ArrayList<>();
        for (Card card : hand) {
            if (card.getCardColor() == couleurInverse) {
                Card invertedCard = new CardInverse(card.getCardRank().InverserOrdre(), card.getCardColor());
                listeAtout.add(invertedCard);
            } else {
                listeAtout.add(card);
            }
        }
        return listeAtout;
    }

    public static CombinaisonGagnante trouverQuinte(List<Card> cartes) {
        Collections.sort(cartes, Comparator.comparing(Card::getCardRank));
        Map<CardColor, List<Card>> cartesParCouleur = new HashMap<>();
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

        return null;
    }

    public static boolean sontConsecutives(List<Card> cartes) {
        for (int i = 0; i < cartes.size() - 1; i++) {
            if (Math.abs(cartes.get(i).getCardRank().ordinal() - cartes.get(i + 1).getCardRank().ordinal()) != 1) {
                return false;
            }
        }
        return true;
    }

    public static CombinaisonGagnante trouverCombinaisonsMultiples(List<Card> main) {
        Map<CardRank, Integer> compteParRang = new HashMap<>();
        for (Card carte : main) {
            compteParRang.put(carte.getCardRank(), compteParRang.getOrDefault(carte.getCardRank(), 0) + 1);
        }

        CardRank rangCarre = null, rangBrelan = null, rangPaire = null;
        for (Map.Entry<CardRank, Integer> entry : compteParRang.entrySet()) {
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

    public static CardRank trouverCarteLaPlusHaute(List<Card> main) {
        CardRank carteLaPlusHaute = CardRank.DEUX;
        for (Card carte : main) {
            if (carte.getCardRank().compareTo(carteLaPlusHaute) > 0) {
                carteLaPlusHaute = carte.getCardRank();
            }
        }
        return carteLaPlusHaute;
    }

}
