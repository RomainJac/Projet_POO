package fr.pantheonsorbonne.miage.game.classes;

public class CombinaisonGagnante implements Comparable<CombinaisonGagnante> {
    private ConditionDeVictoire ConditionDeVictoire;
    private Card.cardRank cardRank;

    public CombinaisonGagnante(ConditionDeVictoire cv, Card.cardRank vc) {
        this.ConditionDeVictoire = cv;
        this.cardRank = vc;
    }

    public int compareTo(CombinaisonGagnante combinaison) {
        return ConditionDeVictoire.compareTo(combinaison.ConditionDeVictoire) != 0 ? 
            ConditionDeVictoire.compareTo(combinaison.ConditionDeVictoire) : cardRank.compareTo(combinaison.cardRank);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CombinaisonGagnante that = (CombinaisonGagnante) o;
        return ConditionDeVictoire == that.ConditionDeVictoire && cardRank == that.cardRank;
    }

    @Override
    public String toString() {
        return cardRank + " " + ConditionDeVictoire;
    }

    public enum ConditionDeVictoire {
        CARTE_HAUTE, PAIRE, DEUX_PAIRE, BRELAN, SUITE, COULEUR, FULL, CARRE, QUINTE_FLUSH,
        QUINTE_FLUSH_ROYALE;

        }
}